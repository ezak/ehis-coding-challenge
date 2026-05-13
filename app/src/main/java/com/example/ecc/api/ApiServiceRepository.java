package com.example.ecc.api;

import com.example.ecc.BuildConfig;
import com.example.ecc.api.model.Post;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceRepository {
    private static final String TAG = "ApiServiceRepository";
    private final ApiService apiService;
    public ApiServiceRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    /**
     * Add new post
     * */
    public Single<Post> addNewPost(Post post) {
        return apiService.addNewPost(post).subscribeOn(Schedulers.io());
    }

    /**
     * Fetch All posts
     * */
    public Single<List<Post>> fetchAllPosts() {
        return apiService.fetchAllPosts();
    }

    /**
     * Fetch post by id
     * */
    public Single<Post> fetchPostById(int id) {
        return apiService.fetchPostById(id);
    }

    /**
     * update post
     * */
    public Single<Post> updatePost(int id) {
        return apiService.updatePost(id);
    }

    /**
     * delete post
     * */
    public Single<Post> deletePost(int id) {
        return apiService.deletePost(id);
    }

}
