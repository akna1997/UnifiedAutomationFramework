package com.qa.framework.runners;
    
    import io.cucumber.testng.AbstractTestNGCucumberTests;
    import io.cucumber.testng.CucumberOptions;
    
    @CucumberOptions(
            features = "src/test/resources/features",
            glue = "com.qa.framework.stepdefinitions",
            tags = "@Api",
            plugin = { "pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" }
    )
    public class ApiRunner extends AbstractTestNGCucumberTests {}
