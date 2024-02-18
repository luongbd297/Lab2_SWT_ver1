package dao;

import dal.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Location;

public class LocationDAO extends DBContext {

    public ArrayList<Location> getAllLocations() {
        ArrayList<Location> locations = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Locations";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String locationID = String.valueOf(rs.getInt("LocationID"));
                String clusterName = rs.getString("ClusterName");
                String siteName = rs.getString("SiteName");
                String room = rs.getString("Room");

                Location location = new Location(locationID, clusterName, siteName, room);
                locations.add(location);
            }

            rs.close();
            statement.close();
        } catch (SQLException ex) {
            ex.getMessage();
        }

        return locations;
    }

    public Location getLocationByID(String locationID) {
        Location location = null;

        try {
            String sql = "SELECT * FROM Locations WHERE LocationID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(locationID));
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String clusterName = rs.getString("ClusterName");
                String siteName = rs.getString("SiteName");
                String room = rs.getString("Room");

                location = new Location(locationID, clusterName, siteName, room);
            }

            rs.close();
            statement.close();
        } catch (SQLException ex) {
            ex.getMessage();
        }

        return location;
    }

    public void addLocation(Location location) {
        try {
            String sql = "INSERT INTO Locations (ClusterName, SiteName, Room) VALUES (?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, location.getCluster());
            statement.setString(2, location.getSite());
            statement.setString(3, location.getRoom());
            statement.executeUpdate();

            statement.close();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public void updateLocation(Location location) {
        try {
            String sql = "UPDATE Locations SET ClusterName = ?, SiteName = ?, Room = ? WHERE LocationID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, location.getCluster());
            statement.setString(2, location.getSite());
            statement.setString(3, location.getRoom());
            statement.setInt(4, Integer.parseInt(location.getId()));
            statement.executeUpdate();

            statement.close();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }

    public void deleteLocation(int locationID) {
        try {
            String sql = "DELETE FROM Locations WHERE LocationID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, locationID);
            statement.executeUpdate();

            statement.close();
        } catch (SQLException ex) {
            ex.getMessage();
        }
    }
}
