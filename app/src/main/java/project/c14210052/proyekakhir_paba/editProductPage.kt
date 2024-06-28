package project.c14210052.proyekakhir_paba

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class editProductPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get product data from intent
        val productId = intent.getStringExtra("PRODUCT_ID")
        val productName = intent.getStringExtra("PRODUCT_NAME")
        val productDescription = intent.getStringExtra("PRODUCT_DESCRIPTION")
        val productCategory = intent.getStringExtra("PRODUCT_CATEGORY")
        val productSupplier = intent.getStringExtra("PRODUCT_SUPPLIER")
        val productCostPrice = intent.getStringExtra("PRODUCT_COST_PRICE")
        val productSellingPrice = intent.getStringExtra("PRODUCT_SELLING_PRICE")
        val productQuantity = intent.getStringExtra("PRODUCT_QUANTITY")
        val productUnit = intent.getStringExtra("PRODUCT_UNIT")

        // Set data to EditText and Spinner
        findViewById<EditText>(R.id.etEditNamaProduk).setText(productName)
        findViewById<EditText>(R.id.etEditDeskripsiProduk).setText(productDescription)
        findViewById<Spinner>(R.id.dropdownEditKategoriProduk).setSelection(getSpinnerIndex(R.id.dropdownEditKategoriProduk, productCategory))
        findViewById<Spinner>(R.id.dropdownEditSupplier).setSelection(getSpinnerIndex(R.id.dropdownEditSupplier, productSupplier))
        findViewById<EditText>(R.id.etEditHargaPokokProduk).setText(productCostPrice)
        findViewById<EditText>(R.id.etEditHargaJualProduk).setText(productSellingPrice)
        findViewById<EditText>(R.id.etEditJumlahProduk).setText(productQuantity)
        findViewById<EditText>(R.id.etEditSatuanProduk).setText(productUnit)

        findViewById<Button>(R.id.btnEditSaveProduk).setOnClickListener {
            saveProductChanges(productId)
        }
    }

    private fun getSpinnerIndex(spinnerId: Int, value: String?): Int {
        val spinner = findViewById<Spinner>(spinnerId)
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString() == value) {
                return i
            }
        }
        return 0
    }

    private fun saveProductChanges(productId: String?) {
        val updatedProduct = hashMapOf(
            "namaProduk" to findViewById<EditText>(R.id.etEditNamaProduk).text.toString(),
            "deskripsiProduk" to findViewById<EditText>(R.id.etEditDeskripsiProduk).text.toString(),
            "kategoriProduk" to findViewById<Spinner>(R.id.dropdownEditKategoriProduk).selectedItem.toString(),
            "supplierProduk" to findViewById<Spinner>(R.id.dropdownEditSupplier).selectedItem.toString(),
            "hargaPokokProduk" to findViewById<EditText>(R.id.etEditHargaPokokProduk).text.toString(),
            "hargaJualProduk" to findViewById<EditText>(R.id.etEditHargaJualProduk).text.toString(),
            "jumlahProduk" to findViewById<EditText>(R.id.etEditJumlahProduk).text.toString(),
            "satuanProduk" to findViewById<EditText>(R.id.etEditSatuanProduk).text.toString()
        )

        val db = FirebaseFirestore.getInstance()
        if (productId != null) {
            db.collection("tbProduk").document(productId)
                .set(updatedProduct)
                .addOnSuccessListener {
                    Toast.makeText(this, "Product updated successfully", Toast.LENGTH_SHORT).show()
                    finish() // Close the activity
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error updating product", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
