package com.zinedu.quiz;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

class Quiz {
    int quiz_id;
    float quiz_time;
    String question_IDs;
}
class Question{
    int question_id;
    String question_data;
    String data_media;
    String optA;
    String optA_media;
    String optB;
    String optB_media;
    String optC;
    String optC_media;
    String optD;
    String optD_media;
    String correct_ans;
    int class_name;
    String subject_id;
    String media;
}

@RestController
@CrossOrigin(origins = "*")
public class Controller {

    @RequestMapping("/")
    public Connection connector() throws Exception {

        Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/mydatabse?useSSL=false", "root", "aaaa");
        return con;
    }

    public String index() {
        return "Congratulations from BlogController.java";
    }

    @RequestMapping(value = "/ax" , method = RequestMethod.POST)
    public String getQuestionData(@RequestBody String string_data) {
        String ar[] = string_data.split("%22");
        String question_data = ar[3];
        String optA= ar[7];
        String optB = ar[11];
        String optC = ar[15];
        String optD = ar[19];
        String ques_marks = ar[23];
        String correct_ans = ar[27];
        QuestionRequest obj =new QuestionRequest(question_data,optA,optB,optC,optD,ques_marks,correct_ans);
        insertQuestionData(obj);
        System.out.println();
        return "";
    }
    @RequestMapping(value = "/az" , method = RequestMethod.POST)
    public String getQuizData(@RequestBody String string_data) {
        String arr[] = string_data.split("%22");
        String class_no = arr[3];
        String numOfQus= arr[7];
        String quiz_time = arr[11];
        String start_date = arr[15];
        String start_time = arr[19];
        String end_date = arr[23];
        String end_time = arr[27];
        QuizRequest obj = new QuizRequest(class_no,numOfQus,quiz_time,start_date,start_time,end_date,end_time);
        System.out.println(string_data);
        return string_data;

    }
    public JSONObject stringToJSON(String stringToParse)
    { JSONObject json = new JSONObject();
        try{
    JSONParser parser = new JSONParser();
    json = (JSONObject) parser.parse(stringToParse);}
    catch (Exception e) {
        e.printStackTrace();
    }
    return json;
    }
    public void insertQuestionData(QuestionRequest obj){
        int qus_marks= Integer.parseInt(obj.getCorrect_ans());

        try{
            Connection con = connector();
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("insert into question (question_data,optA,optB,optC,optD,question_marks,correct_ans) values ("+obj.getQuestion_data()+""+obj.getOptA_data()+""+obj.getOptB_data()+""+obj.getOptC_data()+""+obj.getOptD_data()+""+obj.getQuestion_marks()+""+obj.getCorrect_ans());
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return ;
    }

    public int insertQuizData(){

        try{
            Connection con = connector();


            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery("insert into quiz (quiz_time, question_IDs) values (2,)");
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }
    @RequestMapping("/fetchQuizData")
    public String fetchQuizData(@RequestParam("QuizID") int j) {
        String s = "";
        Quiz quiz_data = new Quiz();
        Question questionData=new Question();
        try {
            //Class.forName("com.mysql.jdbc.Driver")
            Connection con = connector();

//here sonoo is database name, root is username and password
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from quiz where quiz_id=" + j);
            while (rs.next()) {
                quiz_data.quiz_id = rs.getInt(1);
                quiz_data.quiz_time = rs.getFloat(2);
                quiz_data.question_IDs = rs.getString(3);
            }
            String s1=quiz_data.question_IDs;
            String arr[]=(s1.split(","));
            ArrayList<Integer>QIDs=new ArrayList<>();
            for(int i=0;i<arr.length;i++){
                int temp=Integer.parseInt(arr[i]);
                QIDs.add(temp);
                // System.out.println(QIDs.get(i));
            }
            ArrayList<Question>Question_data=new ArrayList<>();
            for(int i=0;i<QIDs.size();i++){
                questionData =fetchQuestionData(QIDs.get(i),con);
                Question_data.add(questionData);
                System.out.println(questionData.question_data);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        s = s + Integer.toString(quiz_data.quiz_id) + " " + Float.toString(quiz_data.quiz_time) + " " + quiz_data.question_IDs;
        return s;
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
                question.correct_ans = rs.getString(12);
                question.class_name = rs.getInt(13);
                question.subject_id = rs.getString(14);
            }
           // con.close();
        }catch(Exception e){ e.printStackTrace();}
        return question;
        //s=s+Integer.toString(question.question_id)+" "+(question.question_data)+" "+( question.optA)+" "+( question.optB)+" "+( question.optC)+" "+( question.optD)+" "+(question.correct_ans)+" "+Integer.toString(question.class_name)+" "+(question.subject_id)+" "+(question.media);
        //return s;
    }

}