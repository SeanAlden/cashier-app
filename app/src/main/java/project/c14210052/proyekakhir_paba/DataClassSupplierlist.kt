package project.c14210052.proyekakhir_paba

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataClassSupplierlist(
    var nama: String,
    var alamat: String
) : Parcelable
