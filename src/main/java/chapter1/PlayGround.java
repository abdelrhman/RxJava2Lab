package chapter1;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Code Samples in Learning-Reactive-Programming-With-Java in RxJava 2 .
 */
public class PlayGround {

    public static void main(String[] args) {

        // Iterator pattern.
        final List<String> numbers = Arrays.asList("One", "Two", "Three", "Four", "Five");
        Iterator<String> numbersIterator = numbers.iterator();
        while (numbersIterator.hasNext()) {
            System.out.println(numbersIterator.next());
        }

        //Observable pattern.
        //OnNext Only.
        Observable<String> numbersObservable = Observable.fromIterable(numbers);
        numbersObservable.subscribe(new Consumer<String>() {
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });

        //Complete subscription
        Observable<String> numbersObservableCompleteImplementation = Observable.fromIterable(numbers);
        numbersObservableCompleteImplementation.subscribe(new Consumer<String>() {
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        }, new Consumer<Throwable>() {
            public void accept(Throwable throwable) throws Exception {
                System.err.println(throwable.getLocalizedMessage());
            }
        }, new Action() {
            public void run() throws Exception {
                System.out.println("On Complete");
            }
        }, new Consumer<Disposable>() {
            public void accept(Disposable disposable) throws Exception {
                System.out.println("On Subscribe");
            }
        });


    }


}
