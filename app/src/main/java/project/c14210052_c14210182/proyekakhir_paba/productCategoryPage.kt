package project.c14210052_c14210182.proyekakhir_paba

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import project.c14210052_c14210182.proyekakhir_paba.adapter.adapterKategori
import project.c14210052_c14210182.proyekakhir_paba.dataClass.kategoriProduk

class productCategoryPage : AppCompatActivity() {

    private lateinit var _backBtn: ImageButton
    private lateinit var _addBtn: FloatingActionButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterKategori: adapterKategori
    private val kategoriList = mutableListOf<kategoriProduk>()

    private val db = FirebaseFirestore.getInstance()

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

    private fun showEditCategoryDialog(kategori: kategoriProduk) {
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
        val newCategory = kategoriProduk(idKategori = kategoriList.size, namaKategori = categoryName)
        db.collection("kategoriProduk")
            .add(newCategory)
            .addOnSuccessListener {
                fetchCategoriesFromFirestore() // refresh data setelah penambahan
            }
            .addOnFailureListener {
                Log.d("Add Product", "gagal menambah kategori produk")
            }
    }

    private fun updateCategoryInFirestore(idKategori: Int, categoryName: String) {
        val categoryRef = db.collection("kategoriProduk")
            .whereEqualTo("idKategori", idKategori)

        categoryRef.get().addOnSuccessListener { documents ->
            for (document in documents) {
                document.reference.update("namaKategori", categoryName)
                    .addOnSuccessListener {
                        fetchCategoriesFromFirestore() // melakukan refresh data setelah update
                    }
                    .addOnFailureListener {
                        Log.d("Update Product", "gagal meng-update produk")
                    }
            }
        }
    }

    private fun deleteCategoryFromFirestore(kategori: kategoriProduk){
            checkIfCategoryIsInUse(kategori)
    }

    private fun checkIfCategoryIsInUse(kategori: kategoriProduk) {
        val productsRef = db.collection("tbProduk")
            .whereEqualTo("kategoriProduk", kategori.namaKategori)

        productsRef.get().addOnSuccessListener { documents ->
            if (documents.isEmpty) {
                // kalau tidak ada produk yang menggunakan kategori ini, lakukan delete
                showDeleteConfirmationDialog(kategori)
            } else {
                // kalau ada produk yang menggunakan kategori terkait, tampilkan pesan bahwa kategori masih digunakan
                showCategoryInUseDialog()
            }
        }.addOnFailureListener {
            Log.d("Product Exists Checking", "gagal mengecek kategori produk")
        }
    }

    private fun showCategoryInUseDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Kategori ini masih digunakan")
            .setMessage("Kategori ini masih digunakan oleh beberapa produk")
            .setPositiveButton("Ok", null)
            .create()
        dialog.show()
    }

    private fun showDeleteConfirmationDialog(kategori: kategoriProduk) {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Hapus Kategori")
            .setMessage("Apakah Anda ingin menghapus kategori ini?")
            .setNegativeButton("No", null)
            .setPositiveButton("Yes") { _, _ ->
                val categoryRef = db.collection("kategoriProduk")
                    .whereEqualTo("idKategori", kategori.idKategori)

                categoryRef.get().addOnSuccessListener { documents ->
                    for (document in documents) {
                        document.reference.delete()
                            .addOnSuccessListener {
                                fetchCategoriesFromFirestore()
                            }
                            .addOnFailureListener {
                                Log.d("delete product", "gagal melakukan delete")
                            }
                    }
                }
            }
            .create()
        dialog.show()
    }

//    private fun deleteCategoryFromFirestore(kategori: KategoriProduk){
//        val categoryRef = firestore.collection("kategoriProduk")
//            .whereEqualTo("idKategori", kategori.idKategori)
//
//        categoryRef.get().addOnSuccessListener { documents ->
//            for (document in documents) {
//                document.reference.delete()
//                    .addOnSuccessListener {
//                        fetchCategoriesFromFirestore()
//                    }
//                    .addOnFailureListener {
//
//                    }
//            }
//        }
//    }

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
        db.collection("kategoriProduk")
            .get()
            .addOnSuccessListener { result ->
                kategoriList.clear()
                for (document in result) {
                    val kategori = document.toObject<kategoriProduk>()
                    kategoriList.add(kategori)
                }
                adapterKategori.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Log.d("fetch data", "gagal melakukan fetch kategori data")
            }
    }
}