package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserDAO extends dal.DBContext {

    public UserDAO() {
    }

    public void createUser(User user) {
        String query = "INSERT INTO dbo.Users (Account, Password, UserName, Role) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getAccount());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getUserName());
            statement.setString(4, user.getRole());
            statement.execute();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public User getUserByAccount(String account) {
        String query = "SELECT * FROM dbo.Users WHERE Account = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, account);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String password = rs.getString("Password");
                String userName = rs.getString("UserName");
                String email = rs.getString("Email");
                String phone = rs.getString("Phone");
                String address = rs.getString("Address");
                String role = rs.getString("Role");
                return new User(account, password, userName, email, phone, address, role);
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return null;
    }

    public List<User> getAllUsers() {
        String query = "SELECT * FROM dbo.Users";
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String account = rs.getString("Account");
                String password = rs.getString("Password");
                String userName = rs.getString("UserName");
                String email = rs.getString("Email");
                String phone = rs.getString("Phone");
                String address = rs.getString("Address");
                String role = rs.getString("Role");
                User user = new User(account, password, userName, email, phone, address, role);
                users.add(user);
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return users;
    }

    public void updateUser(User user) {
        String query = "UPDATE dbo.Users SET Password = ?, UserName = ?, Email = ?, Phone = ?, Address = ?, Role = ? WHERE Account = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getPassword());
            statement.setString(2, user.getUserName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhone());
            statement.setString(5, user.getAddress());
            statement.setString(6, user.getRole());
            statement.setString(7, user.getAccount());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public void deleteUser(String account) {
        String query = "DELETE FROM dbo.Users WHERE Account = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, account);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public boolean isAdmin(String account) {
        String query = "SELECT Role FROM dbo.Users WHERE Account = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, account);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                String role = rs.getString("Role");
                return "Admin".equals(role);
            }
        } catch (SQLException ex) {
            ex.getMessage();
        }
        return false;
    }
}
