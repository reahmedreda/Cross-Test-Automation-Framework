package com.testAutomationFramework.actionsImp.api.dtos;

import com.testAutomationFramework.actionsImp.api.interfaces.ApiActions;

public class ApiActionsDto {
    public ApiActions apiActions;
    public ApiSupportedLibraries library;

    public ApiActionsDto(ApiActions apiActions,ApiSupportedLibraries lib){
        this.apiActions=apiActions;
        this.library=lib;
    }

}
