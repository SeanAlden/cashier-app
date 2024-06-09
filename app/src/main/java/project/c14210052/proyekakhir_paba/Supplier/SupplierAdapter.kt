package project.c14210052.proyekakhir_paba.Supplier

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.c14210052.proyekakhir_paba.R

class SupplierAdapter(
    private val suppliers: List<Supplier>,
    private val onDeleteClick: (Int) -> Unit,
    private val onItemClick: (Supplier) -> Unit
) : RecyclerView.Adapter<SupplierAdapter.SupplierViewHolder>(){
    class SupplierViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaSupplier: TextView = itemView.findViewById(R.id.namaSupplier)
        val alamatSupplier: TextView = itemView.findViewById(R.id.alamatSupplier)
        val deleteBtn: ImageButton = itemView.findViewById(R.id.deleteBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplierViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_supplier, parent, false)
        return SupplierViewHolder(view)
    }

    override fun getItemCount(): Int = suppliers.size

    override fun onBindViewHolder(holder: SupplierViewHolder, position: Int) {
        val supplier = suppliers[position]
        holder.namaSupplier.text = supplier.namaSupplier
        holder.alamatSupplier.text = supplier.alamatSupplier

        holder.deleteBtn.setOnClickListener {
            onDeleteClick(position)
        }

        holder.itemView.setOnClickListener {
            onItemClick(supplier)
        }
    }
}