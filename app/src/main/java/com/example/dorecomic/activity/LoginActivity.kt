package com.example.dorecomic.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.dorecomic.R
import com.example.dorecomic.model.User
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    private lateinit var btnLogin: LoginButton
    private lateinit var callBackManager: CallbackManager
    private lateinit var user:User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//
//        val loginFragment = LoginFragment()

//        supportFragmentManager
//            .beginTransaction()
//            .add(R.id.login_container, loginFragment, null)
//            .commit()
        iniView()
        initAction()
    }

    private fun iniView() {
        btnLogin = findViewById(R.id.facebook_btn_login)
    }

    override fun onBackPressed() {
        setResult(0)
        super.onBackPressed()
    }


    private fun initAction(){
        btnLogin.setReadPermissions(listOf("public_profile", "email"))
        btnLogin.setOnClickListener{
            callBackManager = CallbackManager.Factory.create()
//            LoginManager
//                .getInstance()
//                .logInWithReadPermissions(this@LoginActivity, listOf("public_profile", "email"))

            LoginManager
                .getInstance()
                .registerCallback(callBackManager,
                    object : FacebookCallback<LoginResult> {
                        override fun onSuccess(result: LoginResult?) {
                            Log.d("TAG", "onSuccess: ${result?.accessToken?.token}")
                            getFBInfo()
//                            val i = Intent(this@LoginActivity, MainActivity::class.java)
//                            val user = User("Đào Hải Đô", "801998")
//                            i.putExtra("user", user)
//                            this@LoginActivity.setResult(1, i)
//                            this@LoginActivity.finish()
                        }

                        override fun onCancel() {
                            Toast
                                .makeText(this@LoginActivity, "Bạn đã từ chối đăng nhập", Toast.LENGTH_LONG)
                                .show()
                        }

                        override fun onError(error: FacebookException?) {
                            error?.printStackTrace()
                        }

                    }
                )
        }
    }

    private fun getFBInfo(){
        if(AccessToken.getCurrentAccessToken() != null){
            val rq = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken()) { `object`, response ->
                if (`object` != null) {
                    Log.d("TAG", "onCompleted: ${`object`.opt("name")}")

                    user = User(`object`.opt("name")?.toString(), `object`.opt("id")?.toString())
                }
            }
            val bundle = Bundle()
            bundle.putString("field", "id,name,link")
            rq.parameters = bundle
            rq.executeAsync()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callBackManager.onActivityResult(requestCode, resultCode, data)
    }


}