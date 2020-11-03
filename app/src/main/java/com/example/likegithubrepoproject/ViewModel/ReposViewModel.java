package com.example.likegithubrepoproject.ViewModel;

import android.app.ProgressDialog;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.likegithubrepoproject.Interface.GithubService;
import com.example.likegithubrepoproject.Model.ReposModel;
import com.example.likegithubrepoproject.Retrofit.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReposViewModel extends ViewModel {
    public MutableLiveData<List<ReposModel>> getReposLiveData() {
        return reposLiveData;
    }

    private MutableLiveData<List<ReposModel>> reposLiveData = new MutableLiveData<>();

    public MutableLiveData<Boolean> getShowProgressBar() {
        return showProgressBar;
    }

    private MutableLiveData<Boolean> showProgressBar = new MutableLiveData<>();

    public MutableLiveData<ReposModel> getReposDetailLiveData() {
        return reposDetailLiveData;
    }

    public MutableLiveData<ReposModel> reposDetailLiveData = new MutableLiveData<>();

    public MutableLiveData<String> getUserNameLiveData() {
        return userNameLiveData;
    }

    public MutableLiveData<String> userNameLiveData = new MutableLiveData<>();

    GithubService githubService;
    List<ReposModel> repoList = new ArrayList<>();

    public ReposViewModel() {
        init();
    }

    //liste burada dolduruluyor.
    public void init(){
        if (userNameLiveData.getValue() != null && !userNameLiveData.getValue().equals(""))
            getUserRepos();
    }

    private void getUserRepos(){

        showProgressBar.setValue(true);
        githubService = ApiClient.getClient().create(GithubService.class);

        githubService.listRepos(getUserNameLiveData().getValue()).enqueue(new Callback<List<ReposModel>>() {
            @Override
            public void onResponse(Call<List<ReposModel>> call, Response<List<ReposModel>> response) {
                repoList = response.body();
                reposLiveData.setValue(repoList);
                showProgressBar.setValue(false);
            }
            @Override
            public void onFailure(Call<List<ReposModel>> call, Throwable t) {
                String aa = "dsd";
                showProgressBar.setValue(false);
            }
        });
    }
}
