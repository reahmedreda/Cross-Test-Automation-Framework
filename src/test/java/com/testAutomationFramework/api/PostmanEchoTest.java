package com.testAutomationFramework.api;

import com.testAutomationFramework.Modules.apis.PostmanEchoEndpoint;
import com.testAutomationFramework.TestBaseForRunWith;
import com.testAutomationFramework.actionsImp.api.dtos.ApiResponseDto;
import com.testAutomationFramework.actionsImp.api.dtos.ApiSupportedLibraries;
import com.testAutomationFramework.utils.testNgUtilities.annotations.RunApiTestWith;
import com.testAutomationFramework.utils.testNgUtilities.listeners.RunTestWithTransformer;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

@Listeners(RunTestWithTransformer.class)
public class PostmanEchoTest extends TestBaseForRunWith {


    @Test
    @RunApiTestWith(ApiSupportedLibraries.RESTTEMPLATE)
    public void testEchoEndPoint(){
        PostmanEchoEndpoint postmanEchoEndpoint = new PostmanEchoEndpoint(apiActionsDtos.get());
        Map<String, String> param = new HashMap<>();
        param.put("name", "reda");
        param.put("age", "30");
        ApiResponseDto<JSONObject> responseDto = postmanEchoEndpoint.getRequestWithQueryParams(param);
        Assert.assertEquals(responseDto.getStatusCode(),200);
        Assert.assertEquals(responseDto.getResponseBody().getJSONObject("args").getString("name"),"reda");
        Assert.assertEquals(responseDto.getResponseBody().getJSONObject("args").getString("age"),"30");

    }

    @Test
    @RunApiTestWith(ApiSupportedLibraries.RESTTEMPLATE)
    public void testBasicAuthValidCredentials(){
        PostmanEchoEndpoint postmanEchoEndpoint = new PostmanEchoEndpoint(apiActionsDtos.get());
        Map<String, String> param = new HashMap<>();
        param.put("username", "postman");
        param.put("password", "password");
        ApiResponseDto<String > responseDto = postmanEchoEndpoint.performBasicAuthRequest(param);
        Assert.assertEquals(responseDto.getStatusCode(),200);
    }

    @Test
    @RunApiTestWith(ApiSupportedLibraries.RESTASSURED)
    public void testBasicAuthInvalidCredentials(){
        PostmanEchoEndpoint postmanEchoEndpoint = new PostmanEchoEndpoint(apiActionsDtos.get());
        Map<String, String> param = new HashMap<>();
        param.put("username", "reda");
        param.put("password", "password");
        ApiResponseDto<String > responseDto = postmanEchoEndpoint.performBasicAuthRequest(param);
        Assert.assertEquals(responseDto.getStatusCode(),401);
    }
}
