package project.c14210052_c14210182.proyekakhir_paba.adapter

// cuma buat tes tampilan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import project.c14210052_c14210182.proyekakhir_paba.R
import project.c14210052_c14210182.proyekakhir_paba.dataClass.NewsItem

class adapterNews(private val newsList: List<NewsItem>) : RecyclerView.Adapter<adapterNews.NewsViewHolder>() {

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val newsTitle: TextView = view.findViewById(R.id.txtNews)
        val newsImage: ImageView = view.findViewById(R.id.imgNews)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_api, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = newsList[position]
        holder.newsTitle.text = newsItem.title
//        holder.newsImage.setImageResource(newsItem.imageResId)
        // menggunakan glide untuk menampilkan gambar
        Glide.with(holder.newsImage.context)
            .load(newsItem.imageResId)
            .transform(RoundedCorners(20)) // mengatur radius corner dengan nilai 20
            .into(holder.newsImage)
    }

    override fun getItemCount(): Int = newsList.size
}



