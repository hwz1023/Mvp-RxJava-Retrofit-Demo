package hwz.mvprxjavaretrofit.Main.contract;

import hwz.mvprxjavaretrofit.common.mvpview.MvpView;

/**
 * Created by huangweizhou on 16/8/11.
 */
public class MainContract {
    /**
     * Created by huangweizhou on 16/8/10.
     */

    public interface IMainView extends MvpView {
        /**
         * 第一个点和第二个点之间的距离
         *
         * @param distance
         */
        void setFirstPointToSecondPointDistance(String distance);

        /**
         * 第二个点和第三个点之间的距离
         *
         * @param distance
         */

        void setSecondPointToThreePointDistance(String distance);
    }
}
