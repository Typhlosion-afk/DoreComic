package com.example.dorecomic.fragment.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dorecomic.R
import com.example.dorecomic.adapter.FileDialogAdapter
import com.example.dorecomic.minterface.OnResultDialog
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class RootFileChooserFragment : DialogFragment() {

    private lateinit var rootView: View
    private lateinit var currentPath: String

    private lateinit var btnBack: Button
    private lateinit var btnChoose: Button
    private lateinit var btnOpen: Button

    private lateinit var txtPath: TextView
    private var listFile = ArrayList<File>()
    private lateinit var curFile : File
    private lateinit var fileDialogAdapter: FileDialogAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var onResultDialog: OnResultDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        rootView = inflater.inflate(R.layout.fragment_filedialog, container, false)
        currentPath = "/storage"

        initView()
        initAdapter()
        initAction()

        return rootView
    }

    private fun initAction() {
        btnBack.setOnClickListener {
            if(currentPath != "/storage") {
                currentPath = curFile.parentFile?.absolutePath.toString()
                updateAdapter()
            }
        }

        btnOpen.setOnClickListener {
            currentPath = fileDialogAdapter.getSelectedPath()
            updateAdapter()
        }

        btnChoose.setOnClickListener {
            val rootPath = fileDialogAdapter.getSelectedPath()
            val sharedPrefs : SharedPreferences = activity?.getPreferences(AppCompatActivity.MODE_PRIVATE)
                ?: return@setOnClickListener

            with(sharedPrefs.edit()){
                putString("root_file", rootPath)
                apply()
            }

            setFragmentResult("result", bundleOf("path" to rootPath))
            this@RootFileChooserFragment.dismiss()
        }
    }

    private fun initView(){
        btnChoose = rootView.findViewById<Button>(R.id.btn_chosefile)
        btnOpen = rootView.findViewById<Button>(R.id.btn_openfile)
        btnBack = rootView.findViewById<Button>(R.id.btn_back)
        txtPath = rootView.findViewById<TextView>(R.id.txt_current_path)


    }

    private fun initAdapter(){
        curFile = File(currentPath)
        listFile = ArrayList<File>()
        listFile.addAll(curFile.listFiles() ?: return)
        fileDialogAdapter = FileDialogAdapter(rootView.context, listFile)

        recyclerView = rootView.findViewById<RecyclerView>(R.id.list_file_content)
        LinearLayoutManager(rootView.context).apply {
            recyclerView.layoutManager = this
        }
        recyclerView.adapter = fileDialogAdapter
    }

    // update list file
    @SuppressLint("NotifyDataSetChanged")
    private fun updateAdapter(){
        listFile.clear()
        curFile = File(currentPath)
        listFile.addAll(curFile.listFiles()?:return)
        Log.d("size", "updateAdapter: ${listFile.size}")
        Log.d("path", "updateAdapter: $currentPath")
        Log.d("size", "updateAdapter: ${listFile.size}")
        fileDialogAdapter.update(listFile)
    }

    override fun onResume() {
        //set size dialog
        val percentage = 90
        val percent = percentage.toFloat() / 100
        val dm = Resources.getSystem().displayMetrics
        val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
        val percentWidth = rect.width() * percent
        val percentHeight = rect.height() * percent
        dialog?.window?.setLayout(percentWidth.toInt(), percentHeight.toInt())
        super.onResume()
    }

}