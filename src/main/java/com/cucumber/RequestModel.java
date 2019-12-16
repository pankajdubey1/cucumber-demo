package com.cucumber;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.json.simple.JSONObject;

import junit.framework.Assert;


public class RequestModel {

    public static String httpResponse = "";
    private String base_url;
    private String operation;
    private static int expected_status_code;
    private final String USER_AGENT = "Mozilla/5.0";
    public static ResponseHandler responseHandler = new ResponseHandler();
    JsonHandler jsonHandler = new JsonHandler();
    private Properties prop = new Properties();
    private static String envprop = "/properties/";

    /* This class contains methods for sending the HTTP request"*/

    public String whenISendHTTPRequest(String jsonRequest) throws Exception {
        prop.load(RequestModel.class.getResourceAsStream(envprop + "baseurl.properties"));
        responseHandler.setJsonRequest(jsonRequest);
        base_url = JsonHandler.getValueFromJson(jsonRequest, "url");
        operation = JsonHandler.getValueFromJson(jsonRequest, "operation");
        expected_status_code = Integer.parseInt(JsonHandler.getValueFromJson(jsonRequest, "status"));
        URL url = new URL(prop.getProperty(jsonRequest.replaceAll(".json", "")) + base_url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod(operation);
        switch (operation) {
            case "POST":
            case "PUT":
                conn.setDoInput(true);
                conn.setDoOutput(true);
                conn.setRequestProperty("User-Agent", USER_AGENT);
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                Map payload_json = jsonHandler.getAllKeySet("payload");
                JSONObject jsonObject = new JSONObject(payload_json);
                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
                writer.write(String.valueOf(jsonObject));
                writer.close();
            default:
                int responseCode = conn.getResponseCode();
                responseHandler.setStatusCode(responseCode);
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                httpResponse = response.toString();
        }
        return httpResponse;
    }


    public void thenIVerifyTheResponseCode() {
        int actualStatusCode = responseHandler.getStatusCode();
        assertTrue(actualStatusCode == expected_status_code);
  
    }

    public void thenIValidateFieldsInResponseOfWebservice() throws IOException {
        Map k = jsonHandler.getAllKeySet("jsontoverify");
        Set keys = k.keySet();
        for (Object key : keys) {
            assertTrue(jsonHandler.isFieldValuePresentInJsonArray(RequestModel.httpResponse, String.valueOf(key), String.valueOf(k.get(key))));
        }
    }

    public void thenICountFieldOccurrenceInResponseOfWebservice() throws Exception {
        jsonHandler.countOccurranceOfFieldInResponse();
    }
}


