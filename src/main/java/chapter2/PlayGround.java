package chapter2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * Creating a generic map function to a list.
 */
public class PlayGround {


    public static void main(String[] args) {
        // using the map function without lambdas.
        List<Integer> mapped = map(Arrays.asList(1, 2, 3, 4, 5), new Mapper<Integer, Integer>() {
            public Integer map(Integer value) {
                return value * value;
            }
        });
        System.out.println(mapped);

        //using map function with lambdas.
        List<Integer> wordCount = map(Arrays.asList("Ali", "TOTO", "KARIM"), v -> v.length());
        System.out.println(wordCount);

        //using the action interface.
        act(wordCount, System.out::println);
        act(mapped, v -> System.out.println(v));

    }

    public interface Mapper<V, M> {
        M map(V value);
    }

    public interface Action<V> {
        void act(V value);
    }

    public static <V, M> List<M> map(List<V> list, final Mapper<V, M> mapper) {
        final List<M> mapped = new ArrayList<M>(list.size());
        list.stream()
                .forEach(new Consumer<V>() {
                    public void accept(V v) {
                        mapped.add(mapper.map(v));
                    }
                });
        return mapped;
    }

    public static <V> void act(List<V> list, Action<V> action) {
        list.stream()
                .forEach(action::act);
    }

}
