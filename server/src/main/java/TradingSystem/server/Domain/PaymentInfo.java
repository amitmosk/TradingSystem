package TradingSystem.server.Domain;

public class PaymentInfo {
    private String card_number;
    private String month;
    private String year;
    private String holder;
    private String ccv;
    private String id;

    public PaymentInfo() {
    }

    public PaymentInfo(String card_number, String month, String year, String holder, String ccv, String id) {
        this.card_number = card_number;
        this.month = month;
        this.year = year;
        this.holder = holder;
        this.ccv = ccv;
        this.id = id;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public void setCcv(String ccv) {
        this.ccv = ccv;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCard_number() {
        return card_number;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }

    public String getHolder() {
        return holder;
    }

    public String getCcv() {
        return ccv;
    }

    public String getId() {
        return id;
    }


}
