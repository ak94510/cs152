import java.util.List;
import java.util.ArrayList;

public class Op<T> implements Expr<T> {

    private T value;
    private Function<T> fun;
    private Expr<T>[] args;

    @SafeVarargs public Op(Function<T> fun,Expr<T>... args)
    {
        this.fun = fun;
        this.args = args;
    }
    @Override
    public T value(){
        List<T> list = new ArrayList<>();
        for( Expr<T> x : args){
            list.add(x.value());
        }
        value = fun.apply(list);
        return this.value;
    }
}
