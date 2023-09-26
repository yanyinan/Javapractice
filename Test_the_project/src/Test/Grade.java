package Test;

/**
 * @title:
 * @author:nanzhou
 * @date:
 */
public class Grade {
    int id;
    String name;
    int age;

    public Grade(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public Grade() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
