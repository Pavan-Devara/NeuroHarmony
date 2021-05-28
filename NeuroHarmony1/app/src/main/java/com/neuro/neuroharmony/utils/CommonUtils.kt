package com.neuro.neuroharmony.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import com.neuro.neuroharmony.R
import com.google.android.material.snackbar.Snackbar

class CommonUtils {

    fun showDialog(mContext: Context): Dialog {
        val dialog = Dialog(mContext)
        if (!dialog.isShowing) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)
            dialog.setContentView(R.layout.progress_bar)
            dialog.show()

            val window = dialog.window
            window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        return dialog
    }

    fun showSnackbar(view: View, message: String?) {
        val color: Int
        color = Color.WHITE
        if (message != null) {
            val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            val sbView = snackbar.view
            val textView = sbView.findViewById(R.id.snackbar_text) as TextView
            textView.setTextColor(color)
            snackbar.show()
        }
    }
}