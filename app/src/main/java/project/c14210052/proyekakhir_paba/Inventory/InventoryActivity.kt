package project.c14210052.proyekakhir_paba.Inventory

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import project.c14210052.proyekakhir_paba.MainActivity
import project.c14210052.proyekakhir_paba.R

class InventoryActivity : AppCompatActivity() {

    private lateinit var backButton : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inventory)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        backButton = findViewById(R.id.backButtonFromInventory)

        backButton.setOnClickListener {
            val intent = Intent(this@InventoryActivity, MainActivity::class.java)
            startActivity(intent)
        }

    }
}