package project.c14210052.proyekakhir_paba.dataClass

import android.os.Parcel
import android.os.Parcelable

data class Supplier(
    var id: String = "",
    val namaSupplier: String = "",
    val emailSupplier: String = "",
    val teleponSupplier: String = "",
    val alamatSupplier: String = "",
    val kotaSupplier: String = "",
    val provinsiSupplier: String = "",
    val kodeSupplier: String = "",
)
