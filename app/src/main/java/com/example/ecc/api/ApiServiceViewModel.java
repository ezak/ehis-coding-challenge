package com.example.ecc.api;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.ecc.api.model.Post;

import java.util.Collections;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ApiServiceViewModel extends ViewModel {
    private static final String TAG = "ApiServiceViewModel";

    private final CompositeDisposable disposable = new CompositeDisposable();
    private final MutableLiveData<Resource<List<Post>>> dataState = new MutableLiveData<>();

    private final ApiServiceRepository apiServiceRepository;

    public ApiServiceViewModel(ApiServiceRepository apiServiceRepository) {
        this.apiServiceRepository = apiServiceRepository;
    }


    /**
     * add new post
     * */
    public void addNewPost(Post post) {
        disposable.add(apiServiceRepository
                .addNewPost(post)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> dataState.setValue(Resource.loading()))
                .subscribe(p -> {
                    dataState.setValue(Resource.success(Collections.singletonList(p)));
                }, throwable -> {
                    dataState.setValue(Resource.error(throwable.getLocalizedMessage()));
                }));
    }

    /**
     * fetch all posts
     * */
    public void fetchAllPosts() {
        disposable.add(apiServiceRepository
                .fetchAllPosts()
                .retryWhen(new ExponentialBackoffRetry(3, 2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> dataState.setValue(Resource.loading()))
                .subscribe(posts -> {
                    dataState.setValue(Resource.success(posts));
                }, throwable -> {
                    dataState.setValue(Resource.error(throwable.getLocalizedMessage()));
                }));
    }


    /**
     * fetch post by id
     * */
    public void fetchPostById(int id) {
        disposable.add(apiServiceRepository
                .fetchPostById(id)
                .retryWhen(new ExponentialBackoffRetry(3, 2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> dataState.setValue(Resource.loading()))
                .subscribe(p -> {
                    dataState.setValue(Resource.success(Collections.singletonList(p)));
                }, throwable -> {
                    dataState.setValue(Resource.error(throwable.getLocalizedMessage()));
                }));
    }


    /**
     * update post
     * */
    public void updatePost(int id) {
        disposable.add(apiServiceRepository
                .updatePost(id)
                .retryWhen(new ExponentialBackoffRetry(3, 2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> dataState.setValue(Resource.loading()))
                .subscribe(p -> {
                    dataState.setValue(Resource.success(Collections.singletonList(p)));
                }, throwable -> {
                    dataState.setValue(Resource.error(throwable.getLocalizedMessage()));
                }));
    }


    /**
     * delete post
     * */
    public void deletePost(int id) {
        disposable.add(apiServiceRepository
                .deletePost(id)
                .retryWhen(new ExponentialBackoffRetry(3, 2))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(d -> dataState.setValue(Resource.loading()))
                .subscribe(p -> {
                    dataState.setValue(Resource.success(Collections.singletonList(p)));
                }, throwable -> {
                    dataState.setValue(Resource.error(throwable.getLocalizedMessage()));
                }));
    }


    /**
     * observable posts
     * */
    public LiveData<Resource<List<Post>>> getDataLive() {
        return dataState;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
