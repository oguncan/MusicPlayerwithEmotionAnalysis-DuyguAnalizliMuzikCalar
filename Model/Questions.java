package com.example.joousope.bakdinle.Model;

public class Questions {
    private String Question,AnswerA,AnswerB,AnswerC,AnswerD,AnswerE,AnswerF,CategoryId,Important;
    private String IsImageQuestion;
    public Questions(){}

    public String getAnswerE() {
        return AnswerE;
    }

    public Questions(String question, String answerA, String answerB, String answerC, String answerD, String answerE, String answerF, String categoryId, String important, String isImageQuestion) {
        Question = question;
        AnswerA = answerA;
        AnswerB = answerB;
        AnswerC = answerC;
        AnswerD = answerD;
        AnswerE = answerE;
        AnswerF = answerF;
        CategoryId = categoryId;
        Important = important;
        IsImageQuestion = isImageQuestion;
    }

    public String getImportant() {
        return Important;
    }

    public void setImportant(String important) {
        Important = important;
    }

    public void setAnswerE(String answerE) {
        AnswerE = answerE;
    }

    public String getAnswerF() {
        return AnswerF;
    }

    public void setAnswerF(String answerF) {
        AnswerF = answerF;
    }

    public String getQuestion() {
        return Question;
    }

    public void setQuestion(String question) {
        Question = question;
    }

    public String getAnswerA() {
        return AnswerA;
    }

    public void setAnswerA(String answerA) {
        AnswerA = answerA;
    }

    public String getAnswerB() {
        return AnswerB;
    }

    public void setAnswerB(String answerB) {
        AnswerB = answerB;
    }

    public String getAnswerC() {
        return AnswerC;
    }

    public void setAnswerC(String answerC) {
        AnswerC = answerC;
    }

    public String getAnswerD() {
        return AnswerD;
    }

    public void setAnswerD(String answerD) {
        AnswerD = answerD;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }

    public String getIsImageQuestion() {
        return IsImageQuestion;
    }

    public void setIsImageQuestion(String isImageQuestion) {
        IsImageQuestion = isImageQuestion;
    }
}
