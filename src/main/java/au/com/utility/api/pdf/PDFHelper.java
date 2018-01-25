package au.com.utility.api.pdf;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.lowagie.text.DocumentException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.ITextUserAgent;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Base64;
import java.util.Map;

import static au.com.utility.api.utils.Constants.*;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class PDFHelper {

    public static String transformFTLtoHTML(File ftlFile, String templateName, String requestBody) throws IOException, TemplateException {
        Type mapType = new TypeToken<Map<String, String>>() {
        }.getType();
        Map<String, String> requestBodyMap = new Gson().fromJson(requestBody, mapType);
        StringWriter writer = new StringWriter();
        getFreemarkerTemplate(ftlFile, templateName).process(requestBodyMap, writer);
        writer.flush();
        return writer.toString();
    }

    private static Template getFreemarkerTemplate(File ftlFile, String templateName) throws IOException {
        Configuration config = new Configuration();
        config.setDirectoryForTemplateLoading(ftlFile);
        config.setDefaultEncoding(UTF_8);

        Template template = config.getTemplate(templateName);
        template.setEncoding(UTF_8);
        return template;
    }

    public static void generatePDFOutput(String transformedHTML, String outputPath, String template) throws IOException, DocumentException {
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(outputPath);

            ITextRenderer iTextRenderer = new ITextRenderer();
            ResourceLoaderUserAgent callback = new ResourceLoaderUserAgent(iTextRenderer.getOutputDevice(), template);
            callback.setSharedContext(iTextRenderer.getSharedContext());
            iTextRenderer.getSharedContext().setUserAgentCallback(callback);
            iTextRenderer.setDocumentFromString(transformedHTML);
            iTextRenderer.layout();
            iTextRenderer.createPDF(outputStream);
            iTextRenderer.finishPDF();
            outputStream.close();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    public static void generatePDFOutputStream(String transformedHTML, OutputStream outputStream, String template) throws IOException, DocumentException {
        OutputStream pdfOutputStream = new ByteArrayOutputStream();

        try {
            ITextRenderer iTextRenderer = new ITextRenderer();
            ResourceLoaderUserAgent callback = new ResourceLoaderUserAgent(iTextRenderer.getOutputDevice(), template);
            callback.setSharedContext(iTextRenderer.getSharedContext());
            iTextRenderer.getSharedContext().setUserAgentCallback(callback);
            iTextRenderer.setDocumentFromString(transformedHTML);
            iTextRenderer.layout();
            iTextRenderer.createPDF(pdfOutputStream);
            iTextRenderer.finishPDF();

            byte[] pdfBytes = ((ByteArrayOutputStream) pdfOutputStream).toByteArray();
            String body = Base64.getEncoder().encodeToString(pdfBytes);

            JsonObject headerJson = new JsonObject();
            addResponseHeaders(headerJson, body);
            writeToOutputStream(outputStream, body, headerJson);
        } finally {
            pdfOutputStream.close();
        }
    }

    public static class ResourceLoaderUserAgent extends ITextUserAgent {

        private String template;

        public ResourceLoaderUserAgent(ITextOutputDevice outputDevice, String templateName) {
            super(outputDevice);
            template = templateName;
        }

        protected InputStream resolveAndOpenStream(String uri) {
            InputStream inputStream = super.resolveAndOpenStream(uri);

            String fileName = "";
            String[] splitURI = uri.split(FORWARD_SLASH);
            fileName = splitURI[splitURI.length - 2] + FORWARD_SLASH + splitURI[splitURI.length - 1]; //css/filename
            String templateName = template;

            if (inputStream == null) {
                inputStream = ResourceLoaderUserAgent.class.getResourceAsStream(FORWARD_SLASH + templateName + FORWARD_SLASH + fileName);
            }
            return inputStream;
        }
    }

    public static void addResponseHeaders(JsonObject headerJson, String body) {
        headerJson.addProperty("Content-type", "application/pdf");
        headerJson.addProperty("Content-Disposition", "inline;filename=\"Confirmation.pdf\"");
        headerJson.addProperty("Content-Transfer-Encoding", "binary");
        headerJson.addProperty("Access-Control-Allow-Origin", "*");
        headerJson.addProperty("Access-Control-Allow-Methods", "PUT, POST, DELETE, OPTIONS");
        headerJson.addProperty("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        if (!isEmpty(body))
            headerJson.addProperty("Content-length", Integer.toString(body.length()));
    }

    public static void writeToOutputStream(OutputStream outputStream, String body, JsonObject headerJson) throws IOException {
        JsonObject responseJson = new JsonObject();
        if (!isEmpty(body))
            responseJson.addProperty("body", body);
        responseJson.add("headers", headerJson);
        responseJson.addProperty("statusCode", OK);

        OutputStreamWriter writer = new OutputStreamWriter(outputStream, UTF_8);
        writer.write(responseJson.toString());
        writer.close();
    }
}
