package project.c14210052_c14210182.proyekakhir_paba

import android.annotation.SuppressLint
import android.content.Intent
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
import java.util.UUID

class addProductPage : AppCompatActivity() {

    private lateinit var _backBtn : ImageButton

    private lateinit var _btnSaveProduk: Button
    private lateinit var _etNamaProduk: EditText
    private lateinit var _etDeskripsiProduk: EditText
    private lateinit var _etHargaPokokProduk: EditText
    private lateinit var _etHargaJualProduk: EditText
    private lateinit var _etJumlahProduk: EditText
    private lateinit var _etSatuanProduk: EditText
    private lateinit var _dropdownKategoriProduk: Spinner
    private lateinit var _dropdownSupplier: Spinner

    private lateinit var db: FirebaseFirestore

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // menginisialisasi Firebase Firestore
        db = FirebaseFirestore.getInstance()

        // menginisialisasi tampilan
        _backBtn = findViewById(R.id.btnBackFromAddProduct)
        _btnSaveProduk = findViewById(R.id.btnSaveProduk)
        _etNamaProduk = findViewById(R.id.etNamaProduk)
        _etDeskripsiProduk = findViewById(R.id.etDeskripsiProduk)
        _etHargaPokokProduk = findViewById(R.id.etHargaPokokProduk)
        _etHargaJualProduk = findViewById(R.id.etHargaJualProduk)
        _etJumlahProduk = findViewById(R.id.etJumlahProduk)
        _etSatuanProduk = findViewById(R.id.etSatuanProduk)
        _dropdownKategoriProduk = findViewById(R.id.dropdownKategoriProduk)
        _dropdownSupplier = findViewById(R.id.dropdownSupplier)

        // load data untuk spinner
        loadKategoriData()
        loadSupplierData()

        // membuat TextWatcher untuk validasi inputan
        addInputValidation(_etHargaPokokProduk)
        addInputValidation(_etHargaJualProduk)
        addInputValidation(_etJumlahProduk)

        _backBtn.setOnClickListener {
            val intent = Intent(this@addProductPage, daftarProdukPage::class.java)
            startActivity(intent)
        }

        _btnSaveProduk.setOnClickListener {
            saveProductData()
        }
    }

    private fun addInputValidation(editText: EditText){
        editText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString()
                if (input.isNotEmpty() && !input.matches(Regex("\\d+"))) {
                    editText.error = "Masukkan hanya angka 0-9"
                    editText.setText("")
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }

    private fun loadKategoriData() {
        db.collection("kategoriProduk")
            .get()
            .addOnSuccessListener { documents ->
                val kategoriList = mutableListOf<String>()
                for (document in documents) {
                    val kategori = document.getString("namaKategori")
                    kategori?.let { kategoriList.add(it) }
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, kategoriList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                _dropdownKategoriProduk.adapter = adapter
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to load categories: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadSupplierData() {
        db.collection("tbSupplier")
            .get()
            .addOnSuccessListener { documents ->
                val supplierList = mutableListOf<String>()
                for (document in documents) {
                    val supplier = document.getString("namaSupplier")
                    supplier?.let { supplierList.add(it) }
                }
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, supplierList)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                _dropdownSupplier.adapter = adapter
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to load suppliers: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
    private fun saveProductData() {
        val idProduk = UUID.randomUUID().toString()
        val namaProduk = _etNamaProduk.text.toString()
        val deskripsiProduk = _etDeskripsiProduk.text.toString()
        val kategoriProduk = _dropdownKategoriProduk.selectedItem.toString()
        val supplierProduk = _dropdownSupplier.selectedItem.toString()
        val hargaPokokProduk = _etHargaPokokProduk.text.toString().toIntOrNull() ?: 0
        val hargaJualProduk = _etHargaJualProduk.text.toString().toIntOrNull() ?: 0
        val jumlahProduk = _etJumlahProduk.text.toString().toIntOrNull() ?: 0
        val satuanProduk = _etSatuanProduk.text.toString()

        val produk = Produk(
            idProduk,
            namaProduk = namaProduk,
            deskripsiProduk = deskripsiProduk,
            kategoriProduk = kategoriProduk,
            supplierProduk = supplierProduk,
            hargaPokokProduk = hargaPokokProduk,
            hargaJualProduk = hargaJualProduk,
            jumlahProduk = jumlahProduk,
            satuanProduk = satuanProduk
        )

        db.collection("tbProduk")
            .document(idProduk)
            .set(produk)
            .addOnSuccessListener {
                Toast.makeText(this, "Product added successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Failed to add product: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}