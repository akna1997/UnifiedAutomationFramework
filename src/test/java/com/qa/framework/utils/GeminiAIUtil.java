package com.qa.framework.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class GeminiAIUtil {

    private static final String API_KEY = ConfigReader.getProperty("gemini.api.keys");
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent?key=" + API_KEY;

    public static String askGemini(String prompt) {
        try {
            // Membentuk JSON Body sesuai standar API Gemini
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

            // Menembak API dan mendapatkan response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Mengambil teks jawaban dari JSON response Gemini
            JSONObject jsonResponse = new JSONObject(response.body());

            // 1. Cek kalau Gemini balikin ERROR (key invalid, quota, dll)
            if (jsonResponse.has("error")) {
                JSONObject error = jsonResponse.getJSONObject("error");
                System.err.println("Gemini ERROR " + error.optInt("code") + ": " + error.optString("message"));
                return null;
            }
            
            // 2. Cek kalau prompt di-blokir safety (candidates kosong)
            if (!jsonResponse.has("candidates") || jsonResponse.getJSONArray("candidates").isEmpty()) {
                String blockReason = jsonResponse.has("promptFeedback")
                        ? jsonResponse.getJSONObject("promptFeedback").optString("blockReason", "unknown")
                        : "candidates kosong";
                System.err.println("Gemini tidak mengembalikan jawaban. Penyebab: " + blockReason);
                return null;
            }
            JSONArray candidatesResp = jsonResponse.getJSONArray("candidates");
            JSONObject firstCandidate = candidatesResp.getJSONObject(0);
            JSONObject contentResp = firstCandidate.getJSONObject("content");
            JSONArray partsResp = contentResp.getJSONArray("parts");
            String textResp = partsResp.getJSONObject(0).getString("text").trim();
            return textResp;

        } catch (Exception e) {
            System.err.println("Gagal menghubungi Gemini API: " + e.getMessage());
            return null;
        }
    }
}