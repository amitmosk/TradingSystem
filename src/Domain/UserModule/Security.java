package Domain.UserModule;

public class Security {
    protected String password;

    public Security(String password) {
        this.password = password;
    }


    public void check_password(String password) throws Exception {
        if (!password.equals(this.password)) throw new Exception("password does not match to current password");
    }

    public void edit_password(String old_password, String password) throws Exception {
        if (!old_password.equals(this.password)) throw new Exception("password does not match to current password");
        this.password = password;
    }

    public boolean isImproved(){
        return false;
    }

    public String get_question() throws Exception {
        throw new Exception("Only premium security has security question");
    }

    public void verify_answer(String answer) throws Exception {
        throw new Exception("Only premium security has security question");
    }

    public void check_improvable() throws Exception { }
}
