package com.example.ecc.ui;


import com.example.ecc.api.ApiServiceRepository;

public class RepositoryHelper {
    private static final String TAG = "RepositoryHelper";
    private static ApiServiceRepository apiRepository;

    public RepositoryHelper() {
    }

    public synchronized static ApiServiceRepository getApiServiceRepository() {
        if (apiRepository == null)
            apiRepository = new ApiServiceRepository();

        return apiRepository;
    }

}
