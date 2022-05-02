package Domain.UserModule;
// TODO: add logger
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

    public String get_question() throws Exception {
        return question;
    }

    public void verify_answer(String answer) throws Exception {
        if(this.answer.equals(answer)) throw new Exception("Incorrect answer for the security question");
    }

    public void check_improvable() throws Exception { throw new Exception("Cannot improve premium security");}
}
