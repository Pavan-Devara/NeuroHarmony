package com.neuro.neuroharmony.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.neuro.neuroharmony.R

class QuestionsFragmentCoreBelief: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.questions_fragment_core_belief, container, false);
        return view;
    }
}