package project.c14210052_c14210182.proyekakhir_paba

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import project.c14210052_c14210182.proyekakhir_paba.adapter.adapterInventory
import project.c14210052_c14210182.proyekakhir_paba.dataClass.Produk

class inventoryPage : AppCompatActivity() {

    private lateinit var _backButton : ImageButton
    private lateinit var rvInventory: RecyclerView
    private var _productList = mutableListOf<Produk>()
    private lateinit var adapterInventory: adapterInventory

    private val db = FirebaseFirestore.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inventory)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _backButton = findViewById(R.id.btnBackFromInventory)

        _backButton.setOnClickListener {
            finish()
        }

        rvInventory = findViewById(R.id.rvInventory)
        rvInventory.layoutManager = LinearLayoutManager(this)
        adapterInventory = adapterInventory(_productList)
        rvInventory.adapter = adapterInventory

        db.collection("tbProduk")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val product = document.toObject(Produk::class.java)
                    product?.let {
                        it.idProduk = document.id
                        _productList.add(it)
                    }
                }
                adapterInventory.notifyDataSetChanged()
            }

    }
}