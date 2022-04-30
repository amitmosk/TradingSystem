package Domain.UserModule;

public class Admin extends AssignUser{
    public Admin(String email, String pw, String name, String lastName) {
        super(email, pw, name, lastName);
    }

    @Override
    public void check_admin_permission() {}

    @Override
    public void unregister(String password) throws Exception {
        throw new Exception("admin cannot unregister from system.");
    }
}
