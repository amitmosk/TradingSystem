package Service;

public class User {
    private AssignState state;
    private Cart cart;
    private boolean isGuest;
    private boolean loggedIn;
    public User() { // new login guest
        this.state = new Guest();
        this.cart = new Cart();
        isGuest = true;
    }

    public void buy(){

    }

    private boolean emailCheck(String email){
        return false;
    }

    private boolean passwordCheck(String pw){
        return false;
    }

    private boolean nameCheck(String name){
        return false;
    }

    public boolean register(String email, String pw, String name, String lastName) {
        if(!isGuest) return false;
        if(!emailCheck(email) || !passwordCheck(pw) || !nameCheck(name) || !nameCheck(lastName)) return false;
        this.state = new AssignUser(email,pw,name,lastName);
        return true;
    }

    public boolean login(String password) {
        boolean res = this.state.login(password);
        if(res) isGuest = false;
        return res;
    }

    public void view_cart() {
        //todo implement
    }
}
