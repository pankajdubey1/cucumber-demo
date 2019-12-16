package com.cucumber;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/feature/", glue = { "com.cucumber" }, plugin = { "pretty" }, format = {
		"html:target/Destination" }, monochrome = true

)

public class TestSuiteRunner {

}
