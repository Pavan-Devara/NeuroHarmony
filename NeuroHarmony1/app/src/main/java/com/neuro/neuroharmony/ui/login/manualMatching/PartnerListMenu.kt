package com.neuro.neuroharmony.ui.login.manualMatching

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.ui.login.BaseActivity
import com.neuro.neuroharmony.ui.login.ResourceStatus
import com.neuro.neuroharmony.ui.login.StatusType
import com.neuro.neuroharmony.utils.CommonUtils
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_partner_list_menu.*
import kotlinx.android.synthetic.main.manual_matching_confirmed_list.*
import kotlinx.android.synthetic.main.manual_matching_received_list.*
import kotlinx.android.synthetic.main.manual_matching_sent_list.*

class PartnerListMenu : AppCompatActivity() {
    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_partner_list_menu)


        val intent1 = this.getIntent();
        if (intent1 != null && intent1.getExtras() != null) {
            //val jobID = this.getIntent().getExtras()!!.getInt("JOBID");
            position = intent1.getIntExtra("position", 0)
        };
        val viewPager: ViewPager = findViewById(R.id.list_partners_viewPager)
        val adapter = PartnerListViewPageAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        viewPager.setCurrentItem(position)

        val tabLayout = findViewById<TabLayout>(R.id.list_partner_tabs)
        tabLayout.setupWithViewPager(viewPager)

        back_partner_list_menu.setOnClickListener {
            super.onBackPressed();
        }

        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                adapter.notifyDataSetChanged()
            }

        })
    }

    class processStatus(res: ResourceStatus) : BaseActivity() {

        val resource = res
        override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
            super.onCreate(savedInstanceState, persistentState)

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
                        view_manual_match_confirmed_user.rootView,
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
                    //CommonUtils().showSnackbar(view_manual_match_confirmed_user.rootView,"session expired")
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
                        view_manual_match_confirmed_delink_user.rootView,
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
                    //CommonUtils().showSnackbar(view_manual_match_confirmed_delink_user.rootView,"session expired")
                }
            }
        }
    }


    class processStatus2(res: ResourceStatus) : BaseActivity() {

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
                        revoke_manual_match_interest.rootView,
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
                    //CommonUtils().showSnackbar(revoke_manual_match_interest.rootView,"session expired")
                }
            }
        }
    }

    class processStatus3(res: ResourceStatus) : BaseActivity() {

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
                        received_decline_button_manual.rootView,
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
                    //CommonUtils().showSnackbar(received_decline_button_manual.rootView,"session expired")
                }
            }
        }
    }


    class processStatus4(res: ResourceStatus) : BaseActivity() {

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
                        received_accept_button_manual.rootView,
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
                    //CommonUtils().showSnackbar(received_accept_button_manual.rootView,"session expired")
                }
            }
        }
    }





    data class  UserDetails(
        val mobile_number: String,
        val userdetails: MutableMap<String, Any>
    )
}
