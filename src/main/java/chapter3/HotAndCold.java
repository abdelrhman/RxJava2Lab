package chapter3;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;

import java.util.concurrent.TimeUnit;

import static chapter3.Playground.subscribePrint;

/**
 * The ConnectableObservable.
 */
public class HotAndCold {

    public static void main(String[] args) {
        Observable<Long> interval = Observable.interval(100L, TimeUnit.MILLISECONDS);
        ConnectableObservable<Long> puplished = interval.publish();
        Disposable disposable1 = subscribePrint(puplished,"First");
        Disposable disposable2 = subscribePrint(puplished,"Second");
        puplished.connect();

        Disposable disposable3 = null;
        try{
            Thread.sleep(500L);
            disposable3 = subscribePrint(puplished,"Third");
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        disposable1.dispose();
        disposable2.dispose();
        disposable3.dispose();


    }

}
