package com.qa.framework.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class GeminiAIUtil {

    public static String askGemini(String prompt) {

        String API_KEY = ConfigReader.getProperty("gemini.api.keys");
        if (API_KEY == null || API_KEY.trim().isEmpty()) {
            System.err.println("Have gemini empty key — skip call.");
            return null;
        }
        String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent?key=" + API_KEY;

        try {
            // Create json request
            JSONObject part = new JSONObject();
            part.put("text", prompt);

            JSONObject content = new JSONObject();                                                                                                                                                                                                                                     
            content.put("parts", new org.json.JSONArray().put(part));

            JSONObject jsonBody = new JSONObject();                                                                                                                                                                                                                                     
            jsonBody.put("contents", new org.json.JSONArray().put(content));                                                                                                                                                                                                            

            String jsonBodyString = jsonBody.toString();

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GEMINI_API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBodyString))
                    .build();

            // Hit API
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Get text response
            JSONObject jsonResponse = new JSONObject(response.body());

            // check gemini response error
            if (jsonResponse.has("error")) {
                JSONObject error = jsonResponse.getJSONObject("error");
                System.err.println("Gemini ERROR " + error.optInt("code") + ": " + error.optString("message"));
                return null;
            }
            
            // check if gemini has candidates or not 
            if (!jsonResponse.has("candidates") || jsonResponse.getJSONArray("candidates").isEmpty()) {
                String blockReason = jsonResponse.has("promptFeedback") ? jsonResponse.getJSONObject("promptFeedback").optString("blockReason", "unknown") : "candidates empty";
                System.err.println("Gemini has no response, Reason : " + blockReason);
                return null;
            }
            JSONArray candidatesResp = jsonResponse.getJSONArray("candidates");
            JSONObject firstCandidate = candidatesResp.getJSONObject(0);
            JSONObject contentResp = firstCandidate.getJSONObject("content");
            JSONArray partsResp = contentResp.getJSONArray("parts");
            String textResp = partsResp.getJSONObject(0).getString("text").trim();
            return textResp;

        } catch (Exception e) {
            System.err.println("Gemini API failed to response: " + e.getMessage());
            return null;
        }
    }
}