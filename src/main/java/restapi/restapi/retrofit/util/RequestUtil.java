package restapi.restapi.retrofit.util;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.http.HttpLogging;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Optional;

public abstract class RequestUtil {


    private static final String BASE_URL = "https://www.abee997.co.kr/";
    private static final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor);
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient.build())
            .build();


    public static <T> T createService(Class<T> sClass){
        return retrofit.create(sClass);
    }

    public static <T>Optional<T> requestSync(Call<T> call){
        try{
            Response<T> execute = call.execute();
            System.out.println("execute = " + execute);
            if(execute.isSuccessful()){
                return Optional.ofNullable(execute.body());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
    public static <T> void requestAsync(Call<T> call , CustomCallback<T> callback){
        call.enqueue(callback);
    }
}
