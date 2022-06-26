<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Create</title>
</head>
<body>
<form action="/api/code/new" method="post">
    <textarea rows="5" cols="50" id="code_snippet"></textarea>
    <input id="time_restriction" type="text"/>
    <input id="views_restriction" type="text"/>
    <br/>
    <button type="button" id="send_snippet" value="Submit" onclick="send()">Submit</button>
</form>
</body>
</html>

<script>
    function send() {
        let object = {
            "code": document.getElementById("code_snippet").value,
            "time": document.getElementById("time_restriction").value,
            "views": document.getElementById("views_restriction").value
        };

        let json = JSON.stringify(object);

        let xhr = new XMLHttpRequest();
        xhr.open("POST", '/api/code/new', false)
        xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
        xhr.send(json);

        if (xhr.status == 200) {
            alert("Success!");
        }
    }
</script>