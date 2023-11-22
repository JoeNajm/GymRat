package com.example.finalgymlog.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.finalgymlog.data.Session

@Dao
interface SessionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSession(session: Session)

    @Query("SELECT * FROM session_table ORDER BY id DESC")
    fun readAllSession(): LiveData<List<Session>>

    @Update
    suspend fun updateSession(session: Session)

    @Delete
    suspend fun deleteSession(session: Session)

}

@Dao
interface ExoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addExo(exo: Exo)

    @Query("SELECT * FROM exo_table ORDER BY id ASC")
    fun readAllExo(): LiveData<List<Exo>>

    @Query("SELECT * FROM exo_table WHERE parentId = :parentId ORDER BY id ASC")
    fun readExoByParentId(parentId: Int): LiveData<List<Exo>>

    @Update
    suspend fun updateExo(exo: Exo)

    @Delete
    suspend fun deleteExo(exo: Exo)

    @Query("DELETE FROM exo_table WHERE parentId = :parentId")
    abstract fun deleteExoByParentId(parentId: Int)
}

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFood(food: Food)

    @Query("SELECT * FROM food_table ORDER BY id ASC")
    fun readAllFood(): LiveData<List<Food>>

    @Query("SELECT * FROM food_table WHERE parentId = :parentId ORDER BY id ASC")
    fun readFoodByParentId(parentId: Int): LiveData<List<Food>>

    @Update
    suspend fun updateFood(food: Food)

    @Delete
    suspend fun deleteFood(food: Food)

    @Query("DELETE FROM food_table WHERE parentId = :parentId")
    abstract fun deleteFoodByParentId(parentId: Int)
}