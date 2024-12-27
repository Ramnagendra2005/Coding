import java.util.ArrayList;
import java.util.List;
public class Wildcard {
    public static void printArray(List<?> list) {
        for (Object o : list){
            System.out.println(o);
        }
    }
    public static void main(String[] args) {
        List<Integer> lis = new ArrayList<Integer>();


        lis.add(1);
        lis.add(2);
        lis.add(3);
        lis.add(4);

        lis.add(5);
        lis.add(6);
        lis.add(7);
        printArray(lis);
    }
}
