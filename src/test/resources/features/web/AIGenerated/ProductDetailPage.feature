Feature: Melihat Detail Produk di Halaman PDP Tokopedia

  Sebagai pengguna Tokopedia
  Saya ingin melihat thumbnail, nama, dan harga produk di halaman detail
  Agar saya bisa memutuskan pembelian

  @Web @AIGenerated
  Scenario Outline: TC-01 s/d TC-05 Elemen inti PDP tampil
    Given pengguna membuka halaman detail produk "<url_produk>"
    Then thumbnail produk harus tampil di halaman detail
    And nama produk harus tampil di halaman detail
    And harga produk harus tampil dengan format rupiah
    And nama produk harus mengandung kata "<kata_kunci>"

    Examples:
      | url_produk                                                                                                          | kata_kunci |
      | https://www.tokopedia.com/multikomputer201/mouse-wireless-logitech-m331-silent-plus-original-pengganti-m280-hitam    | Logitech   |
      | https://www.tokopedia.com/specialdealshop/logitech-m100r-mouse-black                                                 | Logitech   |

  @Web @AIGenerated
  Scenario: TC-06 Nama dan harga tetap tampil setelah klik thumbnail
    Given pengguna membuka halaman detail produk "https://www.tokopedia.com/multikomputer201/mouse-wireless-logitech-m331-silent-plus-original-pengganti-m280-hitam"
    When pengguna mengklik thumbnail produk pertama
    Then nama produk harus tampil di halaman detail
    And harga produk harus tampil dengan format rupiah
