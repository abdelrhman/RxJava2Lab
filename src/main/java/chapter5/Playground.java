package chapter5;

import io.reactivex.Observable;
import utils.Helpers;

import java.util.Arrays;
import java.util.Random;
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
                        Observable.interval(0, 300, TimeUnit.MILLISECONDS),
                        Observable.fromIterable(Arrays.asList("Z", "I", "I", "P")),
                        (value, i) -> i
                ), "Timed Zip");

        Observable<String> greetings =
                Observable.just("Hello", "Hi", "Howdy", "Zdravei", "Yo", "Good to see ya")
                        .zipWith(Observable.interval(1, TimeUnit.SECONDS), Helpers::onlyFirstArg);

        Observable<String> names =
                Observable.just("Meddle", "Tanya", "Dali", "Joshua")
                        .zipWith(Observable.interval(1500L, TimeUnit.MILLISECONDS), Helpers::onlyFirstArg);

        Observable<String> punctuation =
                Observable.just(".", "?", "!", "!!!", "...")
                        .zipWith(Observable.interval(1100, TimeUnit.MILLISECONDS), Helpers::onlyFirstArg);

        Observable<String> combined = Observable
                .combineLatest(
                        greetings, names, punctuation,
                        (greeting, name, puntuation) ->
                                greeting + " " + name + puntuation);
        blockingSubscribePrint(combined, "Sentences");

        Observable<String> merged = Observable
                .merge(greetings, names, punctuation);
        subscribePrint(merged, "Words");


        Observable<String> words = Observable.just("Some", "Other");
        Observable<Long> interval = Observable.interval(100, TimeUnit.MILLISECONDS).take(2);
        blockingSubscribePrint(Observable.amb(Arrays.asList(words, interval)), "Amb 1");

        Random r = new Random();
        Observable<String> source1 = Observable.just("data from source 1").delay(r.nextInt(1000), TimeUnit.MILLISECONDS);
        Observable<String> source2 = Observable.just("data from source 2").delay(r.nextInt(1000), TimeUnit.MILLISECONDS);
        blockingSubscribePrint(Observable.amb(Arrays.asList(source1, source2)), "Amb 2");


        Observable<String> willLearn = Observable.just("one", "way", "or", "another", "I'll", "learn", "RxJava")
                .zipWith(Observable.interval(200, TimeUnit.MILLISECONDS), (x, y) -> x);
        Observable<Long> learnInterval = Observable.interval(500, TimeUnit.MILLISECONDS);

        blockingSubscribePrint(willLearn.takeUntil(learnInterval), "Take until");
        blockingSubscribePrint(willLearn.takeWhile(word -> word.length() > 2), "Take while");
        blockingSubscribePrint(willLearn.skipUntil(learnInterval), "skip until");

        Observable<Object> test = Observable
                .empty()
                .defaultIfEmpty(5);
        subscribePrint(test, "defaultIfEmpty");


        //One more item and then completes.
        Observable<Integer> numbers = Observable.just("1", "2", "three", "4", "5")
                .map(Integer::parseInt)
                .onErrorReturn(e -> -1);
        subscribePrint(numbers, "OnErrorReturn");

        // replace with default observable.
        Observable<Integer> defaultOnError = Observable.just(1, 2, 3, 5);
        numbers = Observable.just("1", "2", "three", "4", "5")
                .map(Integer::parseInt)
                .onExceptionResumeNext(defaultOnError);
        subscribePrint(numbers, "OnExceptionResumeNext");

        numbers = Observable.just("1", "2", "three", "4", "5")
                .doOnNext(e -> {
                    assert !e.equals("three");
                })
                .map(Integer::parseInt)
                .onErrorResumeNext(defaultOnError);
        subscribePrint(numbers, "onErrorResumeNext");


    }


}
