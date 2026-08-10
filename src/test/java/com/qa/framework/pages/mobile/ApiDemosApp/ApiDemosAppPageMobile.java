package com.qa.framework.pages.mobile.ApiDemosApp;

import org.openqa.selenium.WebDriver;

import com.qa.framework.pages.base.BasePage;
import com.qa.framework.pages.elements.apidemosapp.ApiDemosAppLocator;

public class ApiDemosAppPageMobile extends BasePage {
    private final ApiDemosAppLocator elements;

    public ApiDemosAppPageMobile(WebDriver driver) {
        super(driver);
        this.elements = new ApiDemosAppLocator(driver);
    }

    public void clickMenu(String menuType) {
        switch (menuType) {
            case "App":
                click(elements.buttonMenuApp);
                break;
            case "Activity":
                click(elements.buttonMenuActivity);
                break;
            case "Hello World":
                click(elements.buttonMenuHW);
                break;
            default:
                break;
        }
    }

    public String getTextApiDemos() {
        return getText(elements.textHW); 
    }

    // public enum menuType {
    //     APP, ACTIVITY, HELLOWORLD
    // }
}
