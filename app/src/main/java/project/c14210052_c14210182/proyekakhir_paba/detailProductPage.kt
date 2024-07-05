package project.c14210052_c14210182.proyekakhir_paba

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import project.c14210052_c14210182.proyekakhir_paba.dataClass.Produk

class detailProductPage : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance()
    private lateinit var _tvNamaProdukDetail : TextView
    private lateinit var _tvDeskripsiProdukDetail : TextView
    private lateinit var _tvKategoriProdukDetail : TextView
    private lateinit var _tvSupplierProdukDetail : TextView
    private lateinit var _tvHargaPokokDetail : TextView
    private lateinit var _tvHargaJualDetail : TextView
    private lateinit var _tvJumlahProdukDetail : TextView
    private lateinit var _tvSatuanProdukDetail : TextView
    private lateinit var _btnBackFromDetailProduct : ImageButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _tvNamaProdukDetail = findViewById(R.id.tvNamaProdukDetaill)
        _tvDeskripsiProdukDetail = findViewById(R.id.tvDeskripsiProdukDetaill)
        _tvKategoriProdukDetail = findViewById(R.id.tvKategoriProdukDetaill)
        _tvSupplierProdukDetail = findViewById(R.id.tvSupplierProdukDetaill)
        _tvHargaPokokDetail = findViewById(R.id.tvHargaPokokDetaill)
        _tvHargaJualDetail = findViewById(R.id.tvHargaJualDetaill)
        _tvJumlahProdukDetail = findViewById(R.id.tvJumlahProdukDetaill)
        _tvSatuanProdukDetail = findViewById(R.id.tvSatuanProdukDetaill)

            // mengambil data yang dikirim melalui intent
            val produkId = intent.getStringExtra("produk_id")

            // Jika ada id produk yang dikirim, maka akan mengambil data produk dari firestore database
            // berdasarkan id
            if (produkId != null) {
                db.collection("tbProduk").document(produkId)
                    .get()
                    .addOnSuccessListener { document ->
                        if (document != null) {
                            // melakukan mapping data ke UI
                            val produk = document.toObject(Produk::class.java)
                            produk?.let {
                                _tvNamaProdukDetail.setText(produk.namaProduk)
                                _tvDeskripsiProdukDetail.setText(produk.deskripsiProduk)
                                _tvKategoriProdukDetail.setText(produk.kategoriProduk)
                                _tvSupplierProdukDetail.setText(produk.supplierProduk)
                                _tvHargaPokokDetail.setText(produk.hargaPokokProduk.toString())
                                _tvHargaJualDetail.setText(produk.hargaJualProduk.toString())
                                _tvJumlahProdukDetail.setText(produk.jumlahProduk.toString())
                                _tvSatuanProdukDetail.setText(produk.satuanProduk)
                            }
                        } else {
                            Log.d("Product Detail", "data produk tidak ditemukan")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.d("Fetch Product", "gagal mengambil data produk")
                    }
            } else {
                Log.d("Product ID", "id produk tidak ditemukan")
            }
            _btnBackFromDetailProduct = findViewById(R.id.btnBackFromDetailProduct)

            _btnBackFromDetailProduct.setOnClickListener {
                finish()
            }
        }
    }