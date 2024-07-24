package com.ljakovic.simplebankingsystem.account.model;

public enum ECurrency {
     EUR("EUR"),
     CAD("CAD"),
     CZK("CZK"),
     DKK("DKK"),
     HUF("HUF"),
     JPY("JPY"),
     NOK("NOK"),
     SEK("SEK"),
     CHF("CHF"),
     GBP("GBP"),
     USD("USD"),
     BAM("BAM"),
     PLN("PLN");
     
     private String value;
     
     ECurrency(String value) {
         this.value = value;
     }

     public String toString() {
         return this.value;
     }
     public static ECurrency get(String currency) {
          for(ECurrency eCurrency : ECurrency.values()) {
               if (eCurrency.toString().equals(currency)) {
                    return eCurrency;
               }
          }
          return null;
     }
}
