
public class StaticDemo extends Demo1 {
    static int x;
    
    public static void main(String[] args) {
        x = 12;
        Demo1.x =2;
        System.out.println("balls" + Demo1.x + "balls" + StaticDemo.x);
    }

}

class Demo1 {
    
    static int x;

}
