package hwz.mvprxjavaretrofit.http;

/**
 * Created by huangweizhou on 16/7/18.
 */
public class BaseHttpResult<T> {


    public String status;

    private T results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
