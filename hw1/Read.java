import java.util.Scanner;
public class Read implements Expr<Integer> {
    private Integer value;
    private static Scanner in = new Scanner(System.in);
    public Read(){
        value = in.nextInt();
    }
    public Integer value(){
        return value;
    }
}
