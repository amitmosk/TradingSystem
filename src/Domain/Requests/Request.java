package Domain.Requests;

public class Request {
    int storeID;
    String senderEmail;
    String request;

    public Request(int storeID, String senderEmail, String request) {
        this.storeID = storeID;
        this.senderEmail = senderEmail;
        this.request = request;
    }

}
