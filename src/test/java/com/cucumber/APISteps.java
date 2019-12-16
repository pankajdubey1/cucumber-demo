package com.cucumber;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class APISteps {

    RequestModel requestModel=new RequestModel();


    @When("^I send http request using json ([^\\\"]*)")
    public void iSendHttpRequestUsingJsonJsonRequest(String jsonRequest) throws Exception {
        // Write code here that turns the phrase above into concrete actions
        requestModel.whenISendHTTPRequest(jsonRequest);
    }

    @Then("^I verify the response code")
    public void iVerifyTheResponseCode() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        requestModel.thenIVerifyTheResponseCode();
    }

    @And("^I validate fields in response of webservice$")
    public void iValidateFieldsInResponseOfWebservice() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        requestModel.thenIValidateFieldsInResponseOfWebservice();
    }


    @And("^I count field occurrence in response of webservice$")
    public void iCountFieldOccurrenceInResponseOfWebservice() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        requestModel.thenICountFieldOccurrenceInResponseOfWebservice();
    }
}
