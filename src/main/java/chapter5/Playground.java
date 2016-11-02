package chapter5;

import io.reactivex.Observable;
import utils.Helpers;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static utils.Helpers.blockingSubscribePrint;

/**
 * chapter 5
 */
public class Playground {

    public static void main(String[] args) {

//        subscribePrint(
//                Observable.zip(
//                        Observable.just(1, 3, 4),
//                        Observable.just(5, 2, 6),
//                        (a, b) -> a + b
//                ), "Simple Zip"
//        );
        //
        blockingSubscribePrint(
                Observable.zip(
                        Observable.interval(0,300, TimeUnit.MILLISECONDS),
                        Observable.fromIterable(Arrays.asList("Z","I","I","P")),
                        (value, i) -> i
                ), "Timed Zip");



    }


}