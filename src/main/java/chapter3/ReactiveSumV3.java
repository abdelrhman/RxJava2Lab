package chapter3;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

/**
 * ReactiveSum With subjects.
 */
public class ReactiveSumV3 {
    private BehaviorSubject<Double> a = BehaviorSubject.createDefault(0.0);
    private BehaviorSubject<Double> b = BehaviorSubject.createDefault(0.0);
    private BehaviorSubject<Double> c = BehaviorSubject.createDefault(0.0);

    public ReactiveSumV3() {
        Observable.combineLatest(a,b,(v1,v2) ->v1+v2).subscribe(c);
    }

    public double getA(){ return a.getValue();}
    public double getB(){ return b.getValue();}
    public double getC(){ return c.getValue();}
    public void setA(double v){  a.onNext(v);}
    public void setB(double v){  b.onNext(v);}
    public Observable<Double> obsC(){return  c;}

}
