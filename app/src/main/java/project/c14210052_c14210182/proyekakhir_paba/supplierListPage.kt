package project.c14210052_c14210182.proyekakhir_paba

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import project.c14210052_c14210182.proyekakhir_paba.adapter.adapterSupplier
import project.c14210052_c14210182.proyekakhir_paba.dataClass.Supplier

class supplierListPage : AppCompatActivity(), adapterSupplier.OnDeleteClickListener, adapterSupplier.OnEditClickListener, adapterSupplier.OnItemClickListener {

    private lateinit var _recyclerView: RecyclerView
    private lateinit var _suppAdapter: adapterSupplier
    private lateinit var db: FirebaseFirestore
    private var _suppList = mutableListOf<Supplier>()
    private var _suppListener: ListenerRegistration? = null
    lateinit var _btnBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_supplier_list)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        _recyclerView = findViewById(R.id.rvSupplier)
        _recyclerView.layoutManager = LinearLayoutManager(this)
        _suppAdapter = adapterSupplier(_suppList, this, this, this)
        _recyclerView.adapter = _suppAdapter

        val btnTambahSupp: FloatingActionButton = findViewById(R.id.btnAddSupplier)
        btnTambahSupp.setOnClickListener {
            val intent = Intent(this@supplierListPage, addSupplierPage::class.java)
            startActivity(intent)
        }

        db = FirebaseFirestore.getInstance()
        loadData()

        _btnBack = findViewById(R.id.btnBackFromSupplierList)
        _btnBack.setOnClickListener {
            val intent = Intent(this@supplierListPage, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onItemClick(suppItem: Supplier){
        val intent = Intent(this, supplierInformationPage::class.java)
        intent.putExtra("SUPP_ID", suppItem.id)
        intent.putExtra("SUPP_NAME", suppItem.namaSupplier)
        intent.putExtra("SUPP_EMAIL", suppItem.emailSupplier)
        intent.putExtra("SUPP_TELP", suppItem.teleponSupplier)
        intent.putExtra("SUPP_ALAMAT", suppItem.alamatSupplier)
        intent.putExtra("SUPP_KOTA", suppItem.kotaSupplier)
        intent.putExtra("SUPP_PROV", suppItem.provinsiSupplier)
        intent.putExtra("SUPP_KODE", suppItem.kodeSupplier)
        startActivity(intent)
    }

    private fun loadData() {
        _suppListener?.remove()
        _suppListener = db.collection("tbSupplier")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                snapshots?.let { snapshot ->
                    _suppList.clear()
                    for (document in snapshot.documents) {
                        val suppItem = document.toObject(Supplier::class.java)
                        suppItem?.let {
                            it.id = document.id
                            _suppList.add(it)
                        }
                    }
                    _suppAdapter.notifyDataSetChanged()
                }
            }
    }

    override fun onDeleteClick(position: Int) {
        if (position in _suppList.indices) {
            val suppItem = _suppList[position]

            suppItem.namaSupplier?.let { namaSupplier ->
                db.collection("tbProduk")
                    .whereEqualTo("supplierProduk", namaSupplier)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        if (querySnapshot.isEmpty) {
                            // jika tidak ada produk yang menggunakan supplier ini maka hapus suppliernya
                            suppItem.id?.let {
                                db.collection("tbSupplier").document(it)
                                    .delete()
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            println("Deleting item at position: $position, List size before removal: ${_suppList.size}")
                                            loadData() // melakukan load data ulang dengan data terbaru
                                            println("List size after removal: ${_suppList.size}")
                                        } else {
                                            println("Failed to delete item from Firestore: ${task.exception?.message}")
                                        }
                                    }
                            }
                            println("Position: $position, List size: ${_suppList.size}, todoList: $_suppList")
                        } else {
                            // jika ada produk yang menggunakan supplier ini maka tampilkan message
                            println("Supplier ini masih digunakan oleh beberapa produk")
                            showMessage("Supplier ini masih digunakan oleh beberapa produk")
                        }
                    }
                    .addOnFailureListener { exception ->
                        println("Error checking supplier usage: ${exception.message}")
                    }
            }
        } else {
            println("Invalid position: $position, List size: ${_suppList.size}")
        }
    }

    private fun showMessage(message: String) {
        val dialog = android.app.AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }

    override fun onEditClick(suppItem: Supplier) {
        val intent = Intent(this, editSupplierPage::class.java)
        intent.putExtra("SUPP_ID", suppItem.id)
        intent.putExtra("SUPP_NAME", suppItem.namaSupplier)
        intent.putExtra("SUPP_EMAIL", suppItem.emailSupplier)
        intent.putExtra("SUPP_TELP", suppItem.teleponSupplier)
        intent.putExtra("SUPP_ALAMAT", suppItem.alamatSupplier)
        intent.putExtra("SUPP_KOTA", suppItem.kotaSupplier)
        intent.putExtra("SUPP_PROV", suppItem.provinsiSupplier)
        intent.putExtra("SUPP_KODE", suppItem.kodeSupplier)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _suppListener?.remove()
    }
}




