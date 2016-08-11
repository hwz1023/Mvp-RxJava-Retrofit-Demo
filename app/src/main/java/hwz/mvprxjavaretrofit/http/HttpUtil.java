package hwz.mvprxjavaretrofit.http;


import java.util.concurrent.TimeUnit;

import hwz.mvprxjavaretrofit.common.StaticCode;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by huangweizhou on 16/7/13.
 */
public class HttpUtil {
    /**
     * 超时时间
     */
    private static final int DEFAULT_TIMEOUT = 10;
    /**
     * retrofit
     */
    private Retrofit retrofit;
    /**
     * 接口请求
     */
    private HttpService httpService;

    public HttpService getHttpService() {
        return httpService;
    }

    private HttpUtil() {
        //创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        //添加迭代器
        httpClientBuilder.addInterceptor(new LoggerInterceptor());
        retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(StaticCode.BASE_URL)
                .build();
        httpService = retrofit.create(HttpService.class);
    }

    //在访问HttpUtil时创建单例
    private static class SingletonHolder {
        private static final HttpUtil INSTANCE = new HttpUtil();
    }

    //获取单例
    public static HttpUtil getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 组装Observable
     *
     * @param observable
     */
    public Observable packageObservable(Observable observable) {
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取网络数据不转化
     *
     * @param observable
     */
    public Subscription sendHttp(Observable observable, HttpSubscriber listener) {
        return packageObservable(observable)
                .subscribe(listener);
    }

    /**
     * 获取网络数据转化
     *
     * @param observable
     */
    public <T> Subscription sendHttpWithMap(Observable observable, HttpSubscriber<T>
            listener) {
        return observable.compose(this.<T>applySchedulers())
                .subscribe(listener);
    }

    /**
     * Observable 转化
     *
     * @param <T>
     * @return
     */
    <T> Observable.Transformer<BaseHttpResult<T>, T> applySchedulers() {
        return new Observable.Transformer<BaseHttpResult<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseHttpResult<T>> baseHttpResultObservable) {
                return baseHttpResultObservable.map(new HttpFunc<T>())
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 用来统一处理Http请求到的数据,并将数据解析成对应的Model返回
     *
     * @param <T> Subscriber真正需要的数据类型
     */
    private class HttpFunc<T> implements Func1<BaseHttpResult<T>, T> {

        @Override
        public T call(BaseHttpResult<T> baseHttpResult) {
            //获取数据失败则抛出异常 会进入到subscriber的onError中
            if (!baseHttpResult.getStatus().equals(StaticCode.HTTP_RESPONSE_SUCCESS))
                throw new RuntimeException(baseHttpResult.getStatus());

            return baseHttpResult.getResults();
        }
    }
}

