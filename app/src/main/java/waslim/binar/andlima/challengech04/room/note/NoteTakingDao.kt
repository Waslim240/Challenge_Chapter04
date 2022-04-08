package waslim.binar.andlima.challengech04.room.note

import androidx.room.*
import waslim.binar.andlima.challengech04.room.note.NoteTaking

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