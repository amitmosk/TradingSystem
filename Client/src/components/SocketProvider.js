import {useState} from "react";
import SockJS from 'sockjs-client';
import { Stomp } from "@stomp/stompjs";
import { Notifications } from "@mui/icons-material";
function SocketProvider(setMessage) {
    const notifications=[];
    const get_notifications = () => {
        return notifications;
    }
    function createSocket(userEmail) {
        const sock = new SockJS('http://localhost:8080/chat');
        let stompClient = Stomp.over(sock);
        sock.addEventListener("open", event => {
            console.log("Open Web Socket !");
        })
        sock.addEventListener("message", event => {
            let tempi = event.data.split("s1:");
            console.log(tempi);
            if (tempi.length != 1)
            // here come code for alert the user of new noticiation
            notifications.push(tempi[1]);
                alert("New Notification : "+tempi[1]);

            console.log(event);
        })




        stompClient.connect({email:"amit"}, function (frame) {
            console.log("connect 1");
            stompClient.send('/start', {});
            console.log("connect 2");
            stompClient.subscribe(`/topic/${userEmail}`, function (greeting) {
                //you can execute any function here
                console.log("try to subsribe...");
                console.log(greeting.body);
                setTimeout(() => {
                    setMessage(null)
                    console.log("cleared")
                }, 3000)
            });
        });
    }

    return { createSocket }
}

export default SocketProvider