package com.example.imageonline

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.example.imageonline.POJO.Item
import com.squareup.picasso.Picasso


class CustomAdapter(context: Context, val items: ArrayList<List<Item>>) :
    ArrayAdapter<List<Item>>(context, R.layout.row_item_pictures, items) {

    var size = 0

    override fun getCount() = items.size
    override fun getItem(position: Int) = items[position]

    init {
        val display = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        size = display.width/3
    }

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
            viewHolder.iv1.setOnClickListener {
                val intent = Intent(context, ViewActivity::class.java)
                intent.putExtra("src", item[0].link)
                context.startActivity(intent)
            }
            viewHolder.iv2.setOnClickListener {
                val intent = Intent(context, ViewActivity::class.java)
                intent.putExtra("src", item[1].link)
                context.startActivity(intent)
            }
            viewHolder.iv3.setOnClickListener {
                val intent = Intent(context, ViewActivity::class.java)
                intent.putExtra("src", item[2].link)
                context.startActivity(intent)
            }

            Picasso.with(context)
                .load(item[0].link)
                .placeholder(R.drawable.ic_launcher_foreground)
                .resize(size, size)
                .centerInside()
                .into(viewHolder.iv1)

            Picasso.with(context)
                .load(item[1].link)
                .placeholder(R.drawable.ic_launcher_foreground)
                .resize(size, size)
                .centerInside()
                .into(viewHolder.iv2)

            Picasso.with(context)
                .load(item[2].link)
                .placeholder(R.drawable.ic_launcher_foreground)
                .resize(size, size)
                .centerInside()
                .into(viewHolder.iv3)

        } catch (e : Exception) {
            Log.e("DEBUG", e.message.toString())
        }
            return result
        }

}

    data class ViewHolder(val iv1 : ImageView, val iv2 : ImageView, val iv3 : ImageView)
