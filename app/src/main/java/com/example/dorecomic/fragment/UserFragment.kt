package com.example.dorecomic.fragment

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
import com.example.dorecomic.fragment.user.NoneUserFragment
import com.example.dorecomic.fragment.user.UserDetailFragment
import com.example.dorecomic.fragment.user.UserProfileFragment
import com.example.dorecomic.minterface.NoneUserClick
import com.example.dorecomic.model.User

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var rootView: View

    private var isLogin = false

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
        rootView = inflater.inflate(R.layout.fragment_user, container, false)
        initFragment()
        return rootView
    }

    private fun initFragment() {
        val noneUserView = rootView.findViewById<FrameLayout>(R.id.none_user_container)
        val userDetailView = rootView.findViewById<FrameLayout>(R.id.user_detail_container)
        val userProfileView = rootView.findViewById<FrameLayout>(R.id.user_profile_container)


        if (!isLogin) {
            noneUserView.visibility = View.VISIBLE
            userDetailView.visibility = View.GONE
            userProfileView.visibility = View.GONE

            val noneUserFragment = NoneUserFragment()
            childFragmentManager.beginTransaction()
                .add(R.id.none_user_container, noneUserFragment, null)
                .commit()

            noneUserFragment.loginClick(object : NoneUserClick {
                override fun onNoneUserClick(click: Boolean) {
                    if(click){
                        startActivityForResult(Intent(activity , LoginActivity::class.java), 999)
                    }
                }
            })

        } else {
            noneUserView.visibility = View.GONE
            userProfileView.visibility = View.VISIBLE
            userDetailView.visibility = View.VISIBLE

            val userDetailFragment = UserDetailFragment()

            val bundle = Bundle()
            bundle.putSerializable("user", user)
            userDetailFragment.arguments = bundle

            childFragmentManager.beginTransaction()
                .add(R.id.user_detail_container, userDetailFragment, null)
                .commit()

            val userProfileFragment = UserProfileFragment()
            childFragmentManager.beginTransaction()
                .add(R.id.user_profile_container, userProfileFragment, null)
                .commit()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 999 && resultCode == 1){
            isLogin = true
            user = data?.getSerializableExtra("user") as User
            initFragment()
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
         * @return A new instance of fragment UserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}