package Domain.UserModule;

import Domain.Utils.PasswordManagerImpl;
import Domain.Utils.iPasswordManager;

public class Security {
    protected iPasswordManager passwordManager;
    protected String token;

    public Security(String password) {
        this.passwordManager = new PasswordManagerImpl();
        this.set_token(password);
    }

    private void set_token(String password) {
        this.token = this.passwordManager.hash(password);
    }


    public void check_correct_password(String password) throws Exception {
        if (!passwordManager.authenticate(password, this.token)) throw new Exception("password does not match to current password");
    }

    public void edit_password(String old_password, String password) throws Exception {
        this.check_correct_password(old_password);
        this.token = this.passwordManager.hash(password);
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

