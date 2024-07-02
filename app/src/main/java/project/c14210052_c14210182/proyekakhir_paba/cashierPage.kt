package project.c14210052_c14210182.proyekakhir_paba

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import project.c14210052_c14210182.proyekakhir_paba.adapter.adapterCashier
import project.c14210052_c14210182.proyekakhir_paba.dataClass.DetailPenjualan
import project.c14210052_c14210182.proyekakhir_paba.dataClass.Produk
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class cashierPage : AppCompatActivity() {

    private lateinit var backButton: ImageButton
    private lateinit var rvCashier: RecyclerView
    private lateinit var _productList: MutableList<Pair<Produk, Int>>
    private lateinit var adapterCashier: adapterCashier

    private val db = FirebaseFirestore.getInstance()
    var total = 0

    private val someActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val produk: Produk? = if (android.os.Build.VERSION.SDK_INT >= 33) {
                data?.getParcelableExtra("result_key", Produk::class.java)
            } else {
                @Suppress("DEPRECATION")
                data?.getParcelableExtra("result_key")
            }

            val jumlahBeli: Int = data?.getIntExtra("jumlah_beli", 0) ?: 0

            if (produk != null) {
                val existingIndex = _productList.indexOfFirst { it.first.idProduk == produk.idProduk }
                if (existingIndex != -1) {
                    // Product already exists, update the quantity or other properties
                    _productList[existingIndex] = Pair(produk, jumlahBeli)
                } else {
                    // Product doesn't exist, add it to the list
                    _productList.add(Pair(produk, jumlahBeli))
                }            }
            updateTotalDisplay()

            adapterCashier.notifyDataSetChanged()
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cashier)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        _productList = mutableListOf()

        rvCashier = findViewById(R.id.rvCashier)

        backButton = findViewById(R.id.btnBackFromCashier)

        backButton.setOnClickListener {
            finish()
        }

        val _btnTambah = findViewById<Button>(R.id.btnTambahCashier)
        val _btnRiwayat = findViewById<Button>(R.id.btnRiwayatCashier)

        val _btnBayar = findViewById<Button>(R.id.btnBayarCashier)
        val _clOverlay = findViewById<ConstraintLayout>(R.id.clOverlayCashier)

        _btnTambah.setOnClickListener {
            val intent = Intent(this@cashierPage, addCashierPage::class.java)
            someActivityResultLauncher.launch(intent)
        }
        _productList = mutableListOf()
        adapterCashier = adapterCashier(_productList,
            { produk ->
                // pergi ke detail
                deleteProduct(produk)
            })
        rvCashier.layoutManager = LinearLayoutManager(this)
        rvCashier.adapter = adapterCashier


        _btnRiwayat.setOnClickListener {
            val intent = Intent(this@cashierPage, riwayatPenjualanPage::class.java)
            startActivity(intent)
        }


        ////////////////////////// proses bayar ///////////////////////////////////////////

        _btnBayar.setOnClickListener {
            _clOverlay.visibility = View.VISIBLE
            val _tvTotalOverlayCashier = findViewById<TextView>(R.id.tvTotalOverlayCashier)
            _tvTotalOverlayCashier.setText(total.toString())
        }

        val delay = 800 // 1 seconds after user stops typing
        var lastTextEdit: Long = 0
        var handler = Handler(Looper.getMainLooper())
        val _etBayarOverlayCashier = findViewById<EditText>(R.id.etBayarOverlayCashier)
        val _kembalian = findViewById<TextView>(R.id.tvKembalianOverlayCashier)

        val inputFinishChecker = Runnable {
            if (SystemClock.uptimeMillis() > (lastTextEdit + delay - 500)) {
                val _etBayarOverlayCashierText = _etBayarOverlayCashier.text.toString()
                if (_etBayarOverlayCashierText.isNotEmpty()) {
                    val _etBayarOverlayCashierInt = _etBayarOverlayCashierText.toIntOrNull() ?: 0
                    if (_etBayarOverlayCashierInt >= total) {
                        _kembalian.setText((_etBayarOverlayCashierInt - total).toString())
                    }
                }
            }
        }

        val editText = findViewById<EditText>(R.id.etBayarOverlayCashier)
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.length > 0) {
                    lastTextEdit = SystemClock.uptimeMillis()
                    handler.postDelayed(inputFinishChecker, delay.toLong())
                }

            }
        })

        val _btnBayarOverlay = findViewById<Button>(R.id.btnBayarOverlayCashier)

        _btnBayarOverlay.setOnClickListener {
            val _etBayarOverlayCashierText = _etBayarOverlayCashier.text.toString()
            if (_etBayarOverlayCashierText.isNotEmpty()) {
                val _etBayarOverlayCashierInt = _etBayarOverlayCashierText.toIntOrNull() ?: 0
                if (_etBayarOverlayCashierInt >= total) {
                    val date = Date()
                    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val timeFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                    val tanggal = dateFormat.format(date)
                    val waktu = timeFormat.format(date)
                    val namaProduk = _productList.map { it.first.namaProduk }.toMutableList()
                    val hargaPerProduk =
                        _productList.map { it.first.hargaJualProduk.toString() }.toMutableList()
                    val jumlah = _productList.map { it.second.toString() }.toMutableList()
                    val bayar = _etBayarOverlayCashier.text.toString().toInt()
                    val kembalian = bayar - total

                    val detPenjualan = hashMapOf(
                        "tanggal" to tanggal,
                        "waktu" to waktu,
                        "namaProduk" to namaProduk,
                        "hargaPerProduk" to hargaPerProduk,
                        "jumlah" to jumlah,
                        "total" to total,
                        "bayar" to bayar,
                        "kembalian" to kembalian
                    )

                    db.collection("tbRiwayatPenjualan")
                        .add(detPenjualan)
                        .addOnSuccessListener { documentReference ->
                            updateProductQuantities()
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Log.e("Firestore", "Error adding document", e)
                            Toast.makeText(this@cashierPage, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }


        }

        _clOverlay.setOnClickListener {
            _clOverlay.visibility = View.GONE
        }
    }

    private fun updateProductQuantities() {
        for ((product, quantitySold) in _productList) {
            val productRef = product.idProduk?.let { db.collection("tbProduk").document(it) }

            productRef?.get()?.addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val currentQuantity = document.getLong("jumlahProduk") ?: 0L
                    val newQuantity = currentQuantity - quantitySold

                    if (newQuantity >= 0) {
                        productRef.update("jumlahProduk", newQuantity)
                            .addOnSuccessListener {
                                Log.d("Firestore", "Product ${product.idProduk} quantity updated to $newQuantity")
                            }
                            .addOnFailureListener { e ->
                                Log.e("Firestore", "Error updating product quantity", e)
                            }
                    } else {
                        Log.e("Firestore", "Insufficient stock for product ${product.idProduk}")
                    }
                }
            }?.addOnFailureListener { e ->
                Log.e("Firestore", "Error retrieving product", e)
            }
        }
    }

    private fun deleteProduct(produk: Produk) {
        val pairToRemove = _productList.find { it.first == produk }
        pairToRemove?.let {
            _productList.remove(it)
            adapterCashier.notifyDataSetChanged()
            Toast.makeText(this, "Product removed from list", Toast.LENGTH_SHORT).show()
        } ?: run {
            Toast.makeText(this, "Product not found in list", Toast.LENGTH_SHORT).show()
        }
        updateTotalDisplay()
    }

    private fun calculateTotal() {
        total = 0
        for ((product, quantity) in _productList) {
            total += product.hargaJualProduk * quantity
        }
    }

    private fun updateTotalDisplay() {
        if (_productList.isNotEmpty()) {
            calculateTotal()
            val _tvTotalAkhirCashier = findViewById<TextView>(R.id.tvTotalAkhirCashier)
            _tvTotalAkhirCashier.text = total.toString()
        }
    }


}