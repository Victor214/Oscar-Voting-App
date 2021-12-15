package Services;

import ServiceBeans.Token;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TokenService {
    @GET("token/{login}/")
    Call<Token> getFullAddress(@Path("login") String login);
}
