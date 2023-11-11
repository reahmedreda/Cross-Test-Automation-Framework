package com.testAutomationFramework.actionsImp.api.dtos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiRequestDto<T> {

    private String url;
    private HttpRequestType httpMethod;
    private Map<String, String> headers;
    private Map<String, String> parameters;
    private T requestBody;



    private HttpRequestType requestType;

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

    public Map<String, ?> getHeaders() {
        if(headers==null){
            return new HashMap<>();
        }
        return headers;
    }
    public Map<String, List<String>> getHeadersAsMapOfLists() {
        if(headers==null){
            return new HashMap<>();
        }
        return headers.entrySet().stream()
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));

    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getParameters() {
        if(parameters==null){
            return new HashMap<>();
        }
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public T getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(T requestBody) {
        this.requestBody = requestBody;
    }

    public HttpRequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(HttpRequestType requestType) {
        this.requestType = requestType;
    }
}

