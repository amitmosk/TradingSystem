package TradingSystem.server.Domain.UserModule;

import TradingSystem.server.Domain.Utils.Exception.LoginException;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Exception.SecuirtyException;

public class Security {
    protected String password;

    public Security(String password) {
        this.password = password;
    }


    public void check_password(String password) throws SecuirtyException {
        if (!password.equals(this.password)) throw new SecurityException("password does not match to current password");
    }

    public void edit_password(String old_password, String password) throws SecuirtyException {
        if (!old_password.equals(this.password))
            throw new SecurityException("password does not match to current password");
        this.password = password;
    }

    public boolean isImproved() {
        return false;
    }

    public String get_question() throws MarketException {
        throw new SecurityException("Only premium security has security question");
    }

    public void verify_answer(String answer) throws MarketException {
        throw new SecurityException("Only premium security has security question");
    }

    public void check_improvable() throws SecuirtyException {
    }
}
