package TradingSystem.server.Domain.StoreModule.Appointment;

public class AppointmentInformation {
    private String member_email;
    private String appointer_email;
    private String type;
    private String status;

    public AppointmentInformation() {
    }

    public AppointmentInformation(String member_email, String appointer_email, String type, String status) {
        this.member_email = member_email;
        this.appointer_email = appointer_email;
        this.type = type;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
