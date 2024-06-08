package project.c14210052.proyekakhir_paba

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SupplierListActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SupplierAdapter
    private lateinit var btnBack: ImageButton
    private val gson = Gson()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_supplier_list)

        sharedPreferences = getSharedPreferences("SupplierData", MODE_PRIVATE)

//        val clearData = intent.getBooleanExtra("clearData", false)
//        if (clearData) {
//            sharedPreferences.edit().clear().apply()
//        }

        val suppliersJson = sharedPreferences.getString("suppliers", "[]")
        val suppliersType = object : TypeToken<List<Supplier>>() {}.type
        val suppliers: MutableList<Supplier> = gson.fromJson(suppliersJson, suppliersType)

        recyclerView = findViewById(R.id.rvSupplier)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SupplierAdapter(suppliers, { position ->
            suppliers.removeAt(position)
            val editor = sharedPreferences.edit()
            editor.putString("suppliers", gson.toJson(suppliers))
            editor.apply()
            adapter.notifyItemRemoved(position)
        }, { supplier ->
            val intent = Intent(this, SupplierInformationActivity::class.java)
            intent.putExtra("namaSupplier", supplier.namaSupplier)
            intent.putExtra("alamatSupplier", supplier.alamatSupplier)
            startActivity(intent)
        })
        recyclerView.adapter = adapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val addSuppButton = findViewById<ImageButton>(R.id.addSuppButton)
        btnBack = findViewById(R.id.backButtonFromSupplierList)

        addSuppButton.setOnClickListener {
            val intent = Intent(this, AddSupplierActivity::class.java)
            startActivity(intent)
        }

        btnBack.setOnClickListener {
            val intent = Intent(this@SupplierListActivity, HomeFragment::class.java)
            startActivity(intent)
        }

    }

}