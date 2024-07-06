package project.c14210052_c14210182.proyekakhir_paba.dataClass

import android.os.Parcel
import android.os.Parcelable

data class Produk(
    var idProduk: String? = "",
    val namaProduk: String? = "",
    val deskripsiProduk: String? = "",
    val kategoriProduk: String? = "",
    val supplierProduk: String? = "",
    val hargaPokokProduk: Int = 0,
    val hargaJualProduk: Int = 0,
    val jumlahProduk: Int = 0,
    val satuanProduk: String? = ""

): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString()
    )

    override fun describeContents(): Int = 0

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idProduk)
        parcel.writeString(namaProduk)
        parcel.writeString(deskripsiProduk)
        parcel.writeString(kategoriProduk)
        parcel.writeString(supplierProduk)
        parcel.writeInt(hargaPokokProduk)
        parcel.writeInt(hargaJualProduk)
        parcel.writeInt(jumlahProduk)
        parcel.writeString(satuanProduk)
    }

    companion object CREATOR : Parcelable.Creator<Produk> {
        override fun createFromParcel(parcel: Parcel): Produk {
            return Produk(parcel)
        }

        override fun newArray(size: Int): Array<Produk?> {
            return arrayOfNulls(size)
        }
    }

}
