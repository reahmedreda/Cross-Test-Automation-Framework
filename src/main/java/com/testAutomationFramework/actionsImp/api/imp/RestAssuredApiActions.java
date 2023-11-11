package com.testAutomationFramework.actionsImp.api.imp;

import com.testAutomationFramework.actionsImp.api.dtos.ApiRequestDto;
import com.testAutomationFramework.actionsImp.api.dtos.ApiResponseDto;
import com.testAutomationFramework.actionsImp.api.dtos.AuthorizationType;
import com.testAutomationFramework.actionsImp.api.dtos.ResponseTypes;
import com.testAutomationFramework.actionsImp.api.interfaces.ApiActions;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class RestAssuredApiActions implements ApiActions {


     @Override
    public ApiResponseDto get(ApiRequestDto apiRequestDto, ResponseTypes responseType) {
        Response response = sendRequest(apiRequestDto);
        return createResponseDto(response, responseType);
    }

    @Override
    public ApiResponseDto post(ApiRequestDto apiRequestDto, ResponseTypes responseType) {
        Response response = sendRequest(apiRequestDto);
        return createResponseDto(response, responseType);
    }

    @Override
    public ApiResponseDto put(ApiRequestDto apiRequestDto, ResponseTypes responseType) {
        Response response = sendRequest(apiRequestDto);
        return createResponseDto(response, responseType);
    }

    @Override
    public ApiResponseDto delete(ApiRequestDto apiRequestDto, ResponseTypes responseType) {
        Response response = sendRequest(apiRequestDto);
        return createResponseDto(response, responseType);
    }


    private Response sendRequest(ApiRequestDto<?> apiRequestDto) {
        RequestSpecification requestSpecification = RestAssured.given()
                .headers(apiRequestDto.getHeaders())
                .queryParams(apiRequestDto.getParameters());
        if(apiRequestDto.getRequestBody()!=null){
            requestSpecification = requestSpecification.body(apiRequestDto.getRequestBody());
        }

        switch (apiRequestDto.getHttpMethod()) {
            case GET:
                return requestSpecification.when().get(apiRequestDto.getUrl());
            case POST:
                return requestSpecification.when().post(apiRequestDto.getUrl());
            case PUT:
                return requestSpecification.when().put(apiRequestDto.getUrl());
            case DELETE:
                return requestSpecification.when().delete(apiRequestDto.getUrl());
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + apiRequestDto.getHttpMethod());
        }
    }

    private <R> ApiResponseDto<R> createResponseDto(Response response, ResponseTypes responseType) {

       ApiResponseDto<R> apiResponseDto = new ApiResponseDto<>();
        switch (responseType) {
            case JSON:
                JSONObject jsonObject = new JSONObject(response.getBody().asString());
                apiResponseDto.setResponseBody((R) jsonObject);
                break;
            case PrettyString:
                String stringResponse = response.getBody().asPrettyString();
                apiResponseDto.setResponseBody((R) stringResponse);
                break;

            default:
                System.out.println("Unsupported responseType: " + responseType);
        }
        apiResponseDto.setStatusCode(response.getStatusCode());
        Headers headers = response.getHeaders();
        Map<String, String> headersMap = headers.asList().stream()
                .collect(Collectors.toMap(h -> h.getName(), h -> h.getValue()));
        apiResponseDto.setHeaders(headersMap);


        return apiResponseDto;
    }

    @Override
    public Map<String, String> setAuthorization(AuthorizationType authorizationType, Map values) {

         Map<String, String> headers = new HashMap<>();

            switch (authorizationType){
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
                    // Unsupported authorization type
                    throw new IllegalArgumentException("Unsupported authorization type: " + authorizationType);
            }
            return headers;
        }

}
