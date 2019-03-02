package com.zinedu.quiz;

public class QuizRequest {
    private String class_no;
    private String numOfQus;
    private String quiz_time;
    private String start_date;
    private String start_time;
    private String end_date;
    private String end_time;
    public QuizRequest(String class_no, String numOfQus, String quiz_time, String start_date, String start_time,
                       String end_date, String end_time) {
        this.class_no = class_no;
        this.numOfQus = numOfQus;
        this.quiz_time = quiz_time;
        this.start_date = start_date;
        this.start_time = start_time;
        this.end_date = end_date;
        this.end_time = end_time;

    }
    public String getNumOfQus() {
        return numOfQus;
    }

    public void setNumOfQus(String numOfQus) {
        this.numOfQus = numOfQus;
    }

    public String getQuiz_time() {
        return quiz_time;
    }

    public void setQuiz_time(String quiz_time) {
        this.quiz_time = quiz_time;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }


    public String getClass_no() {
        return class_no;
    }



}



