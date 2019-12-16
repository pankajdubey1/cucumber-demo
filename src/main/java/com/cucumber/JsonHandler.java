package com.cucumber;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class JsonHandler {

    /* This class contains methods for parsing the json and verify the json response */
    public static List<String> foundValues;
    public static String userdir = System.getProperty("user.dir");

    public static List<String> getValueFromJson(String field) {
        ObjectMapper mapper = new ObjectMapper();
        foundValues = new ArrayList<String>();
        try {
            JsonNode rootNode = mapper.readTree(RequestModel.httpResponse);
            foundValues = rootNode.findValuesAsText(field);
        } catch (Exception e) {
        }
        return foundValues;
    }


    public static String getValueFromJson(String JsonName, String field) {
        ObjectMapper mapper = new ObjectMapper();
        try {

            JsonNode rootNode = mapper.readTree(new FileReader(userdir + "/src/test/resources/json/" + JsonName));
            foundValues = rootNode.findValuesAsText(field);
        } catch (Exception e) {
        }
        return foundValues.get(0);
    }

    public Map getAllKeySet(String fieldtoverify) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMap = null;
        try {
            String jsonRequest = RequestModel.responseHandler.getJsonRequest();
            JsonNode rootNode = mapper.readTree(new FileReader(userdir + "/src/test/resources/json/" + jsonRequest));
            List<JsonNode> jsonToVerify = rootNode.findValues(fieldtoverify);
            ObjectMapper objectMapper = new ObjectMapper();
            jsonMap = objectMapper.readValue(jsonToVerify.get(0),
                    new TypeReference<Map<String, Object>>() {
                    });
        } catch (Exception e) {

        }
        return jsonMap;
    }

    public static boolean isFieldValuePresentInJsonArray(String json, String field, String value) {
        // Create an objectmapper
        ObjectMapper mapper = new ObjectMapper();
        boolean found = false;
        try {
            JsonNode rootNode = mapper.readTree(json);
            List<String> foundValues = rootNode.findValuesAsText(field);
            for (String listValue : foundValues) {
                if (listValue.contains(value)) {
                    found = true;
                }
            }

        } catch (JsonGenerationException e) {

        } catch (JsonMappingException e) {

        } catch (IOException e) {

        }

        // Return whether the value was found or not
        return found;
    }


    public static void countOccurranceOfFieldInResponse() throws Exception {
        Map<String, Object> jsonMap = null;
        String jsonRequest = RequestModel.responseHandler.getJsonRequest();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(new FileReader(userdir + "/src/test/resources/json/" + jsonRequest));
        List<JsonNode> jsonToVerify = rootNode.findValues("fieldoccurence");
        jsonMap = mapper.readValue(jsonToVerify.get(0),
                new TypeReference<Map<String, Object>>() {
                });
        Set keys = jsonMap.keySet();
        for (Object key : keys) {
            int len = RequestModel.httpResponse.split(String.valueOf(key)).length - 1;
            if (len != Integer.parseInt(String.valueOf(jsonMap.get(key)))) {
                throw new Exception("Count do not match\n" + "Expected->" + len + "\nActual->" + Integer.parseInt(String.valueOf(jsonMap.get(key))));
            }

        }

    }
}
