import java.sql.*;

public class ExerciseCreator {
    private DBConnector dbConnector;


    public ExerciseCreator(DBConnector dbConnector) {
        this.dbConnector = dbConnector;
    }


    public void addExercise(Exercise exercise) throws SQLException {
        String sql = "INSERT INTO Exercise (exerciseName, sets, reps, weight, restTime, muscleType) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, exercise.getExerciseName());
            statement.setInt(2, exercise.getSets());
            statement.setInt(3, exercise.getReps());
            statement.setFloat(4, exercise.getWeight());
            statement.setFloat(5, exercise.getRestTime());
            statement.setString(6, exercise.getMuscleType());
            statement.executeUpdate();
        }
    }

    public void deleteExercise(int exerciseID) throws SQLException {
        String sql = "DELETE FROM Exercise WHERE exerciseID = ?";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, exerciseID);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Exercise with ID " + exerciseID + " has been removed.");
            } else {
                System.out.println("No exercise found with ID " + exerciseID + ".");
            }
        } catch (SQLException e) {
            System.out.println("Error removing exercise: " + e.getMessage());
        }
    }

    public void editExercise(Exercise updatedExercise) throws SQLException {
        String sql = "UPDATE Exercise SET exerciseName = ?, sets = ?, reps = ?, weight = ?, restTime = ?, muscleType = ? WHERE exerciseID = ?";

        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, updatedExercise.getExerciseName());
            statement.setInt(2, updatedExercise.getSets());
            statement.setInt(3, updatedExercise.getReps());
            statement.setFloat(4, updatedExercise.getWeight());
            statement.setFloat(5, updatedExercise.getRestTime());
            statement.setString(6, updatedExercise.getMuscleType());
            statement.setInt(7, updatedExercise.getExerciseID());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Exercise with ID " + updatedExercise.getExerciseID() + " has been updated.");
            } else {
                System.out.println("No exercise found with ID " + updatedExercise.getExerciseID());
            }
        } catch (SQLException e) {
            System.out.println("Error updating exercise: " + e.getMessage());
        }
    }



    /*
    public Exercise getExerciseById(int id) throws SQLException {
        String sql = "SELECT * FROM Exercise WHERE exerciseID = ?";
        try (Connection connection = dbConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Exercise(
                            resultSet.getInt("exerciseID"),
                            resultSet.getString("exerciseName"),
                            resultSet.getInt("sets"),
                            resultSet.getInt("reps"),
                            resultSet.getFloat("weight"),
                            resultSet.getFloat("restTime"),
                            resultSet.getString("muscleType")
                    );
                }
            }
        }
        return null; // Return null if no exercise is found
    }

    public List<Exercise> getAllExercises() throws SQLException {
        String sql = "SELECT * FROM Exercise";
        List<Exercise> exercises = new ArrayList<>();

        try (Connection connection = dbConnector.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Exercise exercise = new Exercise(
                        resultSet.getInt("exerciseID"),
                        resultSet.getString("exerciseName"),
                        resultSet.getInt("sets"),
                        resultSet.getInt("reps"),
                        resultSet.getFloat("weight"),
                        resultSet.getFloat("restTime"),
                        resultSet.getString("muscleType")
                );
                exercises.add(exercise);
            }
        }
        return exercises;
    }
    */
}

//2