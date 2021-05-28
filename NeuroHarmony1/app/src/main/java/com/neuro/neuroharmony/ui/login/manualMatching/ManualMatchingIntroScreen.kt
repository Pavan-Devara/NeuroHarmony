package com.neuro.neuroharmony.ui.login.manualMatching

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neuro.neuroharmony.R
import kotlinx.android.synthetic.main.activity_manual_matching_intro_screen.*

class ManualMatchingIntroScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_matching_intro_screen)

        add_partner_view.setOnClickListener {
            val intent = Intent(this,AddAPartner::class.java)
            startActivity(intent)


        }
        partner_list_view.setOnClickListener {
            val intent = Intent(this,PartnerListMenu::class.java)
            startActivity(intent)

        }

        back_manual_match_menu.setOnClickListener{
            onBackPressed()
        }

    }
}
