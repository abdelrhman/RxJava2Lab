package chapter5;

import io.reactivex.Observable;
import utils.Helpers;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import static utils.Helpers.blockingSubscribePrint;
import static utils.Helpers.onlyFirstArg;
import static utils.Helpers.subscribePrint;

/**
 * chapter 5
 */
public class Playground {

    public static void main(String[] args) {

        subscribePrint(
                Observable.zip(
                        Observable.just(1, 3, 4),
                        Observable.just(5, 2, 6),
                        (a, b) -> a + b
                ), "Simple Zip"
        );

        blockingSubscribePrint(
                Observable.zip(
                        Observable.interval(0,300, TimeUnit.MILLISECONDS),
                        Observable.fromIterable(Arrays.asList("Z","I","I","P")),
                        (value, i) -> i
                ), "Timed Zip");

        Observable<String> greetings =
                Observable.just("Hello", "Hi", "Howdy", "Zdravei", "Yo", "Good to see ya")
                        .zipWith(Observable.interval(1,TimeUnit.SECONDS),Helpers::onlyFirstArg);

        Observable<String> names =
                Observable.just("Meddle", "Tanya", "Dali", "Joshua")
                        .zipWith(Observable.interval(1500L,TimeUnit.MILLISECONDS),Helpers::onlyFirstArg);

        Observable<String> punctuation =
                Observable.just(".", "?", "!", "!!!", "...")
                        .zipWith(Observable.interval(1100,TimeUnit.MILLISECONDS),Helpers::onlyFirstArg);

        Observable<String> combined = Observable
                .combineLatest(
                        greetings, names, punctuation,
                        (greeting, name, puntuation) ->
                                greeting + " " + name + puntuation)
                ;
        blockingSubscribePrint(combined, "Sentences");

        Observable<String> merged = Observable
                .merge(greetings, names, punctuation);
        subscribePrint(merged, "Words");







    }


}
