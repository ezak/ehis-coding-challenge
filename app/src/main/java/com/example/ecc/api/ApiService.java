package com.example.ecc.api;

import com.example.ecc.api.model.Post;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {


    /**
     * Assuming endpoint has /post/add
     * */
    @POST("/posts/add")
    Single<Post> addNewPost(@Body Post post);

    /**
     * List all posts
     * */
    @GET("/posts")
    Single<List<Post>> fetchAllPosts();

    /**
     * View post details by id
     * */
    @GET("/posts/id")
    Single<Post> fetchPostById(int id);

    /**
     * Update post by id */
    @PUT("/posts/id")
    Single<Post> updatePost(int id);

    /*
    * Delete post by id
    * */
    @PUT("/posts/id")
    Single<Post> deletePost(int id);

}
