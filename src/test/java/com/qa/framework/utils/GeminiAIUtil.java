package com.qa.framework.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

public class GeminiAIUtil {

    private static final String API_KEY = ConfigReader.getProperty("gemini.api.keys");
    // Menggunakan model flash agar sangat cepat untuk Automation Test
    private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-flash-latest:generateContent?key=" + API_KEY;

    public static String askGemini(String prompt) {
        try {
            // Membentuk JSON Body sesuai standar API Gemini
            String jsonBody = "{\n" +
                    "  \"contents\": [{\n" +
                    "    \"parts\":[{\"text\": \"" + prompt.replace("\"", "\\\"") + "\"}]\n" +
                    "  }]\n" +
                    "}";

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(GEMINI_API_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            // Menembak API dan mendapatkan response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Mengambil teks jawaban dari JSON response Gemini
            JSONObject jsonResponse = new JSONObject(response.body());
            return jsonResponse.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text").trim();

        } catch (Exception e) {
            System.err.println("Gagal menghubungi Gemini API: " + e.getMessage());
            return null;
        }
    }
}