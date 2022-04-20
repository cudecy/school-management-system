$(document).ready(function () {
    var studentId = $("#studentId").val();
    $.ajax({
        type: "GET",
        url: baseBackendUrl + "/read/get-single/" + studentId,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Bearer " + retrieveToken());
        },
        async: true,
        success: function (data){
            console.log("Response data: ", data);
            if(data) {
                if (data.responseCode == "99") {
                    var body = data.responseData;
                    if(body) {
                        populateStudentForm(body);
                    }
                }else {
                    bootbox.alert(data.errorMessage);
                }
            }
        },
        error: function (e){
            console.log("An error occurred: ", e);
        }
    });
});

function populateStudentForm(body) {
    $("#studentId").val(body.id);
    $("#studentFirstName").val(body.firstName);
    $("#studentLastName").val(body.lastName);
    $("#studentEmail").val(body.email);
    $("#studentPhoneNumber").val(body.phoneNumber);
    $("#studentCountry").val(body.country);
    $("#studentState").val(body.state);
    $("#studentName").html(body.firstName + ' ' + body.lastName);
}

function editProfile() {
    toggleLoaderButton("show");
    var formData = document.getElementById("edform");
    var id = formData.studentId.value;
    var postData = JSON.stringify({
        "email": formData.studentEmail.value,
        "oldPassword": formData.studentOldPassword.value,
        "newPassword": formData.studentNewPassword.value,
        "confirmNewPassword": formData.studentConfirmNewPassword.value,
        "firstName": formData.studentFirstName.value,
        "lastName": formData.studentLastName.value,
        "phoneNumber": formData.studentPhoneNumber.value,
        "country": formData.studentCountry.value,
        "state": formData.studentState.value,
    });
    $.ajax({
        type: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Bearer " + retrieveToken());
        },
        dataType: "json",
        url: baseBackendUrl + "/update?studentId=" + id,
        data: postData,
        async: true,
        success: function (data){
            console.log("Response data: ", data);
            toggleLoaderButton("hide");
            if(data) {
                if (data.responseCode == "79") {
                    bootbox.alert(data.errorMessage);
                } else {
                    bootbox.alert(data.message, function () {
                        if(formData.studentOldPassword.value.trim() != "" &&
                            formData.studentNewPassword.value.trim() != "" &&
                            formData.studentConfirmNewPassword.value.trim() != "") {
                            logout();
                            window.location.href = "/login";
                        }else {
                            window.location.reload();
                        }
                    });
                }
            }else bootbox.alert("Oops! Cannot process request at the moment");
        },
        error: function (e){
            console.log("An error occurred: ", e);
            toggleLoaderButton("hide");
            if(e) {
                if (e.responseJSON != undefined)
                    bootbox.alert(e.responseJSON.errorMessage);
                else
                    bootbox.alert("Oops! Cannot process request at the moment. Please check your internet connection and try again");
            }else bootbox.alert("Oops! Cannot process request at the moment. Please check your internet connection and try again");
        }
    });
}

function createProfile() {
    toggleLoaderButton("show");
    var formData = document.getElementById("crform");
    var postData = JSON.stringify({
        "email": formData.studentEmail.value,
        "password": formData.studentPassword.value,
        "firstName": formData.studentFirstName.value,
        "lastName": formData.studentLastName.value,
        "phoneNumber": formData.studentPhoneNumber.value,
        "country": formData.studentCountry.value,
        "state": formData.studentState.value,
    });
    $.ajax({
        type: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Bearer " + retrieveToken());
        },
        dataType: "json",
        url: baseBackendUrl + "/create",
        data: postData,
        async: true,
        success: function (data){
            console.log("Response data: ", data);
            toggleLoaderButton("hide");
            if(data) {
                if (data.responseCode == "79") {
                    bootbox.alert(data.errorMessage);
                } else {
                    bootbox.alert(data.message, function () {
                        logout();
                        window.location.href = "/login";
                    });
                }
            }else bootbox.alert("Oops! Cannot process request at the moment");
        },
        error: function (e){
            console.log("An error occurred: ", e);
            toggleLoaderButton("hide");
            if(e) {
                if (e.responseJSON != undefined)
                    bootbox.alert(e.responseJSON.errorMessage);
                else
                    bootbox.alert("Oops! Cannot process request at the moment. Please check your internet connection and try again");
            }else bootbox.alert("Oops! Cannot process request at the moment. Please check your internet connection and try again");
        }
    });
}