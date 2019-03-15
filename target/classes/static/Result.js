var resultList=[];
function getResultListData(){
	console.log("load");
	$.ajax({
		url: "http://localhost:8080/fetchResultList",
	  type: "get", //send it through get method
	  data: "check",
	  success: function(response) {
	  	console.log(response);
	  	loadResultList(JSON.parse(response));
	  },
	  error: function(xhr) {
	  	console.log(xhr);
	  }
	});	

}
function loadResultList(resultListData){
	resultList=resultListData;
	console.log(resultList);
	showResultList(resultList);
}

function showResultList(resultList){
	var resultHTML="";
	for(var i=0;i<resultList.length;i++){
		resultHTML+="<li class='li' style='margin-bottom: 10px;'><span style='font-size: 20px; color: black; margin-right: 30px'>" + resultList[i]["quiz_name"] + " " +resultList[i]["student_name"]+ "</span><button class='btn' onclick=getResultData("+resultList[i]["result_list_id"]+")>Generate CSV</button></li>";
	}
	document.getElementById("quiz_lists").innerHTML=resultHTML;
}

function getResultData(i){
	console.log("load");
	$.ajax({
		url: "http://localhost:8080/getCsv",
	  type: "get", //send it through get method
	  data: {
	  	//QuizID: res["questions"][quiz_id]
		result_list_id: i           	
	  },
	  success: function(response) {
	  	// loadQuiz(JSON.parse(response));
	  	console.log(response);
	  },
	  error: function(xhr) {
	  	console.log(xhr);
	  }
	});	
	//$.ajax("http://localhost:8080/getQuiz",string_data).done(loadQuiz);
};