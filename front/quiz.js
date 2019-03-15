
res = {};
currentQuestion = 0;
lastQuestion= 0;
var responseArray = {};
function startTimer(duration, display) {
    var start = Date.now(),
        diff,
        minutes,
        seconds,
        interval_id;
    function timer() {
        // get the number of seconds that have elapsed since 
        // startTimer() was called
        diff = duration - (((Date.now() - start) / 1000) | 0);

        // does the same job as parseInt truncates the float
        minutes = (diff / 60) | 0;
        seconds = (diff % 60) | 0;

        minutes = minutes < 10 ? "0" + minutes : minutes;
        seconds = seconds < 10 ? "0" + seconds : seconds;

        display.textContent = minutes + ":" + seconds; 

        if (diff <= 0) {
        	clearInterval(interval_id);
            handleSubmit(null);

        }
    };
    // we don't want to wait a full second before the timer starts
    timer();
    interval_id = setInterval(timer, 1000);
}
function quizTimer(i) {
	console.log("FF");
    var fiveMinutes = 60 * i,
        display = document.querySelector('#time');
    startTimer(fiveMinutes, display);
};
function callGetData(){
	var x = localStorage.getItem("quiz_id");
	console.log(x);
	getData(x);
}
function getData(i){
	console.log(i);
	console.log("load");
	$.ajax({
		url: "/fetchQuizData",
	  type: "get", //send it through get method
	  data: {
	  	//QuizID: res["questions"][quiz_id]
		QuizID: i
	  },
	  success: function(response) {
	  	loadQuiz(JSON.parse(response));
	  	var quizTime =res["quiz_time"];
		console.log("quizTime=" + quizTime);
		quizTimer(quizTime*60);

	  },
	  error: function(xhr) {
	  	console.log(xhr);
	  }
	});	
	//$.ajax("http://localhost:8080/getQuiz",string_data).done(loadQuiz);
};

loadQuiz = function(quizData){
	console.log(quizData);
	res = quizData;
	populateNavButtons(quizData["questions"].length, "handleSwitchQuestionUser");
	handleSwitchQuestionUser(0);
	//response.getData
}
var questionID=0;
handleSwitchQuestionUser = function(quesId){
	prevQuesId = res["questions"][currentQuestion]["question_id"];
	if( responseArray[prevQuesId] )
		addClassToQuestion(currentQuestion, "attempted");
	else
		addClassToQuestion(currentQuestion, "read");

	document.getElementById('quesButton' + (currentQuestion + 1)).classList.remove('selectedQuestion');
	document.getElementById('quesButton' + (quesId + 1)).classList.add('selectedQuestion');
	var question_element = document.getElementById('question_data');
	var optionA = document.getElementById('optA');
	var optionB = document.getElementById('optB');
	var optionC = document.getElementById('optC');
	var optionD = document.getElementById('optD');
	question_element.innerHTML = "Question "+ (quesId+1) + "<br></br>" + res["questions"][quesId]["question_data"];
	optionA.innerHTML = res["questions"][quesId]["optA"];
	optionB.innerHTML = res["questions"][quesId]["optB"];
	optionC.innerHTML = res["questions"][quesId]["optC"];
	optionD.innerHTML = res["questions"][quesId]["optD"];
	var elements = document.getElementsByClassName("question_info");
	newQuesId = res["questions"][quesId]["question_id"];
	for(var i = 0; i<elements.length; i++) {
		if(elements[i].type == "radio" ){
			elements[i].checked = false;
			if(elements[i].value == responseArray[newQuesId]){ 
				elements[i].checked=true;
				console.log(responseArray);
		}}

	}
	if(quesId==(res["questions"].length)-1){
		makeSubmit();
	}
	else{
		makeNext();
	}
 	if(quesId == 0)
		document.getElementById("prevButton").disabled = true;
	else
		document.getElementById("prevButton").disabled = false;
	//question
	console.log(quesId);
	currentQuestion = quesId;
}

handleChangeQuestionFieldOnQuiz = function(event) {
	// console.log(res); 
	element = event.target;
	x = res["questions"][currentQuestion]["question_id"];
	responseArray[x] = event.target.value;
	console.log(responseArray);

};

handlePrevious = function(event){
	if(currentQuestion!=0)
		handleSwitchQuestionUser(currentQuestion-1);
}

handleNextAndSubmit = function(event){
	lastQuestion = res["questions"].length -1;
	if(currentQuestion==lastQuestion)
		handleSubmit(event);
	else
		handleSwitchQuestionUser(currentQuestion+1);
}
makeNext = function(){
	var nexttosubmit = document.getElementById("nextandsubmit");
	nexttosubmit.innerHTML = "Next Question";
}
makeSubmit = function(){
	var nexttosubmit = document.getElementById("nextandsubmit");
	nexttosubmit.innerHTML = "Submit";
	//handleSwitchQuestionUser(currentQuestion+1);
}
handleClearResponse = function(){
	var elements = document.getElementsByClassName("question_info");
	for(var i = 0; i<elements.length; i++) {
		if(elements[i].type == "radio" ){
			elements[i].checked = false;
			x = res["questions"][currentQuestion]["question_id"];
			delete(responseArray[x]);

		}
	}
	addClassToQuestion(currentQuestion, "attempted", true);
}

handleMarkForReview = function(event) {
	if(res["questions"][currentQuestion]["marked"]==true){
		addClassToQuestion(currentQuestion,"markedForReview",true);
		res["questions"][currentQuestion]["marked"]=false;
	}
	else{
	addClassToQuestion(currentQuestion, "markedForReview");
	res["questions"][currentQuestion]["marked"]=true;
}}


handleSubmit =function(event){
	if(event) event.preventDefault();
	window.location = 'SubmitQuizDisplay.html';
    //quizDetails = {"quizQuestions": quizQuestions, "quizMetaData": quizMetaData};
    var quizIdObject = {
    	"student_name":localStorage.getItem("student_name"),
    	"class":localStorage.getItem("class"),
    	"school_name":localStorage.getItem("school_name"),
    	"roll_no":localStorage.getItem("roll_no"),
		"quiz_id": res["quiz_id"]
	};
    quizIdObject["attempted_answers"] = responseArray;
    var string_data = JSON.stringify(quizIdObject);
    $.ajax({
    	 type: "post",
		url: "/submitQuiz",
	  data: string_data,
	  success: my_function})}




function my_function(data){
    console.log(data);
  }

  query = "select ques.question_data qd, attempted_option aa, correct_ans ca, question_marks  m from quiz q join quiz_question qq on q.quiz_id=qq.quiz_id join question ques on ques.question_id=qq.question_id  join result on qq.quiz_id=result.question_id where q.quiz_id=1"