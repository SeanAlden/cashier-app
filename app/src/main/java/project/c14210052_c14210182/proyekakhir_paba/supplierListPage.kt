package project.c14210052_c14210182.proyekakhir_paba

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
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
        _suppListener?.remove() // Remove the previous listener if it exists
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

            suppItem.id?.let {
                db.collection("tbSupplier").document(it)
                    .delete()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            println("Deleting item at position: $position, List size before removal: ${_suppList.size}")
                            loadData() // Reload data from Firestore
                            println("List size after removal: ${_suppList.size}")
                        } else {
                            println("Failed to delete item from Firestore: ${task.exception?.message}")
                        }
                    }
            }
            println("Position: $position, List size: ${_suppList.size}, todoList: $_suppList")
        } else {
            println("Invalid position: $position, List size: ${_suppList.size}")
        }
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




