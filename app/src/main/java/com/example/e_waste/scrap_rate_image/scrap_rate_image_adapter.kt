package com.example.e_waste.scrap_rate_image

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import com.example.e_waste.LeaderboardItem
import com.example.e_waste.R
import com.example.e_waste.scrapRateAdapter

class scrap_rate_image_adapter(
    private val context: Context,
    private val dataset: List<Affirmation>
) : RecyclerView.Adapter<scrap_rate_image_adapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.item_title)
        val textView1: TextView = view.findViewById(R.id.item_title1)
        val imageTextView : ImageView = view.findViewById(R.id.item_image)
        val imageTextView1 : ImageView = view.findViewById(R.id.item_image1)
        fun bindItems(user: Affirmation) {
            val textView: TextView? = itemView.findViewById(R.id.item_title)
            val textView1: TextView? = itemView.findViewById(R.id.item_title1)
            textView?.text = user.stringResourceId
            textView1?.text = user.stringResourceId1
        }
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.scrap_rate_image_item, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
//        holder.textView.text = context.resources.getString(item.stringResourceId)
        holder.imageTextView.setImageResource(item.imageResourceId)
//        holder.textView1.text = context.resources.getString(item.stringResourceId1)
        holder.imageTextView1.setImageResource(item.imageResourceId1)
        holder.bindItems(item)
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size



}
