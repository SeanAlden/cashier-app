package project.c14210052_c14210182.proyekakhir_paba.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.c14210052_c14210182.proyekakhir_paba.R
import project.c14210052_c14210182.proyekakhir_paba.dataClass.Produk

class adapterCashier (
    private val productList: MutableList<Pair<Produk, Int>>,
    private val onDeleteClickListener: (Produk) -> Unit,
) : RecyclerView.Adapter<adapterCashier.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nama: TextView = itemView.findViewById(R.id.tvProdukCashier)
        val jumlah: TextView = itemView.findViewById(R.id.tvJumlahCashier)
        val total: TextView = itemView.findViewById(R.id.tvTotalCashier)
        val btnDelete: Button = itemView.findViewById(R.id.btnDeleteCashier)

        fun bind(produk: Produk, jumlahBeli: Int) {
            nama.text = produk.namaProduk
            jumlah.text = jumlahBeli.toString()

            total.text = (produk.hargaJualProduk * jumlahBeli).toString()
            btnDelete.setOnClickListener { onDeleteClickListener(produk) }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cashier, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val (produk, jumlahBeli) = productList[position]
        holder.bind(produk, jumlahBeli)
    }

    override fun getItemCount(): Int = productList.size

    fun updateList(newList: List<Pair<Produk, Int>>) {
        productList.clear()
        productList.addAll(newList)
        notifyDataSetChanged()
    }

}