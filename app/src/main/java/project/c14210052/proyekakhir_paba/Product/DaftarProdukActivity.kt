package project.c14210052.proyekakhir_paba.Product

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import project.c14210052.proyekakhir_paba.MainActivity
import project.c14210052.proyekakhir_paba.R

class DaftarProdukActivity : AppCompatActivity() {

    private lateinit var addProductBtn : ImageButton
    private lateinit var backBtn : ImageButton
    private lateinit var categoryBtn : Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_daftar_produk)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        addProductBtn = findViewById(R.id.addProductButton)
        backBtn = findViewById(R.id.backFromProductList)
        categoryBtn = findViewById(R.id.categoryBtn)

        addProductBtn.setOnClickListener {
            val intent = Intent(this@DaftarProdukActivity, AddProductActivity::class.java)
            startActivity(intent)
        }

        backBtn.setOnClickListener {
            val intent = Intent(this@DaftarProdukActivity, MainActivity::class.java)
            startActivity(intent)
        }

        categoryBtn.setOnClickListener {
            val intent = Intent(this@DaftarProdukActivity, ProductCategoryActivity::class.java)
            startActivity(intent)
        }
    }
}