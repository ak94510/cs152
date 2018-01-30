public class Product extends Op<Integer> {
    @SafeVarargs
    public Product(Expr<Integer>... args)
    {
        super(new Multiply<>(), args);
    }

}
