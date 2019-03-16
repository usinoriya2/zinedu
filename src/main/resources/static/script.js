
var quizMetaData = {
  quiz_name: '',
  class: '',
  quiz_description :'',
  numberOfQuestions: '',
  quizTime: '',
  startTime: '',
  endTime: '',
}
var question={
  questionData : '',
  addMedia : '',
  optA_data : '',
  optA_media : '',
  optB_data : '',
  optB_media : '',
  optC_data : '',
  optC_media : '',
  optD_data : '',
  optD_media : '',
  questionMarks: '',
  correctAns: ''
}
var quizQuestions = [];
// question Question = new question(); 
 quizQuestions.push(question);
var string_data;
var string_data1;
var currentQuestion = 0;

function handleChangeMetaData(event) {
  console.log(quizMetaData);
  console.log(event.target.name);
  console.log(event.target.value);
  quizMetaData[event.target.name] = event.target.value;
  if(event.target.name === "numberOfQuestions") {
    for(var i = quizQuestions.length; i < quizMetaData.numberOfQuestions; i++) {
      var question1={
  questionData : '',
  addMedia : '',
  optA_data : '',
  optA_media : '',
  optB_data : '',
  optB_media : '',
  optC_data : '',
  optC_media : '',
  optD_data : '',
  optD_media : '',
  questionMarks: '',
  correctAns: ''
}
      quizQuestions.push(question1);
    }
    populateNavButtons(quizMetaData.numberOfQuestions, "handleSwitchQuestion");
  }
}

function callNavButtons(quizData){
  quizMetaData=quizData;
  console.log(quizMetaData);
  for(var i=0;i<quizMetaData.numberOfQuestions; i++){
    var question1={
  questionData : '',
  addMedia : '',
  optA_data : '',
  optA_media : '',
  optB_data : '',
  optB_media : '',
  optC_data : '',
  optC_media : '',
  optD_data : '',
  optD_media : '',
  questionMarks: '',
  correctAns: ''
}
      quizQuestions.push(question1);
  }
  populateNavButtons(quizMetaData.numberOfQuestions, "handleSwitchQuestion");

}
function populateNavButtons(numberOfQuestions,handler){
    var questionNavButtons="";
    for (var i = 1; i <= numberOfQuestions; i++) {
      questionNavButtons = questionNavButtons + "<button style='margin-right: 15px;' class='btn' id='quesButton" + i + "' type='button' onclick=" + "'" + handler + " ("+ (i - 1) + ")'" + ">"+ i +"</button>"; 
    }
    questionNavButtons = questionNavButtons;
    if(document.getElementById('questionsLabel')!=null){
    document.getElementById('questionsLabel').innerHTML = "Questions";}
    document.getElementById('questionNav').innerHTML = questionNavButtons;
}

function handleChangeQuestionField(event) {
  console.log(quizQuestions); 
  element = event.target;
  if(element.type == "textarea") quizQuestions[currentQuestion][event.target.name] = event.target.value;
  if(element.type == "number") quizQuestions[currentQuestion][event.target.name] = event.target.value;
  if(element.type == "file") quizQuestions[currentQuestion][event.target.name] = event.target.value;
  if(element.type == "radio") {
      quizQuestions[currentQuestion][event.target.name] = event.target.value;

  } 
  // whatever changes you make add it in quizQuestions[i]
}
function loadQuestion(quesId){
  var elements = document.getElementsByClassName("question_info");
  for(var i = 0; i<elements.length; i++) {
    if(elements[i].type == "textarea" ) elements[i].value=quizQuestions[quesId][elements[i].name] || "";
    if(elements[i].type == "radio" ){
      elements[i].checked = false;
      if(elements[i].value == quizQuestions[quesId][elements[i].name]) elements[i].checked=true;
    }

  }
}

function handleSwitchQuestion(i){
  addClassToQuestion(currentQuestion,"selectedQuestion",true);
  loadQuestion(i);
  console.log(i)
  currentQuestion = i;
  addClassToQuestion(i,"selectedQuestion");
  // return false;
  
}


  function serverRequest(event) {
    event.preventDefault();
    quizDetails = {"quizQuestions": quizQuestions, "quizMetaData": quizMetaData};
    console.log(quizDetails);
    string_data = JSON.stringify(quizDetails);
    console.log(string_data);
    $.post("http://localhost:8080/az",string_data).done(my_function);
 };
  function my_function(data){
    console.log("Request accepted");
    window.location="QuizSuccessfullySubmitted.html";
  }
addClassToQuestion = function(quesId, className, shouldRemove){  
  var elements = document.getElementById("quesButton" + (quesId + 1));
  console.log(elements);
  if(shouldRemove) elements.classList.remove(className);
  else elements.classList.add(className);
}
 function backToDashboard(){
  window.location="CreateQuiz.html";
 }


