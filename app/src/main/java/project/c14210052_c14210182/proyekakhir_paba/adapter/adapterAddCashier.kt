package project.c14210052_c14210182.proyekakhir_paba.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import project.c14210052_c14210182.proyekakhir_paba.R
import project.c14210052_c14210182.proyekakhir_paba.dataClass.Produk


class adapterAddCashier (
    private val productList: List<Produk>,
    private val onAddClickListener: (Produk, Int) -> Unit,
    ) : RecyclerView.Adapter<adapterAddCashier.CashierViewHolder>() {

        inner class CashierViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val nama: TextView = itemView.findViewById(R.id.tvNamaAddCashier)
            val kategori: TextView = itemView.findViewById(R.id.tvKategoriAddCashier)
            val harga: TextView = itemView.findViewById(R.id.tvHargaProdukAddCashier)
            val jumlah: TextView = itemView.findViewById(R.id.tvJumlahAddCashier)
            val satuan: TextView = itemView.findViewById(R.id.tvSatuanAddCashier)
            val jumlahBeli: EditText = itemView.findViewById(R.id.etJumlahCashier)
            val btnAdd: ImageButton = itemView.findViewById(R.id.btnAddCashier)

            fun bind(produk: Produk) {
                nama.text = produk.namaProduk
                kategori.text = produk.kategoriProduk
                harga.text = produk.hargaJualProduk.toString()
                jumlah.text = produk.jumlahProduk.toString()
                satuan.text = produk.satuanProduk

                btnAdd.setOnClickListener {
                    val jumlahBeliText = jumlahBeli.text.toString()
                    if (jumlahBeliText.isNotEmpty()) {
                        val jumlahBeliInt = jumlahBeliText.toIntOrNull() ?: 0
                        val jumlahInt = jumlah.text.toString().toIntOrNull() ?: 0
                        if (jumlahBeliInt > 0 && jumlahBeliInt <= jumlahInt) {
                            onAddClickListener(produk, jumlahBeliInt)
                        }
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CashierViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_add_cashier, parent, false)
            return CashierViewHolder(view)
        }

        override fun onBindViewHolder(holder: CashierViewHolder, position: Int) {
            holder.bind(productList[position])
        }

        override fun getItemCount(): Int = productList.size
    }