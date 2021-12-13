package com.example.header.config;

import java.util.HashMap;
import java.util.Map;

public class KurumService {

  public static Map<String, String> getKurumList() {
    Map<String, String> data = new HashMap<>();
    data.put("kurumAdi", "ERZURUM OLTU DEVLET HASTANESİ");
    data.put("kurumAdi", "DÜZCE ATATÜRK DEVLET HASTANESİ");
    data.put("kurumAdi", "BOLU MUDURNU İLÇE HASTANESİ");
    return data;
  }

}
