package project.c14210052_c14210182.proyekakhir_paba

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isNotEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import project.c14210052_c14210182.proyekakhir_paba.adapter.adapterAddCashier
import project.c14210052_c14210182.proyekakhir_paba.dataClass.Produk

class addCashierPage : AppCompatActivity() {
    private lateinit var backButton: ImageButton

    private lateinit var spinnerCategory: Spinner

    private var db = FirebaseFirestore.getInstance()

    private lateinit var rvAddCashier: RecyclerView

    private var _productListener: ListenerRegistration? = null
    private var _productList = mutableListOf<Produk>()
    private lateinit var adapterAddCashier: adapterAddCashier


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_cashier)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        adapterAddCashier = adapterAddCashier(_productList,
            { produk, jumlahBeli ->
                finishResult(produk, jumlahBeli)
            })

        backButton = findViewById(R.id.btnBackFromAddCashier)

        backButton.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }

        rvAddCashier = findViewById(R.id.rvAddCashier)
        rvAddCashier.layoutManager = LinearLayoutManager(this)
        rvAddCashier.adapter = adapterAddCashier

        spinnerCategory = findViewById(R.id.spinnerCategoryCashier)

        fetchDataFromFirebase()
        loadCategory()

        val delay = 1000 // 1 seconds after user stops typing
        var lastTextEdit: Long = 0
        var handler = Handler(Looper.getMainLooper())

        val inputFinishChecker = Runnable {
            if (SystemClock.uptimeMillis() > (lastTextEdit + delay - 500)) {
                filterProductsByCategoryWithQuery(spinnerCategory.selectedItem.toString())
            }
        }

        val editText = findViewById<EditText>(R.id.etSearchCashier)
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                handler.removeCallbacks(inputFinishChecker)
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.length > 0) {
                    lastTextEdit = SystemClock.uptimeMillis()
                    handler.postDelayed(inputFinishChecker, delay.toLong())
                } else
                    if (spinnerCategory.isNotEmpty()) {
                        filterProductsByCategory(spinnerCategory.selectedItem.toString())
                    }
            }
        })
    }

    private fun finishResult(produk: Produk, jumlahBeli: Int) {
        val resultIntent = Intent()
        resultIntent.putExtra("result_key", produk)
        resultIntent.putExtra("jumlah_beli", jumlahBeli)

        // Set the result and finish
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    private fun loadQuery(query: String) {
        if (!_productList.isEmpty()) {
            val filteredList = _productList.filter {
                it.namaProduk?.lowercase()?.contains(query.lowercase()) == true
            }

            _productList.clear()
            _productList.addAll(filteredList)
            adapterAddCashier.notifyDataSetChanged()

        }
    }


    private fun loadCategory() {
        val kategoriList = mutableListOf<String>()

        db.collection("kategoriProduk")
            .get()
            .addOnSuccessListener { documents ->
                kategoriList.add("Semua Kategori") // Add "Semua Kategori" as the default option
                for (document in documents) {
                    val kategori = document.getString("namaKategori")
                    kategori?.let { kategoriList.add(it) }
                }
                val spinnerAdapter =
                    ArrayAdapter(this, android.R.layout.simple_spinner_item, kategoriList)
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

                val _btnKategoriProduk = findViewById<Button>(R.id.btnCategoryCashier)
                spinnerCategory.adapter = spinnerAdapter

                _btnKategoriProduk.setOnClickListener {
                    val selectedCategory = spinnerCategory.selectedItem.toString()
                    filterProductsByCategoryWithQuery(selectedCategory)
                }
            }

            .addOnFailureListener { exception ->
                // Handle the error
                Toast.makeText(this, "Gagal memuat kategori", Toast.LENGTH_SHORT).show()
            }
    }

    private fun filterProductsByCategoryWithQuery(category: String) {
        _productListener?.remove() // Remove previous listener if exists

        if (category == "Semua Kategori") {
            _productListener = db.collection("tbProduk")
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
                    }
                }
        } else {
            // Load products filtered by category
            _productListener = db.collection("tbProduk")
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
                    }
                }
        }
        loadQuery(findViewById<EditText>(R.id.etSearchCashier).text.toString())


    }


    private fun filterProductsByCategory(category: String) {
        _productListener?.remove() // Remove previous listener if exists

        if (category == "Semua Kategori") {
            _productListener = db.collection("tbProduk")
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
                    }
                }
        } else {
            // Load products filtered by category
            _productListener = db.collection("tbProduk")
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
                    }
                }
        }
        adapterAddCashier.notifyDataSetChanged()
    }


    private fun fetchDataFromFirebase() {
        db.collection("tbProduk")
            .get()
            .addOnSuccessListener { snapshots ->
                _productList.clear()
                for (document in snapshots.documents) {
                    val product = document.toObject(Produk::class.java)
                    product?.let {
                        it.idProduk = document.id
                        _productList.add(it)
                    }
                }
                adapterAddCashier.notifyDataSetChanged() // Notify adapter of data change
            }
            .addOnFailureListener { exception ->
                // Handle error loading products
                Toast.makeText(this, "Failed to load products", Toast.LENGTH_SHORT).show()
            }
    }


}