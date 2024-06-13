package project.c14210052.proyekakhir_paba

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson

class supplierInformationPage : AppCompatActivity() {

//    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var _suppliers: Supplier
    private lateinit var _btnBack : ImageButton
    private lateinit var gson: Gson
    private lateinit var supplier: Supplier
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_supplier_information)

//        sharedPreferences = getSharedPreferences("SupplierData", MODE_PRIVATE)
        var supplierJson = intent.getStringExtra("supplier")
        supplier = Gson().fromJson(supplierJson, Supplier::class.java)

//        supplierJson = intent.getStringExtra("updateSupplier")
//        supplier = Gson().fromJson(supplierJson, Supplier::class.java)

        val namaSupplierView = findViewById<TextView>(R.id.tvNamaSupplierInfoo)
        val emailSupplierView = findViewById<TextView>(R.id.tvEmailSupplierInfoo)
        val nomorTeleponView = findViewById<TextView>(R.id.tvTeleponSupplierInfoo)
        val alamatSupplierView = findViewById<TextView>(R.id.tvAlamatSupplierInfoo)
        val kotaSupplierView = findViewById<TextView>(R.id.tvKotaSupplierInfoo)
        val provinsiSupplierView = findViewById<TextView>(R.id.tvProvinsiSupplierInfoo)
        val kodePosSupplierView = findViewById<TextView>(R.id.tvKodePosSupplierInfoo)


//        namaSupplierView.text = sharedPreferences.getString("namaSupplier", "")
//        emailSupplierView.text = sharedPreferences.getString("emailSupplier", "")
//        nomorTeleponView.text = sharedPreferences.getString("nomorTelepon", "")
//        alamatSupplierView.text = sharedPreferences.getString("alamatSupplier", "")
//        kotaSupplierView.text = sharedPreferences.getString("kotaSupplier", "")
//        provinsiSupplierView.text = sharedPreferences.getString("provinsiSupplier", "")
//        kodePosSupplierView.text = sharedPreferences.getString("kodePosSupplier", "")

        namaSupplierView.text = supplier.namaSupplier
        emailSupplierView.text = supplier.emailSupplier
        nomorTeleponView.text = supplier.teleponSupplier
        alamatSupplierView.text = supplier.alamatSupplier
        kotaSupplierView.text = supplier.kotaSupplier
        provinsiSupplierView.text = supplier.provinsiSupplier
        kodePosSupplierView.text = supplier.kodeSupplier

//        namaSupplierView = findViewById<TextView>(R.id.tvNamaSupplierInfoo)
//        emailSupplierView = findViewById<TextView>(R.id.tvEmailSupplierInfoo)
//        nomorTeleponView = findViewById<TextView>(R.id.tvTeleponSupplierInfoo)
//        alamatSupplierView = findViewById<TextView>(R.id.tvAlamatSupplierInfoo)
//        kotaSupplierView = findViewById<TextView>(R.id.tvKotaSupplierInfoo)
//        provinsiSupplierView = findViewById<TextView>(R.id.tvProvinsiSupplierInfoo)
//        kodePosSupplierView = findViewById<TextView>(R.id.tvKodePosSupplierInfoo)

        gson = Gson()
        _suppliers = gson.fromJson(intent.getStringExtra("supplier"), Supplier::class.java)

        _btnBack = findViewById(R.id.btnBackFromInformasiSupplier)

        _btnBack.setOnClickListener {
            val intent = Intent(this@supplierInformationPage, supplierListPage::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

