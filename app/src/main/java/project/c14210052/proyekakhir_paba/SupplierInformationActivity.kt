package project.c14210052.proyekakhir_paba

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SupplierInformationActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var btnBack : ImageButton
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_supplier_information)

        sharedPreferences = getSharedPreferences("SupplierData", MODE_PRIVATE)

        val namaSupplierView = findViewById<TextView>(R.id.namaSupplierView)
        val emailSupplierView = findViewById<TextView>(R.id.emailSupplierView)
        val nomorTeleponView = findViewById<TextView>(R.id.nomorTeleponView)
        val alamatSupplierView = findViewById<TextView>(R.id.alamatSupplierView)
        val kotaSupplierView = findViewById<TextView>(R.id.kotaSupplierView)
        val provinsiSupplierView = findViewById<TextView>(R.id.provinsiSupplierView)
        val kodePosSupplierView = findViewById<TextView>(R.id.kodePosSupplierView)

        namaSupplierView.text = sharedPreferences.getString("namaSupplier", "")
        emailSupplierView.text = sharedPreferences.getString("emailSupplier", "")
        nomorTeleponView.text = sharedPreferences.getString("nomorTelepon", "")
        alamatSupplierView.text = sharedPreferences.getString("alamatSupplier", "")
        kotaSupplierView.text = sharedPreferences.getString("kotaSupplier", "")
        provinsiSupplierView.text = sharedPreferences.getString("provinsiSupplier", "")
        kodePosSupplierView.text = sharedPreferences.getString("kodePosSupplier", "")

        btnBack = findViewById(R.id.backButton)

        btnBack.setOnClickListener {
            val intent = Intent(this@SupplierInformationActivity, SupplierListActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}