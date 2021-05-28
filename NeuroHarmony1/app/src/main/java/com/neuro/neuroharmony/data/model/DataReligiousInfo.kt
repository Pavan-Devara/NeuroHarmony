package com.neuro.neuroharmony.data.model

import com.neuro.neuroharmony.data.model.Social_profile.ReligiousInfo.ChoiceReligiousInfo
import com.neuro.neuroharmony.data.model.Social_profile.ReligiousInfo.UserChoiceReligiousInfo

data class DataReligiousInfo(
    val choice: ChoiceReligiousInfo,
    val user_choice: UserChoiceReligiousInfo

)