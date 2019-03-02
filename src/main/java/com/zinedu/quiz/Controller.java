package com.zinedu.quiz;

import com.google.gson.*;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;


@RestController
@CrossOrigin(origins = "*")
public class Controller {

//    @RequestMapping("/")
    public Connection connector() throws Exception {
//        System.out.println("connecting to db");
        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mydatabse?useSSL=false", "root", "aaaa");
        return con;
    }

    public String index() {
        return "Congratulations from BlogController.java";
    }

//    @RequestMapping(value = "/ax" , method = RequestMethod.POST)
//    public String getQuestionData(@RequestBody String string_data) {
//        String ar[] = string_data.split("%22");
//
//        QuestionRequest obj =new QuestionRequest(question_data,optA,optB,optC,optD,ques_marks,correct_ans);
//        insertQuestionData(obj);
//        System.out.println();
//        return "";
//    }

    class abc{ String def, g;}
    @RequestMapping(value = "/az" , method = RequestMethod.POST)
    public String getData(@RequestBody String string_data) throws Exception{
        String decoded = "";

        try {
            decoded = java.net.URLDecoder.decode(string_data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(string_data);
        System.out.println(decoded);
        JsonElement jsonElement = new JsonParser().parse(decoded);
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        System.out.println(jsonObject);
        JsonArray quizQuestions = jsonObject.getAsJsonArray("quizQuestions");
        Vector<Question> questions = new Vector<>();
        for(JsonElement quizQuestionElement : quizQuestions){
            JsonObject quizQuestion = quizQuestionElement.getAsJsonObject();
            Question question = new Question();
            question.question_data = quizQuestion.getAsJsonPrimitive("questionData").getAsString();
            question.optA = quizQuestion.getAsJsonPrimitive("optA_data").getAsString();
            question.question_marks = quizQuestion.getAsJsonPrimitive("questionMarks").getAsString();
            question.optB = quizQuestion.getAsJsonPrimitive("optB_data").getAsString();
            question.optC = quizQuestion.getAsJsonPrimitive("optC_data").getAsString();
            question.optD = quizQuestion.getAsJsonPrimitive("optD_data").getAsString();
            question.optA_media = quizQuestion.getAsJsonPrimitive("optA_media").getAsString();
            question.optB_media = quizQuestion.getAsJsonPrimitive("optB_media").getAsString();
            question.optC_media = quizQuestion.getAsJsonPrimitive("optC_media").getAsString();
            question.optD_media = quizQuestion.getAsJsonPrimitive("optD_media").getAsString();
            question.correct_ans = quizQuestion.getAsJsonPrimitive("correctAns").getAsString();
            questions.add(question);
            insertQuestionData(question);
        }

        //System.out.println(string_data);
        return string_data;
    }
    public void insertQuestionData(Question obj){
        int qus_marks= 0;
        try{
            qus_marks = Integer.parseInt(obj.getQuestion_marks());
        } catch (Exception e) {}

        try{
            Connection con = connector();
            Statement statement = con.createStatement();
            String sqlQuery = "insert into question (question_data,data_media,optA,optA_media,optB,optB_media,optC,optC_media,optD,optD_media,question_marks,correct_ans) values ("+
                    "\""+obj.getQuestion_data()+"\",'',\""+obj.getOptA()+"\",'',\""+obj.getOptB()+"\",'',\""+obj.getOptC()+"\",'',\""+obj.getOptD()+"\",'',\""+qus_marks+"\",\""+obj.getCorrect_ans() + "\")";
            System.out.println(sqlQuery);
            int result = statement.executeUpdate(sqlQuery);
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

//    public int insertQuizData(){
//
//        try{
//            Connection con = connector();
//
//
//            Statement statement = con.createStatement();
//            ResultSet result = statement.executeQuery("insert into quiz (quiz_time, question_IDs) values (2,)");
//            con.close();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 1;
//    }
    @RequestMapping("/fetchQuizData")
    public String fetchQuizData(@RequestParam("QuizID") int j) {
        String s = "";
        Quiz quiz_data = new Quiz();
        Question questionData=new Question();
        ArrayList<Question>questionDataList=new ArrayList<>();
        try {
            //Class.forName("com.mysql.jdbc.Driver")
            Connection con = connector();

//here sonoo is database name, root is username and password
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from quiz where quiz_id=" + j);
            while (rs.next()) {
                quiz_data.quiz_id = rs.getInt(1);
                quiz_data.quiz_time = rs.getFloat(2);

            }
            ResultSet quizScheduleResult = stmt.executeQuery("select * from quiz_schedule where quiz_schedule_id=" + quiz_data.quiz_id);
            QuizSchedule quizSchedule = new QuizSchedule();
            while (quizScheduleResult.next()) {
                quizSchedule.quiz_schedule_id = quizScheduleResult.getInt(1);
            }
            quiz_data.quiz_schedule = quizSchedule;
            Statement get_questions_statement = con.createStatement();
            ResultSet questionListResult = get_questions_statement.executeQuery("select question_id from quiz_question where quiz_id=" + j );
            while (questionListResult.next() ) {
                questionData = fetchQuestionData(questionListResult.getInt(1),con);
                questionDataList.add(questionData);
                System.out.println(questionData.question_data);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        quiz_data.questions = questionDataList;
        String json = new Gson().toJson(quiz_data);

        //s = s + Integer.toString(quiz_data.quiz_id) + " " + Float.toString(quiz_data.quiz_time) + " " + quiz_data.question_IDs;
        return json;
    }
    public Question fetchQuestionData(/*@RequestParam("QuestionID")*/int k,Connection con){
        //String s = "";
        Question question =new Question();
        try{
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from question where question_id=" + k);
            while(rs.next()) {
                question.question_id = rs.getInt(1);
                question.question_data = rs.getString(2);
                question.data_media = rs.getString(3);
                question.optA = rs.getString(4);
                question.optA_media= rs.getString(5);
                question.optB = rs.getString(6);
                question.optB_media = rs.getString(7);
                question.optC = rs.getString(8);
                question.optC_media = rs.getString(9);
                question.optD = rs.getString(10);
                question.optD_media = rs.getString(11);
                question.question_marks = rs.getString(12);
                question.correct_ans = rs.getString(13);
                question.class_name = rs.getInt(14);
                question.subject_id = rs.getString(15);
            }
           // con.close();
        }catch(Exception e){ e.printStackTrace();}
        return question;
        //s=s+Integer.toString(question.question_id)+" "+(question.question_data)+" "+( question.optA)+" "+( question.optB)+" "+( question.optC)+" "+( question.optD)+" "+(question.correct_ans)+" "+Integer.toString(question.class_name)+" "+(question.subject_id)+" "+(question.media);
        //return s;
    }

}