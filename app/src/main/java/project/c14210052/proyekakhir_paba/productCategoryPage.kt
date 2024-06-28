package project.c14210052.proyekakhir_paba

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import project.c14210052.proyekakhir_paba.adapter.adapterKategori
import project.c14210052.proyekakhir_paba.dataClass.KategoriProduk

class productCategoryPage : AppCompatActivity() {

    private lateinit var _backBtn: ImageButton
    private lateinit var _addBtn: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterKategori: adapterKategori
    private val kategoriList = mutableListOf<KategoriProduk>()

    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_category)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _backBtn = findViewById(R.id.btnBackFromAddCategory)
        _addBtn = findViewById(R.id.btnAddProductCategory)
        recyclerView = findViewById(R.id.rvCategory)

        _backBtn.setOnClickListener {
            val intent = Intent(this@productCategoryPage, daftarProdukPage::class.java)
            startActivity(intent)
        }

        _addBtn.setOnClickListener {
            showAddCategoryDialog()
        }

        setupRecyclerView()
        fetchCategoriesFromFirestore()
    }

    @SuppressLint("MissingInflatedId")
    private fun showAddCategoryDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_category, null)
        val editTextCategoryName = dialogView.findViewById<EditText>(R.id.etCategoryName)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Tambah Kategori")
            .setMessage("Tambahkan nama kategori!")
            .setView(dialogView)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Save") { _, _ ->
                val categoryName = editTextCategoryName.text.toString()
                if (categoryName.isNotEmpty()) {
                    addCategoryToFirestore(categoryName)
                }
            }
            .create()

        dialog.show()
    }

    private fun showEditCategoryDialog(kategori: KategoriProduk) {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_category, null)
        val editTextCategoryName = dialogView.findViewById<EditText>(R.id.etCategoryName)
        editTextCategoryName.setText(kategori.namaKategori)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Edit Kategori")
            .setMessage("Edit nama kategori!")
            .setView(dialogView)
            .setNegativeButton("Cancel", null)
            .setPositiveButton("Save") { _, _ ->
                val categoryName = editTextCategoryName.text.toString()
                if (categoryName.isNotEmpty()) {
                    updateCategoryInFirestore(kategori.idKategori, categoryName)
                }
            }
            .create()
        dialog.show()
    }

    private fun addCategoryToFirestore(categoryName: String) {
        val newCategory = KategoriProduk(idKategori = kategoriList.size, namaKategori = categoryName)
        firestore.collection("kategoriProduk")
            .add(newCategory)
            .addOnSuccessListener {
                fetchCategoriesFromFirestore() // Refresh data after adding
            }
            .addOnFailureListener {
                // Handle failure
            }
    }

    private fun updateCategoryInFirestore(idKategori: Int, categoryName: String) {
        val categoryRef = firestore.collection("kategoriProduk")
            .whereEqualTo("idKategori", idKategori)

        categoryRef.get().addOnSuccessListener { documents ->
            for (document in documents) {
                document.reference.update("namaKategori", categoryName)
                    .addOnSuccessListener {
                        fetchCategoriesFromFirestore() // melakukan refresh data setelah update
                    }
                    .addOnFailureListener {

                    }
            }
        }
    }

    private fun deleteCategoryFromFirestore(kategori: KategoriProduk){
        val categoryRef = firestore.collection("kategoriProduk")
            .whereEqualTo("idKategori", kategori.idKategori)

        categoryRef.get().addOnSuccessListener { documents ->
            for (document in documents) {
                document.reference.delete()
                    .addOnSuccessListener {
                        fetchCategoriesFromFirestore()
                    }
                    .addOnFailureListener {

                    }
            }
        }
    }

    private fun setupRecyclerView() {
//        adapterKategori = adapterKategori(kategoriList)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = adapterKategori

        adapterKategori = adapterKategori(kategoriList, onEditClick = { kategori ->
            showEditCategoryDialog(kategori)
        }) { kategori ->
            deleteCategoryFromFirestore(kategori)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapterKategori
    }

    private fun fetchCategoriesFromFirestore() {
        firestore.collection("kategoriProduk")
            .get()
            .addOnSuccessListener { result ->
                kategoriList.clear()
                for (document in result) {
                    val kategori = document.toObject<KategoriProduk>()
                    kategoriList.add(kategori)
                }
                adapterKategori.notifyDataSetChanged()
            }
            .addOnFailureListener {
                // Handle failure
            }
    }
}


//package project.c14210052.proyekakhir_paba
//
//import android.annotation.SuppressLint
//import android.content.Intent
//import android.os.Bundle
//import android.widget.ImageButton
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//
//class productCategoryPage : AppCompatActivity() {
//
//    private lateinit var _backBtn : ImageButton
//    @SuppressLint("MissingInflatedId")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_product_category)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//
//        _backBtn = findViewById(R.id.btnBackFromAddCategory)
//
//        _backBtn.setOnClickListener {
//            val intent = Intent(this@productCategoryPage, daftarProdukPage::class.java)
//            startActivity(intent)
//        }
//    }
//}