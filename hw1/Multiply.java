import java.util.List;

public class Multiply<T> implements Function<T> {

    @Override
    public T apply(List<T> args){
        Integer prod = 1;
        for(Object i : args){
            Integer a = (int)i;
            prod = prod * a;
        }
        return (T)prod;
    }
}
