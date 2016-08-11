package hwz.mvprxjavaretrofit.http;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by huangweizhou on 16/7/13.
 */
public interface HttpService {
    /**
     * 测量多点之间的直线距离
     *
     * @param waypoints 需要测距的点的经纬度坐标；需传入两个或更多的点。两个点之间用 “; ”进行分割开，单个点的经纬度用“,”分隔开；例如： waypoints=118
     *                  .77147503233,32.054128923368;116.3521416286, 39.965780080447;116
     *                  .28215586757,39.965780080447
     * @param ak
     * @param output
     * @return
     */
    @FormUrlEncoded
    @POST("distance?")
    Observable<BaseHttpResult<List<String>>> getDistance(@Field("waypoints") String waypoints,
                                                         @Field("ak") String ak,
                                                         @Field("output") String output);
}
