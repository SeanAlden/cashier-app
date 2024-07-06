package project.c14210052_c14210182.proyekakhir_paba

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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
import project.c14210052_c14210182.proyekakhir_paba.dataBaseOffline.RiwayatOffline
import project.c14210052_c14210182.proyekakhir_paba.dataBaseOffline.RiwayatOfflineDatabase
import project.c14210052_c14210182.proyekakhir_paba.dataClass.detailPenjualan

class riwayatPenjualanPage : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var rvRiwayat: RecyclerView
    private var _riwayatList = mutableListOf<detailPenjualan>()
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
        val DB = RiwayatOfflineDatabase.getDatabase(this)
        val riwayatOfflineDao = DB.RiwayatOfflineDao()

        backButton = findViewById(R.id.btnBackFromInventory)

        backButton.setOnClickListener {
            finish()
        }

        rvRiwayat = findViewById(R.id.rvRiwayatPenjualan)
        rvRiwayat.layoutManager = LinearLayoutManager(this)
        adapterRiwayat = adapterRiwayat(_riwayatList) { DetailPenjualan ->
            val intent = Intent(this@riwayatPenjualanPage, detailPenjualanPage::class.java)
            intent.putExtra("Detail", DetailPenjualan)
            startActivity(intent)
        }
        rvRiwayat.adapter = adapterRiwayat

        if (NetworkUtils.isOnline(this)) {
            // Fetch data from Firestore
            db.collection("tbRiwayatPenjualan")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val product = document.toObject(detailPenjualan::class.java)
                        product?.let {
                            it.id = document.id
                            _riwayatList.add(it)

                            val riwayatOffline = RiwayatOffline(
                                id = it.id,
                                tanggal = it.tanggal,
                                waktu = it.waktu,
                                namaProduk = it.namaProduk,
                                hargaPerProduk = it.hargaPerProduk,
                                jumlah = it.jumlah,
                                total = it.total,
                                bayar = it.bayar,
                                kembalian = it.kembalian
                            )
                            riwayatOfflineDao.insert(riwayatOffline)

                        }
                    }
                    adapterRiwayat.notifyDataSetChanged()
                }
        } else {
            val offlineRiwayatList = riwayatOfflineDao.getAllRiwayat()
            offlineRiwayatList.forEach { offlineRiwayat ->
                val detailPenjualan = detailPenjualan(
                    id = offlineRiwayat.id,
                    tanggal = offlineRiwayat.tanggal,
                    waktu = offlineRiwayat.waktu,
                    namaProduk = offlineRiwayat.namaProduk,
                    hargaPerProduk = offlineRiwayat.hargaPerProduk,
                    jumlah = offlineRiwayat.jumlah,
                    total = offlineRiwayat.total,
                    bayar = offlineRiwayat.bayar,
                    kembalian = offlineRiwayat.kembalian
                )
                _riwayatList.add(detailPenjualan)
            }
            adapterRiwayat.notifyDataSetChanged()
        }
    }

    object NetworkUtils {
        fun isOnline(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork ?: return false
                val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
                return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            } else {
                @Suppress("DEPRECATION")
                val activeNetworkInfo = connectivityManager.activeNetworkInfo
                @Suppress("DEPRECATION")
                return activeNetworkInfo != null && activeNetworkInfo.isConnected
            }
        }
    }
}
