package project.c14210052.proyekakhir_paba

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.firestore
import com.google.firebase.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import project.c14210052.proyekakhir_paba.dataClass.Supplier

class addSupplierPage : AppCompatActivity() {

    val db = Firebase.firestore

    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()
    private lateinit var _btnBack : ImageButton

    lateinit var _namaSupplierEdtTxt : EditText
    lateinit var _emailSupplierEdtTxt : EditText
    lateinit var _nomorTeleponEdtTxt : EditText
    lateinit var _alamatSupplierEdtTxt : EditText
    lateinit var _kotaSupplierEdtTxt : EditText
    lateinit var _provinsiSupplierEdtTxt : EditText
    lateinit var _kodePosSupplierEdtTxt : EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_supplier)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = getSharedPreferences("SupplierData", MODE_PRIVATE)

        _namaSupplierEdtTxt = findViewById<EditText>(R.id.etNamaSupplier)
        _emailSupplierEdtTxt = findViewById<EditText>(R.id.etEmailSupplier)
        _nomorTeleponEdtTxt = findViewById<EditText>(R.id.etTeleponSupplier)
        _alamatSupplierEdtTxt = findViewById<EditText>(R.id.etAlamatSupplier)
        _kotaSupplierEdtTxt = findViewById<EditText>(R.id.etKotaSupplier)
         _provinsiSupplierEdtTxt = findViewById<EditText>(R.id.etProvinsiSupplier)
        _kodePosSupplierEdtTxt = findViewById<EditText>(R.id.etKodePosSupplier)

        val _saveBtn = findViewById<Button>(R.id.btnSaveSupplier)

        _btnBack = findViewById(R.id.btnBackFromAddSupplier)
        _btnBack.setOnClickListener {
            val intent = Intent(this@addSupplierPage, supplierListPage::class.java)
            startActivity(intent)
        }

        _saveBtn.setOnClickListener {

            val saved = sharedPreferences.edit()
            saved.putString("namaSupplier", _namaSupplierEdtTxt.text.toString())
            saved.putString("emailSupplier", _emailSupplierEdtTxt.text.toString())
            saved.putString("nomorTelepon", _nomorTeleponEdtTxt.text.toString())
            saved.putString("alamatSupplier", _alamatSupplierEdtTxt.text.toString())
            saved.putString("kotaSupplier", _kotaSupplierEdtTxt.text.toString())
            saved.putString("provinsiSupplier", _provinsiSupplierEdtTxt.text.toString())
            saved.putString("kodePosSupplier", _kodePosSupplierEdtTxt.text.toString())
            saved.apply()

            val suppliersJson = sharedPreferences.getString("suppliers", "[]")
            val suppliersType = object : TypeToken<List<Supplier>>() {}.type
            val suppliers: MutableList<Supplier> = gson.fromJson(suppliersJson, suppliersType)

            val newSupplier = Supplier(
                namaSupplier = _namaSupplierEdtTxt.text.toString(),
                emailSupplier = _emailSupplierEdtTxt.text.toString(),
                teleponSupplier = _nomorTeleponEdtTxt.text.toString(),
                alamatSupplier = _alamatSupplierEdtTxt.text.toString(),
                kotaSupplier = _kotaSupplierEdtTxt.text.toString(),
                provinsiSupplier = _provinsiSupplierEdtTxt.text.toString(),
                kodeSupplier = _kodePosSupplierEdtTxt.text.toString()
            )
            suppliers.add(newSupplier)

            val editor = sharedPreferences.edit()
            editor.putString("suppliers", gson.toJson(suppliers))
            editor.apply()

            val intent = Intent(this, supplierInformationPage::class.java)
            intent.putExtra("supplier", gson.toJson(newSupplier))
            startActivity(intent)

            addData(
                _namaSupplierEdtTxt.text.toString(),
                _emailSupplierEdtTxt.text.toString(),
                _nomorTeleponEdtTxt.text.toString(),
                _alamatSupplierEdtTxt.text.toString(),
                _kotaSupplierEdtTxt.text.toString(),
                _provinsiSupplierEdtTxt.text.toString(),
            )
            finish()
        }
    }

    fun addData (nama : String, email : String, noTelp : String, alamat : String, kota : String, provinsi : String){
        val docRef = db.collection("tbSupplier").document()
        val newData = Supplier(docRef.id, nama, email, noTelp, alamat, kota, provinsi)

        newData.namaSupplier?.let {
            db.collection("tbSupplier")
                .document(it)
                .set(newData)
                .addOnSuccessListener {
                    _namaSupplierEdtTxt.setText("")
                    _emailSupplierEdtTxt.setText("")
                    _nomorTeleponEdtTxt.setText("")
                    _alamatSupplierEdtTxt.setText("")
                    _kotaSupplierEdtTxt.setText("")
                    _provinsiSupplierEdtTxt.setText("")
                    Toast.makeText(this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener { e ->
                    Log.e(
                        "PROJ_ONFIREBASE",
                        "Error adding document"
                    )
                }
        }
    }

}



