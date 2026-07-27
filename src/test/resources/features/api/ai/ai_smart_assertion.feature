@ai-test
Feature: Pengujian Smart Assertion menggunakan Gemini AI

  Scenario: Memvalidasi respons dinamis Chatbot Customer Service
    Given Sistem chatbot mengembalikan respons "Halo Kak! Barang yang kakak cari saat ini stoknya masih melimpah di gudang kami, silakan langsung di-checkout ya!"
    When Saya meminta Gemini memvalidasi apakah respons tersebut berarti barang "In Stock"
    Then Gemini harus menjawab "YES"