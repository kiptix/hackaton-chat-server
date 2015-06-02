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
        "type":"login",
        "userName": username,
        "displayName": username});

    websocket.send(msg);
}

function send_message() {
    var msg = JSON.stringify({
        "type": "message", 
        "recipient": receiver.value,
        "message": message.value});
    
    websocket.send(msg);
}

function onOpen() {
    writeToScreen("Connected to " + wsUri);
}

function onMessage(evt) {
    console.log("onMessage: " + evt.data);
    var msg = JSON.parse(evt.data);
    if (msg.type == "login") {
        userField.innerHTML += msg.displayName + "\n";
        if (msg.userName == username) {
            sender.readOnly = true;
            joinbtn.disabled = true;
        }
    } else {
        var wisper = msg.recipient != null ? " wispers " : "";
        chatlogField.innerHTML += msg.displayName + wisper + " (" + msg.date + "): " + msg.message + "\n";
    }
}

function onError(evt) {
    writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);
}

function writeToScreen(message) {
    output.innerHTML += message + "<br>";
}