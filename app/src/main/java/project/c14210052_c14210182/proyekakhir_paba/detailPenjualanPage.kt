package project.c14210052_c14210182.proyekakhir_paba

import android.os.Bundle
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

        recyclerView = findViewById(R.id.rvDetailPenjualan)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.isNestedScrollingEnabled = false

        // tes
        val detailItems = detailPenjualan(
            id = "1",
            tanggal = "4 November 2023",
            waktu = "14:00",
            namaProduk = mutableListOf("Product 1", "Product 2", "Product 3", "Product 4"),
            hargaPerProduk = mutableListOf("10.000", "20.000", "30.000", "40.000"),
            jumlah = mutableListOf("1", "2", "3", "4"),
            total = 100000,
            bayar = 120000,
            kembalian = 20000
        )

        adapter = adapterDetailPenjualan(detailItems)
        recyclerView.adapter = adapter

        findViewById<TextView>(R.id.tvIsiID).text = detailItems.id
        findViewById<TextView>(R.id.tvIsiTanggal).text = detailItems.tanggal
        findViewById<TextView>(R.id.tvIsiWaktu).text = detailItems.waktu
        findViewById<TextView>(R.id.tvIsiTotal).text = detailItems.total.toString()
        findViewById<TextView>(R.id.tvIsiBayar).text = detailItems.bayar.toString()
        findViewById<TextView>(R.id.tvIsiKembalian).text = detailItems.kembalian.toString()


    }
}