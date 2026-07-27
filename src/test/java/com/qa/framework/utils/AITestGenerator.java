package com.qa.framework.utils;

import java.io.FileWriter;
import java.io.IOException;

public class AITestGenerator {

    public static void main(String[] args) {
        // 1. Tentukan User Story (Bisa juga dibaca dari file .txt atau Jira API)
        String userStory = "Sebagai pengguna, saya ingin bisa mencari produk 'Laptop Gaming' " +
                           "di kotak pencarian dan melihat daftar produk yang relevan. " +
                           "Jika saya mencari kata kunci tidak masuk akal seperti 'Xyz123', " +
                           "sistem harus menampilkan pesan 'Produk tidak ditemukan'.";

        String platform = "@Web";

        // 2. Siapkan Prompt untuk AI
        // Kita instruksikan AI agar HANYA memberikan teks Gherkin murni tanpa basa-basi Markdown
        String prompt = "Kamu adalah QA Automation Expert. Buatkan isi file .feature (Gherkin syntax) " +
                        "berdasarkan User Story berikut: '" + userStory + "'. " +
                        "dengan tag " + platform + " " +
                        "Sertakan skenario Positive dan Negative dengan model data driven. " +
                        "PENTING: Output HANYA berisi kode Gherkin murni, tanpa backticks (```), " +
                        "tanpa penjelasan, tanpa pembukaan atau penutup dan menggunakan bahasa inggris";

        System.out.println("Menghubungi Gemini untuk merancang Test Case...");

        // 3. Panggil Gemini menggunakan Utility yang sudah kita buat sebelumnya
        String generatedGherkin = GeminiAIUtil.askGemini(prompt);

        if (generatedGherkin != null && !generatedGherkin.isEmpty()) {
            // Bersihkan sisa backticks jika AI kebetulan masih membandel
            // generatedGherkin = generatedGherkin.replace("```gherkin", "").replace("```", "").trim();
            
            // 4. Simpan hasilnya langsung ke folder features project kamu!
            String filePath = "src/test/resources/features/web/ai_generated_search.feature";
            
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(generatedGherkin);
                System.out.println("BERHASIL! File .feature otomatis dibuat di: " + filePath);
                System.out.println("Cek folder features kamu sekarang!");
            } catch (IOException e) {
                System.err.println("Gagal menyimpan file: " + e.getMessage());
            }
        } else {
            System.err.println("Gagal mendapatkan respons dari Gemini.");
        }
    }
}