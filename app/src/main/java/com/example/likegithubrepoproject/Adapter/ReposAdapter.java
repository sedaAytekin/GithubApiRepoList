package com.example.likegithubrepoproject.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.likegithubrepoproject.Model.ReposModel;
import com.example.likegithubrepoproject.R;

import java.util.List;

public class ReposAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnItemClickListener {
        public void onRepo(String userLogin, int position);
        public void onStar(ImageView callerImage);
    }
    Activity context;
    public List<ReposModel> reposList;
    private OnItemClickListener listener;

    public ReposAdapter(Activity context, List<ReposModel> reposList, OnItemClickListener listener) {
        this.context = context;
        this.reposList = reposList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.adapter_repos_item, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReposModel repoItem = reposList.get(position);
        ViewHolder viewHolder= (ViewHolder) holder;

        viewHolder.repoNameTV.setText(repoItem.getName());
        viewHolder.bind(repoItem, listener, position);

    }


    @Override
    public int getItemCount() {
        return reposList.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder {
    public ImageView starIV;
    public TextView repoNameTV;

    public void bind(final ReposModel item, final ReposAdapter.OnItemClickListener listener, int position) {

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                listener.onRepo(item.getOwner().getLogin(), getAdapterPosition());
            }
        });
    }

    public ViewHolder(View itemView) {
        super(itemView);
        starIV = itemView.findViewById(R.id.starIV);
        repoNameTV = itemView.findViewById(R.id.repoNameTV);

    }
}