package TradingSystem.server.Domain.StoreModule;

public class AppointmentInformation {
    private String member_email;
    private String appointer_email;
    private String type;

    public AppointmentInformation() {
    }

    public AppointmentInformation(String member_email, String appointer_email, String type) {
        this.member_email = member_email;
        this.appointer_email = appointer_email;
        this.type = type;
    }

    public void setMember_email(String member_email) {
        this.member_email = member_email;
    }

    public void setAppointer_email(String appointer_email) {
        this.appointer_email = appointer_email;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMember_email() {
        return member_email;
    }

    public String getAppointer_email() {
        return appointer_email;
    }

    public String getType() {
        return type;
    }
}
