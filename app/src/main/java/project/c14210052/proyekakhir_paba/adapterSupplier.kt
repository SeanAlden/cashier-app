package project.c14210052.proyekakhir_paba

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapterSupplier(
    private val suppliers: MutableList<Supplier>,
    private val onDeleteClick: (Int) -> Unit,
    private val onItemClick: (Supplier) -> Unit
) : RecyclerView.Adapter<adapterSupplier.SupplierViewHolder>(){

    lateinit var context : Context
    class SupplierViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val _namaSupplier: TextView = itemView.findViewById(R.id.namaPrdk)
        val _alamatSupplier: TextView = itemView.findViewById(R.id.kategoriPrdk)
        val _kodePosSupplier: TextView = itemView.findViewById(R.id.kodeSupplier)
        val _deleteBtn: ImageButton = itemView.findViewById(R.id.deleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplierViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context)
            .inflate(R.layout.item_supplier, parent, false)
        return SupplierViewHolder(view)
    }

    override fun getItemCount(): Int = suppliers.size

    override fun onBindViewHolder(holder: SupplierViewHolder, position: Int) {
        val supplier = suppliers[position]
        holder._namaSupplier.text = supplier.namaSupplier
        holder._alamatSupplier.text = supplier.alamatSupplier
        holder._kodePosSupplier.text = supplier.kodeSupplier

        holder._deleteBtn.setOnClickListener {
            AlertDialog.Builder(context).apply {
                setTitle("Hapus Supplier")
                setMessage("Apakah Anda yakin ingin menghapus supplier ini?")
                setPositiveButton("Ya") { dialog, _ ->
                    onDeleteClick(position)
                    dialog.dismiss()
                }
                setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }
                create()
                show()
            }
        }
        holder.itemView.setOnClickListener {
            onItemClick(supplier)
        }
    }
}