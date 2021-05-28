package com.neuro.neuroharmony.ui.login.ViewPartnerProfile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.neuro.neuroharmony.FullProfilePic
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.BaseActivity
import com.neuro.neuroharmony.ui.login.CircleTransform
import com.neuro.neuroharmony.ui.login.ViewPagerAdapter
import com.neuro.neuroharmony.utils.PrefsHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_matched_user_neuro_desire_graph_result_screen.*
import kotlinx.android.synthetic.main.activity_view_partner_profile.*

class ViewPartnerProfile : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_partner_profile)

        val intent = intent
        val profile_pic = intent.getStringExtra("profile_pic")
        val name = intent.getStringExtra("user_name")


        Picasso.get()
            .load(profile_pic)
            .resize(200,200)
            .transform(CircleTransform())
            .placeholder(R.mipmap.profile_pic_placeholder)
            .centerCrop()
            .into(partner_profile_image)

        partner_profile_image.setOnClickListener{
            if (profile_pic != "") {
                val intent = Intent(this, FullProfilePic::class.java)
                intent.putExtra(
                    "profile_pic_url",
                    profile_pic
                )
                startActivity(intent)
            }
            else{
                Toast.makeText(this
                    , "You haven't uploaded the profile picture", Toast.LENGTH_LONG).show()
            }
        }

        partner_profile_name.text = name

        val text: String =
            java.lang.String.format(
                "%s | %s | %s ",
                PrefsHelper().getPref<String>("matched_user_age"),
                PrefsHelper().getPref<String>("matched_user_marital_status"),
                PrefsHelper().getPref<String>("matched_user_gender")
            )

        partner_profile_details.text = text

        val viewPager: ViewPager = findViewById(R.id.viewPagerViewPartnerProfile)
        val adapter = ViewPagerPartnerProfileAdapter(supportFragmentManager)
        viewPager.adapter = adapter

        val tabLayout = findViewById<TabLayout>(R.id.tabsViewPartnerProfile)
        tabLayout.setupWithViewPager(viewPager)

        back_partner_profile.setOnClickListener {
            onBackPressed()
        }

    }
}
