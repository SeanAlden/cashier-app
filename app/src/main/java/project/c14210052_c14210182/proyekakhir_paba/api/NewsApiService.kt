package project.c14210052_c14210182.proyekakhir_paba.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/everything")
    fun getSupermarketNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): Call<NewsResponse>
}

//import retrofit2.Call
//import retrofit2.http.GET
//
//interface NewsApiService {
//    @GET("v2/top-headlines?country=us&apiKey=16f57f8d0e444696863da47a233e651b")
//    fun getTopHeadlines(): Call<NewsResponse>
//}