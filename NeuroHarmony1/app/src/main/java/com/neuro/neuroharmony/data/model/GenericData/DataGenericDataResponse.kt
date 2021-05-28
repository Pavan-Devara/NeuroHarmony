package com.neuro.neuroharmony.data.model.GenericData

import com.google.gson.annotations.SerializedName

data class DataGenericDataResponse(
    val baseline: Baseline,
    val is_initial_matchmaking_done: Boolean,
    val token_debit_actions: TokenDebitActions,
    val express_interest_status: Boolean,
    val qs_time_limit: Int,
    val auto_pause_time: Int,
    val social_filter_active: Boolean,
    val match_pagination: Int,
    val do_not_match: Boolean,

    val unread_notifications:Int,

    @SerializedName("final_match")
    val final_match: Boolean

)