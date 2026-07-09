Feature: Pencarian Barang Hardware di E-Commerce

  @Web
  Scenario Outline: Pengguna mencari komponen peralatan rumah tangga
    Given pengguna membuka halaman utama "Tokopedia"
    When pengguna mencari barang "<nama_barang>"
    Then sistem harus menampilkan daftar produk yang relevan

    Examples:
      | nama_barang              |
      | housing filter air       |
      | selotip drat pipa        |
      | kunci inggris            |