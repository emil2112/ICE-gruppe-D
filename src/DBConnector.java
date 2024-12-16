import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnector {
    private Connection conn;
    private TextUI ui = new TextUI();

    public void connect(String url) {
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void registerUser(String username, String password, String sex, int age, int height, float weight) {
        String sql = "INSERT INTO Users (username, password, sex, age, height, weight) " +
                "VALUES ('" + username + "', '" + password + "', '" + sex + "', '" + age + "', '" + height + "', '" + weight + "')";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            //System.out.println(e.getMessage()); Ved ikke hvorfor den altid catcher den her. Men det virker :')
        }
    }



    public boolean userExists(String username) {
        String sql = "SELECT username FROM Users WHERE username = '" + username + "'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return rs.next();
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean validLogin(String username, String password) {
        String sql = "SELECT password FROM users WHERE username =" + "'" + username + "'";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next() && rs.getString("password").equals(password)) { // Hvis der er en row og password passer med password i den row
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }



    public User getUserData(String username) {
        String sql = "SELECT username, password, sex, age, height, weight FROM users WHERE username = '" + username + "'";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            if(rs.next()) { // Læser dataen fra den row og lægger det i variabler. Returns ny User med de variabler.
                String password = rs.getString("password");
                String sex = rs.getString("sex");
                int age = rs.getInt("age");
                int height = rs.getInt("height");
                float weight = rs.getFloat("weight");
                return new User(username, password, sex, age, height, weight);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int getWorkoutID(String name) {
        String sql = "SELECT WorkoutID FROM WorkoutProgram WHERE workoutName = '" + name + "'";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                return rs.getInt("WorkoutID");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public void createCalendarTable(String username) {
        String sql = "CREATE TABLE " + username + "CalendarDecember2024(" +
                "dayID INT AUTO_INCREMENT PRIMARY KEY," +
                "programs INT," +
                "dayNumber int," +
                "Foreign KEY (programs) REFERENCES WorkoutProgram(WorkoutID)" +
                ")";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void registerWorkoutDay(int workoutID, int day, String username) {
        String sql = "INSERT INTO " + username + "CalendarDecember2024 (programs, dayNumber)" +
                "VALUES ('" + workoutID + "', '" + day + "')";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeWorkoutDay(int day, String username) {
        String sql = "DELETE FROM " + username + "CalendarDecember2024 WHERE dayNumber = '" + day + "'";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<String> getworkoutNames(String username) {
        ArrayList<String> workoutNames = new ArrayList<>();
        for(int i = 1; i <= 3; i++) {
            String sql = "SELECT WorkoutProgram.workoutName FROM Users " +
                    "JOIN WorkoutProgram ON Users.WorkoutProgram" + i + " = WorkoutProgram.workoutID " +
                    "WHERE username = '" + username + "'";
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    workoutNames.add(rs.getString("workoutName"));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return workoutNames;
    }

    public boolean hasWorkout(int day, String username) {
        String sql = "SELECT COUNT(*) FROM " + username + "CalendarDecember2024 WHERE dayNumber = '" + day + "'";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public String getWorkoutName(int day, String username) {
        String sql = "SELECT WorkoutProgram.workoutName FROM " + username + "CalendarDecember2024 c "+
                "JOIN WorkoutProgram ON c.programs = WorkoutProgram.workoutID " +
                "WHERE c.dayNumber = '" + day + "'";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            return rs.getString("workoutName");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<>();
        String sql = "SELECT ExerciseID, ExerciseName, Sets, Reps, Weight, RestTime, MuscleType FROM Exercise";

        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String exerciseName = rs.getString("ExerciseName");
                int sets = rs.getInt("Sets");
                int reps = rs.getInt("Reps");
                float weight = rs.getFloat("Weight");
                float restTime = rs.getFloat("RestTime");
                String muscleType = rs.getString("MuscleType");

                exercises.add(new Exercise(exerciseName, sets, reps, weight, restTime, muscleType));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving exercises: " + e.getMessage());
        }
        return exercises;
    }

    public List<Exercise> getProgramExercises(String workoutName){
        List<Exercise> exercises = new ArrayList<>();
        for(int i = 1; i <= 3; i++) {
            String sql = "SELECT Exercise.ExerciseName, Exercise.Sets, Exercise.Reps, Exercise.Weight, Exercise.RestTime, Exercise.MuscleType " +
                    "FROM WorkoutProgram JOIN Exercise ON WorkoutProgram.ExerciseID"+i+" = Exercise.ExerciseID WHERE WorkoutName = '"+workoutName+"'";

            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    String exerciseName = rs.getString("ExerciseName");
                    int sets = rs.getInt("Sets");
                    int reps = rs.getInt("Reps");
                    float weight = rs.getFloat("Weight");
                    float restTime = rs.getFloat("RestTime");
                    String muscleType = rs.getString("MuscleType");

                    exercises.add(new Exercise(exerciseName, sets, reps, weight, restTime, muscleType));
                }
            } catch (SQLException e) {
                System.out.println("Error retrieving exercises: " + e.getMessage());
            }
        }
        return exercises;
    }

    // Method to get the current connection
    public Connection getConnection() {
        return conn;
    }

    public ArrayList<String> displayPrograms(String username){
        ArrayList<String> workoutNames = new ArrayList<>();
        for(int i = 1; i <= 3; i++) {
            String sql = "SELECT WorkoutProgram.workoutName FROM Users " +
                    "JOIN WorkoutProgram ON Users.WorkoutProgram" + i + " = WorkoutProgram.workoutID " +
                    "WHERE username = '" + username + "'";

            try{
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()) {
                    workoutNames.add(rs.getString("workoutName"));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return workoutNames;
    }
    public void saveWorkoutProgram(WorkoutProgram workoutProgram, User currentUser) {
        String workoutName = ui.promptText("Enter a name for your workout program:");

        String insertWorkoutProgramSQL = "INSERT INTO WorkoutProgram (workoutName, ExerciseID1, ExerciseID2, ExerciseID3, ExerciseID4, ExerciseID5) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertWorkoutProgramSQL, Statement.RETURN_GENERATED_KEYS)) {

            // get selected exercises
            List<Exercise> exercises = workoutProgram.getSelectedExercises();
            Integer[] exerciseIds = new Integer[5]; // Array for Exercise IDs

            // get Exercise IDs for each exercise in the list
            for (int i = 0; i < exercises.size(); i++) {
                String exerciseName = exercises.get(i).getExerciseName();
                int exerciseId = getExerciseIdByName(exerciseName); // Method to get Exercise ID by name
                exerciseIds[i] = exerciseId != -1 ? exerciseId : null;  // Store ID or null
            }

            stmt.setString(1, workoutName);
            for (int i = 0; i < 5; i++) {
                if (i < exercises.size() && exerciseIds[i] != null) {
                    // Set Exercise ID for valid entries, i+2 so we jump over WorkoutID and workoutName
                    stmt.setInt(i + 2, exerciseIds[i]);
                } else {
                    stmt.setNull(i + 2, Types.INTEGER); // Set NULL for empty slots
                }
            }

            int rowsAffected = stmt.executeUpdate();
            // get generated workoutID
            if (rowsAffected > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int workoutId = rs.getInt(1);

                        associateWorkoutProgramWithUser(currentUser, workoutId);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error saving workout program: " + e.getMessage());
        }
    }

    private void associateWorkoutProgramWithUser(User currentUser, int workoutId) {
        String fetchUserSQL = "SELECT WorkoutProgram1, WorkoutProgram2, WorkoutProgram3 FROM Users WHERE username = ?";
        String updateUserSQL = "UPDATE Users SET %s = ? WHERE username = ?";

        try (PreparedStatement fetchStmt = conn.prepareStatement(fetchUserSQL)) {
            fetchStmt.setString(1, currentUser.getUsername());

            try (ResultSet rs = fetchStmt.executeQuery()) {
                if (rs.next()) {
                    String columnToUpdate = null;

                    // Check which column is empty and pick the first available one
                    if (rs.getObject("WorkoutProgram1") == null) {
                        columnToUpdate = "WorkoutProgram1";
                    } else if (rs.getObject("WorkoutProgram2") == null) {
                        columnToUpdate = "WorkoutProgram2";
                    } else if (rs.getObject("WorkoutProgram3") == null) {
                        columnToUpdate = "WorkoutProgram3";
                    }

                    if (columnToUpdate != null) {
                        // Update the selected column with the new workout program ID
                        try (PreparedStatement updateStmt = conn.prepareStatement(String.format(updateUserSQL, columnToUpdate))) {
                            updateStmt.setInt(1, workoutId);
                            updateStmt.setString(2, currentUser.getUsername());
                            updateStmt.executeUpdate();
                            System.out.println("Workout program associated with user in " + columnToUpdate);
                        }
                    } else {
                        // All slots are occupied
                        System.out.println("User already has 3 workout programs. Cannot add more.");
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error associating workout program with user: " + e.getMessage());
        }
    }





    public int getExerciseIdByName(String exerciseName) {
        String sql = "SELECT ExerciseID FROM Exercise WHERE ExerciseName = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, exerciseName);  // Use the exercise name to fetch the ID
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("ExerciseID");  // Return the exercise ID from the query
            } else {
                System.out.println("Error fetching exercise ID: Exercise with name '" + exerciseName + "' not found.");
                return -1;  // Return -1 or handle as needed if exercise is not found
            }
        } catch (SQLException e) {
            System.out.println("Error fetching exercise ID: " + e.getMessage());
            return -1;
        }
    }


    public void setNewUsername(String newUsername, String oldUsername) {
        String sql = "UPDATE Users SET username = '" + newUsername + "' WHERE username = '" + oldUsername + "'";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void setNewPassword(String newPassword, String username) {
        String sql = "UPDATE Users Set password = '" + newPassword + "' WHERE username = '" + username + "'";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void changeCalendarTableName(String newUsername, String oldUsername) {
        String sql = "ALTER TABLE " + oldUsername + "CalendarDecember2024 RENAME TO " + newUsername +"CalendarDecember2024";

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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

//2