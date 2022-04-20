$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: baseBackendUrl + "/user/get-details",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Bearer " + retrieveToken());
        },
        async: true,
        success: function (data){
            console.log("Response data: ", data);
            if(data) {
                if (data.responseCode == "79") {
                    if(!window.location.href.includes("/login")) {
                        bootbox.alert("Your session has expired. Please login", function () {
                            logout();
                            window.location.href = "/login";
                        });
                    }
                } else {
                    if(window.location.href.includes("/login")) {
                        window.location.href = "/user/all-students";
                    }
                }
            }else {
                if(!window.location.href.includes("/login")) {
                    bootbox.alert("Your session has expired. Please login", function () {
                        logout();
                        window.location.href = "/login";
                    });
                }
            }
        },
        error: function (e){
            console.log("An error occurred: ", e);
            if(!window.location.href.includes("/login")) {
                bootbox.alert("Your session has expired. Please login", function () {
                    logout();
                    window.location.href = "/login";
                });
            }
        }
    });
});