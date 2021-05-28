package com.neuro.neuroharmony.ui.login

import android.app.Dialog
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.neuro.neuroharmony.utils.CommonUtils

open class BaseActivity: AppCompatActivity() {

    var dialog: Dialog? = null


    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    /**
     * Creating progress dialog
     */
    fun showDialog() {
        this@BaseActivity.runOnUiThread {
             dialog = CommonUtils().showDialog(this@BaseActivity)
        }

    }

    open fun dismissDialog() {
        dialog?.let {
            it.dismiss()
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
//                    v.isCursorVisible = false
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                } else {
//                    v.isCursorVisible = true
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }


    fun Context.toast(context: Context = applicationContext, message: String, toastDuration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, toastDuration).show()
    }

    fun Context.stringResToast(context: Context = applicationContext, message: Int, toastDuration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, context.getString(message), toastDuration).show()
    }

    fun Context.logE(msg:String){
        //Log.e(getString(R.string.app_name),"$msg")
    }

    open fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}
    open fun onNothingSelected(parent: AdapterView<*>?) {}
}
