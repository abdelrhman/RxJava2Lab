package chapter6;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static utils.Helpers.debug;
import static utils.Helpers.schedule;

/**
 * Chapter 6.
 */
public class Playground {

    public static void main(String[] args) throws InterruptedException {


//        Observable.range(1, 5)
//                .doOnEach(debug("Test"))
//                .subscribe();

//        Observable.interval(10L, TimeUnit.MILLISECONDS)
//                .take(4)
//                .doOnEach(debug("Default"))
//                .subscribe();

//        Observable.interval(10L, TimeUnit.MILLISECONDS,Schedulers.trampoline() )
//                .take(4)
//                .doOnEach(debug("Trampoline "))
//                .subscribe();


//        schedule(Schedulers.trampoline(), 2,false);
//        schedule(Schedulers.trampoline(), 2,true);
//        schedule(Schedulers.newThread(), 2,false);
//        schedule(Schedulers.computation(), 4,false);
//        schedule(Schedulers.io(), 4,false);

//        Observable.range(1,5)
//                .doOnEach(debug("source"))
//                .subscribe();
//        System.out.println("Hey");

//        CountDownLatch countDown = new CountDownLatch(1);
//        Observable<Integer> range = Observable.range(20, 4)
//                .doOnEach(debug("Source"))
//                .subscribeOn(Schedulers.io())
//                .doAfterTerminate(() -> countDown.countDown());
//        range.subscribe();
//        System.out.println("Hey!");
//        countDown.await();

        Observable<Integer> range = Observable.range(1,4)
                .flatMap(i -> Observable.range(i, 3)
                        .doOnEach(debug("Range"))
                .subscribeOn(Schedulers.computation()
                ));
        range.subscribe();




        Thread.sleep(1000);

    }
}
