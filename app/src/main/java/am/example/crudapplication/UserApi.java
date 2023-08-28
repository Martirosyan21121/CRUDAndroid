package am.example.crudapplication;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface UserApi {

    @GET("/apiusers")
    Call<List<User>> fetchAllContacts();
}
