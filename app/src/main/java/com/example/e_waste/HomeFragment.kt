package com.example.e_waste

//import com.example.e_waste.databinding.ActivityHome2Binding
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_waste.model.UserModel
import com.example.e_waste.scrap_rate_image.ScrapRateImageRecycler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {

    private lateinit var auth : FirebaseAuth
    private var database : FirebaseDatabase? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance()

        val user_text_view = view.findViewById<TextView>(R.id.user_text_view)

        val youtubeButton : ImageView = view.findViewById(R.id.youtube_info_image)

        val scrapRate : ImageView = view.findViewById(R.id.splash_imageview)

        scrapRate.setOnClickListener {
//            val intent = Intent(activity, scrap_rate_recycler::class.java)
            val intent = Intent(activity, ScrapRateImageRecycler::class.java)

            activity?.startActivity(intent)
        }

        youtubeButton.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://youtu.be/HgEo7YnvJs0")
                )
            )
        }

        database!!.reference.child("Users").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshot1 in snapshot.children){
                    val user = snapshot1.getValue(UserModel::class.java)
                    if (user!!.uid == FirebaseAuth.getInstance().uid){
                        user_text_view.text = user!!.name
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
        return view
    }
}