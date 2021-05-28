package com.neuro.neuroharmony.ui.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.neuro.neuroharmony.FullProfilePic
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.PrefsHelper
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_matched_user_core_belief_graphical_result.*
import kotlinx.android.synthetic.main.activity_matched_user_neuro_desire_graph_result_screen.*
import org.json.JSONObject

class MatchedUserCoreBeliefGraphicalResult : AppCompatActivity() {

    val IDEALIST = 1
    val POWERITE = 2
    val GROUPIE = 3

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matched_user_core_belief_graphical_result)

        back_core_match_report.setOnClickListener {
            super.onBackPressed()
        }

        val self_profile_pic = PrefsHelper().getPref<String>("user_pic")
        if (self_profile_pic!=""){
            Picasso.get()
                .load(self_profile_pic)
                .resize(200,200)
                .transform(CircleTransform())
                .placeholder(R.mipmap.profile_pic_placeholder)
                .centerCrop()
                .into(self_pic_view)}
        match_core_belief_report.setOnClickListener {
            super.onBackPressed();
        }

        username_matched_core_belief_graphical_result.text = PrefsHelper().getPref("user_name")
        val user_name = intent.getStringExtra("user_name")
        match_name.text = user_name

        val profile_pic = intent.getStringExtra("profile_pic")
        Picasso.get()
            .load(profile_pic)
            .resize(200,200)
            .transform(CircleTransform())
            .placeholder(R.mipmap.profile_pic_placeholder)
            .centerCrop()
            .into(match_pic_view)


        self_pic_view.setOnClickListener{
            if (self_profile_pic != "") {
                val intent = Intent(this, FullProfilePic::class.java)
                intent.putExtra(
                    "profile_pic_url",
                    self_profile_pic
                )
                startActivity(intent)
            }
            else{
                Toast.makeText(this
                    , "You haven't uploaded the profile picture", Toast.LENGTH_LONG).show()
            }
        }
        match_pic_view.setOnClickListener{
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
                    , "This user hasn't uploaded the profile picture", Toast.LENGTH_LONG).show()
            }
        }

        val score_core_belief = intent.getStringExtra("core_belief_graph")
        val obj = JSONObject(score_core_belief)
        val user_score_group_type = obj.getJSONObject("user_score").getString("group_type")
        val user_score_order_number = obj.getJSONObject("user_score").getString("order_number")
        val match_score_group_type = obj.getJSONObject("matched_user_score").getString("group_type")
        val match_score_order_number = obj.getJSONObject("matched_user_score").getString("order_number")

        val array = obj.getJSONArray("items")
        for (i in 0 until array.length()){
            val obj1 = array.getJSONObject(i)
            val group_type = obj1.getInt("group_type")
            val subItems = obj1.getJSONArray("sub_items")
            val button1 = subItems.getJSONObject(0).getString("name")
            val button2 = subItems.getJSONObject(1).getString("name")
            val button3 = subItems.getJSONObject(2).getString("name")
            Log.d(i.toString(), button1+"_"+button2+"_"+button3)
            val order1 = subItems.getJSONObject(0).getInt("order_number")
            val order2 = subItems.getJSONObject(1).getInt("order_number")
            val order3 = subItems.getJSONObject(2).getInt("order_number")


            if(group_type==IDEALIST){
                if ((user_score_group_type == match_score_group_type)&&
                    (group_type.toString() == user_score_group_type) &&
                    (group_type.toString() == match_score_group_type)&&
                    (user_score_order_number.toInt()==match_score_order_number.toInt())) {
                    when (user_score_order_number.toInt()) {
                        order1 -> match_idealisticCreativity.background =
                            getDrawable(R.drawable.round_green_button)
                        order2 -> match_idealisticPerfectionist.background =
                            getDrawable(R.drawable.round_green_button)
                        order3 -> match_idealisticVisionary.background =
                            getDrawable(R.drawable.round_green_button)
                    }
                }else{
                    if (group_type.toString() == user_score_group_type) {
                        when (user_score_order_number.toInt()) {
                            order1 -> match_idealisticCreativity.background =
                                getDrawable(R.drawable.rounded_button_square)
                            order2 -> match_idealisticPerfectionist.background =
                                getDrawable(R.drawable.rounded_button_square)
                            order3 -> match_idealisticVisionary.background =
                                getDrawable(R.drawable.rounded_button_square)
                        }
                    }
                    if (group_type.toString() == match_score_group_type) {
                        when (match_score_order_number.toInt()) {
                            order1 -> match_idealisticCreativity.background =
                                getDrawable(R.drawable.rounded_button_beige)
                            order2 -> match_idealisticPerfectionist.background =
                                getDrawable(R.drawable.rounded_button_beige)
                            order3 -> match_idealisticVisionary.background =
                                getDrawable(R.drawable.rounded_button_beige)
                        }
                    }
                }
                match_idealisticCreativity.text = button1
                match_idealisticPerfectionist.text = button2
                match_idealisticVisionary.text = button3

            }else if (group_type==POWERITE){

                if ((user_score_group_type == match_score_group_type)&&
                    (group_type.toString() == user_score_group_type) &&
                    (group_type.toString() == match_score_group_type)&&
                    (user_score_order_number.toInt()==match_score_order_number.toInt())) {
                    when (user_score_order_number.toInt()) {
                        order1 -> match_PoweriteAnalyst.background = getDrawable(R.drawable.round_green_button)
                        order2 -> match_PoweriteHelper.background = getDrawable(R.drawable.round_green_button)
                        order3 -> match_PoweriteBoss.background = getDrawable(R.drawable.round_green_button)
                    }

                }else{

                    if (group_type.toString() == user_score_group_type) {
                        when (user_score_order_number.toInt()) {
                            order1 -> match_PoweriteAnalyst.background = getDrawable(R.drawable.rounded_button_square)
                            order2 -> match_PoweriteHelper.background = getDrawable(R.drawable.rounded_button_square)
                            order3 -> match_PoweriteBoss.background = getDrawable(R.drawable.rounded_button_square)
                        }
                    }
                    if (group_type.toString() == match_score_group_type){
                        when (match_score_order_number.toInt()){
                            order1 -> match_PoweriteAnalyst.background = getDrawable(R.drawable.rounded_button_beige)
                            order2 -> match_PoweriteHelper.background = getDrawable(R.drawable.rounded_button_beige)
                            order3 -> match_PoweriteBoss.background = getDrawable(R.drawable.rounded_button_beige)
                        }
                    }
                }
                match_PoweriteAnalyst.text = button1
                match_PoweriteHelper.text = button2
                match_PoweriteBoss.text = button3

            }else if (group_type == GROUPIE){

                if ((user_score_group_type == match_score_group_type)&&
                    (group_type.toString() == user_score_group_type) &&
                    (group_type.toString() == match_score_group_type)&&
                    (user_score_order_number.toInt()==match_score_order_number.toInt())) {
                    when (user_score_order_number.toInt()) {
                        order1 -> match_GroupiePeacemaker.background =
                            getDrawable(R.drawable.round_green_button)
                        order2 -> match_GroupieLoyalSkeptie.background =
                            getDrawable(R.drawable.round_green_button)
                        order3 -> match_GroupieAchiever.background =
                            getDrawable(R.drawable.round_green_button)
                    }

                }else{
                    if (group_type.toString() == user_score_group_type) {
                        when (user_score_order_number.toInt()) {
                            order1 -> match_GroupiePeacemaker.background = getDrawable(R.drawable.rounded_button_square)
                            order2 -> match_GroupieLoyalSkeptie.background = getDrawable(R.drawable.rounded_button_square)
                            order3 -> match_GroupieAchiever.background = getDrawable(R.drawable.rounded_button_square)
                        }
                    }
                    if (group_type.toString() == match_score_group_type){
                        when (match_score_order_number.toInt()) {
                            order1 -> match_GroupiePeacemaker.background = getDrawable(R.drawable.rounded_button_beige)
                            order2 -> match_GroupieLoyalSkeptie.background = getDrawable(R.drawable.rounded_button_beige)
                            order3 -> match_GroupieAchiever.background = getDrawable(R.drawable.rounded_button_beige)
                        }
                    }
                }
                match_GroupiePeacemaker.text = button1
                match_GroupieLoyalSkeptie.text = button2
                match_GroupieAchiever.text = button3
            }else{
                Toast.makeText(this, "No proper Group", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
