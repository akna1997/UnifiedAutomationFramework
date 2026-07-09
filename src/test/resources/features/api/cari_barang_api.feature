@Api
Feature: API Pencarian Barang E-Commerce

  Scenario: Sukses mencari produk spesifik via API
    Given user mengatur endpoint API pencarian
    When user mengirim GET request dengan kata kunci "phone"
    Then status code API Read harus 200
    And respon API harus menampilkan minimal 1 data produk