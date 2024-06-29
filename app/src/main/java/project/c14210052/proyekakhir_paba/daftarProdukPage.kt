package project.c14210052.proyekakhir_paba

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import project.c14210052.proyekakhir_paba.adapter.adapterProduct
import project.c14210052.proyekakhir_paba.dataClass.Produk

class daftarProdukPage : AppCompatActivity() {

    private lateinit var _addProductBtn: FloatingActionButton
    private lateinit var _backBtn: ImageButton
    private lateinit var _categoryBtn: Button
    private lateinit var spinnerCategory: Spinner
    private lateinit var rvProduct: RecyclerView
    private lateinit var _productList: MutableList<Produk>
    private lateinit var adapterProduct: adapterProduct

    //    private var _productList = mutableListOf<Produk>()
    private var _productListener: ListenerRegistration? = null
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

        loadProducts()
        loadCategories()

        _addProductBtn = findViewById(R.id.btnAddProduct)
        _backBtn = findViewById(R.id.btnBackFromProductList)
        _categoryBtn = findViewById(R.id.btnCategory)
        spinnerCategory = findViewById(R.id.spinnerCategory)
        rvProduct = findViewById(R.id.rvProduct)
//        recyclerView = findViewById(R.id.rvProduct)

        _productList = mutableListOf()
        adapterProduct = adapterProduct(_productList,
            { produk ->
                // pergi ke detail
                navigateToDetailPage(produk)
            },
            { produk ->
                // Delete product
                deleteProduct(produk)
            }, { produk ->
                // Edit product
                navigateToEditProductPage(produk)
            })

        rvProduct.layoutManager = LinearLayoutManager(this)
        rvProduct.adapter = adapterProduct

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

    }

    private fun navigateToDetailPage(produk: Produk) {
        val intent = Intent(this@daftarProdukPage, detailProductPage::class.java)
        intent.putExtra(
            "produk_id",
            produk.idProduk
        ) // kirim ID produk atau data yang diperlukan untuk detail
        startActivity(intent)
    }

    private fun navigateToEditProductPage(produk: Produk) {
        val intent = Intent(this@daftarProdukPage, editProductPage::class.java)
        intent.putExtra("produk", produk)
        startActivity(intent)
    }

    private fun loadProducts() {
        _productListener?.remove() // Remove the previous listener if it exists
        _productListener = firestore.collection("tbProduk")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                snapshots?.let { snapshot ->
                    _productList.clear()
                    for (document in snapshot.documents) {
                        val suppItem = document.toObject(Produk::class.java)
                        suppItem?.let {
                            it.idProduk = document.id
                            _productList.add(it)
                        }
                    }
                    adapterProduct.notifyDataSetChanged()
                }
            }
    }

    private fun deleteProduct(produk: Produk) {
        val db = FirebaseFirestore.getInstance()

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Apakah ingin menghapus produk ini?")
        builder.setMessage("Produk ini akan dihapus secara permanen.")

        builder.setPositiveButton("Yes") { dialog, which ->
            db.collection("tbProduk").document(produk.idProduk!!)
                .delete()
                .addOnSuccessListener {
                    // Successfully deleted
                    _productList.remove(produk)
                    adapterProduct.notifyDataSetChanged()
                    Toast.makeText(this, "Produk berhasil dihapus.", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    // Failed to delete
                    Toast.makeText(this, "Gagal menghapus produk.", Toast.LENGTH_SHORT).show()
                }
        }

        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    private fun editProduct(produk: Produk) {

    }

    private fun loadCategories() {
        val db = FirebaseFirestore.getInstance()
        val kategoriList = mutableListOf<String>()

        db.collection("kategoriProduk")
            .get()
            .addOnSuccessListener { documents ->
                kategoriList.add("Semua Kategori") // Add "Semua Kategori" as the default option
                for (document in documents) {
                    val kategori = document.getString("namaKategori")
                    kategori?.let { kategoriList.add(it) }
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, kategoriList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerCategory.adapter = adapter

                // Setup initial category filter if needed
                spinnerCategory.setSelection(0) // Select first item as default
                spinnerCategory.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            val selectedCategory = parent?.getItemAtPosition(position).toString()
                            filterProductsByCategory(selectedCategory)
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {
                            // Do nothing
                        }
                    }
            }
            .addOnFailureListener { exception ->
                // Handle the error
                Toast.makeText(this, "Gagal memuat kategori", Toast.LENGTH_SHORT).show()
            }
    }

    private fun filterProductsByCategory(category: String) {
        _productListener?.remove() // Remove previous listener if exists

        if (category == "Semua Kategori") {
            // Load all products
            loadProducts()
        } else {
            // Load products filtered by category
            _productListener = firestore.collection("tbProduk")
                .whereEqualTo(
                    "kategoriProduk",
                    category
                ) // Adjust field name according to your Firestore structure
                .addSnapshotListener { snapshots, e ->
                    if (e != null) {
                        return@addSnapshotListener
                    }

                    snapshots?.let { snapshot ->
                        _productList.clear()
                        for (document in snapshot.documents) {
                            val product = document.toObject(Produk::class.java)
                            product?.let {
                                it.idProduk = document.id
                                _productList.add(it)
                            }
                        }
                        adapterProduct.notifyDataSetChanged()
                    }
                }
        }
    }
}