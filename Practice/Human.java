public class Human {

    float height;
    String name;

    static void pri(Human h) {
        System.out.println(h.height);
        System.out.println(h.name);
    }

    Human(float height, String name) {
        this.height = height;
        this.name = name;
    }

    Human() {
        this(0, "Default Name");
    }

    public static void main(String[] args) {
        Human h = new Human();
        pri(h);  // Pass the instance to the static method
    }
}
