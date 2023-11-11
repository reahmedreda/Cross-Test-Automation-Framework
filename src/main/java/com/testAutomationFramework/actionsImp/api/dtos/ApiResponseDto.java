package com.testAutomationFramework.actionsImp.api.dtos;

import java.util.Map;

public class ApiResponseDto<R> {

    private int statusCode;
    private Map<String, String> headers;
    private R responseBody;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public R getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(R responseBody) {
        this.responseBody = responseBody;
    }

}
