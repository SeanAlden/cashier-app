package project.c14210052_c14210182.proyekakhir_paba.adapter

import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import project.c14210052_c14210182.proyekakhir_paba.R
import project.c14210052_c14210182.proyekakhir_paba.detailNewsPage
import java.util.logging.Handler

class adapterNews(private val newsList: List<project.c14210052_c14210182.proyekakhir_paba.api.NewsItem>) : RecyclerView.Adapter<adapterNews.NewsViewHolder>() {

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val newsTitle: TextView = view.findViewById(R.id.txtNews)
        val newsImage: ImageView = view.findViewById(R.id.imgNews)

        // progress bar saat load image
        val progressBar: ProgressBar= view. findViewById(R.id.progressBar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news_api, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = newsList[position]
        holder.newsTitle.text = newsItem.title

        // menampilkan progress bar saat memuat gambar
        holder.progressBar.visibility = View.VISIBLE

//        holder.newsImage.setImageResource(newsItem.imageResId)
        // menggunakan glide untuk menampilkan gambar
        Glide.with(holder.newsImage.context)
            .load(newsItem.urlToImage)
//            .load(newsItem.imageResId)
            .transform(RoundedCorners(20)) // mengatur radius corner dengan nilai 20
            // mengatur tampilan progress bar
            .listener(object: RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    // menyembunyikan progress bar saat gambar gagal dimuat
                    holder.progressBar.visibility = View.GONE
                    return false

                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    // menyembunyikan progress bar saat gambar berhasil dimuat setelah 5 detik
                    android.os.Handler().postDelayed({
                        holder.progressBar.visibility = View.GONE
                    }, 2000)
//                    holder.progressBar.visibility = View.GONE
                    return false
                }
            })
            .into(holder.newsImage)

        // Set onClickListener to open detailNewsPage with the clicked newsItem
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, detailNewsPage::class.java)
            intent.putExtra("news_item", newsItem)
            context.startActivity(intent)
        }

//        holder.itemView.setOnClickListener {
//            val intent = Intent(holder.itemView.context, detailNewsPage::class.java).apply {
//                putExtra("news_title", newsItem.title)
//                putExtra("news_source", newsItem.source)
//                putExtra("news_description", newsItem.description)
//                putExtra("news_image_url", newsItem.urlToImage)
//                putExtra("news_url", newsItem.url)
//            }
//            holder.itemView.context.startActivity(intent)
//        }
    }

    override fun getItemCount(): Int = newsList.size
}



