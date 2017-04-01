package interfaces;

import pojo.order.Order;
import pojo.order.Result;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Lvmoy on 2017/4/1 0001.
 * Mode: - - !
 */

public interface OrderService {
    @POST("servlets/RunbatServlet")
    Call<Result> reportOrder(
            @Body Order order
    );
}
