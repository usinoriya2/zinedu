
var quizMetaData = {
  class: '',
  numberOfQuestions: '',
  quizTime: '',
  startTime: '',
  startDate: '',
  endTime: '',
  endDate: ''
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
    var questionNavButtons="";
    for (var i = 1; i <= quizMetaData.numberOfQuestions; i++) {
      questionNavButtons = questionNavButtons + "<button class='btn' type='button' onclick=" + "'handleSwitchQuestion("+ (i - 1) + ")'" + ">"+ i +"</button>"; 
    }
    questionNavButtons = "Questions: " + questionNavButtons;
    document.getElementById('questionNav').innerHTML = questionNavButtons;
  }
}

function handleChangeQuestionField(event) {
  console.log(quizQuestions); 
  element = event.target;
  if(element.type == "textarea") quizQuestions[currentQuestion][event.target.name] = event.target.value;
  if(element.type == "number") quizQuestions[currentQuestion][event.target.name] = event.target.value;
  if(element.type == "file") quizQuestions[currentQuestion][event.target.name] = event.target.value;
  if(element.type == "radio") quizQuestions[currentQuestion][event.target.name] = event.target.value;
  // whatever changes you make add it in quizQuestions[i]
}
function loadQuestion(quesId){
  var elements = document.getElementsByClassName("question_info");
  for(var i = 0; i<elements.length; i++) {
    if(elements[i].type == "textarea" ) elements[i].value=quizQuestions[quesId][elements[i].name] || "";
    if(elements[i].type == "radio" ){
      elements[i].checked = false;
      if(quizQuestions[quesId][elements[i].name]) quizQuestions[quesId][elements[i].name].checked=true;
    }

  }
}

function handleSwitchQuestion(i){
  loadQuestion(i);
  console.log(i)
  currentQuestion = i;
  // return false;
  
}


  function serverRequest(event) {
    event.preventDefault();
    quizDetails = {"quizQuestions": quizQuestions, "quizMetaData": quizMetaData};
    string_data = JSON.stringify(quizDetails);
    console.log(string_data);
    $.post("http://localhost:8080/az",string_data).done(my_function);
 };
  function my_function(data){
    console.log(data);
  }
// $(document).ready(function(){
//   $("#submitForm").click(function(){
//   	var string_data;
//   	var class_no = $.trim($("input[name='cls']:checked").val());
//   	var numberOfQus = $.trim($("#numberOfQus").val());
//     var quiz_time = $.trim($("#quiz_time").val());
//     var start_date = $.trim($("#start_date").val());
//     var start_time = $.trim($("#start_time").val());
//     var end_date = $.trim($("#end_date").val());
//     var end_time = $.trim($("#end_time").val());

//     var data={	class_no : class_no,
//       numberOfQus : numberOfQus,
//       quiz_time : quiz_time,
//       start_date : start_date,
//       start_time : start_time,
//       end_date : end_date,
//       end_time : end_time

//     }
//     string_data=JSON.stringify(data);
//     console.log(string_data);
//     $.post("http://localhost:8080/az",string_data).done(my_function);

//   });

//   $("form").submit(function(event) {
//    event.preventDefault();
//  });

  


// });
// $(document).ready(function(){
//   $("#submitForm").click(function(){
//     var string_data;
//     var question_data = $.trim($("#question_data").val());
//     var optA_data = $.trim($("#optA_data").val());
//     var optB_data = $.trim($("#optB_data").val());
//     var optC_data = $.trim($("#optC_data").val());
//     var optD_data = $.trim($("#optD_data").val());
//     var question_marks = $.trim($("#question_marks").val());
//     var correct_ans = $.trim($("input[name='c_ans']:checked").val());
//     var data={
//       question_data : question_data,
//       optA_data : optA_data,
//       optB_data : optB_data,
//       optC_data : optC_data,
//       optD_data : optD_data,
//       question_marks : question_marks,
//       correct_ans : correct_ans

//     }
//     string_data=JSON.stringify(data);
//     console.log(string_data);
//     $.post("http://localhost:8080/ax",string_data).done(my_function);
//     var allData=[data, data, data, data, data];
//     console.log(allData);

//   });
//   function my_function(data){
//     console.log("running");
//     console.log(data);
//   }

//   $("form").submit(function(event) {
//     event.preventDefault();
//   });

// });




