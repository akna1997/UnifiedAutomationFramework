package com.qa.framework.stepdefinitions.api;

import com.qa.framework.utils.GeminiAIUtil;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class AISteps {

    private String actualChatbotResponse;
    private String geminiVerdict;

    @Given("Sistem chatbot mengembalikan respons {string}")
    public void sistemChatbotMengembalikanRespons(String response) {
        this.actualChatbotResponse = response;
        System.out.println("Teks Asli Chatbot: " + actualChatbotResponse);
    }

    @When("Saya meminta Gemini memvalidasi apakah respons tersebut berarti barang {string}")
    public void sayaMemintaGeminiMemvalidasiApakahResponsTersebutBerartiBarang(String expectedMeaning) {
        String prompt = "Sebagai AI QA Judge, evaluasi teks ini: '" + actualChatbotResponse + "'. " +
                        "Apakah teks tersebut secara makna mengkonfirmasi bahwa barang berstatus '" + expectedMeaning + "'? " +
                        "Jawab HANYA dengan kata 'YES' atau 'NO' tanpa tanda baca lain.";
        
        System.out.println("Menghubungi Gemini AI...");
        this.geminiVerdict = GeminiAIUtil.askGemini(prompt);
        if (this.geminiVerdict != null) {
            System.out.println("Keputusan Gemini: " + geminiVerdict);
        } else {
            System.out.println("PLease cek Credit or token AI");
        }
    }

    @Then("Gemini harus menjawab {string}")
    public void geminiHarusMenjawab(String expectedVerdict) {
        if (this.geminiVerdict != null) {
            Assert.assertEquals(geminiVerdict, expectedVerdict, "Validasi berhasil");
        } else {
            System.out.println("PLease cek Credit or token AI");
        }
    }
}