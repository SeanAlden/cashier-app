// supplierInformationPage.kt
package project.c14210052_c14210182.proyekakhir_paba

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class supplierInformationPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supplier_information)

        val tvNamaSupplier: TextView = findViewById(R.id.tvNamaSupplierInfoo)
        val tvEmailSupplier: TextView = findViewById(R.id.tvEmailSupplierInfoo)
        val tvTeleponSupplier: TextView = findViewById(R.id.tvTeleponSupplierInfoo)
        val tvAlamatSupplier: TextView = findViewById(R.id.tvAlamatSupplierInfoo)
        val tvKotaSupplier: TextView = findViewById(R.id.tvKotaSupplierInfoo)
        val tvProvinsiSupplier: TextView = findViewById(R.id.tvProvinsiSupplierInfoo)
        val tvKodePosSupplier: TextView = findViewById(R.id.tvKodePosSupplierInfoo)
        val _btnBackFromInformasiSupplier: ImageButton = findViewById(R.id.btnBackFromInformasiSupplier)

        val intent = intent
        tvNamaSupplier.text = intent.getStringExtra("SUPP_NAME")
        tvEmailSupplier.text = intent.getStringExtra("SUPP_EMAIL")
        tvTeleponSupplier.text = intent.getStringExtra("SUPP_TELP")
        tvAlamatSupplier.text = intent.getStringExtra("SUPP_ALAMAT")
        tvKotaSupplier.text = intent.getStringExtra("SUPP_KOTA")
        tvProvinsiSupplier.text = intent.getStringExtra("SUPP_PROV")
        tvKodePosSupplier.text = intent.getStringExtra("SUPP_KODE")

        _btnBackFromInformasiSupplier.setOnClickListener {
            val intent = Intent(this@supplierInformationPage, supplierListPage::class.java)
            startActivity(intent)
        }
    }
}




