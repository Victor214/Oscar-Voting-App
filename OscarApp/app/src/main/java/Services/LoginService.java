package Services;

import ServiceBeans.Login;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LoginService {
    @GET("login/{login}/{senha}")
    Call<Login> getFullAddress(@Path("login") String login, @Path("senha") String senha);
}
