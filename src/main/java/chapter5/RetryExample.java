package chapter5;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

import java.util.concurrent.TimeUnit;

import static utils.Helpers.subscribePrint;

/**
 * Chapter 5 Retry.
 */
public class RetryExample {

    public static void main(String[] args) {
        subscribePrint(Observable.create(new ErrorEmitter()).retry(), "Infinite Retry");
        Observable retryWhen = Observable.create(new ErrorEmitter())
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                        return throwableObservable.flatMap(error -> {
                            if (error instanceof FooException){
                                System.err.println("Delay");
                                return Observable.timer(1, TimeUnit.SECONDS);
                            }
                            return Observable.error(error);
                        });
                    }
                }).retry((attempts, error) -> {
                    return (error instanceof BooException) && (int)attempts < 3;
                });
    }

    static class FooException extends RuntimeException {
        public FooException() {
            super("Foo");
        }
    }

    static class BooException extends RuntimeException {
        public BooException() {
            super("Boo");
        }
    }

    static class ErrorEmitter implements ObservableOnSubscribe {

        private int throwErrorCounter = 5;

        @Override
        public void subscribe(ObservableEmitter e) throws Exception {
            e.onNext(1);
            e.onNext(2);
            if (throwErrorCounter > 4 ){
                throwErrorCounter--;
                e.onError(new FooException());
                return;
            }
            if (throwErrorCounter > 0 ){
                throwErrorCounter--;
                e.onError(new BooException());
                return;
            }
            e.onNext(3);
            e.onNext(4);
            e.onComplete();

        }
    }

}
