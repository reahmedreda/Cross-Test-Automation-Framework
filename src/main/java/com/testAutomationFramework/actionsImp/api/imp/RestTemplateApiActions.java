package com.testAutomationFramework.actionsImp.api.imp;

import com.testAutomationFramework.actionsImp.api.dtos.ApiRequestDto;
import com.testAutomationFramework.actionsImp.api.dtos.ApiResponseDto;
import com.testAutomationFramework.actionsImp.api.dtos.AuthorizationType;
import com.testAutomationFramework.actionsImp.api.dtos.ResponseTypes;
import com.testAutomationFramework.actionsImp.api.interfaces.ApiActions;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RestTemplateApiActions implements ApiActions {

    private final RestTemplate restTemplate;

    public RestTemplateApiActions() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public ApiResponseDto get(ApiRequestDto apiRequestDto, ResponseTypes responseType) {
        ResponseEntity<String> responseEntity = sendRequest(apiRequestDto, responseType);
        return createResponseDto(responseEntity, responseType);
    }

    @Override
    public ApiResponseDto post(ApiRequestDto apiRequestDto, ResponseTypes responseType) {
        ResponseEntity<String> responseEntity = sendRequest(apiRequestDto, responseType);
        return createResponseDto(responseEntity, responseType);
    }

    @Override
    public ApiResponseDto put(ApiRequestDto apiRequestDto, ResponseTypes responseType) {
        ResponseEntity<ResponseTypes> responseEntity = sendRequest(apiRequestDto, responseType);
        return createResponseDto(responseEntity, responseType);
    }

    @Override
    public ApiResponseDto delete(ApiRequestDto apiRequestDto, ResponseTypes responseType) {
        ResponseEntity<String> responseEntity = sendRequest(apiRequestDto, responseType);
        return createResponseDto(responseEntity, responseType);
    }

    private <R> ResponseEntity<R> sendRequest(ApiRequestDto<?> apiRequestDto, ResponseTypes responseType) {
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(apiRequestDto.getHeadersAsMapOfLists());

        HttpEntity<?> httpEntity = new HttpEntity<>(apiRequestDto.getRequestBody(), headers);

        URI url = buildUriWithParams(apiRequestDto.getUrl(), apiRequestDto.getParameters());
        HttpMethod httpMethod = HttpMethod.valueOf(apiRequestDto.getHttpMethod().name());
        return (ResponseEntity<R>) restTemplate.exchange(url, httpMethod, httpEntity, String.class);


    }

    private <R> ApiResponseDto<R> createResponseDto(ResponseEntity<R> responseEntity, ResponseTypes responseType) {
        ApiResponseDto<R> apiResponseDto = new ApiResponseDto<>();

        if(responseEntity.getBody()!=null){
        switch (responseType) {
            case JSON:
                JSONObject jsonObject = new JSONObject(responseEntity.getBody().toString());
                apiResponseDto.setResponseBody((R) jsonObject);
                break;
            case PrettyString:
                String stringResponse = responseEntity.getBody().toString();
                apiResponseDto.setResponseBody((R) stringResponse);
                break;
            default:
                System.out.println("Unsupported responseType: " + responseType);
        }
}
        apiResponseDto.setStatusCode(responseEntity.getStatusCodeValue());
        HttpHeaders headers = responseEntity.getHeaders();
        Map<String, String> headersMap = headers.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().get(0)));
        apiResponseDto.setHeaders(headersMap);

        return apiResponseDto;
    }

    public static URI buildUriWithParams(String baseUrl, Map<String, String> queryParams) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl);

        // Add query parameters from the map
        queryParams.forEach(builder::queryParam);

        return builder.build().toUri();
    }
    @Override
    public Map<String, String> setAuthorization(AuthorizationType authorizationType, Map values) {
        Map<String, String> headers = new HashMap<>();

        switch (authorizationType) {
            case BASIC_AUTH:
                String username = (String) values.get("username");
                String password = (String) values.get("password");
                String base64Credentials = Base64.getEncoder().encodeToString((username + ":" + password).getBytes());
                headers.put("Authorization", "Basic " + base64Credentials);
                break;
            case BEARER_TOKEN:
                String token = (String) values.get("token");
                headers.put("Authorization", "Bearer " + token);
                break;
            default:
                throw new IllegalArgumentException("Unsupported authorization type: " + authorizationType);
        }

        return headers;
    }
}

