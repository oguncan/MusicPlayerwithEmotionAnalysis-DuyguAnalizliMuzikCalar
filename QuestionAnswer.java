package com.example.joousope.bakdinle;

public class QuestionAnswer {
    private String Cevap;
    private String Metin;

    public QuestionAnswer(String cevap,String metin)
    {
        Metin=metin;
        Cevap = cevap;
    }


    public String getCevap() {
        return Cevap;
    }

    public void setCevap(String cevap) {
        Cevap = cevap;
    }

    public String getMetin() {
        return Metin;
    }

    public void setMetin(String metin) {
        Metin = metin;
    }
}
