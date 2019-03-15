var student={
};

function getInformation(){
	student[event.target.name] = event.target.value;
  	console.log(student);
  	}

function postData(){
	event.preventDefault();
    string_data = JSON.stringify(student);
    console.log(string_data);
    $.post("http://localhost:8080/postStudentData",string_data).done(my_function);

};
function my_function(data){
    console.log(data);
    localStorage.setItem("student_name",student["student_name"]);
    localStorage.setItem("class",student["class"]);
    localStorage.setItem("roll_no",student["roll_no"]);
    localStorage.setItem("school_name",student["school_name"]);
    window.location="quizes.html";
  }
