package au.com.utility.api.handler;

import au.com.utility.api.core.aws.LambdaRequest;
import au.com.utility.api.core.exception.ApplicationException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;

import static au.com.utility.api.pdf.PDFHelper.*;
import static au.com.utility.api.utils.CommonUtils.isRequestValid;
import static au.com.utility.api.utils.Constants.*;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;

public class HtmlToPdfHandler implements RequestStreamHandler {

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        LambdaLogger logger = context.getLogger();
        logger.log("Lambda invoked.");

        try {
            LambdaRequest lambdaRequest = convertInputStreamToLambdaRequest(inputStream);
            logAllRequestParams(lambdaRequest, logger);

            if (equalsIgnoreCase(OPTIONS, lambdaRequest.getHttpMethod())) {
                JsonObject headerJson = new JsonObject();
                addResponseHeaders(headerJson, "");
                writeToOutputStream(outputStream, "", headerJson);
            }

            if (isRequestValid(lambdaRequest)) {
                logger.log("Request validity check successful.");
                String template = lambdaRequest.getPathParameters().get(FTL_TEMPLATE);
                String templateName = lambdaRequest.getPathParameters().get(FTL_TEMPLATE) + FTL;
                File ftlFile = new File(HtmlToPdfHandler.class.getClassLoader().getResource(template).toURI());
                if (!ftlFile.exists()) {
                    throw new ApplicationException(INTERNAL_SERVER_ERROR, "Template file does not exist.");
                }
                String transformedHTML = transformFTLtoHTML(ftlFile, templateName, lambdaRequest.getBody());
                logger.log("Transformed HTML generated: " + transformedHTML);
                generatePDFOutputStream(transformedHTML, outputStream, template);
            }
        } catch (Exception e) {
            logger.log("Exception: " + e);
        }
    }

    private LambdaRequest convertInputStreamToLambdaRequest(InputStream inputStream) throws UnsupportedEncodingException {
        Reader reader = new InputStreamReader(inputStream, UTF_8);
        Type lambdaRequestType = new TypeToken<LambdaRequest>() {
        }.getType();
        return new Gson().fromJson(reader, lambdaRequestType);
    }

    private void logAllRequestParams(LambdaRequest lambdaRequest, LambdaLogger logger) {
        logger.log("Lambda request body: " + lambdaRequest.getBody());
        logger.log("Lambda request path parameter: " + lambdaRequest.getPathParameters().get(FTL_TEMPLATE));
        logger.log("Lambda request method: " + lambdaRequest.getHttpMethod());
    }
}