package au.com.utility.api.utils;

import au.com.utility.api.core.aws.LambdaRequest;
import au.com.utility.api.core.exception.ApplicationException;

import static au.com.utility.api.utils.Constants.*;
import static org.apache.commons.lang3.StringUtils.equalsIgnoreCase;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class CommonUtils {

    private CommonUtils() {
    }

    public static boolean isRequestValid(LambdaRequest request) {
        if (equalsIgnoreCase(POST, request.getHttpMethod()) &&
                request.getPathParameters() != null &&
                request.getPathParameters().get(FTL_TEMPLATE) != null &&
                isNotEmpty(request.getBody())) {
            return true;
        } else {
            throw new ApplicationException(BAD_REQUEST, "Invalid request parameters. Refer API docs for valid request contract.");
        }
    }
}