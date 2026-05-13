package com.example.ecc.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecc.R;
import com.example.ecc.api.ApiServiceViewModel;

public abstract class BaseFragment extends Fragment {

    private static final String TAG = "BaseFragment";

    protected MainViewModel mainViewModel;
    protected ApiServiceViewModel apiServiceViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewModelFactory factory = new ViewModelFactory();

        factory.register(MainViewModel.class, () -> new MainViewModel(0));
        factory.register(ApiServiceViewModel.class, () -> new ApiServiceViewModel(RepositoryHelper.getApiServiceRepository()));
        ViewModelProvider provider = new ViewModelProvider(requireActivity(), factory);

        mainViewModel = provider.get(MainViewModel.class);
        apiServiceViewModel = provider.get(ApiServiceViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}