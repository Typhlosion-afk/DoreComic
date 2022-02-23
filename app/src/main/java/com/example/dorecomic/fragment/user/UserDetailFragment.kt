package com.example.dorecomic.fragment.user

import android.annotation.SuppressLint
import android.media.Image
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.dorecomic.R
import com.example.dorecomic.model.User
import com.facebook.AccessToken
import com.facebook.GraphRequest
import java.net.URI
import java.net.URL
import com.facebook.GraphResponse
import com.facebook.Profile


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
    private lateinit var imgAvatar: ImageView

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

        return rootView
    }

    private fun initData(){
        user = arguments?.getSerializable("user") as User
    }


    private fun getFBInfo(){
        if(AccessToken.getCurrentAccessToken() != null){
            val rq = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken()) { `object`, response ->
                if (`object` != null) {
                    user = User(`object`.opt("name")?.toString(), `object`.opt("id")?.toString())
                    Log.d("TAG", "getFBInfo: ${`object`.opt("id")}")
                    val profile = Profile.getCurrentProfile()
                    Log.d("TAG", "getFBInfo: ${profile.getProfilePictureUri(200,200)}")
                    Glide.with(rootView.context)
                        .load(profile.getProfilePictureUri(100,100).toString())
                        .fitCenter()
                        .into(imgAvatar)
                    txtName.text = profile.name
                }
            }
            val bundle = Bundle()
            bundle.putString("field", "id,name,link")
            rq.parameters = bundle
            rq.executeAsync()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView(){
        txtId = rootView.findViewById(R.id.user_id_txt)
        txtName = rootView.findViewById(R.id.user_name_txt)
        imgAvatar = rootView.findViewById(R.id.user_avatar)
        txtId.text = "ID: ${user.id}"
        txtName.text = user.name

        getFBInfo()

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