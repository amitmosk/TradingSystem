import Domain.Market;
import Domain.StoreModule.Product;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        System.out.println("amit");
        List<String> keys = new ArrayList<>();
        Product p = new Product("pro", 1, 1, 1, "cat", keys );
        String g = new Gson().toJson(p);
        Product p1 = new Gson().fromJson(g,Product.class );
        System.out.println(g);
        int a = 5;


        Market market = new Market();
        System.out.println(market.guest_login());
    }
}
