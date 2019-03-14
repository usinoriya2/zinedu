package com.zinedu.quiz;

import java.util.List;

class Quiz {
    int quiz_id;
    int class_id;
    String valid_till;
    QuizSchedule quiz_schedule;
    float quiz_time;
    String quiz_name;
    int quiz_schedule_id;
    String quiz_description;
    List<Question> questions;

}