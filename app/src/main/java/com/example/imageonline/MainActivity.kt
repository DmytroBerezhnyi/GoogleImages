package com.example.imageonline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.example.imageonline.Adapters.APIService
import com.example.imageonline.POJO.Example
import com.example.imageonline.POJO.Item
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private val editText = lazy { findViewById<EditText>(R.id.editText) }
    private val button = lazy { findViewById<Button>(R.id.button) }
    private val listView = lazy { findViewById<ListView>(R.id.list_pictures) }
    private var list = ArrayList<List<Item>>()
    private val listAdapter = lazy { CustomAdapter(this, list) }

    private val baseUrl = "https://www.googleapis.com/"
    private val key = "AIzaSyAeukwlzQm3kJOKFT29jsZbES2cdG62ZLc"
    private val cx = "018013627651465683176:4zpb_umhuas"
    private val searchType = "image"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        listView.value.adapter = listAdapter.value

        button.value.setOnClickListener {
            list.removeAll(list)
            getListItems(editText.value.text.toString());
            Log.e("DEBUG", "Did t!! 1 ${list.size}")
        }
    }

    private fun getListItems(q: String) {
        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(APIService::class.java)
        val call = apiService.getExamples(key, cx, searchType, q)

        call.enqueue(object : Callback<Example> {
            override fun onResponse(call: Call<Example>?, response: Response<Example>?) {
                    if (response!!.isSuccess) {
                        val example = response.body()
                        for(x in 0 until example.items!!.size step 3) {
                            if(x + 2 < example.items!!.size) {
                                list.add(listOf(example.items!![x], example.items!![x + 1], example.items!![x + 2]))
                            } //else {
                                //when(example.items!!.size - x) {
                               //     1 -> {res.add(listOf(example.items!![x]))}
                               //     2 -> {res.add(listOf(example.items!![x], example.items!![x + 1]))}
                               // }
                            //}
                        }
                        listAdapter.value.notifyDataSetChanged()
                        Log.e("DEBUG", "Did t!! 0 ${list.size}")
                    }
            }

            override fun onFailure(call: Call<Example>?, t: Throwable?) {
            }

        })
    }
}
