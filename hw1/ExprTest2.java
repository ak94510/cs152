import java.io.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ExprTest2
{
    public <T> Const<T> c(T v) { return new Const<T>(v); }

    @Test public void simpleExpr()
    {
        assertEquals(42, (Object) new Product(c(6), c(7)).value());
    }

    @Test public void addZero()
    {
        System.setIn(new ByteArrayInputStream(
                "0".getBytes(java.nio.charset.StandardCharsets.UTF_8)));
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        assertEquals(32, (Object) new Sum(c(32), new Read()).value());
    }

    @Test public void negMult()
    {
        Op<Integer> p = new Product(c(-2), c(3));
        assertEquals(-5, (Object) new Sum(c(1), p).value());
    }

    @Test public void negZero()
    {
        Op<Integer> x = new Product(c(-15),c(0));
        assertEquals(0,(Object) x.value());
    }

}
