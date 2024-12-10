public class Main {
    public static void main(String[] args) {
        DBConnector dbConnector = new DBConnector();
        dbConnector.connect("jdbc:sqlite:identifier.sqlite");

        ExerciseCreator exerciseCreator = new ExerciseCreator(dbConnector);

        try{
        Exercise exercise = new Exercise(4,"Bench",0,0,0,0,"Chest");
        //exerciseCreator.addExercise(exercise);
        //exerciseCreator.deleteExercise(3);
        exerciseCreator.editExercise(exercise);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        Application application = new Application("Not Sats");
        application.startApplication();
    }
}

