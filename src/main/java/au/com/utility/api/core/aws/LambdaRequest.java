package au.com.utility.api.core.aws;

import java.util.Map;

/**
 * This class specifies how API Gateway maps the client request to the input parameter of the  Lambda function
 */

public class LambdaRequest {

    private String path;
    private String httpMethod;
    private Map<String, String> headers;
    private Map<String, String> queryStringParameters;
    private Map<String, String> pathParameters;
    private String body;

    public String getPath() {
        return path;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getQueryStringParameters() {
        return queryStringParameters;
    }

    public Map<String, String> getPathParameters() {
        return pathParameters;
    }

    public String getParameter(String paramName) {
        return getQueryStringParameters().get(paramName);
    }

    public String getHeader(String name) {
        return getHeaders().get(name);
    }

    public String getBody() {
        return body;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setQueryStringParameters(Map<String, String> queryStringParameters) {
        this.queryStringParameters = queryStringParameters;
    }

    public void setPathParameters(Map<String, String> pathParameters) {
        this.pathParameters = pathParameters;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("LambdaRequest{");
        sb.append("path='").append(path).append('\'');
        sb.append(", httpMethod='").append(httpMethod).append('\'');
        sb.append(", headers=").append(headers);
        sb.append(", queryStringParameters=").append(queryStringParameters);
        sb.append(", pathParameters=").append(pathParameters);
        sb.append(", body='").append(body).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
