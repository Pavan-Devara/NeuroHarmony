package com.neuro.neuroharmony.ui.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.neuro.neuroharmony.R
import kotlinx.android.synthetic.main.activity_core_belief_view_report.*
import org.json.JSONArray

class CoreBeliefViewReport : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        setContentView(R.layout.activity_core_belief_view_report)

        back_core_match_report3.setOnClickListener {
            super.onBackPressed()
        }

        okay_coreBeliefReport.setOnClickListener {
            val intent1 = Intent(this, HomePage1::class.java)

            startActivity(intent1)
        }

        if (Build.VERSION.SDK_INT >= 19) {
            coreBeliefReport.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            coreBeliefReport.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }



        val intent = intent
        val jsonArray = intent.getStringExtra("jsonArray")
        val userGroupScore = intent.getStringExtra("userGroupScore")
        val userOrderNumber = intent.getStringExtra("userOrderNumber")
        val array = JSONArray(jsonArray)

        for (i in 0 until array.length()){
            val obj = array.getJSONObject(i)
            val group_type = obj.getInt("group_type")
            val subItems = obj.getJSONArray("sub_items")
            if (userGroupScore == group_type.toString()) {
                for (j in 0 until subItems.length()) {
                    val order_number = subItems.getJSONObject(j).getInt("order_number")
                    if (userOrderNumber == order_number.toString()){
                        val mimeType: String = "text/html"
                        val utfType: String = "UTF-8"
                        coreBeliefReport.loadDataWithBaseURL(null,subItems.getJSONObject(j).getString("description"),mimeType,utfType, null)
                        break
                    }
                }
            }
        }
    }

}
