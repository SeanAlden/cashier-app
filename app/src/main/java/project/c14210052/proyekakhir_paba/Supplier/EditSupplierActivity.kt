package project.c14210052.proyekakhir_paba.Supplier

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import project.c14210052.proyekakhir_paba.R

class EditSupplierActivity : AppCompatActivity() {

    private lateinit var backButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_supplier)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        backButton = findViewById<ImageButton>(R.id.backButtonFromEditSupplier)

        backButton.setOnClickListener {
            val intent = Intent(this@EditSupplierActivity, SupplierListActivity::class.java)
            startActivity(intent)
        }

    }
}
