package com.neuro.neuroharmony

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.neuro.neuroharmony.ui.login.BaseActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_full_profile_pic.*

class FullProfilePic : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_profile_pic)
        val profile_pic = intent.getStringExtra("profile_pic_url")

        Picasso.get()
            .load(profile_pic)
            .resize(0,800)
            .into(blocked_FullScreenProPic)

    }
}
