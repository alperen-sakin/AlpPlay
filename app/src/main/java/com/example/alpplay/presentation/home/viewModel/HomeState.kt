package com.example.alpplay.presentation.home.viewModel

import com.example.alpplay.domain.model.Channel

data class HomeState(
    val tvCategories: List<String> = emptyList(),
    val movieCategories: List<String> = emptyList(),
    val seriesCategories: List<String> = emptyList(),
    val channels: List<Channel> = emptyList(),
    val isLoading: Boolean = false,

)
