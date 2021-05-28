package com.neuro.neuroharmony.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.neuro.neuroharmony.R
import com.neuro.neuroharmony.utils.CommonUtils
import com.google.android.material.tabs.TabLayout
import com.neuro.neuroharmony.ui.login.PushNotification.PushNotifications
import kotlinx.android.synthetic.main.activity_chat_menu.*
import kotlinx.android.synthetic.main.chat_recycler_view.*
import kotlinx.android.synthetic.main.chat_recycler_view_available.*
import kotlinx.android.synthetic.main.chat_recycler_view_requests.*

class ChatMenu : BaseActivity() {

    var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_menu)


        val intent1 = this.getIntent();
        if (intent1 != null && intent1.getExtras() != null) {
            //val jobID = this.getIntent().getExtras()!!.getInt("JOBID");
            position = intent1.getIntExtra("position", 0)
        };

        val viewPager: ViewPager = findViewById(R.id.chat_viewPager)
        val adapter = ChatViewPageAdapter(supportFragmentManager)
        viewPager.adapter = adapter
        viewPager.setCurrentItem(position)


        val tabLayout = findViewById<TabLayout>(R.id.chat_tabs)
        tabLayout.setupWithViewPager(viewPager)

        back_chat_menu.setOnClickListener {
            onBackPressed();
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
                        chat_suspend_button_chat_window.rootView,
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
                    //CommonUtils().showSnackbar(chat_suspend_button.rootView,"session expired")
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
                        chat_start_button_available.rootView,
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
                    //CommonUtils().showSnackbar(chat_start_button_available.rootView,"session expired")
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
                    Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show()
                }
                StatusType.LOADING_MORE -> {
                    // CommonUtils().showSnackbar(binding.root, "Loading more..")
                }
                StatusType.NO_NETWORK -> {
                    Toast.makeText(this, "Please check internet connection", Toast.LENGTH_SHORT).show()
                }
                StatusType.SESSION_EXPIRED -> {
                    //CommonUtils().showSnackbar(chat_accept_interest_button_requests.rootView,"session expired")
                }
            }
        }
    }


    override fun onBackPressed() {
        val intent1 = this.getIntent();
        if (intent1 != null && intent1.getExtras() != null) {
            val intent = Intent(this, PushNotifications::class.java)
            startActivity(intent)
        }else {
            val intent = Intent(this, HomePage1::class.java)
            startActivity(intent)
        }
    }


    data class Userdetails(val specs: MutableList<Any>)
}
