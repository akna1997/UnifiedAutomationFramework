Feature: Pencarian Barang Hardware di E-Commerce

  @Web @SearchTeam
  Scenario Outline: Pengguna mencari komponen peralatan rumah tangga
    Given pengguna membuka halaman utama "Tokopedia"
    When pengguna mencari barang "<nama_barang>"
    Then sistem harus menampilkan daftar produk yang relevan
    Then searchbox harus terisi dengan text "<nama_barang>"

    Examples:
      | nama_barang              |
      | housing filter air       |
      # | selotip drat pipa        |
      # | kunci inggris            |