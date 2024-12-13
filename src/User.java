public class User {
    private String username;
    private String password;
    private String sex;
    private int age;
    private int height;
    private float weight;

    public User(String username, String password, String sex, int age, int height, float weight) {
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
