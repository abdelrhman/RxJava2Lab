package chapter3;

/**
 * User Object model
 */
public class User {
    private String fname;
    private String lname;

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public User(String fname, String lname) {

        this.fname = fname;
        this.lname = lname;
    }
}
