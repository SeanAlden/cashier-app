package project.c14210052.proyekakhir_paba.dataClass

import android.os.Parcel
import android.os.Parcelable

data class KategoriProduk(
    val idKategori: Int = 0,
    val namaKategori: String? = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idKategori)
        parcel.writeString(namaKategori)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<KategoriProduk> {
        override fun createFromParcel(parcel: Parcel): KategoriProduk {
            return KategoriProduk(parcel)
        }

        override fun newArray(size: Int): Array<KategoriProduk?> {
            return arrayOfNulls(size)
        }
    }
}
