package project.c14210052_c14210182.proyekakhir_paba

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class addSupplierPage : AppCompatActivity() {

    private lateinit var _etNamaSupp : EditText
    private lateinit var _etEmailSupp : EditText
    private lateinit var _etNomorTelpSupp : EditText
    private lateinit var _etAlamatSupp : EditText
    private lateinit var _etKotaSupp : EditText
    private lateinit var _etProvinsiSupp : EditText
    private lateinit var _etKodeSupp : EditText
    private lateinit var _btnSaveSupp : Button
    private lateinit var _btnBackFromAddSupp : ImageButton

    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_supplier)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cashier)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _etNamaSupp = findViewById(R.id.etNamaSupplier)
        _etEmailSupp = findViewById(R.id.etEmailSupplier)
        _etNomorTelpSupp = findViewById(R.id.etTeleponSupplier)
        _etAlamatSupp = findViewById(R.id.etAlamatSupplier)
        _etKotaSupp = findViewById(R.id.etKotaSupplier)
        _etProvinsiSupp = findViewById(R.id.etProvinsiSupplier)
        _etKodeSupp = findViewById(R.id.etKodePosSupplier)

        _btnSaveSupp = findViewById(R.id.btnSaveSupplier)
        _btnBackFromAddSupp = findViewById(R.id.btnBackFromAddSupplier)

        addInputValidation(_etNomorTelpSupp)
        addInputValidation(_etKodeSupp)

        db = FirebaseFirestore.getInstance()

        _btnSaveSupp.setOnClickListener {
            val nama = _etNamaSupp.text.toString()
            val email = _etEmailSupp.text.toString()
            val nomor_telp = _etNomorTelpSupp.text.toString()
            val alamat = _etAlamatSupp.text.toString()
            val kota = _etKotaSupp.text.toString()
            val provinsi = _etProvinsiSupp.text.toString()
            val kode = _etKodeSupp.text.toString()


            if (nama.isEmpty() || email.isEmpty() || nomor_telp.isEmpty() || alamat.isEmpty() || kota.isEmpty() || provinsi.isEmpty() ||kode.isEmpty()) {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            } else {
                val suppItem = hashMapOf(
                    "namaSupplier" to nama,
                    "emailSupplier" to email,
                    "teleponSupplier" to nomor_telp,
                    "alamatSupplier" to alamat,
                    "kotaSupplier" to kota,
                    "provinsiSupplier" to provinsi,
                    "kodeSupplier" to kode
                )

                db.collection("tbSupplier")
                    .add(suppItem)
                    .addOnSuccessListener { documentReference ->
                        finish()
                    }
                    .addOnFailureListener { e ->

                    }
            }
        }

        _btnBackFromAddSupp = findViewById(R.id.btnBackFromAddSupplier)
        _btnBackFromAddSupp.setOnClickListener {
            val intent = Intent(this@addSupplierPage, supplierListPage::class.java)
            startActivity(intent)
        }

    }

    private fun addInputValidation(editText: EditText){
        editText.addTextChangedListener(object: TextWatcher {
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

}



