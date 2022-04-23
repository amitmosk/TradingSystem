package Service;

public class AssignUser extends AssignState {
    private String email;
    private String pw;
    private String name;
    private String lastName;

    public AssignUser(String email, String pw, String name, String lastName) {
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.lastName = lastName;
    }

    @Override
    public boolean login(String pw){
        return pw == this.pw;
    }
}
