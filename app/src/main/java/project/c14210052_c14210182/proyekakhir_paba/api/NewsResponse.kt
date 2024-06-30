package project.c14210052_c14210182.proyekakhir_paba.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class NewsResponse(
    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("totalResults")
    val totalResults: Int? = null,

    @field:SerializedName("articles")
    val articles: List<NewsItem>? = null
)

@Parcelize
data class NewsItem(
    @field:SerializedName("source")
    val source: Source? = null,

    @field:SerializedName("author")
    val author: String?,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("urlToImage")
    val urlToImage: String? = null,

    @field:SerializedName("publishedAt")
    val publishedAt: String? = null,

    @field:SerializedName("content")
    val content: String? = null
) : Parcelable

@Parcelize
data class Source(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("name")
    val name: String? = null
) : Parcelable

//data class NewsResponse(val articles: List<NewsItem>)
//
//data class NewsItem(val title: String, val urlToImage: String)
