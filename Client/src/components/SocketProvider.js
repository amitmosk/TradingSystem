import {useState} from "react";
import SockJS from 'sockjs-client';

import Stomp from 'stompjs';
import { EMPLOYEE_BASE_REST_API_URL } from "../API/ApiPaths";
import { ConnectApi } from "../API/ConnectApi";

const get_notifications = () => {
    console.log("goti");
    let connectApi = new ConnectApi();
    let response = connectApi.get_notifications("amit@gmail.com");
    if (!response.was_exception)
    {
        console.log("in noti moti, noti success!\n");
    }
    else{
        console.log("in noti moti, noti failed!\n");
    }
}

function SocketProvider({setMessage, save_notification}) {
    function createSocket(userEmail) {
        const sock = new SockJS('http://localhost:8080/chat');

        console.log(sock);
        
        let stompClient = Stomp.over(sock);
        console.log(stompClient);
        sock.addEventListener("open", event => {
            console.log("Open Web Socket !");
            
            
        })
        sock.addEventListener("message", event => {
            let tempi = event.data.split("s1:");
            console.log(tempi+"\n\n\n\n\n\n\n\n");
            if (tempi.length != 1)
            {
                console.log(tempi[1]+"\n\n\n\n\n\n\n\n");
                save_notification(tempi[1]);
            }
            else{
                console.log("no nessage\n\n\n\n\n\n\n\n");
            }

            console.log(event);
        })




        stompClient.connect({email:userEmail}, function (frame) {
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