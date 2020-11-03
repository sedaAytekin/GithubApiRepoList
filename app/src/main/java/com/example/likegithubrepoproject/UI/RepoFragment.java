package com.example.likegithubrepoproject.UI;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.likegithubrepoproject.Adapter.ReposAdapter;
import com.example.likegithubrepoproject.MainActivity;
import com.example.likegithubrepoproject.Model.ReposModel;
import com.example.likegithubrepoproject.R;
import com.example.likegithubrepoproject.ViewModel.ReposViewModel;

import java.util.List;

public class RepoFragment extends Fragment implements LifecycleOwner, View.OnClickListener{

    ReposViewModel reposViewModel;
    RecyclerView reposRV;
    EditText usernameET;
    Button submitBT;
    ReposAdapter reposAdapter;
    Context context;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.repo_fragment, container, false);

        reposRV = view.findViewById(R.id.reposRV);
        submitBT = view.findViewById(R.id.submitBT);
        usernameET = view.findViewById(R.id.usernameET);

        submitBT.setOnClickListener(this);

        // Set up progress before call
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMax(100);
        progressDialog.setIndeterminate(true);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));//background transparan olsun
        progressDialog.setMessage("Lütfen bekleyiniz..");
        progressDialog.getWindow().setGravity(Gravity.CENTER);
        progressDialog.setCancelable(false);

        //recyclerview item aralarına divider eklenir
        reposRV.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));

        reposViewModel = ViewModelProviders.of(getActivity()).get(ReposViewModel.class);

        //request-response süresinde progressbar gösterilir.
        reposViewModel.getShowProgressBar().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                    progressDialog.show();
                else
                    progressDialog.dismiss();

            }
        });

        //repo listesi dinleniyor.
        reposViewModel.getReposLiveData().observe(this, new Observer<List<ReposModel>>() {
            @Override
            public void onChanged(final List<ReposModel> reposList) {
                reposAdapter = new ReposAdapter(getActivity(), reposList, new ReposAdapter.OnItemClickListener() {
                    @Override
                    public void onRepo(String userLogin, int position) {
                        progressDialog.dismiss();//servisten cevap geldiğinde progres kaldırılır.
                        //tıklanan rv item ı setleniyor.
                        reposViewModel.reposDetailLiveData.setValue(reposList.get(position));

                        ReposDetailFragment reposDetailFragment = new ReposDetailFragment();
                        FragmentManager manager = getActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.replace(R.id.content, reposDetailFragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }

                    @Override
                    public void onStar(ImageView callerImage) {

                    }
                });
                reposRV.setHasFixedSize(true);
                reposRV.setLayoutManager(new LinearLayoutManager(getActivity()));
                reposRV.setAdapter(reposAdapter);
            }
        });

        //username dinleniyor
        reposViewModel.getUserNameLiveData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String userName) {
                reposViewModel.init(); //username yazıldığında servise istek atılıyor.
            }
        });

        return view;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submitBT:

                // servise istek atıldığında progress gösterilir.
                progressDialog.show();

                //username yazıldıysa,
                if (!usernameET.getText().toString().equals(""))
                    reposViewModel.getUserNameLiveData().setValue(usernameET.getText().toString());
                else
                    Toast.makeText(context, "Kullanıcı adı yazınız..", Toast.LENGTH_SHORT).show();

                break;

        }
    }
}
