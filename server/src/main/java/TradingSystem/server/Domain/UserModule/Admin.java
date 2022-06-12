package TradingSystem.server.Domain.UserModule;

import TradingSystem.server.Domain.Utils.Exception.AdminException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("2")
public class Admin extends AssignUser {
    public Admin(String email, String pw, String name, String lastName) {
        super(email, pw, name, lastName);
    }


    @Override
    public void check_admin_permission() { }

    public Admin() { }

    @Override
    public void unregister(String password) throws AdminException {
        throw new AdminException("admin cannot unregister from system.");
    }
    public UserState find_state(){return UserState.ADMIN;}

    public Admin is_admin(){ return this;}

}
