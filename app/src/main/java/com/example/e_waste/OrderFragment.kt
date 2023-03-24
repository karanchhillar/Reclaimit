package com.example.e_waste

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_waste.orders.OrdersAdapter



//hlobhai
class OrderFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var checkoutButton: Button
    private lateinit var deleteButton: Button
    private lateinit var orderAdapter: OrdersAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_order, container, false)

//        recyclerView = view.findViewById(R.id.your_order_recycler_view)
        checkoutButton = view.findViewById(R.id.checkout_button)
        // codde here



        // code here
        deleteButton = view.findViewById(R.id.delete_button)
        return view
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

}