public class Generics<T> {
    public static void main(String[] args) {
        Box<Integer> box = new Box<>();
        box.setitem(5);
        System.out.println(box.getitem());
    }

}
