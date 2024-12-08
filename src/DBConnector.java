import java.sql.*;
import java.util.ArrayList;

public class DBConnector {
    private Connection conn;

    public void connect(String url) {
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void registerUser(String username, String password, String sex, int age, int height, float weight) {
        String sql = "INSERT INTO users (username, password, sex, age, height, weight) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, sex);
            stmt.setInt(4, age);
            stmt.setInt(5, height);
            stmt.setFloat(6, weight);

            stmt.executeUpdate();  // Execute the insert
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean userExistsInDatabase(String username) {
        if(conn == null) {
            System.out.println("no database connection");
            return false;
        }
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.getInt(1) > 0;  // Returns true if user exists
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean isValidLogin(String username, String password) {
        String query = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getString("password").equals(password)) {
                return true;  // Credentials are correct
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;  // Invalid username or password
    }

    public User getUserDetails(String username) {
        String query = "SELECT username, password, sex, age, height, weight FROM users WHERE username = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Extracting the data from the ResultSet
                String password = rs.getString("password");
                String sex = rs.getString("sex");
                int age = rs.getInt("age");
                int height = rs.getInt("height");
                float weight = rs.getFloat("weight");

                // Return a new User object with all details
                return new User(username, password, sex, age, height, weight);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;  // If no user is found, return null
    }

    public boolean isUserTableEmpty() {
        if (conn == null) {
            System.out.println("Database connection is not established.");
            return true;
        }
        String query = "SELECT COUNT(*) FROM users";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            return rs.getInt(1) == 0;  // Returns true if table is empty
        } catch (SQLException e) {
            System.out.println("Error checking if table is empty: " + e.getMessage());
            return true;
        }
    }




    /*
    public ArrayList<String> selectPlayers(){
        // initialize a List to return the selected data as string elements
        ArrayList<String> data = new ArrayList<>();
        // make the query string
        String sql = "SELECT name, balance, position FROM Players";

        try {
            Statement stmt = conn.createStatement();

            // execute the query
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                //read each row of the result set ( = response from the query execution)
                String row = rs.getString("name") + ", " + rs.getInt("balance")+", "+ rs.getInt("position");
                //add the string to the ArrayList
                data.add(row);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }

    public ArrayList<String> selectFields() {

        // initialize a List to return the selected data as string elements
        ArrayList<String> data = new ArrayList<>();

        String sql = "SELECT label, field_type, cost, income FROM Fields";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String label = rs.getString("label");
                String field_type = rs.getString("field_type");
                int cost = rs.getInt("cost");
                int income = rs.getInt("income");
                String field = label+", "+field_type+", "+income;
                data.add(field);
            }
        }catch(SQLException exception){
        }
        return data;
    }
*/

}