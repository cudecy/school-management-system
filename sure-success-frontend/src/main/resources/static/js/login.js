function submitLoginRequest() {
    toggleLoaderButton("show");
    var formData = document.getElementById("prform");
    var postData = JSON.stringify({
        "emailAddress": formData.emailAddress.value,
        "password": formData.password.value
    });
    $.ajax({
        type: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        dataType: "json",
        url: baseBackendUrl + "/login",
        data: postData,
        async: true,
        success: function (data){
            console.log("Response data: ", data);
            toggleLoaderButton("hide");
            if(data) {
                if (data.responseCode == "79") {
                    bootbox.alert(data.errorMessage);
                } else {
                    storeToken(data.token);
                    window.location.href = "/user/all-students";
                }
            }else bootbox.alert("Oops! Cannot process request at the moment");
        },
        error: function (e){
            console.log("An error occurred: ", e);
            toggleLoaderButton("hide");
            if(e) {
                if (e.responseJSON != undefined)
                    bootbox.alert("Invalid username or password");
                else
                    bootbox.alert("Oops! Cannot process request at the moment. Please check your internet connection and try again");
            }else bootbox.alert("Oops! Cannot process request at the moment. Please check your internet connection and try again");
        }
    });
}