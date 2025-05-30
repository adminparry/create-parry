package com.example.androidapidemo.data

import android.content.Context
import androidx.room.*
import com.example.androidapidemo.model.ApiItem
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "api_items")
data class ApiItemEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val example: String,
    val minSdkVersion: Int
)

@Dao
interface ApiItemDao {
    @Query("SELECT * FROM api_items")
    fun getAllApiItems(): Flow<List<ApiItemEntity>>

    @Query("SELECT * FROM api_items WHERE id = :id")
    suspend fun getApiItemById(id: Int): ApiItemEntity?

    @Query("SELECT * FROM api_items WHERE name LIKE '%' || :query || '%' OR description LIKE '%' || :query || '%'")
    fun searchApiItems(query: String): Flow<List<ApiItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApiItems(items: List<ApiItemEntity>)

    @Query("DELETE FROM api_items")
    suspend fun deleteAllApiItems()
}

@Database(entities = [ApiItemEntity::class], version = 1)
abstract class ApiDatabase : RoomDatabase() {
    abstract fun apiItemDao(): ApiItemDao

    companion object {
        @Volatile
        private var INSTANCE: ApiDatabase? = null

        fun getDatabase(context: Context): ApiDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ApiDatabase::class.java,
                    "api_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

fun ApiItemEntity.toApiItem() = ApiItem(
    id = id,
    name = name,
    description = description,
    category = category,
    example = example,
    minSdkVersion = minSdkVersion
)

fun ApiItem.toEntity() = ApiItemEntity(
    id = id,
    name = name,
    description = description,
    category = category,
    example = example,
    minSdkVersion = minSdkVersion
) 