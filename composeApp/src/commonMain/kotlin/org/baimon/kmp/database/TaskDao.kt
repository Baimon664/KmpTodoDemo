package org.baimon.kmp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {

    @Query("SELECT * FROM task")
    suspend fun getAllTask(): List<TaskEntity>

    @Insert
    suspend fun insertTask(task: TaskEntity)

    @Query("UPDATE task set isCheck =:check where id =:id")
    suspend fun updateCheckById(id: Int, check: Boolean)
}