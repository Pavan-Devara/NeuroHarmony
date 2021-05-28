package com.neuro.neuroharmony.data.model.FAQs

data class DataFAQResponse(
    val category_id: String,
    val category_title: String,
    val items: List<Any>
)