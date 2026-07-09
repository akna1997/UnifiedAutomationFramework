Feature: Mobile Pencarian Barang Hardware di E-Commerce

  @Mobile
  Scenario: Mobile Pengguna mencari komponen peralatan rumah tangga
    Given Mobile pengguna membuka halaman utama "Tokopedia"
    When Mobile pengguna mencari barang "housing filter air 10 inch"
    Then Mobile sistem harus menampilkan daftar produk yang relevan