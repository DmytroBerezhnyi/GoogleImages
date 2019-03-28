package com.example.imageonline

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.example.imageonline.POJO.Item
import com.squareup.picasso.Picasso


class CustomAdapter(context: Context, private val items: List<List<Item>>) :
    ArrayAdapter<List<Item>>(context, R.layout.row_item_pictures, items) {

    override fun getCount() = items.size
    override fun getItem(position: Int) = items[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)
        val viewHolder : ViewHolder
        lateinit var result : View

        if(convertView == null) {
                val inflater = LayoutInflater.from(context)
                result = inflater.inflate(R.layout.row_item_pictures, parent, false)
                viewHolder = ViewHolder(
                    result.findViewById(R.id.imageView1),
                    result.findViewById(R.id.imageView2),
                    result.findViewById(R.id.imageView3))

                result.tag = viewHolder

            } else {
            viewHolder = convertView.tag as ViewHolder
            result = convertView
        }

        try {
            Picasso.with(context)
                .load(item[0].link)
                .into(viewHolder.iv1)

            Picasso.with(context)
                .load(item[1].link)
                .into(viewHolder.iv2)

            Picasso.with(context)
                .load(item[2].link)
                .into(viewHolder.iv3)
        } catch (e : Exception) {
            Log.e("DEBUG", e.message)
        }

            return result
        }

    }

    data class ViewHolder(val iv1 : ImageView, val iv2 : ImageView, val iv3 : ImageView)
