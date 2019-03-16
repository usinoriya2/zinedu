package com.zinedu.quiz;

import com.google.gson.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
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
                "jdbc:mysql://localhost:3306/zinedu?useSSL=false", "root", "aaaa");
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

    @RequestMapping(value = "/az" , method = RequestMethod.POST)
    public String postQuiz(@RequestBody String string_data) throws Exception{
        String decoded = "";

        try {
            decoded = java.net.URLDecoder.decode(string_data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Quiz quiz = new Quiz();
        System.out.println(string_data);
        System.out.println(decoded);
        JsonElement jsonElement = new JsonParser().parse(decoded.substring(0,decoded.length()-1));
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        System.out.println(jsonObject);
        JsonArray quizQuestions = jsonObject.getAsJsonArray("quizQuestions");
        JsonObject quizMetaData = jsonObject.getAsJsonObject("quizMetaData");
        quiz.quiz_name = quizMetaData.getAsJsonPrimitive("quiz_name").getAsString();
        quiz.quiz_description = quizMetaData.getAsJsonPrimitive("quiz_description").getAsString();
        quiz.quiz_time = Integer.parseInt(quizMetaData.getAsJsonPrimitive("quizTime").getAsString());
        quiz.class_id = Integer.parseInt(quizMetaData.getAsJsonPrimitive("class").getAsString());
        QuizSchedule quizSchedule = new QuizSchedule();
       // quizSchedule.start_date = quizMetaData.getAsJsonPrimitive("startDate").getAsString();
        quizSchedule.start_time = quizMetaData.getAsJsonPrimitive("startTime").getAsString();
       // quizSchedule.end_date = quizMetaData.getAsJsonPrimitive("endDate").getAsString();
       quizSchedule.end_time = quizMetaData.getAsJsonPrimitive("endTime").getAsString();
        quiz.quiz_schedule = quizSchedule;
        Vector<Question> questions = new Vector<>();
        int quiz_id=-1;
        int temp = insertQuizData(quiz);
        if(temp!=-1)quiz_id=temp;
        for(JsonElement quizQuestionElement : quizQuestions) {
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
            int temp2=insertQuestionData(question);
            int question_id=-1;
            if(temp2!=-1)question_id=temp2;
            if(temp!=-1&&temp2!=-1){
            insetQuizQuestionTable(quiz_id,question_id);}
        }

        return "";
    }
    @RequestMapping(value = "/submitQuiz" , method = RequestMethod.POST)
    public String submitQuiz(@RequestBody String string_data) throws Exception{
        String decoded = "";
        ResultList resultList = new ResultList();
        try {
            Connection con = connector();
            decoded = java.net.URLDecoder.decode(string_data);
            System.out.println(decoded);
            System.out.println(string_data);
            System.out.println(decoded);
            JsonElement jsonElement = new JsonParser().parse(decoded.substring(0,decoded.length()-1));
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            //JsonArray quizResponseArray = jsonObject.getAsJsonArray("quizResponse");
            System.out.println(jsonObject);
            Statement statement = con.createStatement();
            resultList.class_id=jsonObject.getAsJsonPrimitive("class").getAsInt();
            resultList.quiz_id=jsonObject.getAsJsonPrimitive("quiz_id").getAsInt();
            ResultSet resultSet=statement.executeQuery("select quiz_name from quiz where quiz_id="+resultList.quiz_id);
            resultList.student_name=jsonObject.getAsJsonPrimitive("student_name").getAsString();
            resultList.school_name=jsonObject.getAsJsonPrimitive("school_name").getAsString();
            resultList.roll_no=jsonObject.getAsJsonPrimitive("roll_no").getAsInt();
            while (resultSet.next()) {
                resultList.quiz_name=resultSet.getString(1);
            }
            int resultListId = insertResultList(resultList);
            insertResult(jsonObject.get("attempted_answers").getAsJsonObject(),jsonObject.get("quiz_id").getAsString(),resultListId);
        } catch (Exception e) {
            e.printStackTrace();
        }





        return string_data;
    }

    @RequestMapping(value = "/getCsv", method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
    public String generateCSV(@RequestParam int  result_list_id){
        //System.out.println(quiz_id);
        //int quiz_id = 1;
        try {
            Connection con = connector();
            Statement statement = con.createStatement();
            String query = "select q.quiz_id,question_data,attempted_option,correct_ans, question_marks, rl.result_list_id\n" +
                    "from result_list rl \n" +
                    "join result on result.result_list_id=rl.result_list_id\n" +
                    "join quiz q on q.quiz_id=rl.quiz_id\n" +
                    "join quiz_question qq on result.question_id=qq.question_id\n" +
                    "join question ques on ques.question_id=qq.question_id\n" +
                    "where rl.result_list_id="+ result_list_id+"\n"+
            "into outfile 'C:\\\\ProgramData\\\\MySQL\\\\MySQL Server 8.0\\\\Uploads\\\\" + "result.csv" + "'\n"+
            "FIELDS TERMINATED BY ',' OPTIONALLY ENCLOSED BY '\"'\n"+
            "LINES TERMINATED BY '\n'--";
            System.out.println(query);
            statement.executeQuery(query);
            byte[] encoded = Files.readAllBytes( Paths.get("C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\result.csv"));
            return new String(encoded);
        } catch (Exception e) {

            return e.getMessage();
        } finally {
            try {
                File file = new File("C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Uploads\\result.csv");
                file.delete();
            } catch(Exception E) {

            }
        }

    }

    public void insertResult(JsonObject quizResponse,String quizId, int resultListId) {
        int quiz_id=Integer.parseInt(quizId);
        System.out.println(quiz_id);
        System.out.println(quizResponse);
        try{
            Connection con = connector();
            Statement statement = con.createStatement();
            for(String quesIdStr : quizResponse.keySet()) {
                String attempted_option = quizResponse.get(quesIdStr).getAsString();

                String sqlQuery = "insert into result (quiz_id,question_id,attempted_option,result_list_id) VALUES ('"+ quiz_id+ "','" + quesIdStr+ "','"+attempted_option+"','" +resultListId+ "')" ;
                System.out.println(sqlQuery);
                int result = statement.executeUpdate(sqlQuery);
            }
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
    public int insertQuestionData(Question obj){
        int qus_marks= 0;
        int question_id =-1;
        try{
            qus_marks = Integer.parseInt(obj.getQuestion_marks());
        } catch (Exception e) {}

        try{
            Connection con = connector();
            Statement statement = con.createStatement();
            String sqlQuery = "insert into question (question_data,data_media,optA,optA_media,optB,optB_media,optC,optC_media," +
                    "optD,optD_media,question_marks,correct_ans) values ("+ "\""+obj.getQuestion_data()+"\",'',\""+obj.getOptA()
                    +"\",'',\""+obj.getOptB()+"\",'',\""+obj.getOptC()+"\",'',\""+obj.getOptD()+"\",'',\""+qus_marks+"\",\""+
                    obj.getCorrect_ans() + "\")";
            System.out.println(sqlQuery);
            System.out.println("out -> " + statement.executeUpdate(sqlQuery , 1)); //todo: fill
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            question_id= rs.getInt(1);

            //int result = statement.executeUpdate(sqlQuery);
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return question_id;
    }

    public int insertQuizData(Quiz quiz){
        int quiz_id =-1;
        try{
            Connection con = connector();

            Statement statement = con.createStatement();
            String quiz_schedule_query = "insert into quiz_schedule (start_time, end_time) values('" + quiz.quiz_schedule.start_time + "', '" + quiz.quiz_schedule.end_time + "')";
            System.out.println(quiz_schedule_query);
            System.out.println("out -> " + statement.executeUpdate(quiz_schedule_query , 1)); //todo: fill
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            int quiz_schedule_id = rs.getInt(1);
            String quiz_query = "insert into quiz (quiz_schedule_id, quiz_time, class_id,quiz_name,quiz_description) values ('"+ quiz_schedule_id+ "','"+quiz.quiz_time+"','"+quiz.class_id+"','"+ quiz.quiz_name + "','"+quiz.quiz_description+"')";
            System.out.println(quiz_query);
            System.out.println("out -> " + statement.executeUpdate(quiz_query , 1)); //todo: fill
            ResultSet rs2 = statement.getGeneratedKeys();
            rs2.next();
            quiz_id = rs2.getInt(1);
            con.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return quiz_id;
    }

    public void insetQuizQuestionTable (int quiz_id, int question_id){
        try{
            Connection con=connector();
            Statement statement = con.createStatement();
            String quizQuestionQuery = "insert into quiz_question (quiz_id, question_id)values ('"+quiz_id+"','"+question_id+"') ";
            int result = statement.executeUpdate(quizQuestionQuery);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
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
                //quiz_data.quiz_schedule = rs.getInt(3);
                quiz_data.class_id = rs.getInt(4);
                quiz_data.quiz_name = rs.getString(5);
                quiz_data.quiz_description = rs.getString(6);

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
    @RequestMapping("/fetchQuizList")
    public String fetchQuizList(@RequestParam("ClassID")int k){
        ArrayList<Quiz> quizList=new ArrayList<>();
        String json =new String();
        try {
            Connection con = connector();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from quiz join quiz_schedule on quiz.quiz_schedule_id=quiz_schedule.quiz_schedule_id where start_time<=current_timestamp() and current_timestamp()<=end_time and class_id="+k);
            while(rs.next()){
                Quiz quiz = new Quiz();
                quiz.quiz_id = rs.getInt(1);
                quiz.quiz_time= rs.getFloat(2);
                quiz.class_id = rs.getInt(4);
                quiz.quiz_name = rs.getString(5);
                quiz.valid_till = rs.getString(10);
                quizList.add(quiz);
            }
            json = new Gson().toJson(quizList);
        }
        catch(Exception e){ e.printStackTrace();}
        return json;
    }
    @RequestMapping("/fetchEditQuizList")
    public String fetchEditQuizList(){
        ArrayList<Quiz> quizList=new ArrayList<>();
        String json =new String();
        try {
            Connection con = connector();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from quiz ");
            while(rs.next()){
                Quiz quiz = new Quiz();
                quiz.quiz_id = rs.getInt(1);
                quiz.quiz_time= rs.getFloat(2);
                quiz.class_id = rs.getInt(4);
                quiz.quiz_name = rs.getString(5);
                quiz.quiz_schedule_id = rs.getInt(3);
                quiz.quiz_description = rs.getString(6);
                quizList.add(quiz);
            }
            System.out.println(quizList);
            json = new Gson().toJson(quizList);
        }
        catch(Exception e){ e.printStackTrace();}
        return json;
    }
    @RequestMapping(value = "/postStudentData" , method = RequestMethod.POST)
    public void postStudentData(@RequestBody String string_data){
        System.out.println(string_data);
        String decoded="";
        Student student= new Student();
        try{
            Connection con = connector();
            Statement statement = con.createStatement();
            decoded = java.net.URLDecoder.decode(string_data);
            JsonElement jsonElement = new JsonParser().parse(decoded.substring(0,decoded.length()-1));
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            System.out.println(jsonObject);
            student.student_name = jsonObject.getAsJsonPrimitive("student_name").getAsString();
            student.class_name = jsonObject.getAsJsonPrimitive("class").getAsInt();
            student.roll_no = jsonObject.getAsJsonPrimitive("roll_no").getAsInt();
            student.school_name = jsonObject.getAsJsonPrimitive("school_name").getAsString();
            String studentQuery = "insert into student (student_name,class_name,roll_no, school_name)values ('"+
                    student.student_name+"','"+student.class_name+"','"+student.roll_no+"','"+student.school_name+"')";
            System.out.println(studentQuery);
            int result = statement.executeUpdate(studentQuery);
        }
        catch(Exception e){e.printStackTrace();}

    }

    public int insertResultList(ResultList resultList){
        int resultListId=0;
        try{
            Connection con = connector();
            Statement statement = con.createStatement();
            String resultListQuery = "insert into result_list (student_name,class_id,roll_no, school_name,quiz_id,quiz_name)values ('"+
                    resultList.student_name+"','"+resultList.class_id+"','"+resultList.roll_no+"','"+resultList.school_name+"','"
                    +resultList.quiz_id+"','"+resultList.quiz_name+"')";

            int result = statement.executeUpdate(resultListQuery);
            System.out.println(resultListQuery);
            System.out.println("out -> " + statement.executeUpdate(resultListQuery , 1)); //todo: fill
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            resultListId = rs.getInt(1);
        }
        catch(Exception e){e.printStackTrace();}
        return resultListId;
    }
    @RequestMapping("/fetchResultList")
    public String fetchResultList(){
        ArrayList<ResultList> resultListArray=new ArrayList<>();
        String json=new String();
        try{
            Connection con = connector();
            Statement statement = con.createStatement();
            ResultSet rs= statement.executeQuery("select * from result_list");
            while(rs.next()){
                ResultList resultList =new ResultList();
                resultList.result_list_id = rs.getInt(1);
                resultList.quiz_id= rs.getInt(2);
                resultList.student_name = rs.getString(3);
                resultList.class_id = rs.getInt(4);
                resultList.school_name=rs.getString(5);
                resultList.roll_no=rs.getInt(6);
                resultList.quiz_name=rs.getString(7);
                resultListArray.add(resultList);
            }
            json = new Gson().toJson(resultListArray);
        }
        catch(Exception e){e.printStackTrace();}
        System.out.println(json);
        return json;
    }
    @RequestMapping ("/deleteQuiz")
    public void deleteQuiz(@RequestParam(name = "QuizID") int i){
        //System.out.println("delettttttttttttt " + i );
        try{
            Connection con = connector();
            Statement stmt = con .createStatement();
            stmt.executeUpdate("delete from quiz where quiz_id="+ i );
            stmt.executeUpdate("delete from quiz_schedule where quiz_id="+i);
            stmt.executeUpdate("delete from quiz_question where quiz_id="+1);
            stmt.executeUpdate("delete from result_list where quiz_id="+i);
            stmt.executeUpdate("delete from result where quiz_id="+i);

        }
        catch(Exception e){e.printStackTrace();}
    }

}
