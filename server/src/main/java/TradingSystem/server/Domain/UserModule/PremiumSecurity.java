package TradingSystem.server.Domain.UserModule;

import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Exception.MarketSecuirtyException;

public class PremiumSecurity extends Security{
    private String question;
    private String answer;

    public PremiumSecurity(String password, String question, String answer) {
        super(password);
        this.question = question;
        this.answer = answer;
    }

    public boolean isImproved(){
        return true;
    }

    public String get_question() throws MarketException {
        return question;
    }

    public void verify_answer(String answer) throws MarketSecuirtyException {
        if(!this.answer.equals(answer)) throw new MarketSecuirtyException("Incorrect answer for the security question");
    }

    public void check_improvable() throws MarketSecuirtyException { throw new MarketSecuirtyException("Cannot improve premium security");}
}
