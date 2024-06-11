package project.c14210052.proyekakhir_paba

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class AddSupplierActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()
    private lateinit var _btnBack : ImageButton

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

        val _namaSupplierEdtTxt = findViewById<EditText>(R.id.etNamaSupplier)
        val _emailSupplierEdtTxt = findViewById<EditText>(R.id.etEmailSupplier)
        val _nomorTeleponEdtTxt = findViewById<EditText>(R.id.etTeleponSupplier)
        val _alamatSupplierEdtTxt = findViewById<EditText>(R.id.etAlamatSupplier)
        val _kotaSupplierEdtTxt = findViewById<EditText>(R.id.etKotaSupplier)
        val _provinsiSupplierEdtTxt = findViewById<EditText>(R.id.etProvinsiSupplier)
        val _kodePosSupplierEdtTxt = findViewById<EditText>(R.id.etKodePosSupplier)

        val _saveBtn = findViewById<Button>(R.id.btnSaveSupplier)

        _btnBack = findViewById(R.id.btnBackFromAddSupplier)
        _btnBack.setOnClickListener {
            val intent = Intent(this@AddSupplierActivity, SupplierListActivity::class.java)
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
            val suppliersType = object : TypeToken<List<DataSupplier>>() {}.type
            val suppliers: MutableList<DataSupplier> = gson.fromJson(suppliersJson, suppliersType)

            val newSupplier = DataSupplier(
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

            val intent = Intent(this, SupplierInformationActivity::class.java)
            intent.putExtra("supplier", gson.toJson(newSupplier))
            startActivity(intent)
        }
    }
}



