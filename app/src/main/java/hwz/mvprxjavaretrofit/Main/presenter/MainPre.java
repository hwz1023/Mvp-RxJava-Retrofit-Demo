package hwz.mvprxjavaretrofit.Main.presenter;

import com.orhanobut.logger.Logger;

import java.util.List;

import hwz.mvprxjavaretrofit.common.presnter.BasePresenter;
import hwz.mvprxjavaretrofit.Main.contract.MainContract;

/**
 * Created by huangweizhou on 16/8/10.
 */
public class MainPre extends BasePresenter<MainContract.IMainView, List<String>> {
    public MainPre(MainContract.IMainView view) {
        super(view);
    }

    @Override
    public void onSuccess(List<String> strings, int tag) {
        baseView.setFirstPointToSecondPointDistance(strings.get(0));
        baseView.setSecondPointToThreePointDistance(strings.get(1));
    }

    @Override
    public void onError(String msg, int tag) {
        Logger.e(msg);
    }


    @Override
    public void subscribe() {
        sendHttpWithMap(httpService.getDistance("118.77147503233,32.054128923368;\n" +
                "     116.3521416286,39.965780080447;116.28215586757,39\n" +
                "     .965780080447", "6cyEpstfAo1HSFGPSeshRXa459p3TyT0", "json"));
    }
}
