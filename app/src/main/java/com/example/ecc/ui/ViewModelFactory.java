package com.example.ecc.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ViewModelFactory implements ViewModelProvider.Factory {
    private final Map<Class<? extends ViewModel>, Supplier<? extends ViewModel>> creators = new HashMap<>();

    public void register(Class<? extends ViewModel> modelClass, Supplier<? extends ViewModel> creator) {
        creators.put(modelClass, creator);
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        Supplier<? extends ViewModel> creator = creators.get(modelClass);
        if (creator != null) return (T) creator.get();
        throw new IllegalArgumentException("Not registered");
    }
}
