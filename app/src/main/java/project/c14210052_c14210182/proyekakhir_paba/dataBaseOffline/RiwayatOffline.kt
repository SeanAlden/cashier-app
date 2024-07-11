package project.c14210052_c14210182.proyekakhir_paba.dataBaseOffline

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "riwayat")
data class RiwayatOffline(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "tanggal") val tanggal: String? = null,
    @ColumnInfo(name = "waktu") val waktu: String? = null,
    @ColumnInfo(name = "namaProduk") val namaProduk: ArrayList<String>? = null,
    @ColumnInfo(name = "hargaPerProduk") val hargaPerProduk: ArrayList<String>? = null,
    @ColumnInfo(name = "jumlah") val jumlah: ArrayList<String>? = null,
    @ColumnInfo(name = "total") val total: Int = 0,
    @ColumnInfo(name = "bayar") val bayar: Int = 0,
    @ColumnInfo(name = "kembalian") val kembalian: Int = 0

)
