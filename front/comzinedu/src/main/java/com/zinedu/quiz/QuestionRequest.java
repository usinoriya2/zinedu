package com.zinedu.quiz;

public class QuestionRequest {

    private String question_data;
    private String optA_data;
    private String optB_data;
    private String    question_marks;

    public QuestionRequest(String question_data, String optA_data, String optB_data, String optC_data, String optD_data, String question_marks, String correct_ans) {
        this.question_data = question_data;
        this.optA_data = optA_data;
        this.optB_data = optB_data;
        this.question_marks = question_marks;
        this.correct_ans = correct_ans;
        this.optC_data = optC_data;
        this.optD_data = optD_data;
    }

    private String correct_ans;

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

    private String optC_data;
    private String optD_data;

}
