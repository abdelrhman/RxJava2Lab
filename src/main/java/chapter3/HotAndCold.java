package chapter3;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

import java.util.concurrent.TimeUnit;

import static chapter3.Playground.subscribePrint;

/**
 * The ConnectableObservable.
 */
public class HotAndCold {

    public static void main(String[] args) {
        Observable<Long> interval = Observable.interval(100L, TimeUnit.MILLISECONDS);
//        ConnectableObservable<Long> published = interval.publish();
//        Observable<Long> published = interval.publish().refCount();
//        ConnectableObservable<Long> published = interval.replay();
        Subject<Long> published = PublishSubject.create();
        interval.subscribe(published);

        Disposable disposable1 = subscribePrint(published, "First");
        Disposable disposable2 = subscribePrint(published, "Second");
//        published.connect();

        Disposable disposable3 = null;
        try {
            Thread.sleep(500L);
            published.onNext(550L);
            disposable3 = subscribePrint(published, "Third");
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        disposable1.dispose();
        disposable2.dispose();
        disposable3.dispose();


        ReactiveSumV3 reactiveSumV3 = new ReactiveSumV3();
        subscribePrint(reactiveSumV3.obsC(),"Sum = ");
        reactiveSumV3.setA(6);
        reactiveSumV3.setB(7);


    }

}
