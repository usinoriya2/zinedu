package com.zinedu.quiz;

public class ResultList {
    int result_list_id;
    int quiz_id;
    String quiz_name;
    public int getResult_list_id() {
        return result_list_id;
    }

    public void setResult_list_id(int result_list_id) {
        this.result_list_id = result_list_id;
    }

    String student_name;
    String school_name;

    public int getQuiz_id() {
        return quiz_id;
    }

    public String getQuiz_name() {
        return quiz_name;
    }

    public ResultList(int result_list_id, int quiz_id, String quiz_name, String student_name, String school_name, int class_id, int roll_no) {
        this.result_list_id = result_list_id;
        this.quiz_id = quiz_id;
        this.quiz_name = quiz_name;
        this.student_name = student_name;
        this.school_name = school_name;
        this.class_id = class_id;
        this.roll_no = roll_no;
    }

    public void setQuiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }

    public ResultList(){

    }
    public void setQuiz_id(int quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public int getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(int roll_no) {
        this.roll_no = roll_no;
    }

    int class_id;
    int roll_no;
}
