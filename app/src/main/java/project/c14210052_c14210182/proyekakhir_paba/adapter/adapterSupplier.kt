package project.c14210052_c14210182.proyekakhir_paba.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.c14210052_c14210182.proyekakhir_paba.R
import project.c14210052_c14210182.proyekakhir_paba.dataClass.Supplier

class adapterSupplier(
    private val suppList: MutableList<Supplier>,
    private val listener: OnDeleteClickListener,
    private val editListener: OnEditClickListener,
    private val itemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<adapterSupplier.SupplierViewHolder>(){

    interface OnDeleteClickListener {
        fun onDeleteClick(position: Int)
    }
    interface OnEditClickListener {
        fun onEditClick(suppItem: Supplier)
    }

    interface OnItemClickListener {
        fun onItemClick(suppItem: Supplier)
    }

    class SupplierViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val _namaSupplier: TextView = itemView.findViewById(R.id.namaPrdk)
        val _alamatSupplier: TextView = itemView.findViewById(R.id.kategoriPrdk)
        val _kodePosSupplier: TextView = itemView.findViewById(R.id.kodeSupplier)
        val _deleteBtn: ImageButton = itemView.findViewById(R.id.deleteBtn)
        val _editBtn: ImageButton = itemView.findViewById(R.id.editBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SupplierViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_supplier, parent, false)
        return SupplierViewHolder(view)
    }

    override fun getItemCount(): Int {
        return suppList.size
    }

    override fun onBindViewHolder(holder: SupplierViewHolder, position: Int) {
        val supplier = suppList[position]
        holder._namaSupplier.text = supplier.namaSupplier
        holder._alamatSupplier.text = supplier.alamatSupplier
        holder._kodePosSupplier.text = supplier.kodeSupplier

        holder._editBtn.setOnClickListener {
            val currentPosition = holder.adapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                editListener.onEditClick(supplier)
            }
        }

        holder._deleteBtn.setOnClickListener {
            val currentPosition = holder.adapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                val builder = AlertDialog.Builder(holder.itemView.context)
                builder.setMessage("Apakah Anda ingin menghapus Supplier ini?")
                    .setPositiveButton("Ya") { dialog, id ->
                        listener.onDeleteClick(position)
                    }
                    .setNegativeButton("Tidak") { dialog, id ->
                        dialog.dismiss()
                    }
                builder.create().show()
            }
        }
        holder.itemView.setOnClickListener {
            val currentPosition = holder.adapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                itemClickListener.onItemClick(supplier)
            }
        }
    }
}

