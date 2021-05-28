package com.neuro.neuroharmony.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.CommonUtils
import com.google.android.material.tabs.TabLayout
import com.neuro.neuroharmony.ui.login.PushNotification.PushNotifications
import kotlinx.android.synthetic.main.activity_chat__requests.*
import kotlinx.android.synthetic.main.confirmed_requests.*
import kotlinx.android.synthetic.main.received_requests_users.*


class Chat_Requests : BaseActivity() {

    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat__requests)

        val intent1 = this.getIntent();
        if (intent1 != null && intent1.getExtras() != null) {
            //val jobID = this.getIntent().getExtras()!!.getInt("JOBID");
            position = intent1.getIntExtra("position", 0)
        };

        val viewPager: ViewPager = findViewById(R.id.requests_viewPager)
        val adapter = RequestsViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        viewPager.setCurrentItem(position)

        val tabLayout = findViewById<TabLayout>(R.id.confirmation_requests_tabs)
        tabLayout.setupWithViewPager(viewPager)

        back_request_menu.setOnClickListener {
            super.onBackPressed();
        }

        viewPager?.addOnPageChangeListener(object : OnPageChangeListener {

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
                        received_request_accept_button.rootView,
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
                    //CommonUtils().showSnackbar(received_request_accept_button.rootView,"session expired")
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
                        received_request_decline_button.rootView,
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
                    //CommonUtils().showSnackbar(received_request_decline_button.rootView,"session expired")
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
                        request_chat_button.rootView,
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
                    //CommonUtils().showSnackbar(request_chat_button.rootView,"session expired")
                }
            }
        }
    }



    data class Userdetails(
        val specs: MutableList<Any>,
        var trackerCard: MutableList<Int>
    )

}
