package TradingSystem.server.Domain.UserModule;

import TradingSystem.server.DAL.HibernateUtils;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Exception.MarketSecuirtyException;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
public class PremiumSecurity extends Security{
    private String question;
    private String answer;

    // ------------------------------ constructors ------------------------------
    public PremiumSecurity(String password, String question, String answer) {
        super(password);
        this.question = question;
        this.answer = answer;
    }

    public PremiumSecurity() {
    }

    // ------------------------------ getters ------------------------------
    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    // ------------------------------ setters ------------------------------

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    // ------------------------------ methods ------------------------------

    public boolean isImproved(){
        return true;
    }

    public String find_question() throws MarketException {
        return question;
    }

    public void verify_answer(String answer) throws MarketSecuirtyException {
        if(!this.answer.equals(answer)) throw new MarketSecuirtyException("Incorrect answer for the security question");
    }

    public void check_improvable() throws MarketSecuirtyException { throw new MarketSecuirtyException("Cannot improve premium security");}

    public void merge(){
        PremiumSecurity load = HibernateUtils.getEntityManager().find(this.getClass(),getSecurity_id());
        HibernateUtils.getEntityManager().merge(load);
    }
}
