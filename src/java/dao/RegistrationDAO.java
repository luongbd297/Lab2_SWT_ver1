/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author 84354
 */
import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Registration;

public class RegistrationDAO extends DBContext {

    public ArrayList<Registration> getAllRegistrations() {
        ArrayList<Registration> registrations = new ArrayList<>();

        try {
            String query = """
                           SELECT        ExamSchedules.*, Users.*, ExamSchedules_1.*, Locations.*, Subjects.*
                           FROM            ExamSchedules INNER JOIN
                                                    ExamSchedules AS ExamSchedules_1 ON ExamSchedules.ExamId = ExamSchedules_1.ExamId INNER JOIN
                                                    Subjects ON ExamSchedules.SubjectID = Subjects.SubjectID AND ExamSchedules_1.SubjectID = Subjects.SubjectID INNER JOIN
                                                    Locations ON ExamSchedules.LocationID = Locations.LocationID AND ExamSchedules_1.LocationID = Locations.LocationID CROSS JOIN
                                                    Users""";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String registrationId = String.valueOf(resultSet.getInt("RegistrationId"));
                String name = resultSet.getString("UserName");
                String exam = resultSet.getString("SubjectName") + ", "
                        + String.valueOf(resultSet.getDate("ExamDate"))
                        + " (From  " + String.valueOf(resultSet.getTime("StartAt")) + " To " + String.valueOf(resultSet.getTime("EndAt")) + ") in "
                        + resultSet.getString("Room") + ", " + resultSet.getString("SiteName") + ", " + resultSet.getString("ClusterName");
                String status = resultSet.getString("Status");

                Registration registration = new Registration(registrationId, name, exam, status);
                registrations.add(registration);
            }

            statement.close();
        } catch (SQLException e) {
            e.getMessage();
        }

        return registrations;
    }

    public ArrayList<Registration> getRegistrationByUser(String account) {
        ArrayList<Registration> registrations = new ArrayList<>();

        try {
            String query = "SELECT * FROM \n"
                    + "ExamSchedules INNER JOIN Subjects \n"
                    + "ON ExamSchedules.SubjectID = Subjects.SubjectID INNER JOIN Registrations \n"
                    + "ON ExamSchedules.ExamId = Registrations.ExamId INNER JOIN Locations\n"
                    + "ON ExamSchedules.LocationID = Locations.LocationID\n"
                    + "WHERE Registrations.Account = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, account);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = String.valueOf(resultSet.getInt("RegistrationId"));
                String exam = resultSet.getString("SubjectName") + ", "
                        + String.valueOf(resultSet.getDate("ExamDate"))
                        + " (From  " + String.valueOf(resultSet.getTime("StartAt")) + " To " + String.valueOf(resultSet.getTime("EndAt")) + ") in "
                        + resultSet.getString("Room") + ", " + resultSet.getString("SiteName") + ", " + resultSet.getString("ClusterName");
                String status = resultSet.getString("Status");
                Registration registration = new Registration(id, account, exam, status);
                registrations.add(registration);
            }

            statement.close();
        } catch (SQLException e) {
            e.getMessage();
        }

        return registrations;
    }

    public void addRegistration(Registration registration) {
        try {
            String query = "INSERT INTO dbo.Registrations (Account, ExamId, Status) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, registration.getUser());
            statement.setInt(2, Integer.parseInt(registration.getExam()));
            statement.setString(3, registration.getStatus());
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void updateRegistration(Registration registration) {
        try {
            String query = "UPDATE dbo.Registrations SET Account = ?, ExamId = ?, Status = ? WHERE RegistrationId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, registration.getUser());
            statement.setInt(2, Integer.parseInt(registration.getExam()));
            statement.setString(3, registration.getStatus());
            statement.setInt(4, Integer.parseInt(registration.getId()));
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public void deleteRegistration(String registrationId) {
        try {
            String query = "DELETE FROM dbo.Registrations WHERE RegistrationId = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, Integer.parseInt(registrationId));
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public Registration getRegistrationByAccountAndExamId(String account, String examId) {
        Registration registration = null;
        String query = "SELECT * FROM Registrations WHERE Account = ? AND ExamId = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, account);
            statement.setInt(2, Integer.parseInt(examId));

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Tạo đối tượng Registration từ kết quả truy vấn
                    registration = new Registration();
                    registration.setId(String.valueOf(resultSet.getInt("RegistrationId")));
                    registration.setUser(resultSet.getString("Account"));
                    registration.setExam(String.valueOf(resultSet.getInt("ExamId")));
                    registration.setStatus(resultSet.getString("Status"));
                }
            }
        } catch (SQLException e) {
            e.getMessage();
        }

        return registration;
    }

    public Registration getRegistrationById(String id) {
        Registration registration = null;
        String query = "SELECT * FROM Registrations WHERE [RegistrationId] = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, Integer.parseInt(id));

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    registration = new Registration();
                    registration.setId(String.valueOf(resultSet.getInt("RegistrationId")));
                    registration.setUser(resultSet.getString("Account"));
                    registration.setExam(String.valueOf(resultSet.getInt("ExamId")));
                    registration.setStatus(resultSet.getString("Status"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return registration;
    }

    public ArrayList<Registration> getRegistrationsByStatus(String status) {
        ArrayList<Registration> registrations = new ArrayList<>();

        try {
            String query = """
                           SELECT * FROM 
                           ExamSchedules INNER JOIN Subjects 
                           ON ExamSchedules.SubjectID = Subjects.SubjectID INNER JOIN Registrations 
                           ON ExamSchedules.ExamId = Registrations.ExamId INNER JOIN Locations
                           ON ExamSchedules.LocationID = Locations.LocationID
                           WHERE Registrations.Account = ?""";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, status);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = String.valueOf(resultSet.getInt("RegistrationId"));
                String exam = resultSet.getString("SubjectName") + ", "
                        + String.valueOf(resultSet.getDate("ExamDate"))
                        + " (From  " + String.valueOf(resultSet.getTime("StartAt")) + " To " + String.valueOf(resultSet.getTime("EndAt")) + ") in "
                        + resultSet.getString("Room") + ", " + resultSet.getString("SiteName") + ", " + resultSet.getString("ClusterName");
                String account = resultSet.getString("Account");
                Registration registration = new Registration(id, account, exam, status);
                registrations.add(registration);
            }

            statement.close();
        } catch (SQLException e) {
            e.getMessage();
        }

        return registrations;
    }
}
