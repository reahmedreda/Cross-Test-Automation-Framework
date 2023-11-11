package com.testAutomationFramework.actionsImp.api.interfaces;

import com.testAutomationFramework.actionsImp.api.dtos.ApiRequestDto;
import com.testAutomationFramework.actionsImp.api.dtos.ApiResponseDto;
import com.testAutomationFramework.actionsImp.api.dtos.AuthorizationType;
import com.testAutomationFramework.actionsImp.api.dtos.ResponseTypes;

import java.util.Map;

public interface ApiActions <T> {

    /**
     * Perform a GET request.
     *
     * @param apiRequestDto The request details.
     * @param responseType  The type of the expected response.
     * @return ApiResponseDto containing the response details.
     */
    ApiResponseDto<T> get(ApiRequestDto<?> apiRequestDto, ResponseTypes responseType);

    /**
     * Perform a POST request.
     *
     * @param apiRequestDto The request details.
     * @param responseType  The type of the expected response.
     * @return ApiResponseDto containing the response details.
     */
    ApiResponseDto post(ApiRequestDto<?> apiRequestDto, ResponseTypes responseType);

    /**
     * Perform a PUT request.
     *
     * @param apiRequestDto The request details.
     * @param responseType  The type of the expected response.
     * @return ApiResponseDto containing the response details.
     */
    ApiResponseDto put(ApiRequestDto<?> apiRequestDto, ResponseTypes responseType);

    /**
     * Perform a DELETE request.
     *
     * @param apiRequestDto The request details.
     * @param responseType  The type of the expected response.
     * @return ApiResponseDto containing the response details.
     */
    ApiResponseDto delete(ApiRequestDto<?> apiRequestDto, ResponseTypes responseType);

    Map<String,String> setAuthorization(AuthorizationType authorizationType, Map<String,String> values);
}
