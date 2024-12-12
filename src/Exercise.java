public class Exercise {
    int exerciseID;
    String exerciseName;
    int sets;
    int reps;
    float weight;
    float restTime;
    String muscleType;

    public Exercise(int exerciseID, String exerciseName, int sets, int reps, float weight, float restTime, String muscleType){
        this.exerciseID = exerciseID;
        this.exerciseName = exerciseName;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.restTime = restTime;
        this.muscleType = muscleType;
    }
    public String getExerciseName(){
        return exerciseName;
    }
    public int getExerciseID(){
        return exerciseID;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public float getWeight() {
        return weight;
    }

    public float getRestTime() {
        return restTime;
    }


    public String getMuscleType() {
        return muscleType;
    }

}

//2