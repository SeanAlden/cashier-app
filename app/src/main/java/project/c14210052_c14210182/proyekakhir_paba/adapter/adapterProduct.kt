package project.c14210052_c14210182.proyekakhir_paba.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.c14210052_c14210182.proyekakhir_paba.R
import project.c14210052_c14210182.proyekakhir_paba.dataClass.Produk

class adapterProduct(
    private val productList: List<Produk>,
    private val onItemClick: (Produk) -> Unit,
    private val onDeleteClickListener: (Produk) -> Unit,
    private val onEditClickListener: (Produk) -> Unit
) : RecyclerView.Adapter<adapterProduct.ProductViewHolder>() {

    inner class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val namaPrdk: TextView = itemView.findViewById(R.id.namaPrdk)
        val kategoriPrdk: TextView = itemView.findViewById(R.id.kategoriPrdk)
        val hargaPrdk: TextView = itemView.findViewById(R.id.hargaPrdk)
        val jumlahPrdk: TextView = itemView.findViewById(R.id.jumlahPrdk)
        val satuanPrdk: TextView = itemView.findViewById(R.id.satuanPrdk)
        val deleteBtn: ImageButton = itemView.findViewById(R.id.deleteBtn)
        val editBtn: ImageButton = itemView.findViewById(R.id.editBtn)

        fun bind(produk: Produk) {
            namaPrdk.text = produk.namaProduk
            kategoriPrdk.text = produk.kategoriProduk
            hargaPrdk.text = produk.hargaJualProduk.toString()
            jumlahPrdk.text = produk.jumlahProduk.toString()
            satuanPrdk.text = produk.satuanProduk

            deleteBtn.setOnClickListener { onDeleteClickListener(produk) }
            itemView.setOnClickListener { onItemClick(produk) }
            editBtn.setOnClickListener { onEditClickListener(produk) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_daftarproduk, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size
}