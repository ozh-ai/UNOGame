import java.util.ArrayList;

public class test {
    public static void change(ArrayList<String> p) {
        p.add("hi");
    }
    public static void main(String[] args) {
        ArrayList<String> p = new ArrayList<>();
        change(p);
        System.out.println(p.get(0));
    }
}
