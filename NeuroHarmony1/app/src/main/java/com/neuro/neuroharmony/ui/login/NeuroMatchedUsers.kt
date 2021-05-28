package com.neuro.neuroharmony.ui.login

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.CommonUtils
import com.neuro.neuroharmony.utils.PrefsHelper
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.neuro.neuroharmony.ui.login.SocialPreferences.SocialPreference
import kotlinx.android.synthetic.main.activity_home_page1.*
import kotlinx.android.synthetic.main.activity_neruo_matched_users.*
import kotlinx.android.synthetic.main.matched_users_recycler_view.*
import org.json.JSONObject

class NeuroMatchedUsers : BaseActivity() {

    private lateinit var viewModelNM: InitailNeuroMatchViewModel
    var gson=Gson()
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_neruo_matched_users)

        viewModelNM = ViewModelProviders.of(this)[InitailNeuroMatchViewModel::class.java]
        setupObservers2()
        val intent = intent
        val jsonArray = PrefsHelper().getPref<String>("jsonArray")


        val viewPager: ViewPager = findViewById(R.id.viewPager)
        val adapter = ViewPagerAdapter(supportFragmentManager, jsonArray)
        viewPager.adapter = adapter
        PrefsHelper().savePref("group","Group_1")
        PrefsHelper().savePref("group_match","Group 1")
        PrefsHelper().savePref("filtered",0)

        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                when(position) {
                    0-> PrefsHelper().savePref("group","Group_1")
                    1-> PrefsHelper().savePref("group","Group_2")
                    2-> PrefsHelper().savePref("group","Group_3")
                    3-> PrefsHelper().savePref("group","Group_4")
                    4-> PrefsHelper().savePref("group","Group_5")
                    5-> PrefsHelper().savePref("group","Group_6")
                    6-> PrefsHelper().savePref("group","Group_7")
                    7-> PrefsHelper().savePref("group","Group_8")
                    8-> PrefsHelper().savePref("group","Group_9")
                    9-> PrefsHelper().savePref("group","Group_10")

                    else->PrefsHelper().savePref("group","Group_1")
                }
                when(position) {
                    0-> PrefsHelper().savePref("group_match","Group 1")
                    1-> PrefsHelper().savePref("group_match","Group 2")
                    2-> PrefsHelper().savePref("group_match","Group 3")
                    3-> PrefsHelper().savePref("group_match","Group 4")
                    4-> PrefsHelper().savePref("group_match","Group 5")
                    5-> PrefsHelper().savePref("group_match","Group 6")
                    6-> PrefsHelper().savePref("group_match","Group 7")
                    7-> PrefsHelper().savePref("group_match","Group 8")
                    8-> PrefsHelper().savePref("group_match","Group 9")
                    9-> PrefsHelper().savePref("group_match","Group 10")

                    else->PrefsHelper().savePref("group_match","Group 1")
                }
                PrefsHelper().savePref("filtered",0)
                adapter.notifyDataSetChanged()
            }

        })


        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)

        PrefsHelper().savePref("matched_user_status", true)


        back_groups.setOnClickListener {
            onBackPressed()
        }

        update_social_preferences.setOnClickListener {
            val intent = Intent(this, SocialPreference::class.java)
            intent.putExtra("get_back", 1)
            startActivity(intent)
        }
    }


    private fun setupObservers2() {
        viewModelNM.loginResponseLiveData.observe(this, Observer {

            if(it!=null){
                if( it.message=="Success") {

                    val groups_size = it.data.groupSize
                    val jsonArray = gson.toJson(groups_size)
                    val total_match = it.data.totalMatchedUserCount
                    val intent = Intent(this, MatchedResult::class.java)
                    intent.putExtra("jsonArray", jsonArray)
                    intent.putExtra("totalMatched", total_match)
                    intent.putExtra("social_filter_active", it.data.social_filter_active)
                    startActivity(intent)
                    // Success login.  Add the success scenario here ex: Move to next screen
                } else {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
        //observe API call status
        viewModelNM.loginAPICallStatus.observe(this, Observer {
            processStatusShowNeuroMatch(it)
        })

    }

    private fun processStatusShowNeuroMatch(resource: ResourceStatus?) {

        when (resource?.status) {
            StatusType.SUCCESS -> {
                dismissDialog()


            }
            StatusType.EMPTY_RESPONSE -> {
                dismissDialog()

            }
            StatusType.PROGRESSING -> {
                showDialog()

            }
            StatusType.SWIPE_RELOADING -> {

            }
            StatusType.ERROR -> {
                CommonUtils().showSnackbar(show_neuro_match.rootView, "Please Try again")
                dismissDialog()


            }
            StatusType.LOADING_MORE -> {
                CommonUtils().showSnackbar(back_groups.rootView, "Loading more..")
            }
            StatusType.NO_NETWORK -> {
                Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
            }
            StatusType.SESSION_EXPIRED -> {
                CommonUtils().showSnackbar(back_groups.rootView, "session expired")
            }
        }
    }

    class processStatus(res: ResourceStatus) : BaseActivity() {

        val resource = res
        override fun onStart() {
            super.onStart()
            when (resource.status) {
                StatusType.SUCCESS -> {
                    dismissDialog()


                }
                StatusType.EMPTY_RESPONSE -> {
                    dismissDialog()

                }
                StatusType.PROGRESSING -> {
                    showDialog()

                }
                StatusType.SWIPE_RELOADING -> {


                }
                StatusType.ERROR -> {
                    CommonUtils().showSnackbar(
                        express_interest_button.rootView,
                        "Please try again"
                    )
                    dismissDialog()


                }
                StatusType.LOADING_MORE -> {
                    // CommonUtils().showSnackbar(binding.root, "Loading more..")
                }
                StatusType.NO_NETWORK -> {
                    Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
                }
                StatusType.SESSION_EXPIRED -> {
                    //CommonUtils().showSnackbar(login_button_login.rootView,"session expired")
                }
            }
        }
    }


    class processStatus1(res: ResourceStatus) : BaseActivity() {

        val resource = res
        override fun onStart() {
            super.onStart()
            when (resource.status) {
                StatusType.SUCCESS -> {
                    dismissDialog()


                }
                StatusType.EMPTY_RESPONSE -> {
                    dismissDialog()

                }
                StatusType.PROGRESSING -> {
                    showDialog()

                }
                StatusType.SWIPE_RELOADING -> {


                }
                StatusType.ERROR -> {
                    CommonUtils().showSnackbar(
                        revoke_interest_button.rootView,
                        "Please try again"
                    )
                    dismissDialog()


                }
                StatusType.LOADING_MORE -> {
                    // CommonUtils().showSnackbar(binding.root, "Loading more..")
                }
                StatusType.NO_NETWORK -> {
                    Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
                }
                StatusType.SESSION_EXPIRED -> {
                    //CommonUtils().showSnackbar(revoke_button_login.rootView,"session expired")
                }
            }
        }
    }


    fun processStatus2(res: ResourceStatus){
            when (res.status) {
                StatusType.SUCCESS -> {
                    dismissDialog()


                }
                StatusType.EMPTY_RESPONSE -> {
                    dismissDialog()

                }
                StatusType.PROGRESSING -> {
                    showDialog()

                }
                StatusType.SWIPE_RELOADING -> {


                }
                StatusType.ERROR -> {
                   Toast.makeText(this,
                        "Please try again", Toast.LENGTH_SHORT
                    ).show()
                    dismissDialog()


                }
                StatusType.LOADING_MORE -> {
                    Toast.makeText(this,
                        "Loading more..", Toast.LENGTH_SHORT
                    ).show()
                }
                StatusType.NO_NETWORK -> {
                    Toast.makeText(this,
                        "Please check internet connection", Toast.LENGTH_SHORT
                    ).show()
                }
                StatusType.SESSION_EXPIRED -> {
                    Toast.makeText(this,
                        "session expired", Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


    override fun onBackPressed() {
        viewModelNM.getinitailmatchvm()
    }

    data class Userdetails(
        val name: String,
        val details: MutableList<String>,
        val direction: Int,
        var interestStatus: Int,
        var tracker: MutableList<Int>
    )

    enum class Status(val status: Any?){
        Single(1),
        Widower(2),
        Divorcee(3),
        NA(null)
    }

    enum class Gender(val status: Int?){
        Male(1),
        Female(2),
        NA(null)
    }

    enum class Month(val number: Int?){
        Jan(0),
        Feb(1),
        Mar(2),
        Apr(3),
        May(4),
        Jun(5),
        Jul(6),
        Aug(7),
        Sep(8),
        Oct(9),
        Nov(10),
        Dec(11)
    }



}

enum class Notification(val type: String){
    unspecified("unspecified"),
    Chat_Requests("interest accepted")
}