package com.testAutomationFramework.mixed;

import com.testAutomationFramework.Modules.apis.PostmanEchoEndpoint;
import com.testAutomationFramework.Modules.pom.GoogleHomePage;
import com.testAutomationFramework.TestBaseForRunWith;
import com.testAutomationFramework.actionsImp.api.dtos.ApiResponseDto;
import com.testAutomationFramework.actionsImp.api.dtos.ApiSupportedLibraries;
import com.testAutomationFramework.actionsImp.ui.dtos.UiActionsDto;
import com.testAutomationFramework.actionsImp.ui.dtos.UiSupportedLibraries;
import com.testAutomationFramework.actionsImp.ui.interfaces.BrowserActions;
import com.testAutomationFramework.utils.testNgUtilities.annotations.RunApiTestWith;
import com.testAutomationFramework.utils.testNgUtilities.annotations.RunUiTestWith;
import com.testAutomationFramework.utils.testNgUtilities.listeners.RunTestWithTransformer;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

@Listeners(RunTestWithTransformer.class)
public class MixedUiApi extends TestBaseForRunWith {

    @BeforeMethod
    public void setUp(){
        uiActionsDtos.get().browserActions.createBrowserSession(BrowserActions.BrowserTypes.CHROME);
    }

    @Test
    @RunApiTestWith(ApiSupportedLibraries.RESTASSURED)
    @RunUiTestWith(UiSupportedLibraries.PLAYWRIGHT)
    public void testApiAndUi(){
        //ui
        UiActionsDto uiActionsDto = uiActionsDtos.get();
        GoogleHomePage googleHomePage = new GoogleHomePage(uiActionsDto);
        googleHomePage.navigate();
        googleHomePage.clickLuckButton();

        //api
        PostmanEchoEndpoint postmanEchoEndpoint = new PostmanEchoEndpoint(apiActionsDtos.get());
        Map<String, String> param = new HashMap<>();
        param.put("name", "reda");
        param.put("age", "30");
        ApiResponseDto<JSONObject> responseDto = postmanEchoEndpoint.getRequestWithQueryParams(param);
        Assert.assertEquals(responseDto.getStatusCode(),200);
        Assert.assertEquals(responseDto.getResponseBody().getJSONObject("args").getString("name"),"reda");
        Assert.assertEquals(responseDto.getResponseBody().getJSONObject("args").getString("age"),"30");

    }
}
