var quizList=[];
function callQuizListData(){
	var x = localStorage.getItem("class");
	console.log(x);
	loadQuizList2(x);
}

function getQuizListData(){
	console.log("load");
	$.ajax({
		url: "/fetchEditQuizList",
	  type: "get", //send it through get method
	  data: "check",
	  success: function(response) {
	  	console.log(response);
	  	loadQuizEditList(JSON.parse(response));

	  },
	  error: function(xhr) {
	  	console.log(xhr);
	  }
	});	
}
function getQuizDeleteListData(){
	console.log("getQuizDeleteListData");
	$.ajax({
		url: "/fetchEditQuizList",
	  type: "get", //send it through get method
	  data: "check",
	  success: function(response) {
	  	console.log(response);
	  	loadQuizDeleteList(JSON.parse(response));

	  },
	  error: function(xhr) {
	  	console.log(xhr);
	  }
	});	
}

function loadQuizList2(i){

	console.log("load");
	$.ajax({
		url: "/fetchQuizList",
	  type: "get", //send it through get method
	  data: {
	  	//QuizID: res["questions"][quiz_id]
	  	ClassID: i
	  },
	  success: function(response) {
	  	console.log(response);
	  	loadQuizList3(JSON.parse(response));

	  },
	  error: function(xhr) {
	  	console.log(xhr);
	  }
	});	
}

function loadQuizList3(quizListHai){
	console.log(quizListHai);
	quizList=quizListHai;
	for(var i = 0; i<quizListHai.length; i++){
		trQuizNewId = 'trQuiz' + i;
		trQuizNew = $("#trQuiz").clone().attr('id',trQuizNewId);
		quiz_id = quizListHai[i]['quiz_id'];
		trQuizNew.appendTo("#tbodyQuiz");
		$("#" + trQuizNewId + " .tdQuizName")[0].innerHTML=quizListHai[i]['quiz_name'];
		$("#" + trQuizNewId + " .tdClass")[0].innerHTML=quizListHai[i]['class_id'];
		$("#" + trQuizNewId + " .tdQuizTime")[0].innerHTML=quizListHai[i]['quiz_time'];
		$("#" + trQuizNewId + " .tdValidTill")[0].innerHTML=quizListHai[i]['valid_till'];
		x="<button onclick=loadQuizDescription("+quiz_id+")>Link</button>";
		$("#" + trQuizNewId + " .tdLink")[0].innerHTML=x;
	}
}

function loadQuizEditList(editQuizList){
	var resultHTML="";
	for(var i=0;i<editQuizList.length;i++){
		resultHTML+="<li class='li' style='margin-bottom: 10px;'><span style='font-size: 20px; color: black; margin-right: 30px'>" + editQuizList[i]["quiz_name"] + "</span><button class='btn' >Edit</button></li>";
	}
	document.getElementById("quiz_lists").innerHTML=resultHTML;
}
function loadQuizDeleteList(editQuizList){
	var resultHTML="";
	for(var i=0;i<editQuizList.length;i++){
		resultHTML+="<li class='li' style='margin-bottom: 10px;'><span style='font-size: 20px; color: black; margin-right: 30px'>" + editQuizList[i]["quiz_name"] + "</span><button class='btn' onclick='deleteQuiz(" + editQuizList[ i ]['quiz_id'] + ")' >Delete</button></li>";
	
	document.getElementById("delete_quiz_lists").innerHTML=resultHTML;
}}

function deleteQuiz(i){
	//console.log("loadelete");
	$.ajax({
		url: "/deleteQuiz",
	  type: "get", //send it through get method
	  data: {
	  	//QuizID: res["questions"][quiz_id]
	  	QuizID: i
	  },
	  success: function(response) {
	  	getQuizDeleteListData();

	  },
	  error: function(xhr) {
	  	console.log(xhr);
	  }
	});	
}
function loadQuizDescription(quizId){
	//console.log("pickle rick");
	localStorage.setItem("quiz_id", quizId);
	window.location="QuizDescription.html";
}