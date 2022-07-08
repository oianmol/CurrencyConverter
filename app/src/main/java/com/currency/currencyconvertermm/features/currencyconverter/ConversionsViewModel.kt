package com.currency.currencyconvertermm.features.currencyconverter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.currency.domain.models.DMConversion
import com.currency.domain.usecase.UseCaseFetchRecentConversions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversionsViewModel @Inject constructor(
    private val useCaseFetchRecentConversions: UseCaseFetchRecentConversions
) : ViewModel() {
    private val _conversionsState = MutableStateFlow<Flow<List<DMConversion>>>(emptyFlow())
    val conversionState = _conversionsState.asStateFlow()

    init {
        fetchRecentConversions()
    }

    private fun fetchRecentConversions() {
            viewModelScope.launch {
                val conversions = flowFetchRecentConversions()
                _conversionsState.value = conversions
            }
    }

    private fun flowFetchRecentConversions():Flow<List<DMConversion>>{
        return useCaseFetchRecentConversions.perform()
    }

}