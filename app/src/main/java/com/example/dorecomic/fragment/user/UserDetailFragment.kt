package com.example.dorecomic.fragment.user

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.dorecomic.R
import com.example.dorecomic.model.User

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var rootView:View
    private lateinit var txtName: TextView
    private lateinit var txtId: TextView

    private lateinit var user: User

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
        rootView = inflater.inflate(R.layout.fragment_user_detail, container, false)

        initData()
        initView()
        initAction()

        return rootView
    }

    private fun initData(){
        user = User("Guest", "0000000")
        user = arguments?.getSerializable("user") as User
    }

    private fun initView(){
        txtId = rootView.findViewById(R.id.user_id_txt)
        txtName = rootView.findViewById(R.id.user_name_txt)
    }



    @SuppressLint("SetTextI18n")
    private fun initAction(){
        txtId.text = "ID: ${user.id}"

        txtName.text = user.name
    }

    private fun initFragment(){

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}