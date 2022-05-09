package TradingSystem.server.Domain.UserModule;

import TradingSystem.server.Domain.Utils.Exception.AdminException;

public class Admin extends AssignUser {
    public Admin(String email, String pw, String name, String lastName) {
        super(email, pw, name, lastName);
    }

    @Override
    public void check_admin_permission() {
    }

    @Override
    public void unregister(String password) throws AdminException {
        throw new AdminException("admin cannot unregister from system.");
    }
}
