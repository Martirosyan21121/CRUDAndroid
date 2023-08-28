package am.example.crudapplication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestApi {


    public static final String KEY = "640e4628ae047856979ca5d72b797e9b3208d";
    public static final String BASE_URL = "https://apiuser-c607.restdb.io/rest/userdata";

    private final MutableLiveData<ArrayList<User>> mutableLiveData = new MutableLiveData<>();

    public void loadContacts() {

        OkHttpClient httpClient = createOkHttpClient();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        UserApi api = retrofit.create(UserApi.class);
        api.fetchAllContacts().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull retrofit2.Response<List<User>> response) {
                Log.d(TAG, "onResponse: ");
                assert response.body() != null;
                mutableLiveData.postValue(new ArrayList<>(response.body()));
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }


    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            okhttp3.HttpUrl originalHttpUrl = original.url();
            okhttp3.HttpUrl url = originalHttpUrl.newBuilder()
                    .build();
            Request.Builder requestBuilder = original.newBuilder()
                    .url(url)
                    .header("apikey", KEY);
            Request request = requestBuilder.build();
            return chain.proceed(request);
        });

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);

        return httpClient.build();
    }
}
