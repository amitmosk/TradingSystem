package Service;



import Domain.SupplyAdapter;
import Domain.UserModule.PaymentAdapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class main {
    public static void main(String[] args) throws IOException {
        System.out.println("amit");
        MarketSystem market = new MarketSystem();
        PaymentAdapter payment_adapter = null;
        SupplyAdapter supply_adapter = null;
        try
        {
            market.init_market();
            payment_adapter = market.getPayment_adapter();
            supply_adapter = market.getSupply_adapter();
        }
        catch (Exception e)
        {
            // @TODO : cant continue
        }

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
                Thread thread = new ClientHandler(socket, dis, dos, payment_adapter, supply_adapter);
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