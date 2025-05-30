package com.example.androidapidemo.model

data class ApiItem(
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val example: String,
    val minSdkVersion: Int
) 