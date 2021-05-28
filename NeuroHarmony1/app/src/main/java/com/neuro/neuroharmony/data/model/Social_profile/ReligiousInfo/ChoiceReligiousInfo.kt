package com.neuro.neuroharmony.data.model.Social_profile.ReligiousInfo

import com.neuro.neuroharmony.data.model.DeeplyReligiou
import com.neuro.neuroharmony.data.model.Religion
import com.neuro.neuroharmony.data.model.WorshipPlaceVisit

data class ChoiceReligiousInfo (
    val deeply_religious: List<DeeplyReligiou>,
    val worship_place_visit: List<WorshipPlaceVisit>
)