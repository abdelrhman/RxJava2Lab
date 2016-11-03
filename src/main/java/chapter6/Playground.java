package chapter6;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

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


        schedule(Schedulers.trampoline(), 2,false);
        schedule(Schedulers.trampoline(), 2,true);

//        Thread.sleep(1000);

    }
}
