package project.c14210052.proyekakhir_paba.Supplier

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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import project.c14210052.proyekakhir_paba.R

class AddSupplierActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private val gson = Gson()
    private lateinit var btnBack : ImageButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_supplier)

        sharedPreferences = getSharedPreferences("SupplierData", MODE_PRIVATE)

        val namaSupplierEdtTxt = findViewById<EditText>(R.id.namaSupplierEdtTxt)
        val emailSupplierEdtTxt = findViewById<EditText>(R.id.emailSupplierEdtTxt)
        val nomorTeleponEdtTxt = findViewById<EditText>(R.id.nomorTeleponEdtTxt)
        val alamatSupplierEdtTxt = findViewById<EditText>(R.id.alamatSupplierEdtTxt)
        val kotaSupplierEdtTxt = findViewById<EditText>(R.id.kotaSupplierEdtTxt)
        val provinsiSupplierEdtTxt = findViewById<EditText>(R.id.provinsiSupplierEdtTxt)
        val kodePosSupplierEdtTxt = findViewById<EditText>(R.id.kodePosSupplierEdtTxt)

        val saveBtn = findViewById<Button>(R.id.saveSupplierBtn)

        btnBack = findViewById(R.id.backButtonFromAddSupplier)
        btnBack.setOnClickListener {
            val intent = Intent(this@AddSupplierActivity, SupplierListActivity::class.java)
            startActivity(intent)
        }

        saveBtn.setOnClickListener {
//            val editor = sharedPreferences.edit()
//            editor.putString("namaSupplier", namaSupplierEdtTxt.text.toString())
//            editor.putString("emailSupplier", emailSupplierEdtTxt.text.toString())
//            editor.putString("nomorTelepon", nomorTeleponEdtTxt.text.toString())
//            editor.putString("alamatSupplier", alamatSupplierEdtTxt.text.toString())
//            editor.putString("kotaSupplier", kotaSupplierEdtTxt.text.toString())
//            editor.putString("provinsiSupplier", provinsiSupplierEdtTxt.text.toString())
//            editor.putString("kodePosSupplier", kodePosSupplierEdtTxt.text.toString())

            val suppliersJson = sharedPreferences.getString("suppliers", "[]")
            val suppliersType = object : TypeToken<List<Supplier>>() {}.type
            val suppliers: MutableList<Supplier> = gson.fromJson(suppliersJson, suppliersType)

            val newSupplier = Supplier(
                namaSupplier = namaSupplierEdtTxt.text.toString(),
                alamatSupplier = alamatSupplierEdtTxt.text.toString()
            )
            suppliers.add(newSupplier)

            val editor = sharedPreferences.edit()
            editor.putString("suppliers", gson.toJson(suppliers))
            editor.apply()

            val intent = Intent(this, SupplierInformationActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}