Feature: Pencarian Barang Software di E-Commerce

  @Web
  Scenario Outline: User mencari Windows 11
    Given Tokopedia homepage terbuka
    When pengguna mencari barang di searchbox "<nama_barang>"
    Then sistem harus menampilkan software produk yang relevan

    Examples:
      | nama_barang              |
      | Windows 11               |