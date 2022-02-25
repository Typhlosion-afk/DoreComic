package com.example.dorecomic.fragment

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.dorecomic.R
import com.example.dorecomic.model.User
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.facebook.AccessToken


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UserProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var rootView: View
    private lateinit var txtName: TextView
    private lateinit var txtId: TextView
    private lateinit var imgAvatar: ImageView
    private lateinit var iconBtn: Drawable
    private lateinit var btnLogin: LoginButton
    private lateinit var btnMarkLogin: Button

    private lateinit var callBackManager: CallbackManager
    private var isLogin: Boolean = true

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
        rootView = inflater.inflate(R.layout.fragment_user_profile, container, false)
        checkLogin()
        initData()
        initView()
        initAction()

        return rootView
    }

    private fun initData() {
        user = DEFAULT_USER

    }


    private fun getFBInfo() {
        if (AccessToken.getCurrentAccessToken() != null) {
            val rq =
                GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken()) { `object`, response ->
                    if (`object` != null) {
                        val profile = Profile.getCurrentProfile()
                        user = (User(
                            profile.name,
                            profile.id,
                            profile.getProfilePictureUri(100, 100).toString()
                        ))
                        updateViewWithUser()
                    }
                }
            val bundle = Bundle()
            bundle.putString("field", "id,name,link")
            rq.parameters = bundle
            rq.executeAsync()
        }
    }

    private fun initView() {
        txtId = rootView.findViewById(R.id.user_id_txt)
        txtName = rootView.findViewById(R.id.user_name_txt)
        imgAvatar = rootView.findViewById(R.id.user_avatar)

        btnLogin = rootView.findViewById(R.id.facebook_btn_login)
        btnMarkLogin = rootView.findViewById(R.id.btn_mark_login)
        if (isLogin) {
            btnMarkLogin.text = getString(R.string.logout_vie)
        }

        if (isLogin) {
            getFBInfo()
        }else{
            updateViewWithUser()
        }
    }

    private fun initAction() {

        btnMarkLogin = rootView.findViewById(R.id.btn_mark_login)
        btnMarkLogin.setOnClickListener {
            if (!isLogin) {
                loginFacebook()
            } else {
                logoutFacebook()
            }

        }
    }

    private fun logoutFacebook() {
        LoginManager.getInstance().logOut()
        user = User("Guest", "000000")
        updateViewWithUser()
    }

    //update user when login and logout
    private fun updateViewWithUser() {
        checkLogin()
        if (user.avatar != "") {
            Glide.with(rootView.context)
                .load(user.avatar)
                .fitCenter()
                .into(imgAvatar)
        }else{
            imgAvatar.setImageResource(R.drawable.image_default)
        }
        txtName.text = user.name
        if(isLogin){
            btnMarkLogin.text = getString(R.string.logout_vie)
        }else{
            btnMarkLogin.text = getString(R.string.login_vie)
        }
    }

    private fun loginFacebook() {
        Log.d("TAG", "loginFacebook: running")
        callBackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().logInWithReadPermissions(this, listOf("public_profile"));
        LoginManager
            .getInstance()
            .registerCallback(callBackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(result: LoginResult?) {
                        Log.d("TAG", "onSuccess: ${result?.accessToken?.token}")
                        getFBInfo()
                    }

                    override fun onCancel() {
                        Toast
                            .makeText(
                                rootView.context.applicationContext,
                                "Bạn đã từ chối đăng nhập",
                                Toast.LENGTH_LONG
                            )
                            .show()
                    }

                    override fun onError(error: FacebookException?) {
                        error?.printStackTrace()
                    }

                }
            )
        checkLogin()
    }

    private fun checkLogin() {
        val accessToken = AccessToken.getCurrentAccessToken()
        isLogin = accessToken != null && !accessToken.isExpired
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callBackManager.onActivityResult(requestCode, resultCode, data)
    }

    companion object {
        private val DEFAULT_USER = User("Guest", "000000")

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}