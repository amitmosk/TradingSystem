package Service;

import Domain.MarketFacade;
import Domain.SupplyAdapter;
import Domain.UserModule.PaymentAdapter;
import Domain.iFacade;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler extends Thread {
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    private iFacade marketFacade;

    // -- Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos,
                         PaymentAdapter paymentAdapter, SupplyAdapter supplyAdapter) {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.marketFacade = new MarketFacade(paymentAdapter, supplyAdapter);
    }

    @Override
    public void run() {
        String received;
        String toreturn;
        while (true) {
            try {
                // receive query from client
                received = dis.readUTF();
                // check valid message
                int opcode = this.get_opcode(received);

                if (received.equals("Exit")) {

                    break;
                }

                toreturn = this.handle_query(opcode, received);
                dos.writeUTF(toreturn);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // closing resources & update stats
        this.close_connection();


    }

    /**
     * should update system statisitics : connection time..
     */
    private void close_connection() {
        try {
            this.s.close();
            this.dis.close();
            this.dos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int get_opcode(String received) {
        return 1;
    }

    // TODO : switch opcodes to market Facade methods. should return string or json?
    private String handle_query(int opcode, String received) {
        switch (opcode) {
            case 1:
                marketFacade.login(received, received);
        }
        return "JSON";
    }

}
