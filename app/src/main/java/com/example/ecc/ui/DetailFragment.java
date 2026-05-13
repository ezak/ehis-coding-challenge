package com.example.ecc.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ecc.R;
import com.example.ecc.api.model.Post;
import com.example.ecc.utils.ViewUtil;
import com.google.android.material.appbar.MaterialToolbar;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends BaseFragment implements ConfirmationDialogFragment.Listener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.e("TAG", "onViewCreated: " + mainViewModel.post.getTitle() );

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(mainViewModel.post.getTitle());

        toolbar.inflateMenu(R.menu.posts_menu);

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.edit) {
                ViewUtil.startFragment(requireActivity(), AddNewFragment.newInstance("", ""),
                        R.id.main_activity_fragment_container, "", true);
                return true;
            } else if (item.getItemId() == R.id.delete) {
                // Toast.makeText(requireActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                ConfirmationDialogFragment fragment = ConfirmationDialogFragment.newInstance("", "");
                fragment.setListener(this);
                ViewUtil.showChildDialog(DetailFragment.this, fragment, "");
                return true;
            } else {
                return false;
            }
        });

        TextView textView = view.findViewById(R.id.content);
        textView.setText(mainViewModel.post.getBody());


    }

    @Override
    public void onDeleteConfirmed() {
        apiServiceViewModel.deletePost(mainViewModel.post.getId());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mainViewModel.post = new Post();
    }
}