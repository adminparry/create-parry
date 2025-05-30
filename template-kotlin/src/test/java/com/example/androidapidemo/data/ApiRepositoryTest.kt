package com.example.androidapidemo.data

import com.example.androidapidemo.model.ApiItem
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ApiRepositoryTest {
    @Mock
    private lateinit var apiItemDao: ApiItemDao
    private lateinit var repository: ApiRepository

    private val sampleApiItem = ApiItemEntity(
        id = 1,
        name = "Test API",
        description = "Test Description",
        category = "Test Category",
        example = "Test Example",
        minSdkVersion = 21
    )

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = ApiRepository(apiItemDao)
    }

    @Test
    fun getAllApiItems_returnsTransformedItems() = runBlocking {
        // Given
        val entities = listOf(sampleApiItem)
        `when`(apiItemDao.getAllApiItems()).thenReturn(flowOf(entities))

        // When
        val result = repository.getAllApiItems().first()

        // Then
        assertEquals(1, result.size)
        with(result[0]) {
            assertEquals(1, id)
            assertEquals("Test API", name)
            assertEquals("Test Description", description)
            assertEquals("Test Category", category)
            assertEquals("Test Example", example)
            assertEquals(21, minSdkVersion)
        }
    }

    @Test
    fun searchApiItems_returnsFilteredItems() = runBlocking {
        // Given
        val query = "Test"
        val entities = listOf(sampleApiItem)
        `when`(apiItemDao.searchApiItems(query)).thenReturn(flowOf(entities))

        // When
        val result = repository.searchApiItems(query).first()

        // Then
        assertEquals(1, result.size)
        assertEquals("Test API", result[0].name)
    }

    @Test
    fun getApiItemById_returnsCorrectItem() = runBlocking {
        // Given
        `when`(apiItemDao.getApiItemById(1)).thenReturn(sampleApiItem)

        // When
        val result = repository.getApiItemById(1)

        // Then
        assertEquals(sampleApiItem.id, result?.id)
        assertEquals(sampleApiItem.name, result?.name)
    }

    @Test
    fun getApiItemById_returnsNullForInvalidId() = runBlocking {
        // Given
        `when`(apiItemDao.getApiItemById(999)).thenReturn(null)

        // When
        val result = repository.getApiItemById(999)

        // Then
        assertEquals(null, result)
    }
} 