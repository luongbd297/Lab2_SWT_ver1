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
import model.Subject;

public class SubjectDAO extends DBContext {

    public ArrayList<Subject> getAllSubjects() {
        ArrayList<Subject> subjects = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Subjects";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String subjectID = String.valueOf(rs.getInt("SubjectID"));
                String subjectName = rs.getString("SubjectName");

                Subject subject = new Subject(subjectID, subjectName);
                subjects.add(subject);
            }

            rs.close();
            statement.close();
        } catch (SQLException ex) {
            ex.getMessage();
        }

        return subjects;
    }

    public Subject getSubjectByID(String subjectID) {
        Subject subject = null;

        try {
            String sql = "SELECT * FROM Subjects WHERE SubjectID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(subjectID));
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String subjectName = rs.getString("SubjectName");

                subject = new Subject(subjectID, subjectName);
            }

            rs.close();
            statement.close();
        } catch (SQLException ex) {
            ex.getMessage();
        }

        return subject;
    }

    public void addSubject(Subject subject) {
        try {
            String sql = "INSERT INTO Subjects (SubjectName) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, subject.getName());
            statement.executeUpdate();

            statement.close();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public void updateSubject(Subject subject) {
        try {
            String sql = "UPDATE Subjects SET SubjectName = ? WHERE SubjectID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, subject.getName());
            statement.setInt(2, Integer.parseInt(subject.getId()));
            statement.executeUpdate();

            statement.close();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public void deleteSubject(int subjectID) {
        try {
            String sql = "DELETE FROM Subjects WHERE SubjectID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, subjectID);
            statement.executeUpdate();

            statement.close();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }
}
