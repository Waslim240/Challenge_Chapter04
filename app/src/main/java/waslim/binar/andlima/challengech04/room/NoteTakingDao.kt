package waslim.binar.andlima.challengech04.room

import androidx.room.*

@Dao
interface NoteTakingDao {

    @Query("SELECT * FROM NoteTaking")
    fun getAllNoteTaking() : List<NoteTaking>

    @Insert
    fun insertNoteTaking(noteTaking: NoteTaking) : Long

    @Update
    fun updateNoteTaking(noteTaking: NoteTaking) : Int

    @Delete
    fun deleteNoteTaking(noteTaking: NoteTaking) : Int
}