const WEBSOCKETURL = "ws://localhost:8080/chat";



const websocket = require('ws');
var ws = new websocket(WEBSOCKETURL);

ws.onopen = function(data) {ws.send("-client- want to open web socket with the server");};
ws.onmessage = function(data) {
    console.log("new notification!");
    // update notifications UI with the new notification 
    console.log(data);
    }
