package ru.edgar.launcher.other;

import ru.edgar.launcher.model.News;
import ru.edgar.launcher.model.Servers;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Interface {


    @GET("https://api-free.edgars.site/matrp/servers.json")
    Call<List<Servers>> getServers();

    @GET("https://api-free.edgars.site/matrp/stories.json")
    Call<List<News>> getNews();

}
