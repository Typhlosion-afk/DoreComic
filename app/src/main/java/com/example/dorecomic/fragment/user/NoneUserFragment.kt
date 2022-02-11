package com.example.dorecomic.fragment.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.dorecomic.R
import com.example.dorecomic.activity.LoginActivity
import com.example.dorecomic.minterface.NoneUserClick
import com.example.dorecomic.model.User

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NoneUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NoneUserFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var frameClick : FrameLayout

    private lateinit var rootView : View

    private lateinit var user: User

    private lateinit var noneUserClick: NoneUserClick

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_none_user, container, false)
        initView()
        initAction()


        return rootView
    }


    private fun initAction(){
        frameClick.setOnClickListener{
            noneUserClick.onNoneUserClick(true)
        }
    }

    private fun initView(){
        frameClick = rootView.findViewById(R.id.frame_login_click)
    }

    fun loginClick(noneUserClick: NoneUserClick){
        this.noneUserClick = noneUserClick
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 999 && resultCode == 1){
            user = data?.getSerializableExtra("user") as User
        }
        super.onActivityResult(requestCode, resultCode, data)
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NoneUserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NoneUserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}