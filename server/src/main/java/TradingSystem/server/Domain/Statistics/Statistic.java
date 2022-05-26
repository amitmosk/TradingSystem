package TradingSystem.server.Domain.Statistics;

import java.time.LocalDate;

public class Statistic {
    public String init_system_time;
    private long login_per_minutes;
    private long logout_per_minutes;
    private long connect_per_minutes;
    private long register_per_minutes;
    private long buy_cart_per_minutes;

    public Statistic(LocalDate init_system_time, long login_per_minutes, long logout_per_minutes, long connect_per_minutes, long register_per_minutes, long buy_cart_per_minutes) {
        this.init_system_time = init_system_time.toString();
        this.login_per_minutes = login_per_minutes;
        this.logout_per_minutes = logout_per_minutes;
        this.connect_per_minutes = connect_per_minutes;
        this.register_per_minutes = register_per_minutes;
        this.buy_cart_per_minutes = buy_cart_per_minutes;
    }
}
