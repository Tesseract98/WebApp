package resources;

public class TestResource {
    private String name;
    private int age;

    public TestResource(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public TestResource() {
        this("", 0);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

}
