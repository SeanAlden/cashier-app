package project.c14210052_c14210182.proyekakhir_paba

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import project.c14210052_c14210182.proyekakhir_paba.adapter.adapterDetailPenjualan
import project.c14210052_c14210182.proyekakhir_paba.dataClass.detailPenjualan

class detailPenjualanPage : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: adapterDetailPenjualan
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_penjualan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        backButton = findViewById(R.id.btnBackFromDetailPenjualan)

        backButton.setOnClickListener {
            finish()
        }

        recyclerView = findViewById(R.id.rvDetailPenjualan)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.isNestedScrollingEnabled = false

        val detailItems: detailPenjualan? = intent.getParcelableExtra("Detail")
        detailItems?.let {
            adapter = adapterDetailPenjualan(detailItems)
            recyclerView.adapter = adapter

            findViewById<TextView>(R.id.tvIDDetailPenjualan).text = detailItems.id
            findViewById<TextView>(R.id.tvTanggalDetailPenjualan).text = detailItems.tanggal
            findViewById<TextView>(R.id.tvWaktuDetailPenjualan).text = detailItems.waktu
            findViewById<TextView>(R.id.tvTotalDetailPenjualan).text = detailItems.total.toString()
            findViewById<TextView>(R.id.tvBayarDetailPenjualan).text = detailItems.bayar.toString()
            findViewById<TextView>(R.id.tvKembalianDetailPenjualan).text = detailItems.kembalian.toString()
        }





    }
}