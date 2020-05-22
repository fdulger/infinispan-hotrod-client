
function get() {
    $.ajax({
        url: "/registry",
        context: $("#registry")
    }).done(function(res) {
        $(this).val(JSON.stringify(res))
    });
}

function post() {
    var data = {}
    data[$('#key').val()] = $('#val').val();
    $.ajax({
        method: "post",
        url: "/put",
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data)
    });
}

function subscribe() {
    var socket = new WebSocket($("#eventEndpoint").val())
    ws = Stomp.over(socket)

    ws.connect({}, function(frame) {
        ws.subscribe("/topic/registry", function(message) {
            $("#eventsArea").append(JSON.stringify(message.body) + "<br/>")
        });
    }, function(error) {
        console.log("STOMP error " + error)
    });
}