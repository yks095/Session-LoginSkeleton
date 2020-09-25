$(`.sign-up`).click(function () {

    var jsonData = JSON.stringify({
        email : $(`#email`).val(),
        password : $(`#pwd`).val()
    });

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    alert(jsonData);

    $.ajax({
        url: "/sign-up",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        dataType: "json",
        beforeSend: function (xhr)  {
            xhr.setRequestHeader(header, token);
        },
        success: function () {
            alert('성공' + token + ", " + header);
            location.href = '/sign-in';
        },
        error: function () {
            console.log("왜 안됨?")
            alert('실패');
        }
    });
});