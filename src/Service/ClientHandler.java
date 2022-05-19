package Service;

import Domain.ExternSystems.PaymentAdapter;
import Domain.ExternSystems.SupplyAdapter;
import Domain.Facade.MarketFacade;
import Domain.Facade.iFacade;
import Domain.Utils.Request;
import Domain.Utils.Utils;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;


public class ClientHandler extends Thread {
    final InputStreamReader  dis;
    final DataOutputStream dos;
    final Socket s;
    private iFacade marketFacade;
    private Service service;

    // OPCODES
    private final int EXIT_OPCODE = 505050;

    // -- Constructor
    public ClientHandler(Socket s, InputStreamReader dis, DataOutputStream dos,
                         PaymentAdapter paymentAdapter, SupplyAdapter supplyAdapter) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.marketFacade = new MarketFacade(paymentAdapter, supplyAdapter);
        this.service = new Service(marketFacade);
    }

    @Override
    public void run() {
        String data_received;
        String data_to_return;
        while (true) {
            try {
                // receive query from client
                int character;
                StringBuilder data = new StringBuilder();

                while ((character = dis.read()) != '\0') {
                    data.append((char) character);
                }
                data_received = data.toString();
                // check valid message
                Request client_request = this.decode_request(data_received);
                int opcode = client_request.get_opcode();
                if (opcode == EXIT_OPCODE) {
                    // exit opcode
                    break;
                }

                data_to_return = this.service.handle_query(opcode, client_request.get_data());
                data_to_return = data_to_return + '\0';
                dos.write(data_to_return.getBytes());
                dos.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // closing resources & update stats
        this.close_connection();


    }

    private void close_connection() {
        try {
            this.s.close();
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Request decode_request(String data){
        Gson gson = new Gson();
        Request answer = null;

        try{
            answer = gson.fromJson(data, Request.class);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return answer;
    }



}
