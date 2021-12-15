package Services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OscarListaRetrofit {
    private final Retrofit retrofit;

    public OscarListaRetrofit() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://wecodecorp.com.br/ufpr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public FilmeService getFilmeService() {
        return this.retrofit.create(FilmeService.class);
    }

    public DiretorService getDiretorService() {
        return this.retrofit.create(DiretorService.class);
    }
}
