package Domain.Statistics;

import java.time.LocalDateTime;

public class Statistic {
    public LocalDateTime init_system_time;
    private long login_per_minutes;
    private long logout_per_minutes;
    private long connect_per_minutes;
    private long register_per_minutes;
    private long buy_cart_per_minutes;

    public Statistic(LocalDateTime init_system_time, long login_per_minutes, long logout_per_minutes, long connect_per_minutes, long register_per_minutes, long buy_cart_per_minutes) {
        this.init_system_time = init_system_time;
        this.login_per_minutes = login_per_minutes;
        this.logout_per_minutes = logout_per_minutes;
        this.connect_per_minutes = connect_per_minutes;
        this.register_per_minutes = register_per_minutes;
        this.buy_cart_per_minutes = buy_cart_per_minutes;
    }
}
