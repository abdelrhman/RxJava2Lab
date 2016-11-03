package chapter6;

import io.reactivex.Observable;

import static utils.Helpers.debug;

/**
 * Chapter 6.
 */
public class Playground {

    public static void main(String[] args) {


        Observable.range(1, 5)
                .doOnEach(debug("Test"))
                .subscribe();
    }
}
