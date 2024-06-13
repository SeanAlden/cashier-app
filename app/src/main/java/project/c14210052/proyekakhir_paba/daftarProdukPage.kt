package project.c14210052.proyekakhir_paba

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class daftarProdukPage : AppCompatActivity() {

    private lateinit var _addProductBtn : ImageButton
    private lateinit var _backBtn : ImageButton
    private lateinit var _categoryBtn : Button
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

        _addProductBtn = findViewById(R.id.btnAddProduct)
        _backBtn = findViewById(R.id.btnBackFromProductList)
        _categoryBtn = findViewById(R.id.btnCategory)

        _addProductBtn.setOnClickListener {
            val intent = Intent(this@daftarProdukPage, addProductPage::class.java)
            startActivity(intent)
        }

        _backBtn.setOnClickListener {
            val intent = Intent(this@daftarProdukPage, MainActivity::class.java)
            startActivity(intent)
        }

        _categoryBtn.setOnClickListener {
            val intent = Intent(this@daftarProdukPage, productCategoryPage::class.java)
            startActivity(intent)
        }
    }
}