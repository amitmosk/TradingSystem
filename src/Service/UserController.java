package Service;

import java.util.HashMap;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserController {
    private Map<String,User> users;             // email,user
    private Map<Integer,User> onlineUsers;       // id,user
    private int ID;


    private static class SingletonHolder{
        private static UserController instance = new UserController();
    }

    public UserController() {
        this.ID = 0;
        this.users = Collections.synchronizedMap(new HashMap<String,User>()) ;       //thread safe
        this.onlineUsers = Collections.synchronizedMap(new HashMap<Integer,User>()) ; //thread safe
    }

    public static UserController getInstance(){
        return SingletonHolder.instance;
    }

    public int guest_login() {
        User newUser = new User();
        onlineUsers.put(++ID,newUser);
        return ID;
    }

    private boolean isRegistered(String email){
        return users.containsKey(email);
    }

    private boolean isOnline(int id){
        return onlineUsers.containsKey(id);
    }

    public  void register(String email, String pw, String name, String lastName) {
/*       //todo rewrite method and check how to implement
        if(isRegistered(email)) return null; //todo check if can throw exeptions
        boolean succeed = register(email,pw,name,lastName);
        if (succeed) users.put(email,guest);
        return new User();*/
    }

    public boolean login(int ID,String email, String password) {
        if(isRegistered(email) && users.get(email).login(password)){
            onlineUsers.remove(ID);
            User user = users.get(email);
            onlineUsers.put(ID,user);
            return true;
        }
        return false;
    }

    public int view_user_cart(int user) {
        if(!isOnline(user)) return -1; //todo
        onlineUsers.get(user).view_cart();
        return 1;//todo view products
    }

    //todo add/edit/remove items in cart

    //todo check with amit and tom about policies and add requirments 2.5


}
