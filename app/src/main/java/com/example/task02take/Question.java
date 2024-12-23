package com.example.task02take;

class Question {

    private static int id;
    private int rightanswer;
    private String question;
    private String chooise1 , chooise2 ,  chooise3 , chooise4 ;

    Question() {
        id = id++;
        question = "";
        chooise1 = "";
        chooise2 = "";
        chooise3 = "";
        chooise4 = "";
        rightanswer = 0;
    }

    Question(String question, String chooise1, String chooise2, String chooise3 , String chooise4 ,int rightanswer) {
        id = id++ ;
        this.question = question;
        this.chooise1 = chooise1;
        this.chooise2 = chooise2;
        this.chooise3 = chooise3;
        this.chooise4 = chooise4;
        this.rightanswer = rightanswer;
    }

    int getId() {
        return id;
    }

    String getQuestion() {
        return question;
    }

    String getChooise1() {
        return chooise1;
    }

    String getChooise2() {
        return chooise2;
    }

    String getChooise3() {
        return chooise3;
    }
    String getChooise4() {
        return chooise4;
    }
    int getRightanswer(){
        return rightanswer;
    }

    void setQuestion(String question) {
        this.question = question;
    }

    void setChooise1(String chooise1) {
        this.chooise1 = chooise1;
    }

    void setChooise2(String chooise2) {
        this.chooise2 = chooise2;
    }

    void setChooise3(String chooise3) {
        this.chooise3 = chooise3;
    }
    void setChooise4(String chooise4){
        this.chooise4 = chooise4;
    }
    void setRightanswer (int rightanswer){
        this.rightanswer = rightanswer ;
    }

}
