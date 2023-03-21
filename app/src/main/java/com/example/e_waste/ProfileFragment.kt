package com.example.e_waste

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.e_waste.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private lateinit var auth : FirebaseAuth

    private lateinit var database : FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_profile, container, false)

        val email = view.findViewById<TextView>(R.id.profile_email)
        val editProfileButton : Button = view.findViewById(R.id.edit_profile_button)
        val image = view.findViewById<ImageView>(R.id.profile_image1)
        val name = view.findViewById<TextView>(R.id.profile_name)
        val personalDetails = view.findViewById<TextView>(R.id.profile_info)

        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance()


        database!!.reference.child("Users").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshot1 in snapshot.children) {
                    val user = snapshot1.getValue(UserModel::class.java)
                    if (user!!.uid == FirebaseAuth.getInstance().uid) {
                        Picasso.get().load(user.image_url).into(image)
                        email.text = auth.currentUser?.email
                        name.text = user.name
                        personalDetails.text = "Age : " + user.age.toString() + "\n" + "Phone No. : "+user.address
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        editProfileButton.setOnClickListener {
            val intent = Intent(activity, HomeActivity2::class.java)
            intent.putExtra("change" , "Yes")

            activity?.startActivity(intent)
        }

        val sign_out_button = view.findViewById<Button>(R.id.sign_out_btn)

        sign_out_button.setOnClickListener {
            auth.signOut()

            val intent = Intent(activity, MainActivity::class.java)
            activity?.startActivity(intent)

            activity?.finish()

//            activity?.let{
//                val intent = Intent (it, MainActivity::class.java)
//                it.startActivity(intent)
//            }

//            val intent : Intent = view.Intent(this , MainActivity::class.java)
//            startActivity(intent)
//            finish()
        }


        return view
    }
}