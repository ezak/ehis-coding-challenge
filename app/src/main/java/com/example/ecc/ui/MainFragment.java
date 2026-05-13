package com.example.ecc.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ecc.R;
import com.example.ecc.api.model.Post;
import com.example.ecc.utils.ViewUtil;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends BaseFragment implements RecyclerAdapter.Listener, ConfirmationDialogFragment.Listener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
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
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialToolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        toolbar.setSubtitle(R.string.posts);
        toolbar.inflateMenu(R.menu.default_app_bar_menu);

        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.settings) {
                // Toast.makeText(requireActivity(), item.getTitle(), Toast.LENGTH_SHORT).show();
                ViewUtil.startFragment(requireActivity(),
                        SearchFragment.newInstance("", ""),
                        R.id.main_activity_fragment_container,
                        "main_fragment", true);
                return true;
            } else {
                return false;
            }
        });

        TextView status = view.findViewById(R.id.status);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireActivity());
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter(requireActivity(), this);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // Scroll position and offset tracking
                int position = layoutManager.findFirstVisibleItemPosition();
                View firstItemView = layoutManager.findViewByPosition(position);
                if (firstItemView != null) {
                    int offset = firstItemView.getTop() - recyclerView.getPaddingTop();
                    // Update ViewModel with the index and the offset
                    mainViewModel.recyclerPosition = position;
                    mainViewModel.recyclerOffset = offset;
                }

                // lazy loading
                /*int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                // Check if we are not currently loading and have reached the threshold
                if (!apiServiceViewModel.isLoading()) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount && firstVisibleItemPosition >= 0) {
                        // Trigger next page fetch
                        apiServiceViewModel.fetchNextPage();
                    }
                }*/

            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int swipeDir) {


            }
        }).attachToRecyclerView(recyclerView);



        SwipeRefreshLayout swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);

        // Set the listener for the pull-to-refresh gesture
        swipeRefreshLayout.setOnRefreshListener(() -> {
            // Trigger the data fetch
            apiServiceViewModel.fetchAllPosts();
        });

        apiServiceViewModel.getDataLive().observe(getViewLifecycleOwner(), resource -> {
            switch (resource.status) {
                case LOADING:
                    status.setText("Loading data... ");
                    if (!swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(true);
                    }
                    break;

                case SUCCESS:
                    status.setVisibility(View.GONE);
                    adapter.setDummyModelList(resource.data);
                    adapter.notifyDataSetChanged();

                    swipeRefreshLayout.setRefreshing(false);
                    layoutManager.scrollToPositionWithOffset(mainViewModel.recyclerPosition,
                            mainViewModel.recyclerOffset);
                    break;

                case ERROR:
                    status.setText("Error loading data " + resource.message);
                    Snackbar.make(view, resource.message, Snackbar.LENGTH_SHORT).show();

                    swipeRefreshLayout.setRefreshing(false);
                    break;
            }
        });


        FloatingActionButton fab = view.findViewById(R.id.fab_add);
        fab.setOnClickListener(v -> ViewUtil.startFragment(requireActivity(),
                AddNewFragment.newInstance("", ""),
                R.id.main_activity_fragment_container, "", true));

        apiServiceViewModel.fetchAllPosts();
    }

    @Override
    public void onItemClickedListener(Post post) {
        mainViewModel.post = new Post(post.getUserId(), post.getId(), post.getTitle(), post.getBody());
        Log.e("TAG", "onItemClickedListener: " + post.getTitle() );
        ViewUtil.startFragment(requireActivity(),
                DetailFragment.newInstance("", ""),
                R.id.main_activity_fragment_container,
                "", true);
    }

    @Override
    public void onDeleteConfirmed() {

    }
}