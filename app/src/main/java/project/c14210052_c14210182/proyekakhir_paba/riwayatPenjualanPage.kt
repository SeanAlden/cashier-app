package project.c14210052_c14210182.proyekakhir_paba

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import project.c14210052_c14210182.proyekakhir_paba.adapter.adapterRiwayat
import project.c14210052_c14210182.proyekakhir_paba.dataClass.DetailPenjualan

class riwayatPenjualanPage : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var rvRiwayat: RecyclerView
    private var _riwayatList = mutableListOf<DetailPenjualan>()
    private lateinit var adapterRiwayat: adapterRiwayat

    private val db = FirebaseFirestore.getInstance()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_riwayat_penjualan)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        backButton = findViewById(R.id.btnBackFromInventory)

        backButton.setOnClickListener {
            finish()
        }

        // Initialize the RecyclerView and adapter
        rvRiwayat = findViewById(R.id.rvRiwayatPenjualan)
        rvRiwayat.layoutManager = LinearLayoutManager(this)
        adapterRiwayat = adapterRiwayat(_riwayatList) { DetailPenjualan ->
            val intent = Intent(this@riwayatPenjualanPage, detailPenjualanPage::class.java)
            intent.putExtra("Detail", DetailPenjualan)
            startActivity(intent)
        }
        rvRiwayat.adapter = adapterRiwayat

        // Fetch data from Firestore
        db.collection("tbRiwayatPenjualan")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val product = document.toObject(DetailPenjualan::class.java)
                    product?.let {
                        it.id = document.id
                        _riwayatList.add(it)
                    }
                }
                adapterRiwayat.notifyDataSetChanged()
            }
    }
}
