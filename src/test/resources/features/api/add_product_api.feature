@Api
Feature: API Add Barang E-Commerce

  Scenario: Sukses menambahkan produk hardware baru ke dalam sistem
    Given user mengatur base URL untuk tambah produk
    When user mengirim POST request dengan data barang baru
    Then status code API ADD harus 201
    And respon API harus mengembalikan ID produk yang baru dibuat