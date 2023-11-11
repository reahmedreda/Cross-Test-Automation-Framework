package com.testAutomationFramework.Modules.apis;

import com.testAutomationFramework.actionsImp.api.dtos.*;
import com.testAutomationFramework.actionsImp.api.interfaces.ApiActions;

import java.util.Map;

public class PostmanEchoEndpoint {

    ApiActions apiActions;

    public PostmanEchoEndpoint(ApiActionsDto apiActionsDto){
        this.apiActions= apiActionsDto.apiActions;
    }
        private static final String POSTMAN_ECHO_BASE_URL = "https://postman-echo.com";

        public ApiResponseDto getRequestWithQueryParams(Map<String, String> queryParams) {
            ApiRequestDto<?> apiRequestDto = new ApiRequestDto<>();
            apiRequestDto.setUrl(POSTMAN_ECHO_BASE_URL + "/get");
            apiRequestDto.setHttpMethod(HttpRequestType.GET);
            apiRequestDto.setParameters(queryParams);
            return apiActions.get(apiRequestDto, ResponseTypes.JSON);
        }

        public ApiResponseDto performBasicAuthRequest(Map<String,String> credentials) {
            ApiRequestDto<?> apiRequestDto = new ApiRequestDto<>();
            apiRequestDto.setUrl(POSTMAN_ECHO_BASE_URL + "/basic-auth");
            apiRequestDto.setHttpMethod(HttpRequestType.GET);
            apiRequestDto.setHeaders(apiActions.setAuthorization(AuthorizationType.BASIC_AUTH,credentials));
            return apiActions.get(apiRequestDto, ResponseTypes.PrettyString);
        }


}
