package project.c14210052.proyekakhir_paba

import android.os.Bundle
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
import com.google.firebase.firestore.FirebaseFirestore
import project.c14210052.proyekakhir_paba.dataClass.Produk

class editProductPage : AppCompatActivity() {

    private lateinit var etEditNamaProduk: EditText
    private lateinit var etEditDeskripsiProduk: EditText
    private lateinit var spinnerEditKategoriProduk: Spinner
    private lateinit var spinnerEditSupplierProduk: Spinner
    private lateinit var etEditHargaPokokProduk: EditText
    private lateinit var etEditHargaJualProduk: EditText
    private lateinit var etEditJumlahProduk: EditText
    private lateinit var etEditSatuanProduk: EditText
    private lateinit var btnEditSaveProduk: Button
    private lateinit var btnEditCancel: ImageButton
    private lateinit var firestore: FirebaseFirestore
    private var produk: Produk? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etEditNamaProduk = findViewById(R.id.etEditNamaProduk)
        etEditDeskripsiProduk = findViewById(R.id.etEditDeskripsiProduk)
        spinnerEditKategoriProduk = findViewById(R.id.dropdownEditKategoriProduk)
        spinnerEditSupplierProduk = findViewById(R.id.dropdownEditSupplier)
        etEditHargaPokokProduk = findViewById(R.id.etEditHargaPokokProduk)
        etEditHargaJualProduk = findViewById(R.id.etEditHargaJualProduk)
        etEditJumlahProduk = findViewById(R.id.etEditJumlahProduk)
        etEditSatuanProduk = findViewById(R.id.etEditSatuanProduk)
        btnEditSaveProduk = findViewById(R.id.btnEditSaveProduk)
        btnEditCancel = findViewById(R.id.btnBackFromEditProduct)

        firestore = FirebaseFirestore.getInstance()

        produk = intent.getParcelableExtra("produk")
        produk?.let { populateProductDetails(it) }

        btnEditSaveProduk.setOnClickListener {
            saveProductChanges()
        }

        btnEditCancel.setOnClickListener {
            finish()
        }

        loadCategories()
        loadSuppliers()
    }

    private fun populateProductDetails(produk: Produk){
        etEditNamaProduk.setText(produk.namaProduk)
        etEditDeskripsiProduk.setText(produk.deskripsiProduk)
        etEditHargaPokokProduk.setText(produk.hargaPokokProduk.toString())
        etEditHargaJualProduk.setText(produk.hargaJualProduk.toString())
        etEditJumlahProduk.setText(produk.jumlahProduk.toString())
        etEditSatuanProduk.setText(produk.satuanProduk)
    }

    private fun saveProductChanges() {
        val updatedNamaProduk = etEditNamaProduk.text.toString()
        val updatedDeskripsiProduk = etEditDeskripsiProduk.text.toString()
        val updatedKategoriProduk = spinnerEditKategoriProduk.selectedItem.toString()
        val updatedSupplierProduk = spinnerEditSupplierProduk.selectedItem.toString()
        val updatedHargaPokokProduk = etEditHargaPokokProduk.text.toString().toInt()
        val updatedHargaJualProduk = etEditHargaJualProduk.text.toString().toInt()
        val updatedJumlahProduk = etEditJumlahProduk.text.toString().toInt()
        val updatedSatuanProduk = etEditSatuanProduk.text.toString()

        produk?.let { produk ->
            val updatedProduk = produk.copy(
                namaProduk = updatedNamaProduk,
                deskripsiProduk = updatedDeskripsiProduk,
                kategoriProduk = updatedKategoriProduk,
                supplierProduk = updatedSupplierProduk,
                hargaPokokProduk = updatedHargaPokokProduk,
                hargaJualProduk = updatedHargaJualProduk,
                jumlahProduk = updatedJumlahProduk,
                satuanProduk = updatedSatuanProduk
            )

            firestore.collection("tbProduk")
                .document(produk.idProduk!!)
                .set(updatedProduk)
                .addOnFailureListener {
                    Toast.makeText(this, "Produk berhasil diperbarui.", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Gagal memperbarui produk.", Toast.LENGTH_SHORT).show()
                }
        }
        finish()
    }
    private fun loadCategories() {
        val db = FirebaseFirestore.getInstance()
        val kategoriList = mutableListOf<String>()

        db.collection("kategoriProduk")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val kategori = document.getString("namaKategori")
                    kategori?.let { kategoriList.add(it) }
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, kategoriList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerEditKategoriProduk.adapter = adapter

                // Select the current category of the product
                produk?.let {
                    val categoryPosition = kategoriList.indexOf(it.kategoriProduk)
                    if (categoryPosition >= 0) {
                        spinnerEditKategoriProduk.setSelection(categoryPosition)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Gagal memuat kategori.", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadSuppliers() {
        val db = FirebaseFirestore.getInstance()
        val supplierList = mutableListOf<String>()

        db.collection("tbSupplier")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val supplier = document.getString("namaSupplier")
                    supplier?.let { supplierList.add(it) }
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, supplierList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerEditSupplierProduk.adapter = adapter

                produk?.let {
                    val supplierPosition = supplierList.indexOf(it.supplierProduk)
                    if (supplierPosition >= 0) {
                        spinnerEditSupplierProduk.setSelection(supplierPosition)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Gagal memuat supplier.", Toast.LENGTH_SHORT).show()
            }
    }
}

