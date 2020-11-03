package com.example.likegithubrepoproject.Interface;

import com.example.likegithubrepoproject.Model.ReposModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubService {

    @GET("users/{user}/repos")
    Call<List<ReposModel>> listRepos(@Path("user") String user);

}
