package com.example.androidapidemo.data

import com.example.androidapidemo.model.ApiItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ApiRepository(private val apiItemDao: ApiItemDao) {
    fun getAllApiItems(): Flow<List<ApiItem>> {
        return apiItemDao.getAllApiItems().map { entities ->
            entities.map { it.toApiItem() }
        }
    }

    fun searchApiItems(query: String): Flow<List<ApiItem>> {
        return apiItemDao.searchApiItems(query).map { entities ->
            entities.map { it.toApiItem() }
        }
    }

    suspend fun getApiItemById(id: Int): ApiItem? {
        return apiItemDao.getApiItemById(id)?.toApiItem()
    }

    suspend fun insertApiItems(items: List<ApiItem>) {
        apiItemDao.insertApiItems(items.map { it.toEntity() })
    }

    suspend fun refreshApiItems(items: List<ApiItem>) {
        apiItemDao.deleteAllApiItems()
        apiItemDao.insertApiItems(items.map { it.toEntity() })
    }
} 