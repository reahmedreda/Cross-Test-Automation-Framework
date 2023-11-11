package com.testAutomationFramework.actionsImp.api;

import java.util.Map;

public class ApiRequestDto {

    private String url;
    private HttpRequestType httpMethod;
    private Map<String, String> headers;
    private Map<String, String> parameters;
    private String requestBody;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpRequestType getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpRequestType httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }
}

