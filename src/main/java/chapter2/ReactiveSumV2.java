package chapter2;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.observables.ConnectableObservable;

import java.io.InputStream;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * implementing reactive sum with lambdas.
 */
public class ReactiveSumV2 {

    public static void main(String[] args) {

        ConnectableObservable<String> input = from(System.in);
        Observable<Double> a = varStream("a", input);
        Observable<Double> b = varStream("b", input);
        reactiveSum(a, b);
        input.connect();
    }

    private static void reactiveSum(Observable<Double> a, Observable<Double> b) {
        Observable.combineLatest(a, b, (v1, v2) -> v1 + v2)
                .subscribe(System.out::println,
                        System.err::println,
                        () -> System.out.println("OnComplete"),
                        (d) -> {
                            System.out.println("onSubscribe");
                        });
    }

    private static Observable<Double> varStream(String varName, ConnectableObservable<String> input) {
        final Pattern pattern = Pattern.compile("^\\s*" + varName
                + "\\s*[:|=]\\s*(-?\\d+\\.?\\d*)$");
        return input.map(s -> pattern.matcher(s))
                .filter(m -> m.matches() && m.group(1) != null)
                .map(m -> m.group(1))
                .map(Double::parseDouble);
    }

    private static ConnectableObservable<String> from(InputStream in) {
        Scanner s = new Scanner(in);
        return Observable.create((ObservableOnSubscribe<String>)
                emitter -> {
                    if (emitter.isDisposed())
                        return;
                    String line;
                    while (!emitter.isDisposed() && (line = s.nextLine()) != null) {
                        if (line == null || line.equals("exit"))
                            break;
                        emitter.onNext(line);
                    }
                    if (!emitter.isDisposed())
                        emitter.onComplete();
                }
        ).publish();
    }


}
