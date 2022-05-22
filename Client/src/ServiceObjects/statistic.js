export class Statistic{   
    // public String init_system_time;
    // private long login_per_minutes;
    // private long logout_per_minutes;
    // private long connect_per_minutes;
    // private long register_per_minutes;
    // private long buy_cart_per_minutes; 
    constructor(data) {
        this.init_system_time = data.init_system_time
        this.login_per_minutes =  data.login_per_minutes
        this.logout_per_minutes = data.logout_per_minutes
        this.connect_per_minutes = data.connect_per_minutes
        this.register_per_minutes = data.register_per_minutes
        this.buy_cart_per_minutes = data.buy_cart_per_minutes
    }
    
    static create(init_system_time, login_per_minutes, logout_per_minutes, connect_per_minutes, register_per_minutes, buy_cart_per_minutes) {
        return new Statistic({
            init_system_time : init_system_time,
            login_per_minutes :  login_per_minutes,
            logout_per_minutes : logout_per_minutes,
            connect_per_minutes : connect_per_minutes,
            register_per_minutes : register_per_minutes,
            buy_cart_per_minutes : buy_cart_per_minutes,
        })
    }
}