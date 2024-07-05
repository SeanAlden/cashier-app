package project.c14210052_c14210182.proyekakhir_paba

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.c14210052_c14210182.proyekakhir_paba.api.NewsItem

class detailNewsPage : AppCompatActivity() {

    private lateinit var _btnBackFromDetailNews: Button
    private lateinit var rvDetailNews: RecyclerView
    private lateinit var adapterDetailNewsPage: adapterDetailNewsPage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_news_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _btnBackFromDetailNews = findViewById(R.id.btnBackFromDetailNews)
        rvDetailNews = findViewById(R.id.rvDetailNews)

        // mendapatkan item berita dengan intent
        val newsItem = intent.getParcelableExtra<NewsItem>("news_item")

        if (newsItem != null) {
            val newsList = listOf(newsItem)

            rvDetailNews.layoutManager = LinearLayoutManager(this)
            adapterDetailNewsPage = adapterDetailNewsPage(newsList)
            rvDetailNews.adapter = adapterDetailNewsPage
        }

        _btnBackFromDetailNews.setOnClickListener {
            finish()
        }
    }
}
