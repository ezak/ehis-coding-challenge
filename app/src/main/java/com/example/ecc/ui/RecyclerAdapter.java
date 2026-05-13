package com.example.ecc.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecc.R;
import com.example.ecc.api.model.Post;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {
    private static final String TAG = "RecyclerAdapter";
    private List<Post> posts = new ArrayList<>();

    public interface Listener {
        void onItemClickedListener(Post post);
    }

    private static Listener listener;
    private Context context;
    public RecyclerAdapter(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setDummyModelList(List<Post> posts) {
        this.posts = posts;
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ItemViewHolder";
        TextView item, user;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.title);
            user = itemView.findViewById(R.id.user);
        }

        public void bind(Post post) {
            item.setText(post.getTitle());
            user.setText(String.valueOf(post.getUserId()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClickedListener(post);
                }
            });

        }

    }
}
