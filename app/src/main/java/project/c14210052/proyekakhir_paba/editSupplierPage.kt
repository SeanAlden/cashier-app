package project.c14210052.proyekakhir_paba

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import project.c14210052.proyekakhir_paba.dataClass.Supplier

class editSupplierPage : AppCompatActivity() {

    private lateinit var _etEditNamaSupp: EditText
    private lateinit var _etEditEmailSupp: EditText
    private lateinit var _etEditNomorTelpSupp: EditText
    private lateinit var _etEditAlamatSupp: EditText
    private lateinit var _etEditKotaSupp: EditText
    private lateinit var _etEditProvinsiSupp: EditText
    private lateinit var _etEditKodeSupp: EditText
    private lateinit var _btnSaveEditSupp: Button
    private lateinit var _btnBackFromEditSupp: ImageButton

    private lateinit var db: FirebaseFirestore

    private var suppId: String? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_supplier)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        db = FirebaseFirestore.getInstance()

        _etEditNamaSupp = findViewById(R.id.etNamaSupplierEdit)
        _etEditEmailSupp = findViewById(R.id.etEmailSupplierEdit)
        _etEditNomorTelpSupp = findViewById(R.id.etTeleponSupplierEdit)
        _etEditAlamatSupp = findViewById(R.id.etAlamatSupplierEdit)
        _etEditKotaSupp = findViewById(R.id.etKotaSupplierEdit)
        _etEditProvinsiSupp = findViewById(R.id.etProvinsiSupplierEdit)
        _etEditKodeSupp = findViewById(R.id.etKodePosSupplierEdit)

        _btnSaveEditSupp = findViewById(R.id.btnSaveEditSupplierData)
        _btnBackFromEditSupp = findViewById(R.id.btnBackFromEditSupplier)

        suppId = intent.getStringExtra("SUPP_ID")
        _etEditNamaSupp.setText(intent.getStringExtra("SUPP_NAME"))
        _etEditEmailSupp.setText(intent.getStringExtra("SUPP_EMAIL"))
        _etEditNomorTelpSupp.setText(intent.getStringExtra("SUPP_TELP"))
        _etEditAlamatSupp.setText(intent.getStringExtra("SUPP_ALAMAT"))
        _etEditKotaSupp.setText(intent.getStringExtra("SUPP_KOTA"))
        _etEditProvinsiSupp.setText(intent.getStringExtra("SUPP_PROV"))
        _etEditKodeSupp.setText(intent.getStringExtra("SUPP_KODE"))

        _btnSaveEditSupp.setOnClickListener {
            updateSupp()
        }

        _btnBackFromEditSupp.setOnClickListener {
            val intent = Intent(this@editSupplierPage, supplierListPage::class.java)
            startActivity(intent)
        }
    }

    private fun updateSupp() {
        val nama = _etEditNamaSupp.text.toString()
        val email = _etEditEmailSupp.text.toString()
        val nomorTelp = _etEditNomorTelpSupp.text.toString()
        val alamat = _etEditAlamatSupp.text.toString()
        val kota = _etEditKotaSupp.text.toString()
        val provinsi = _etEditProvinsiSupp.text.toString()
        val kode = _etEditKodeSupp.text.toString()

        if (nama.isEmpty() || email.isEmpty() || nomorTelp.isEmpty()
            || alamat.isEmpty() || kota.isEmpty() || provinsi.isEmpty() || kode.isEmpty()
        ) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            return
        }

        if (suppId != null) {
            db.collection("tbSupplier").document(suppId!!)
                .update(
                    mapOf(
                        "namaSupplier" to nama,
                        "emailSupplier" to email,
                        "alamatSupplier" to alamat,
                        "kotaSupplier" to kota,
                        "provinsiSupplier" to provinsi,
                        "kodeSupplier" to kode
                    ))
                .addOnSuccessListener {
                    finish()
                }
                .addOnFailureListener { e ->
                    println("Failed to update item: ${e.message}")
                }
        }
    }
}


