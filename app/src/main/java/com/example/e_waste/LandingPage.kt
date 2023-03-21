package com.example.e_waste

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.e_waste.databinding.ActivityLandingPageBinding
import com.example.e_waste.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso


class LandingPage : AppCompatActivity() {

    private lateinit var binding:ActivityLandingPageBinding
    private var database : FirebaseDatabase? = null
    lateinit var user_list : ArrayList<UserModel>
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding = ActivityLandingPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()

        user_list = ArrayList()

        database!!.reference.child("Users").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                user_list.clear()
                for (snapshot1 in snapshot.children){
                    val user = snapshot1.getValue(UserModel::class.java)
                    if (user!!.uid == FirebaseAuth.getInstance().uid){
                        user_list.add(user)
                        Picasso.get().load(user_list[0].image_url).into(binding.imageView5)
                        binding.checkTextView.text = user_list[0].name
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        binding.button.setOnClickListener{
            auth.signOut()
            val intent : Intent = Intent(this , MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.button2.setOnClickListener{
            val intent : Intent = Intent(this , Work::class.java)
            startActivity(intent)
        }
    }
}