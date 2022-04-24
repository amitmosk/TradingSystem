package Service;

public class AssignUser extends AssignState {
    private String email;
    private String pw;
    private String name;
    private String lastName;
    private History userHistory;

    public AssignUser(String email, String pw, String name, String lastName) {
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.lastName = lastName;
        this.userHistory = new UserHistory();
    }

    @Override
    public boolean login(String pw){
        return pw.equals(this.pw);
    }

    @Override
    public void addPurchase(Purchase purchase){
        userHistory.addPurchase(purchase);
    }
}
