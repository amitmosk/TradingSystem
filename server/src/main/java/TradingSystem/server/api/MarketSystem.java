package TradingSystem.server.api;

import TradingSystem.server.Domain.StoreModule.StoreController;
import TradingSystem.server.Domain.UserModule.Admin;
import TradingSystem.server.Domain.UserModule.User;
import TradingSystem.server.Domain.UserModule.UserController;
import TradingSystem.server.Domain.Utils.ErrorLogger;
import TradingSystem.server.Domain.Utils.SystemLogger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import TradingSystem.server.Domain.Utils.SystemLogger;
import TradingSystem.server.Domain.ExternSystems.PaymentAdapter;
import TradingSystem.server.Domain.ExternSystems.PaymentAdapterImpl;
import TradingSystem.server.Domain.ExternSystems.SupplyAdapterImpl;
import TradingSystem.server.Domain.ExternSystems.SupplyAdapter;
import TradingSystem.server.Domain.Utils.Exception.MarketException;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class MarketSystem {
    private PaymentAdapter payment_adapter;
    private SupplyAdapter supply_adapter;

    public MarketSystem() {
        this.init_market();
        try {
            this.run_market();
        } catch (IOException e) {
            ErrorLogger.getInstance().add_log(e);
        }


    }


    /**
     * Requirement 1.1
     */

    public void init_market()  {
        SystemLogger.getInstance().add_log("start init market");
        this.payment_adapter = new PaymentAdapterImpl();
        this.supply_adapter = new SupplyAdapterImpl();
        try
        {
            payment_adapter.connect_to_payment_system();
            supply_adapter.connect_to_supply_system();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        // load DB
        UserController.load();
        StoreController.load();
        try {
            this.add_admins();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    public void run_market() throws IOException {
        try
        {
            this.init_market();
            payment_adapter = this.getPayment_adapter();
            supply_adapter = this.getSupply_adapter();
        }
        catch (Exception e)
        {
            ErrorLogger.getInstance().add_log(e);
        }

        ServerSocket serverSocket = new ServerSocket(5056);
        while (true)
        {
            Socket socket = null;
            try
            {
                socket = serverSocket.accept();
                SystemLogger.getInstance().add_log("new client connected");
                // new client connect to the server
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
//                Thread thread = new ClientHandler(socket, dis, dos, payment_adapter, supply_adapter);
//                thread.start();
            }
            catch (Exception e)
            {
                socket.close();
                e.printStackTrace();
            }

        }
    }

    public PaymentAdapter getPayment_adapter() {
        return payment_adapter;
    }

    public SupplyAdapter getSupply_adapter() {
        return supply_adapter;
    }

    public void add_admins() throws MarketException {
        UserController.getInstance().add_admin("barak_bahar@haifa.com", "aA12345678", "Barak", "Bahar");
        SystemLogger.getInstance().add_log("admin added");


    }
}
