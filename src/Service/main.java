package Service;

import Domain.Market;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class main {
    public static void main(String[] args) throws IOException {
        System.out.println("amit");
        MarketService market = new MarketService();
        supply_adapter, payment_adapter = market.init_market();
        ServerSocket serverSocket = new ServerSocket(5056);
        while (true)
        {
            Socket socket = null;
            try
            {
                socket = serverSocket.accept();
                // new client connect to the server
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                Thread thread = new ClientHandler(socket, dis, dos);
                thread.start();
            }
            catch (Exception e)
            {
                socket.close();
                e.printStackTrace();
            }

        }
    }
}

class ClientHandler extends Thread
{
    final DataInputStream dis;
    final DataOutputStream dos;
    final Socket s;
    private Market marketFacade;


    // Constructor
    public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)
    {
        this.s = s;
        this.dis = dis;
        this.dos = dos;
        this.marketFacade = new Market();
    }

    @Override
    public void run()
    {
        String received;
        String toreturn;
        while (true)
        {
            try {
                // receive query from client
                received = dis.readUTF();
                // check valid message
                int opcode = this.get_opcode(received);

                if(received.equals("Exit"))
                {

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
        try
        {
            this.s.close();
            this.dis.close();
            this.dos.close();

        }catch(IOException e){
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

    private void client_exit() throws IOException {
        System.out.println("Client " + this.s + " sends exit...");
        System.out.println("Closing this connection.");
        System.out.println("Connection closed");
    }
}