$(document).ready(function () {
    $.ajax({
        type: "GET",
        url: baseBackendUrl + "/read/get-all",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Bearer " + retrieveToken());
        },
        async: true,
        success: function (data){
            console.log("Response data: ", data);
            if(data) {
                if (data.responseCode == "99") {
                    populateTableBody(data.responseData);
                }
            }
        },
        error: function (e){
            console.log("An error occurred: ", e);
        }
    });
});

function populateTableBody(items) {
    const table = document.getElementById("stTblBd");
    items.forEach( item => {
        let row = table.insertRow();
        let id = row.insertCell(0);
        id.innerHTML = item.id;
        let firstName = row.insertCell(1);
        firstName.innerHTML = item.firstName;
        let lastName = row.insertCell(2);
        lastName.innerHTML = item.lastName;
        let email = row.insertCell(3);
        email.innerHTML = item.email;
        let phoneNumber = row.insertCell(4);
        phoneNumber.innerHTML = item.phoneNumber;
        let country = row.insertCell(5);
        country.innerHTML = item.country;
        let state = row.insertCell(6);
        state.innerHTML = item.state;
        let createdOn = row.insertCell(7);
        createdOn.innerHTML = item.createdOn;
        let action = row.insertCell(8);
        action.innerHTML = "" +
            "<a href=\"/user/view-student/"+ item.id +"\" class=\"btn btn-info\" title=\"View\">\n" +
            "    <i class=\"fa fa-eye\"></i>\n" +
            "</a>\n" +
            "<button type=\"button\" onclick='deleteStudent("+item.id+")' id=\"submitButton-"+item.id+"\" class=\"btn btn-danger\"><i id=\"buttonDesign-"+item.id+"\" class=\"\"></i><i class=\"fa fa-trash\"></i></button>";
    });
}

function toggleLoaderButtonInternal(id, action){
    if(action == "show"){
        $("#buttonDesign-"+ id).addClass("fa fa-refresh fa-spin");
        $("#submitButton-"+ id).attr("disabled", "disabled");
    }else{
        $("#buttonDesign-"+ id).removeClass("fa fa-refresh fa-spin");
        $("#submitButton-"+ id).removeAttr("disabled");
    }
}

function deleteStudent(id) {
    toggleLoaderButtonInternal(id,"show");
    $.ajax({
        type: "DELETE",
        url: baseBackendUrl + "/delete/by-id/" + id,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", "Bearer " + retrieveToken());
        },
        async: true,
        success: function (data){
            console.log("Response data: ", data);
            toggleLoaderButtonInternal(id, "hide");
            if(data) {
                if (data.responseCode == "99") {
                    bootbox.alert("Student deleted successfully", function () {
                        window.location.reload();
                    });
                }
            }
        },
        error: function (e){
            console.log("An error occurred: ", e);
            toggleLoaderButtonInternal(id, "hide");
        }
    });
}

function handleEditClick() {
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
                if (data.responseCode == "99") {
                    if(data.responseData)
                        window.location.href = "/user/edit-profile/" + data.responseData.id;
                }
            }
        },
        error: function (e){
            console.log("An error occurred: ", e);
        }
    });
}

function performLogoutAction() {
    logout();
    window.location.href = "/login";
}