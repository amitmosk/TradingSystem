package TradingSystem.server.Domain.UserModule;

import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Exception.MarketSecuirtyException;
import TradingSystem.server.Domain.Utils.PasswordManagerImpl;
import TradingSystem.server.Domain.Utils.iPasswordManager;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
public class Security {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long security_id;

    @Transient
    protected iPasswordManager passwordManager;

    protected String token;

    public Security(String password) {
        this.passwordManager = new PasswordManagerImpl();
        this.set_token(password);
    }

    public Security() {
    }

    public iPasswordManager getPasswordManager() {
        return passwordManager;
    }

    public String getToken() {
        return token;
    }

    public void setPasswordManager(iPasswordManager passwordManager) {
        this.passwordManager = passwordManager;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private void set_token(String password) {
        this.token = this.passwordManager.hash(password);
    }


    public void check_correct_password(String password) throws MarketException {
        if (!passwordManager.authenticate(password, this.token)) throw new MarketException("password does not match to current password");
    }

    public void edit_password(String old_password, String password) throws MarketException {
        this.check_correct_password(old_password);
        this.token = this.passwordManager.hash(password);
    }

    public boolean isImproved(){
        return false;
    }

    public String find_question() throws MarketException {
        throw new MarketSecuirtyException("Only premium security has security question");
    }

    public void verify_answer(String answer) throws MarketException {
        throw new MarketSecuirtyException("Only premium security has security question");
    }

    public void check_improvable() throws MarketException { }

    public void setSecurity_id(Long security_id) {
        this.security_id = security_id;
    }

    public Long getSecurity_id() {
        return security_id;
    }
}