package retrofit;

import java.util.List;

import datamodels.Track;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GitHubService {
    @GET("users/{username}")
    Call<Object> getUser(@Path("username") String username);

    @GET("users/{username}/repos")
    Call<List<Object>> getRepos(@Path("username") String username);
}
