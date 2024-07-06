package project.c14210052_c14210182.proyekakhir_paba.dataBaseOffline

import androidx.room.*

@Dao
interface RiwayatOfflineDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(riwayat: RiwayatOffline)

    @Delete
    fun delete(riwayat: RiwayatOffline)

    @Query("SELECT * FROM riwayat ORDER BY id ASC")
    fun getAllRiwayat(): List<RiwayatOffline>

    @Query("SELECT * FROM riwayat WHERE id = :id LIMIT 1")
    fun getRiwayatById(id: Int): RiwayatOffline?
}
