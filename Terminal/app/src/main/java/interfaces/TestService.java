package interfaces;

import java.util.List;

import pojo.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Lvmoy on 2017/3/30 0030.
 * Mode: - - !
 */

public interface TestService {
    @GET("answers?order=desc&sort=activity&site=stackoverflow")
    Call<Response> getAnswers();
    @GET("answers?order=desc&sort=activity&site=stackoverflow")
    Call<List<Response>> getAnswers(@Query("tagged") String tags);
}
