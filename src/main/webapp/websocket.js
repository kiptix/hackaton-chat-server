var wsUri = "ws://" + document.location.hostname + ":" + document.location.port + document.location.pathname + "chat";
var websocket = new WebSocket(wsUri);

var username;
websocket.onopen = function (evt) {
    onOpen(evt)
};
websocket.onmessage = function (evt) {
    onMessage(evt)
};
websocket.onerror = function (evt) {
    onError(evt)
};
var output = document.getElementById("output");

function join() {
    username = sender.value;
    var msg = JSON.stringify({
        "sender": username,
        "message": "join"});

    websocket.send(msg);
}

function send_message() {
    var msg = JSON.stringify({
        "sender": username, 
        "receiver": receiver.value,
        "message": message.value});
    
    websocket.send(msg);
//    websocket.send(username + ": " + textField.value);
}

function onOpen() {
    writeToScreen("Connected to " + wsUri);
}

function onMessage(evt) {
    console.log("onMessage: " + evt.data);
    if (evt.data.indexOf("joined") != -1) {
        userField.innerHTML += evt.data.substring(0, evt.data.indexOf(" joined")) + "\n";
    } else {
        chatlogField.innerHTML += evt.data + "\n";
    }
}

function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function writeToScreen(message) {
    output.innerHTML += message + "<br>";
}