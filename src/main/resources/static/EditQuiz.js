quizData={};
function callFetchQuizData(){
	getQuizData(1);
}
function getQuizData(i){
	console.log("load");
	$.ajax({
		url: "http://localhost:8080/fetchQuizData",
	  type: "get", //send it through get method
	  data: {
	  			QuizID:i,
	  		},
	  success: function(response) {
	  	//console.log(response);
	  	loadQuizData(JSON.parse(response));

	  },
	  error: function(xhr) {
	  	console.log(xhr);
	  }
	});	
}

function loadQuizData(quizData1){
	var quizMetaData={};
	quizData = quizData1;
	console.log(quizData);
	var x = quizData.questions.length;
	quizMetaData.quiz_name= quizData.quiz_name;
  	quizMetaData.class= quizData.class_id;
  	quizMetaData.quiz_description =quizData.quiz_description;
  	quizMetaData.numberOfQuestions= quizData.questions.length;
  	quizMetaData.quizTime= quizData.quiz_time;
  	quizMetaData.startTime= quizData.start_time;
  	quizMetaData.endTime= quizData.end_time;
 	callNavButtons(quizMetaData);
}