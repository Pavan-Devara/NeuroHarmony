package com.neuro.neuroharmony.ui.login.CompleteMatch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.HomePage1
import kotlinx.android.synthetic.main.activity_finalize_match_screen.*

class FinalizeMatchScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finalize_match_screen)

        finalize_my_match.setOnClickListener {
            val intent = Intent(this,WhereDidMatch::class.java)
            startActivity(intent)
        }
        finalize_match_requests.setOnClickListener {
            val intent = Intent(this,FinalizeMatchMenu::class.java)
            startActivity(intent)
        }

        back_finalize_match_menu.setOnClickListener {
            onBackPressed()
        }
    }
    override fun onBackPressed() {
        val intent = Intent(this, HomePage1::class.java)
        startActivity(intent)
    }
}
