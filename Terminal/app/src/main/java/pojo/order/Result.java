package pojo.order;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

/**
 * Created by Lvmoy on 2017/4/1 0001.
 * Mode: - - !
 */
@JsonObject
public class Result {
    @JsonField(name = "result")
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Result() {
    }

    public Result(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Result{" +
                "result = '" + result + '\'' +
                "}";
    }
}
