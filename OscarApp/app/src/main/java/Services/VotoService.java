package Services;

import ServiceBeans.Login;
import ServiceBeans.Voto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface VotoService {
    @GET("voto/{login}/{token}/{filme}/{diretor}")
    Call<Voto> getFullAddress(@Path("login") String login, @Path("token") Integer token, @Path("filme") Integer filme, @Path("diretor") Integer diretor);
}
