package com.zinedu.quiz;

public class Student {
    String student_name;
    int class_name;
    int roll_no;

    public Student(String student_name, int class_name, int roll_no, String school_name) {
        this.student_name = student_name;
        this.class_name = class_name;
        this.roll_no = roll_no;
        this.school_name = school_name;
    }
    public Student(){

    }
    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public int getClass_name() {
        return class_name;
    }

    public void setClass_name(int class_name) {
        this.class_name = class_name;
    }

    public int getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(int roll_no) {
        this.roll_no = roll_no;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    String school_name;
}
