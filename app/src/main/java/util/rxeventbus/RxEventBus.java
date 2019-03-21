package util.rxeventbus;

import io.reactivex.subjects.PublishSubject;

public class RxEventBus {

    private static RxEventBus instance;

    private PublishSubject<RxEvent> subject = PublishSubject.create();

    private RxEventBus() {
    }

    public static RxEventBus getInstance() {
        if (instance == null)
            instance = new RxEventBus();
        return instance;
    }

    public void post(RxEvent event) {
        subject.onNext(event);
    }

    public PublishSubject<RxEvent> getSubject() {
        return subject;
    }
}
