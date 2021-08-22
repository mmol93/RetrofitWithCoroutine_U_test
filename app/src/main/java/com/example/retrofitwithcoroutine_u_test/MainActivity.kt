package com.example.retrofitwithcoroutine_u_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.retrofitwithcoroutine_u_test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binder : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // make RetrofitService instance
        // get a JSON data with BASE_URL, End point and GSON Converter
        val retrofitService = RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)

        // get data from album
        val responseLiveData = liveData {
            val response = retrofitService.getAlbums()
            emit(response)
        }

        // observe responseLiveData
        responseLiveData.observe(this, Observer {
            // listIterator: 자바 컬렉션(ArrayList, vector 등)에서 항목을 탐색하는데 쓰이는 커서
            val albumList = it.body()?.listIterator()

            binder.textView.text = null

            if (albumList != null){
                while (albumList.hasNext()){
                    // next(): can access, add, remove, modify while list is iterating
                    val albumItem = albumList.next()
                    val result = " Album id: ${albumItem.title}\n"
                    binder.textView.append(result)
                }
            }
        })
    }
}