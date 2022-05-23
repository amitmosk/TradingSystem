package TradingSystem.server.Domain.UserModule;

import TradingSystem.server.Domain.Utils.Exception.MarketException;

import java.util.List;



public class UserInformation {
    private UserState state;
    private String email;
    private String name;
    private String lastName;
    private String birth_date;
    private CartInformation cart;
    private List<Integer> storesManaged;
    private String security_question;

    public UserInformation(){}

    public UserInformation(User user) throws MarketException {
        this.email = user.user_email();
        this.state = user.find_state();
        this.name = user.user_name();
        this.lastName = user.user_last_name();
        this.birth_date = user.getBirth_date();
        this.cart = user.getCart().cartInformation();
        this.storesManaged = user.stores_managers_list();
        try {
            this.security_question = user.user_security_question();
        }
        catch (Exception e){
            this.security_question = "";

        }
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public void setCart(CartInformation cart) {
        this.cart = cart;
    }

    public void setStoresManaged(List<Integer> storesManaged) {
        this.storesManaged = storesManaged;
    }

    public void setSecurity_question(String security_question) {
        this.security_question = security_question;
    }

    public UserState getState() {
        return state;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public CartInformation getCart() {
        return cart;
    }

    public List<Integer> getStoresManaged() {
        return storesManaged;
    }

    public String getSecurity_question() {
        return security_question;
    }
}
