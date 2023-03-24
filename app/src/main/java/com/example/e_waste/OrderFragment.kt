package com.example.e_waste

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_waste.model.CollectionModel
import com.example.e_waste.orders.OrdersAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.security.AuthProvider
import java.text.SimpleDateFormat
import java.util.Calendar

//hlobhai
class OrderFragment : Fragment() {

    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseDatabase
    private lateinit var storageRef: StorageReference
    private lateinit var firebaseFirestore: FirebaseFirestore




    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_order, container, false)

        val checkoutButton = view.findViewById<Button>(R.id.checkout_button)
        val list = view.findViewById<EditText>(R.id.itemlist)
        val address = view.findViewById<EditText>(R.id.sendaddress)
        val pincode = view.findViewById<EditText>(R.id.pincode)
        val collection_date = view.findViewById<TextView>(R.id.dateofc)
        val coll_btn = view.findViewById<Button>(R.id.coll_date_btn)
        auth = FirebaseAuth.getInstance()
        storageRef = FirebaseStorage.getInstance().reference.child("COLLECTION DETAILS")
        firebaseFirestore = FirebaseFirestore.getInstance()


        checkoutButton.setOnClickListener {

            val items = list.text.toString()
            val collec_add = address.text.toString()
            val pin = pincode.text.toString()
            val coll_date = collection_date.text.toString()


            database = FirebaseDatabase.getInstance()

            val detail = CollectionModel(
                items,
                collec_add,
                pin,
                coll_date,
                auth.uid.toString(),
                System.currentTimeMillis().toString()

            )
            database.reference.child("COLLECTION DEATILS").child(System.currentTimeMillis().toString()).setValue(detail)
                .addOnSuccessListener {
                    Toast.makeText(activity, "YOUR COLLECTION IS CONFIRMED \n CONTACT YOU SOON", Toast.LENGTH_LONG).show()







                }
//            homeactivity2170
        }

        val calenderdate = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dateofMonth ->


            calenderdate.set(Calendar.YEAR,year)
            calenderdate.set(Calendar.MONTH,month)
            calenderdate.set(Calendar.DAY_OF_MONTH,dateofMonth)

            updatedatelabel(calenderdate,collection_date)

        }




        coll_btn.setOnClickListener {

            DatePickerDialog(
                requireContext(),
                datePicker,
                calenderdate.get(Calendar.YEAR),
                calenderdate.get(Calendar.MONTH),
                calenderdate.get(Calendar.DAY_OF_MONTH),


                ).show()
//            updatedatelabel(calenderdate,collection_date)

        }
//        updatedatelabel(calenderdate,collection_date)
//        deleteButton = view.findViewById(R.id.delete_button)
        return view
    }






    }

    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    private fun updatedatelabel(calenderdate: Calendar, coll_date : TextView) {

        val day = SimpleDateFormat("dd").format(calenderdate.time)
        val month = SimpleDateFormat("MM").format(calenderdate.time)
        val year = SimpleDateFormat("yyyy").format(calenderdate.time)
        coll_date.text = "${day}/${month}/${year}"


    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Create sample order data
//        val orderItems = listOf(
//            OrderItem(R.drawable.image1, "Product 1", "$9.99", 1),
//            OrderItem(R.drawable.image2, "Product 2", "$14.99", 2),
//            OrderItem(R.drawable.image3, "Product 3", "$19.99", 3),
//            OrderItem(R.drawable.image4, "Product 4", "$24.99", 4)
//        )
//
//        // Set up RecyclerView and adapter
//        orderAdapter = OrdersAdapter(orderItems)
//        recyclerView.apply {
//            layoutManager = LinearLayoutManager(context)
//            adapter = orderAdapter
//            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
//        }

        // Set up checkout button click listener
//        checkoutButton.setOnClickListener {
//            // TODO: Implement checkout functionality
//        }
//    }
//}

