package com.example.ecc.ui;

import androidx.lifecycle.ViewModel;

import com.example.ecc.api.model.Post;

public class MainViewModel extends ViewModel {

    private static final String TAG = "MainViewModel";

    public MainViewModel(Integer dummy) {;
    }

    public int recyclerPosition = 0;
    public int recyclerOffset = 0;

    public boolean update = false;

    public Post post = new Post();
}
