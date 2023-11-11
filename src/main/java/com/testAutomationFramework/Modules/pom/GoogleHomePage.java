package com.testAutomationFramework.Modules.pom;


import com.testAutomationFramework.actionsImp.ui.interfaces.BrowserActions;
import com.testAutomationFramework.actionsImp.ui.dtos.ElementDto;
import com.testAutomationFramework.actionsImp.ui.dtos.UiActionsDto;
import com.testAutomationFramework.actionsImp.ui.interfaces.ElementActions;

public class GoogleHomePage {
    String homeUrl ="https://www.google.com/en";
    BrowserActions browser;
    ElementActions uiActions ;
    ElementDto searchInput = new ElementDto("//textarea[@name='q']");
    ElementDto searchButton= new ElementDto("//div[@class='lJ9FBc']//input[@name='btnK']");
//    ElementDto searchAssertion=new ElementDto("//span[text()='Meaning']");
    ElementDto searchResult = new ElementDto("//h3[@class='LC20lb MBeuO DKV0Md']");
    ElementDto luckButton =new ElementDto("//descendant::input[contains(@value,'Feeling Lucky')] [2]");

    public GoogleHomePage(UiActionsDto uiActionsDto){
        this.uiActions= uiActionsDto.elementActions;
        this.browser = uiActionsDto.browserActions;
    }
    public void navigate(){
        browser.navigateTo(homeUrl);

    }
    public void setSearch(String searchStatement){

        uiActions.setText(searchInput,searchStatement,true);
    }

    public void clickSearch(){
        uiActions.clickOn(searchButton);
    }

    public void clickLuckButton(){
        uiActions.clickOn(luckButton);
    }
    public String getTextFromFirstResult(){
        return uiActions.getText(searchResult);
    }
}
