Feature: Mobile Api Demos Feature App

  @Mobile
  Scenario: Mobile user bisa click dan melihat menu HalloWorld
    Given Mobile user membuka halaman utama "ApiDemos"
    When Mobile user click menu "App"
    And Mobile user click menu "Activity"
    And Mobile user click menu "Hello World"
    Then Mobile sistem harus menampilkan text "Hello, World!"