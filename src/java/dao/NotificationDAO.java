/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Notification;

/**
 *
 * @author 84354
 */
public class NotificationDAO extends dal.DBContext {

    public ArrayList<Notification> getNotificationsByAccount(String account) {
    ArrayList<Notification> notifications = new ArrayList<>();
    try {
        String query = "SELECT Notifications.*, Registrations.*, Locations.*, ExamSchedules.*, Subjects.*\n"
                + "FROM Notifications INNER JOIN\n"
                + "Registrations ON Notifications.RegistrationId = Registrations.RegistrationId INNER JOIN\n"
                + "ExamSchedules ON Registrations.ExamId = ExamSchedules.ExamId INNER JOIN\n"
                + "Locations ON ExamSchedules.LocationID = Locations.LocationID INNER JOIN\n"
                + "Subjects ON ExamSchedules.SubjectID = Subjects.SubjectID\n"
                + "WHERE Notifications.Account = ?";
        
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, account);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String notificationId = String.valueOf(resultSet.getInt("NotificationId"));
            String registration = resultSet.getString(7) + " join in exam "
                    + resultSet.getString("SubjectName") + " at "
                    + String.valueOf(resultSet.getDate("ExamDate")) + " (From  "
                    + String.valueOf(resultSet.getTime("StartAt")) + " To "
                    + String.valueOf(resultSet.getTime("EndAt")) + ") in "
                    + resultSet.getString("Room") + ", "
                    + resultSet.getString("SiteName") + ", "
                    + resultSet.getString("ClusterName");
            String notificationMessage = resultSet.getString("NotificationMessage");
            String status = String.valueOf(resultSet.getBoolean("Status"));
            Notification notification = new Notification(notificationId, account, notificationMessage, registration, status);
            notifications.add(notification);
        }
        resultSet.close();
        statement.close();
    } catch (SQLException ex) {
        ex.getMessage();
    }
    return notifications;
}

public void addNotification(Notification notification) {
    try {
        String query = "INSERT INTO dbo.Notifications (Account, RegistrationId, NotificationMessage, Status) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, notification.getUser());
        statement.setInt(2, Integer.parseInt(notification.getRegistration()));
        statement.setString(3, notification.getMessage());
        statement.setBoolean(4, Boolean.parseBoolean(notification.getStatus()));
        statement.executeUpdate();
        statement.close();
    } catch (NumberFormatException | SQLException ex) {
        ex.printStackTrace();
    }
}

public void updateNotification(Notification notification) {
    try {
        String query = "UPDATE dbo.Notifications SET Account = ?, NotificationMessage = ?, Status = ? WHERE NotificationId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, notification.getUser());
        statement.setString(2, notification.getMessage());
        statement.setBoolean(3, Boolean.parseBoolean(notification.getStatus()));
        statement.setInt(4, Integer.parseInt(notification.getId()));
        statement.executeUpdate();
        statement.close();
    } catch (SQLException ex) {
        ex.getMessage();
    }
}

public void deleteNotification(String notificationId) {
    try {
        String query = "DELETE FROM dbo.Notifications WHERE NotificationId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, Integer.parseInt(notificationId));
        statement.executeUpdate();
        statement.close();
    } catch (SQLException ex) {
        ex.getMessage();
    }
}

public Notification getNotificationById(String notificationId) {
    Notification notification = null;
    try {
        String query = "SELECT * FROM dbo.Notifications WHERE NotificationId = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, Integer.parseInt(notificationId));
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            String account = resultSet.getString("Account");
            String notificationMessage = resultSet.getString("NotificationMessage");
            String registration = String.valueOf(resultSet.getInt("RegistrationId"));
            String status = String.valueOf(resultSet.getBoolean("Status"));
            notification = new Notification(notificationId, account, notificationMessage, registration, status);
        }
        resultSet.close();
        statement.close();
    } catch (SQLException ex) {
        ex.getMessage();
    }
    return notification;
}
