

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
public class Practice {
    public static void main(String[] args) {
        String src = "source.txt";
        String dest = "dest.txt";

        try(FileInputStream in = new FileInputStream("source.txt");FileOutputStream out = new FileOutputStream("dest.txt")){
            int a;
            while((a=in.read())!=-1){
                out.write(a);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
