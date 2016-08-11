package hwz.mvprxjavaretrofit.http;

import rx.Subscriber;

/**
 * Created by huangweizhou on 16/7/14.
 */
public abstract class HttpSubscriber<T> extends Subscriber<T> {

    /**
     * 请求标示
     */
    private int tag;

    public HttpSubscriber(int tag) {
        this.tag = tag;
    }

    @Override
    public void onCompleted() {
        _complete();
    }

    @Override
    public void onError(Throwable e) {
        _complete();
        onError(e.getMessage(), tag);
    }

    @Override
    public void onNext(T t) {
        onSuccess(t, tag);
    }

    public abstract void onSuccess(T t, int tag);

    public abstract void onError(String msg, int tag);

    public abstract void _complete();

}
