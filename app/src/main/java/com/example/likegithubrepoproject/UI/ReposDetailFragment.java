package com.example.likegithubrepoproject.UI;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.likegithubrepoproject.R;
import com.example.likegithubrepoproject.ViewModel.ReposViewModel;

public class ReposDetailFragment extends Fragment {

    private ImageView avatarIV;
    private TextView ownerNameTV;
    private TextView starCountTV;
    private TextView openIssuesTV;
    private ReposViewModel reposViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.repo_detail, container, false);

        avatarIV = view.findViewById(R.id.avatarIV);
        ownerNameTV = view.findViewById(R.id.ownerNameTV);
        starCountTV = view.findViewById(R.id.starCountTV);
        openIssuesTV = view.findViewById(R.id.openIssuesTV);


        //buradaki değer sıfırlanmazsa datalar dolu gelecektir.
        reposViewModel = ViewModelProviders.of(getActivity()).get(ReposViewModel.class);

        // add back arrow to toolbar
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        toolbar.inflateMenu(R.menu.toolbar_menu);

        toolbar.setTitle(reposViewModel.getReposDetailLiveData().getValue().getName());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    Log.i("MainActivity", "popping backstack");
                    fm.popBackStack();
                } else {
                    Log.i("MainActivity", "nothing on backstack, calling super");
                }
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(getActivity(), "sedasasa", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        ownerNameTV.setText(reposViewModel.getReposDetailLiveData().getValue().getOwner().getLogin());
        starCountTV.setText("Stars: " + String.valueOf(reposViewModel.getReposDetailLiveData().getValue().getStargazersCount()));
        openIssuesTV.setText("Opened issues: " + String.valueOf(reposViewModel.getReposDetailLiveData().getValue().getOpenIssuesCount()));

        Glide.with(getActivity())
                .load(reposViewModel.getReposDetailLiveData().getValue().getOwner().getAvatarUrl())
                .centerCrop()
                .into(avatarIV);


        return view;
    }


}
