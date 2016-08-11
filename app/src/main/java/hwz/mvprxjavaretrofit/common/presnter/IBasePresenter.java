package hwz.mvprxjavaretrofit.common.presnter;

import hwz.mvprxjavaretrofit.common.mvpview.MvpView;

/**
 * Created by huangweizhou on 16/7/28.
 */
public interface IBasePresenter<V extends MvpView> {

    void subscribe();

    void unsubscribe();

    void destory();

}
