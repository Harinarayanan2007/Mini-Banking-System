package service;

import dao.UserDAO;
import model.User;
import util.PasswordUtil;

public class AuthService {
    private UserDAO userDAO = new UserDAO();

    public User login(String email, String password) throws Exception {
        User user = userDAO.findByEmail(email);
        if (user == null || !PasswordUtil.verify(password, user.getPasswordHash())) {
            throw new Exception("Invalid credentials");
        }
        if ("CUSTOMER".equals(user.getRole()) && !"ACTIVE".equals(user.getStatus())) {
            throw new Exception("Account not yet approved.");
        }
        return user;
    }
}
