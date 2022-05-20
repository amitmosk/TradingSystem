package TradingSystem.server.Domain.Statistics;

import java.time.LocalDateTime;

public class Statistic {
    public String init_system_time;
    private long login_per_minutes;
    private long logout_per_minutes;
    private long connect_per_minutes;
    private long register_per_minutes;
    private long buy_cart_per_minutes;

    // ----------------------- constructors ---------------------------------------------
    public Statistic(LocalDateTime init_system_time, long login_per_minutes, long logout_per_minutes, long connect_per_minutes, long register_per_minutes, long buy_cart_per_minutes) {
        this.init_system_time = init_system_time.toString();
        this.login_per_minutes = login_per_minutes;
        this.logout_per_minutes = logout_per_minutes;
        this.connect_per_minutes = connect_per_minutes;
        this.register_per_minutes = register_per_minutes;
        this.buy_cart_per_minutes = buy_cart_per_minutes;
    }

    public Statistic() {
    }

    // --------------------- getters -----------------------------------
    public String getInit_system_time() {
        return init_system_time;
    }

    public long getLogin_per_minutes() {
        return login_per_minutes;
    }

    public long getLogout_per_minutes() {
        return logout_per_minutes;
    }

    public long getConnect_per_minutes() {
        return connect_per_minutes;
    }

    public long getRegister_per_minutes() {
        return register_per_minutes;
    }

    public long getBuy_cart_per_minutes() {
        return buy_cart_per_minutes;
    }

    // ------------------------------- setters ------------------------------------

    public void setInit_system_time(String init_system_time) {
        this.init_system_time = init_system_time;
    }

    public void setLogin_per_minutes(long login_per_minutes) {
        this.login_per_minutes = login_per_minutes;
    }

    public void setLogout_per_minutes(long logout_per_minutes) {
        this.logout_per_minutes = logout_per_minutes;
    }

    public void setConnect_per_minutes(long connect_per_minutes) {
        this.connect_per_minutes = connect_per_minutes;
    }

    public void setRegister_per_minutes(long register_per_minutes) {
        this.register_per_minutes = register_per_minutes;
    }

    public void setBuy_cart_per_minutes(long buy_cart_per_minutes) {
        this.buy_cart_per_minutes = buy_cart_per_minutes;
    }
}
