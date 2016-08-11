package hwz.mvprxjavaretrofit.http;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * Created by huangweizhou on 16/8/9.
 */
public class LoggerInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request().newBuilder().addHeader("version", "1.0")
                .addHeader("clientSort", "android").addHeader("Charset", "UTF-8")
                .build();

        printRequestLog(originalRequest);
        Response response = null;
        try {
            //发送网络请求
            response = chain.proceed(originalRequest);
            printResult(response);
        } catch (SocketTimeoutException e) {
            //此处不抛异常  连接超时会crash 没有找到其他好的方法
            e.printStackTrace();

        }
        return response;
    }

    /**
     * 打印请求日志
     *
     * @param originalRequest
     * @return
     * @throws IOException
     */
    private void printRequestLog(Request originalRequest) throws IOException {
        FormBody.Builder formBuilder = new FormBody.Builder();
        String msg = originalRequest.url() + "\n";
        RequestBody oidBody = originalRequest.body();
        if (oidBody instanceof FormBody) {
            FormBody formBody = (FormBody) oidBody;
            for (int i = 0; i < formBody.size(); i++) {
                String name = URLDecoder.decode(formBody.encodedName(i), "utf-8");
                String value = URLDecoder.decode(formBody.encodedValue(i), "utf-8");
                if (!TextUtils.isEmpty(value)) {
                    formBuilder.add(name, value);
                    msg += name + "  =  " + value + "\n";
                }
            }
        }
        Logger.i(msg);
    }

    /**
     * 打印返回日志
     *
     * @param response
     * @throws IOException
     */
    private void printResult(Response response) throws IOException {
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        source.request(Long.MAX_VALUE); // Buffer the entire body.
        Buffer buffer = source.buffer();
        Charset UTF8 = Charset.forName("UTF-8");
        MediaType contentType = responseBody.contentType();
        if (contentType != null) {
            UTF8 = contentType.charset(UTF8);
        }
        String a = buffer.clone().readString(UTF8);
        Logger.i(a);
    }

}
