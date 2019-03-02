package com.zinedu.quiz;

class Question{
    int question_id;
    String question_data;
    String data_media;
    String optA;
    String optA_media;

    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_data() {
        return question_data;
    }

    public Question() {
    }

    public void setQuestion_data(String question_data) {
        this.question_data = question_data;
    }

    public String getData_media() {
        return data_media;
    }

    public void setData_media(String data_media) {
        this.data_media = data_media;
    }

    public String getOptA() {
        return optA;
    }

    public void setOptA(String optA) {
        this.optA = optA;
    }

    public String getOptA_media() {
        return optA_media;
    }

    public void setOptA_media(String optA_media) {
        this.optA_media = optA_media;
    }

    public String getOptB() {
        return optB;
    }

    public void setOptB(String optB) {
        this.optB = optB;
    }

    public String getOptB_media() {
        return optB_media;
    }

    public void setOptB_media(String optB_media) {
        this.optB_media = optB_media;
    }

    public String getOptC() {
        return optC;
    }

    public void setOptC(String optC) {
        this.optC = optC;
    }

    public String getOptC_media() {
        return optC_media;
    }

    public void setOptC_media(String optC_media) {
        this.optC_media = optC_media;
    }

    public String getOptD() {
        return optD;
    }

    public void setOptD(String optD) {
        this.optD = optD;
    }

    public String getOptD_media() {
        return optD_media;
    }

    public void setOptD_media(String optD_media) {
        this.optD_media = optD_media;
    }

    public String getQuestion_marks() {
        return question_marks;
    }

    public void setQuestion_marks(String question_marks) {
        this.question_marks = question_marks;
    }

    public String getCorrect_ans() {
        return correct_ans;
    }

    public void setCorrect_ans(String correct_ans) {
        this.correct_ans = correct_ans;
    }

    public int getClass_name() {
        return class_name;
    }

    public void setClass_name(int class_name) {
        this.class_name = class_name;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    String optB;
    String optB_media;
    String optC;
    String optC_media;
    String optD;
    String optD_media;

    public Question(int question_id, String question_data, String data_media, String optA, String optA_media, String optB, String optB_media, String optC, String optC_media, String optD, String optD_media, String question_marks, String correct_ans, int class_name, String subject_id, String media) {
        this.question_id = question_id;
        this.question_data = question_data;
        this.data_media = data_media;
        this.optA = optA;
        this.optA_media = optA_media;
        this.optB = optB;
        this.optB_media = optB_media;
        this.optC = optC;
        this.optC_media = optC_media;
        this.optD = optD;
        this.optD_media = optD_media;
        this.question_marks = question_marks;
        this.correct_ans = correct_ans;
        this.class_name = class_name;
        this.subject_id = subject_id;
        this.media = media;
    }

    String question_marks;
    String correct_ans;
    int class_name;
    String subject_id;
    String media;
}
