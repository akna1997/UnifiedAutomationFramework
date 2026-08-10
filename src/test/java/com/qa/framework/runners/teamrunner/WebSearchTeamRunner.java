package com.qa.framework.runners.teamrunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.qa.framework.stepdefinitions",
        tags = "@Web and @SearchTeam",
        plugin = { "pretty", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm" }
)
public class WebSearchTeamRunner extends AbstractTestNGCucumberTests {}