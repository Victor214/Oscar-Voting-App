package Services;

import java.util.List;

import ServiceBeans.Filme;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FilmeService {
    @GET("filme")
    Call<List<Filme>> getFullAddress();
}
