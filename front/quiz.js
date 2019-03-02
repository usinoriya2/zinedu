$.ajax({
     type: "GET",
     url: 'http://localhost:8080/fetchQuizData?QuizID=1',
     success: function(response){
         alert(response);
     }
});