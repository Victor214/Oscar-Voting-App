package Services;

import java.util.List;

import ServiceBeans.Diretor;
import ServiceBeans.Filme;
import retrofit2.Call;
import retrofit2.http.GET;

public interface DiretorService {
    @GET("diretor")
    Call<List<Diretor>> getFullAddress();
}
