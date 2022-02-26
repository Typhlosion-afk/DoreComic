package com.example.dorecomic.fragment.dialog

import android.app.Application
import android.app.Dialog
import android.content.res.Resources
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.DialogFragment
import com.example.dorecomic.R

class CloseAppDialogFragment : DialogFragment() {

    private lateinit var rootView: View
    private lateinit var btnClose: Button
    private lateinit var btnCancel: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.dialog_fragment_close_app, container, false)

        initView()
        initAction()

        return rootView
    }

    private fun initView(){
        btnCancel = rootView.findViewById(R.id.btn_cancel)
        btnClose = rootView.findViewById(R.id.btn_close)
    }

    private fun initAction(){
        btnCancel.setOnClickListener {
            this@CloseAppDialogFragment.dismiss()
        }
        btnClose.setOnClickListener {
            activity?.finishAffinity()
        }
    }

    override fun onResume() {
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.width()
        dialog?.window?.setLayout(percentWidth, 1200)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        super.onResume()
    }
}