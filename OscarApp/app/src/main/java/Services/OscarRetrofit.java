package Services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OscarRetrofit {
    private final Retrofit retrofit;

    public OscarRetrofit() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.101:8080/OscarWebServices-1.0-SNAPSHOT/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public LoginService getLoginService() {
        return this.retrofit.create(LoginService.class);
    }

    public TokenService getTokenService() {
        return this.retrofit.create(TokenService.class);
    }

    public VotoService getVotoService() {
        return this.retrofit.create(VotoService.class);
    }
}
