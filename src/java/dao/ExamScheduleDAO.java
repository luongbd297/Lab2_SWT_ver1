package dao;

/**
 *
 * @author 84354
 */
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.ExamSchedule;

public List<ExamSchedule> getAllExamSchedules() {
    List<ExamSchedule> examSchedules = new ArrayList<>();
    String query = "SELECT * FROM  ExamSchedules INNER JOIN Subjects \n"
            + "ON ExamSchedules.SubjectID = Subjects.SubjectID INNER JOIN \n"
            + "Locations ON ExamSchedules.LocationID = Locations.LocationID";

    PreparedStatement stmt = null;
    ResultSet rs = null;

    try {
        stmt = connection.prepareStatement(query);
        rs = stmt.executeQuery();

        while (rs.next()) {
            String examId = String.valueOf(rs.getInt("ExamId"));
            String subject = rs.getString("SubjectName");
            String examDate = rs.getString("ExamDate");
            String location = rs.getString("Room") + ", " + rs.getString("SiteName") + ", " + rs.getString("ClusterName");
            String startTime = rs.getString("StartAt");
            String endTime = rs.getString("EndAt");

            ExamSchedule examSchedule = new ExamSchedule(examId, subject, examDate, location, startTime, endTime);
            examSchedules.add(examSchedule);
        }
    } catch (SQLException ex) {
        ex.getMessage();
    } finally {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                ex.getMessage();
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException ex) {
                ex.getMessage();
            }
        }
    }

    return examSchedules;
}

    public void addExamSchedule(ExamSchedule examSchedule) {
        String query = "INSERT INTO dbo.ExamSchedules (SubjectID, ExamDate, LocationID, StartAt, EndAt) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(examSchedule.getSubject()));
            stmt.setString(2, examSchedule.getDate());
            stmt.setInt(3, Integer.parseInt(examSchedule.getLocation()));
            stmt.setString(4, examSchedule.getStart());
            stmt.setString(5, examSchedule.getEnd());

            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public void deleteExamSchedule(String examId) {
        String query = "DELETE FROM ExamSchedules WHERE ExamId = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, examId);
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public ExamSchedule getExamScheduleById(String examId) {
        ExamSchedule examSchedule = null;
        String query = "SELECT * FROM  ExamSchedules INNER JOIN Subjects \n"
                + "ON ExamSchedules.SubjectID = Subjects.SubjectID INNER JOIN \n"
                + "Locations ON ExamSchedules.LocationID = Locations.LocationID WHERE ExamId = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(examId));
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String subject = rs.getString("SubjectName");
                String examDate = rs.getString("ExamDate");
                String location = rs.getString("Room") + ", " + rs.getString("SiteName") + ", " + rs.getString("ClusterName");
                String startTime = rs.getString("StartAt");
                String endTime = rs.getString("EndAt");

                examSchedule = new ExamSchedule(examId, subject, examDate, location, startTime, endTime);
            }

        } catch (SQLException ex) {
            ex.getMessage();
        }

        return examSchedule;
    }

    public void updateExamSchedule(ExamSchedule examSchedule) {
        String query = "UPDATE dbo.ExamSchedules SET SubjectID = ?, ExamDate = ?, LocationID = ?, StartAt = ?, EndAt = ? WHERE ExamId = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, Integer.parseInt(examSchedule.getSubject()));
            stmt.setString(2, examSchedule.getDate());
            stmt.setInt(3, Integer.parseInt(examSchedule.getLocation()));
            stmt.setString(4, examSchedule.getStart());
            stmt.setString(5, examSchedule.getEnd());
            stmt.setInt(6, Integer.parseInt(examSchedule.getId()));

            stmt.executeUpdate();

        } catch (SQLException ex) {
            ex.getMessage();
        }
    }
}
