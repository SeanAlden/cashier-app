package project.c14210052.proyekakhir_paba

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson

class editSupplierPage : AppCompatActivity() {

    private lateinit var _backButton : ImageButton
    private lateinit var _saveButton : Button
    private lateinit var supplier: Supplier

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_supplier)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _backButton = findViewById<ImageButton>(R.id.btnBackFromEditSupplier)
        _saveButton = findViewById(R.id.btnSaveEditSupplierData)

        // Get the passed supplier data
        val supplierJson = intent.getStringExtra("supplier")
        supplier = Gson().fromJson(supplierJson, Supplier::class.java)

        // Initialize UI elements
        var etNamaSupplier = findViewById<EditText>(R.id.etNamaSupplierEdit)
        var etEmailSupplier = findViewById<EditText>(R.id.etEmailSupplierEdit)
        var etTeleponSupplier = findViewById<EditText>(R.id.etTeleponSupplierEdit)
        var etAlamatSupplier = findViewById<EditText>(R.id.etAlamatSupplierEdit)
        var etKotaSupplier = findViewById<EditText>(R.id.etKotaSupplierEdit)
        var etProvinsiSupplier = findViewById<EditText>(R.id.etProvinsiSupplierEdit)
        var etKodePosSupplier = findViewById<EditText>(R.id.etKodePosSupplierEdit)

        // Populate fields with current data
        etNamaSupplier.setText(supplier.namaSupplier)
        etEmailSupplier.setText(supplier.emailSupplier)
        etTeleponSupplier.setText(supplier.teleponSupplier)
        etAlamatSupplier.setText(supplier.alamatSupplier)
        etKotaSupplier.setText(supplier.kotaSupplier)
        etProvinsiSupplier.setText(supplier.provinsiSupplier)
        etKodePosSupplier.setText(supplier.kodeSupplier)

        _backButton.setOnClickListener {
            val intent = Intent(this@editSupplierPage, supplierListPage::class.java)
            startActivity(intent)
        }

        _saveButton.setOnClickListener {
            // Update the supplier object with new data
            supplier = Supplier(
                etNamaSupplier.text.toString(),
                etEmailSupplier.text.toString(),
                etTeleponSupplier.text.toString(),
                etAlamatSupplier.text.toString(),
                etKotaSupplier.text.toString(),
                etProvinsiSupplier.text.toString(),
                etKodePosSupplier.text.toString()
            )

            // Convert updated supplier to JSON and pass back
//            val resultIntent = Intent()
//            resultIntent.putExtra("updateSupplier", Gson().toJson(supplier))
//            setResult(RESULT_OK, resultIntent)

            val supplierJsonn = intent.getStringExtra("updateSupplier")
            supplier = Gson().fromJson(supplierJsonn, Supplier::class.java)

            finish()
        }
    }
}
