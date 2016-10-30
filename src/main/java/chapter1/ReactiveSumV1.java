package chapter1;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.ConnectableObservable;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by m on 10/30/16.
 */
public class ReactiveSumV1 {

    public static void main(String[] args) {
        //Implementing the reactive sum.
        ConnectableObservable<String> input = from(System.in);
        Observable<Double> a = varStream("a", input);
        Observable<Double> b = varStream("b", input);
        ReactiveSum sum = new ReactiveSum(a, b);
        input.connect();
    }

    private static Observable<Double> varStream(String varName, ConnectableObservable<String> input) {
        final Pattern pattern = Pattern.compile("^\\s*" + varName
                + "\\s*[:|=]\\s*(-?\\d+\\.?\\d*)$");
        return input.map(new Function<String, Matcher>() {
            public Matcher apply(String s) throws Exception {
                System.out.println("var Stream map  " + s);
                return pattern.matcher(s);
            }
        }).filter(new Predicate<Matcher>() {
            public boolean test(Matcher matcher) throws Exception {
                System.out.println("var Stream filter ");
                return matcher.matches() && matcher.group(1) != null;
            }
        }).map(new Function<Matcher, Double>() {
            public Double apply(Matcher matcher) throws Exception {
                System.out.println("var Stream other map ");
                return Double.parseDouble(matcher.group(1));
            }
        });

    }

    private static ConnectableObservable<String> from(final InputStream in) {
        return from(new BufferedReader(new InputStreamReader(in)));
    }

    private static ConnectableObservable<String> from(final BufferedReader reader) {
        return Observable.create(new ObservableOnSubscribe<String>() {

            public void subscribe(ObservableEmitter<String> e) throws Exception {
                if (e.isDisposed())
                    return;
                String line;
                while (!e.isDisposed() && (line = reader.readLine()) != null) {
                    if (line.equals("exit") || line == null) {
                        break;
                    }
                    e.onNext(line);
                }
                if (!e.isDisposed())
                    e.onComplete();

            }
        }).publish();
    }

    private static class ReactiveSum implements Observer<Double> {
        private double sum;

        public ReactiveSum(Observable<Double> a, Observable<Double> b) {
            this.sum = 0;
            Observable.combineLatest(a, b, new BiFunction<Double, Double, Double>() {
                public Double apply(Double a, Double b) throws Exception {
                    return a + b;
                }
            }).subscribe(this);
        }


        public void onSubscribe(Disposable d) {
            System.out.println("On Subscribe");
        }

        public void onNext(Double value) {
            this.sum = value;
            System.out.println("update : a + b = " + sum);
        }

        public void onError(Throwable e) {
            System.err.println("Error: " + e.getLocalizedMessage());
        }

        public void onComplete() {
            System.out.println("Exiting Last Sum was: " + this.sum);
        }
    }
}
