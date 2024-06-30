package project.c14210052_c14210182.proyekakhir_paba

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import project.c14210052_c14210182.proyekakhir_paba.api.NewsItem

class adapterDetailNewsPage(private val newsList: List<NewsItem>) :
    RecyclerView.Adapter<adapterDetailNewsPage.NewsViewHolder>() {

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleTextView: TextView = view.findViewById(R.id.txtDetailNewsTitle)
        val sourceTextView: TextView = view.findViewById(R.id.txtDetailNewsSource)
        val descriptionTextView: TextView = view.findViewById(R.id.txtDetailNewsDesc)
        val newsImageView: ImageView = view.findViewById(R.id.imgDetailNews)
        val openSourceButton: Button = view.findViewById(R.id.btnToSource)
        val progressBar: View = view.findViewById(R.id.progressBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_detail_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = newsList[position]

        holder.titleTextView.text = newsItem.title ?: "No Title"
        holder.sourceTextView.text = newsItem.source?.name ?: "No Source"
        holder.descriptionTextView.text = newsItem.description ?: "No Description"

        holder.progressBar.visibility = View.VISIBLE
        Glide.with(holder.newsImageView.context)
            .load(newsItem.urlToImage)
            .timeout(5000) // Set timeout for 5 seconds
            .listener(object : com.bumptech.glide.request.RequestListener<android.graphics.drawable.Drawable> {
                override fun onLoadFailed(
                    e: com.bumptech.glide.load.engine.GlideException?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<android.graphics.drawable.Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: android.graphics.drawable.Drawable?,
                    model: Any?,
                    target: com.bumptech.glide.request.target.Target<android.graphics.drawable.Drawable>?,
                    dataSource: com.bumptech.glide.load.DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    holder.progressBar.visibility = View.GONE
                    return false
                }
            })
            .into(holder.newsImageView)

        holder.openSourceButton.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = android.net.Uri.parse(newsItem.url)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }
}


