package utils;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

import java.util.concurrent.CountDownLatch;

/**
 * Created by m on 11/2/16.
 */
public class Helpers {

    public static <T> Disposable subscribePrint(Observable<T> observable, String name) {
        return observable.subscribe((v) -> {
            System.out.println(name + " " + v);
        }, (e) -> {
            System.err.println("Error from " + name);
            System.err.println(e.getLocalizedMessage());
        }, () -> {
            System.out.println(name + " Completed");
        });
    }

    /**
     * Subscribes to an observable, printing all its emissions.
     * Blocks until the observable calls onCompleted or onError.
     */
    public static <T> void blockingSubscribePrint(Observable<T> observable, String name) {
        CountDownLatch latch = new CountDownLatch(1);
        subscribePrint(observable.doAfterTerminate(() -> latch.countDown()), name);
        try {
            latch.await();
        } catch (InterruptedException e) {
        }
    }

    public static <T, R> T onlyFirstArg(T t, R r) {
        return t;
    }
}
