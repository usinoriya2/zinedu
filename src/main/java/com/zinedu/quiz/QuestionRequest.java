package com.zinedu.quiz;

public class QuestionRequest {

    private String question_data;
    private String optA_data;
    private String optB_data;
    private String optC_data;
    private String optD_data;
    private String question_marks;
    private String optA_media;
    private String optB_media;
    private String optC_media;
    private String optD_media;
    private String correct_ans;

    public String getOptA_media() {
        return optA_media;
    }

    public String getOptB_media() {
        return optB_media;
    }

    public void setOptB_media(String optB_media) {
        this.optB_media = optB_media;
    }

    public String getOptC_media() {
        return optC_media;
    }

    public void setOptC_media(String optC_media) {
        this.optC_media = optC_media;
    }

    public String getOptD_media() {
        return optD_media;
    }

    public void setOptD_media(String optD_media) {
        this.optD_media = optD_media;
    }

    public void setOptA_media(String optA_media) {
        this.optA_media = optA_media;
    }

    public QuestionRequest(String question_data, String optA_data, String optB_data,String optC_data, String optD_data,String optA_media, String optB_media, String optC_media,
                           String optD_media,
                           String question_marks,  String correct_ans) {
        this.question_data = question_data;
        this.optA_data = optA_data;
        this.optB_data = optB_data;
        this.question_marks = question_marks;
        this.optA_media = optA_media;
        this.optB_media = optB_media;
        this.optC_media = optC_media;
        this.optD_media = optD_media;
        this.correct_ans = correct_ans;
        this.optC_data = optC_data;
        this.optD_data = optD_data;
    }

    public String getQuestion_data() {
        return question_data;
    }

    public void setQuestion_data(String question_data) {
        this.question_data = question_data;
    }

    public String getOptA_data() {
        return optA_data;
    }

    public void setOptA_data(String optA_data) {
        this.optA_data = optA_data;
    }

    public String getOptB_data() {
        return optB_data;
    }

    public void setOptB_data(String optB_data) {
        this.optB_data = optB_data;
    }

    public String getOptC_data() {
        return optC_data;
    }

    public void setOptC_data(String optC_data) {
        this.optC_data = optC_data;
    }

    public String getOptD_data() {
        return optD_data;
    }

    public void setOptD_data(String optD_data) {
        this.optD_data = optD_data;
    }
    public String getQuestion_marks() {
        return question_marks;
    }

    public void setQuestion_marks(String question_marks) {
        this.question_marks = question_marks;
    }
    public void setCorrect_ans(String optB_data) {
        this.correct_ans = correct_ans;
    }

    public String getCorrect_ans() {
        return correct_ans;
    }
    public QuestionRequest() {
        System.out.println();
    }



}
