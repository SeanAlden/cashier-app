package project.c14210052.proyekakhir_paba

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import project.c14210052.proyekakhir_paba.adapter.adapterKategori
import project.c14210052.proyekakhir_paba.dataClass.KategoriProduk
import project.c14210052.proyekakhir_paba.dataClass.Produk

class daftarProdukPage : AppCompatActivity() {

    private lateinit var _addProductBtn : FloatingActionButton
    private lateinit var _backBtn : ImageButton
    private lateinit var _categoryBtn : Button
    private lateinit var spinnerCategory: Spinner
    private lateinit var recyclerView: RecyclerView
    private lateinit var produkAdapter: adapterKategori
    private val produkList = mutableListOf<KategoriProduk>()
    private val kategoriList = mutableListOf<KategoriProduk>()
    private val firestore = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_daftar_produk)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _addProductBtn = findViewById(R.id.btnAddProduct)
        _backBtn = findViewById(R.id.btnBackFromProductList)
        _categoryBtn = findViewById(R.id.btnCategory)
        spinnerCategory = findViewById(R.id.spinnerCategory)
//        recyclerView = findViewById(R.id.rvProduct)

        _addProductBtn.setOnClickListener {
            val intent = Intent(this@daftarProdukPage, addProductPage::class.java)
            startActivity(intent)
        }

        _backBtn.setOnClickListener {
            val intent = Intent(this@daftarProdukPage, MainActivity::class.java)
            startActivity(intent)
        }

        _categoryBtn.setOnClickListener {
            val intent = Intent(this@daftarProdukPage, productCategoryPage::class.java)
            startActivity(intent)
        }

//        setupRecyclerView()
        fetchCategoriesFromFirestore()
    }

//    private fun setupRecyclerView() {
//        produkAdapter = adapterKategori(produkList)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = produkAdapter
//    }

    private fun fetchCategoriesFromFirestore() {
        firestore.collection("kategoriProduk")
            .get()
            .addOnSuccessListener { result ->
                val categoryNames = mutableListOf<String>()
                for (document in result) {
                    val namaKategori = document.getString("namaKategori")
                    namaKategori?.let {
                        categoryNames.add(it)
                    }
                }
                setupSpinner(categoryNames)
            }
            .addOnFailureListener {
                // Handle failure
            }
    }
    private fun setupSpinner(categoryNames: List<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categoryNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerCategory.adapter = adapter
    }
}