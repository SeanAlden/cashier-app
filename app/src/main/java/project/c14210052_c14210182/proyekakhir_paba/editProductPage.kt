package project.c14210052_c14210182.proyekakhir_paba

import android.os.Bundle
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
import com.google.firebase.firestore.FirebaseFirestore
import project.c14210052_c14210182.proyekakhir_paba.dataClass.Produk

class editProductPage : AppCompatActivity() {

    private lateinit var _etEditNamaProduk: EditText
    private lateinit var _etEditDeskripsiProduk: EditText
    private lateinit var _spinnerEditKategoriProduk: Spinner
    private lateinit var _spinnerEditSupplierProduk: Spinner
    private lateinit var _etEditHargaPokokProduk: EditText
    private lateinit var _etEditHargaJualProduk: EditText
    private lateinit var _etEditJumlahProduk: EditText
    private lateinit var _etEditSatuanProduk: EditText
    private lateinit var _btnEditSaveProduk: Button
    private lateinit var _btnEditCancel: ImageButton
    private lateinit var db: FirebaseFirestore
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

        _etEditNamaProduk = findViewById(R.id.etEditNamaProduk)
        _etEditDeskripsiProduk = findViewById(R.id.etEditDeskripsiProduk)
        _spinnerEditKategoriProduk = findViewById(R.id.dropdownEditKategoriProduk)
        _spinnerEditSupplierProduk = findViewById(R.id.dropdownEditSupplier)
        _etEditHargaPokokProduk = findViewById(R.id.etEditHargaPokokProduk)
        _etEditHargaJualProduk = findViewById(R.id.etEditHargaJualProduk)
        _etEditJumlahProduk = findViewById(R.id.etEditJumlahProduk)
        _etEditSatuanProduk = findViewById(R.id.etEditSatuanProduk)
        _btnEditSaveProduk = findViewById(R.id.btnEditSaveProduk)
        _btnEditCancel = findViewById(R.id.btnBackFromEditProduct)

        db = FirebaseFirestore.getInstance()

        produk = intent.getParcelableExtra("produk")
        produk?.let { populateProductDetails(it) }

        _btnEditSaveProduk.setOnClickListener {
            saveProductChanges()
        }

        _btnEditCancel.setOnClickListener {
            finish()
        }

        loadCategories()
        loadSuppliers()

        addInputValidation(_etEditHargaPokokProduk)
        addInputValidation(_etEditHargaJualProduk)
        addInputValidation(_etEditJumlahProduk)
    }

    private fun addInputValidation(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString()
                if (input.isNotEmpty() && !input.matches(Regex("\\d+"))) {
                    editText.error = "Masukkan hanya angka 0-9"
                    editText.setText("")
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun populateProductDetails(produk: Produk){
        _etEditNamaProduk.setText(produk.namaProduk)
        _etEditDeskripsiProduk.setText(produk.deskripsiProduk)
        _etEditHargaPokokProduk.setText(produk.hargaPokokProduk.toString())
        _etEditHargaJualProduk.setText(produk.hargaJualProduk.toString())
        _etEditJumlahProduk.setText(produk.jumlahProduk.toString())
        _etEditSatuanProduk.setText(produk.satuanProduk)
    }

    private fun saveProductChanges() {
        val updatedNamaProduk = _etEditNamaProduk.text.toString()
        val updatedDeskripsiProduk = _etEditDeskripsiProduk.text.toString()
        val updatedKategoriProduk = _spinnerEditKategoriProduk.selectedItem.toString()
        val updatedSupplierProduk = _spinnerEditSupplierProduk.selectedItem.toString()
        val updatedHargaPokokProduk = _etEditHargaPokokProduk.text.toString().toInt()
        val updatedHargaJualProduk = _etEditHargaJualProduk.text.toString().toInt()
        val updatedJumlahProduk = _etEditJumlahProduk.text.toString().toInt()
        val updatedSatuanProduk = _etEditSatuanProduk.text.toString()

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

            db.collection("tbProduk")
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
                _spinnerEditKategoriProduk.adapter = adapter

                // memilih kategori produk yang sekarang
                produk?.let {
                    val categoryPosition = kategoriList.indexOf(it.kategoriProduk)
                    if (categoryPosition >= 0) {
                        _spinnerEditKategoriProduk.setSelection(categoryPosition)
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
                _spinnerEditSupplierProduk.adapter = adapter

                produk?.let {
                    val supplierPosition = supplierList.indexOf(it.supplierProduk)
                    if (supplierPosition >= 0) {
                        _spinnerEditSupplierProduk.setSelection(supplierPosition)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Gagal memuat supplier.", Toast.LENGTH_SHORT).show()
            }
    }
}

