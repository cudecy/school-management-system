var baseBackendUrl = "http://localhost:9292";

function toggleLoaderButton(action){
    if(action == "show"){
        $("#buttonDesign").addClass("fa fa-refresh fa-spin");
        $("#submitButton").attr("disabled", "disabled");
    }else{
        $("#buttonDesign").removeClass("fa fa-refresh fa-spin");
        $("#submitButton").removeAttr("disabled");
    }
}

function makeHttpRequest(url, method, data, successRedirectUrl, useToken) {
    $.ajax({
        type: method,
        url: url,
        data: data,
        beforeSend: function (xhr) {
            if(useToken)
                xhr.setRequestHeader("Authorization", "Bearer " + retrieveToken());
        },
        async: true,
        success: function (data){
            console.log("Response data: ", data);
            toggleLoaderButton("hide");
            if(data) {
                if (data.responseCode == "79") {
                    bootbox.alert(data.errorMessage);
                } else {
                    bootbox.alert(data.message);
                }
            }else bootbox.alert("Oops! Cannot process request at the moment");

            if(successRedirectUrl)
                window.location.href = successRedirectUrl;
        },
        error: function (e){
            console.log("An error occurred: ", e);
            toggleLoaderButton("hide");
            if(e) {
                if (e.responseJSON != undefined)
                    bootbox.alert(e.responseJSON.errorMessage);
            }else bootbox.alert("Oops! Cannot process request at the moment");
        }
    });
}

function retrieveToken() {
    return localStorage.getItem("userTk");
}

function storeToken(token) {
    localStorage.setItem("userTk", token);
}

function logout() {
    localStorage.removeItem("userTk");
}