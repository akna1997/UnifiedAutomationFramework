package com.qa.framework.stepdefinitions.api;

import com.qa.framework.utils.GeminiAIUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class AISteps {

    private String actualChatbotResponse;
    private String geminiVerdict;

    @Given("Sistem chatbot mengembalikan respons {string}")
    public void sistemChatbotMengembalikanRespons(String response) {
        // Di dunia nyata, ini adalah hasil dari response API.getBody() atau getText() dari UI Mobile/Web
        this.actualChatbotResponse = response;
        System.out.println("Teks Asli Chatbot: " + actualChatbotResponse);
    }

    @When("Saya meminta Gemini memvalidasi apakah respons tersebut berarti barang {string}")
    public void sayaMemintaGeminiMemvalidasiApakahResponsTersebutBerartiBarang(String expectedMeaning) {
        // Ini adalah PROMPT ENGINEERING untuk QA
        String prompt = "Sebagai AI QA Judge, evaluasi teks ini: '" + actualChatbotResponse + "'. " +
                        "Apakah teks tersebut secara makna mengkonfirmasi bahwa barang berstatus '" + expectedMeaning + "'? " +
                        "Jawab HANYA dengan kata 'YES' atau 'NO' tanpa tanda baca lain.";
        
        System.out.println("Menghubungi Gemini AI...");
        this.geminiVerdict = GeminiAIUtil.askGemini(prompt);
        System.out.println("Keputusan Gemini: " + geminiVerdict);
    }

    @Then("Gemini harus menjawab {string}")
    public void geminiHarusMenjawab(String expectedVerdict) {
        // Smart Assertion! Kita tidak assert teks asli, tapi kita assert keputusan juri (Gemini)
        Assert.assertEquals("Validasi gagal! Teks tidak sesuai dengan makna yang diharapkan", 
                            expectedVerdict, geminiVerdict);
    }
}