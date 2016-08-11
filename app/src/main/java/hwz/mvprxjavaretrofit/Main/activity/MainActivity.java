package hwz.mvprxjavaretrofit.Main.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import hwz.mvprxjavaretrofit.Main.contract.MainContract;
import hwz.mvprxjavaretrofit.Main.presenter.MainPre;
import hwz.mvprxjavaretrofit.R;

public class MainActivity extends AppCompatActivity implements MainContract.IMainView {


    private MainPre mainPre;
    private android.widget.TextView getDistance;
    private android.widget.TextView firstToSecondDistance;
    private android.widget.TextView secondToThreeDistance;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.secondToThreeDistance = (TextView) findViewById(R.id.second_to_three_distance);
        this.firstToSecondDistance = (TextView) findViewById(R.id.first_to_second_distance);
        this.getDistance = (TextView) findViewById(R.id.get_distance);

        mainPre = new MainPre(this);

        getDistance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainPre.subscribe();
            }
        });
    }

    @Override
    public void setFirstPointToSecondPointDistance(String distance) {
        firstToSecondDistance.setText(distance);
    }

    @Override
    public void setSecondPointToThreePointDistance(String distance) {
        secondToThreeDistance.setText(distance);
    }

    @Override
    public void showLoadingDialog() {
        //这边可以做Dialog的显示
        Logger.e("请求开始");
    }

    @Override
    public void dismissLoadingDialog() {
        //这边可以做Dialog的隐藏
        Logger.e("请求结束");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mainPre.destory();
    }
}
