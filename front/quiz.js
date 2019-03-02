// $.ajax({
//      type: "GET",
//      url: 'http://localhost:8080/fetchQuizData?QuizID=1',
//      success: function(response){
//          //alert(response);
//      }
// });
res = {};
function getData(){
	console.log("load");
	$.ajax({
	  url: "http://localhost:8080/fetchQuizData",
	  type: "get", //send it through get method
	  data: {
	    QuizID: 1, 
	  },
	  success: function(response) {
	    loadQuiz(JSON.parse(response));
	  },
	  error: function(xhr) {
	    console.log(xhr);
	  }
	});	
	//$.ajax("http://localhost:8080/getQuiz",string_data).done(loadQuiz);
}

loadQuiz = function(quizData){
	console.log(quizData);
	res = quizData;
	populateNavButtons(quizData["questions"].length, "handleSwitchQuestionUser");
	handleSwitchQuestionUser(0);
	//response.getData
}

handleSwitchQuestionUser = function(quesId){
	var question_element = document.getElementById('question_data');
	var optionA = document.getElementById('optA');
	var optionB = document.getElementById('optB');
	var optionC = document.getElementById('optC');
	var optionD = document.getElementById('optD');
	question_element.innerHTML = res["questions"][quesId]["question_data"];
    optionA.innerHTML = res["questions"][quesId]["optA"];
    optionB.innerHTML = res["questions"][quesId]["optB"];
    optionC.innerHTML = res["questions"][quesId]["optC"];
    optionD.innerHTML = res["questions"][quesId]["optD"];

	console.log(quesId);
}