package com.example.retrofitwithcoroutine_u_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.retrofitwithcoroutine_u_test.databinding.ActivityMainBinding
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var retrofitService : AlbumService
    private lateinit var binder : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // make RetrofitService instance
        // get a JSON data with BASE_URL, End point and GSON Converter
        retrofitService = RetrofitInstance.getRetrofitInstance().create(AlbumService::class.java)

//        // get data with @Path = find data with Album's Id
//        val pathResponse : LiveData<Response<AlbumsItem>> = liveData {
//            val response = retrofitService.getAlbum(3)
//            emit(response)
//        }
//        pathResponse.observe(this, Observer {
//            val title = it.body()?.title
//            Toast.makeText(this, "title from path: $title", Toast.LENGTH_LONG).show()
//        })
        getRequestWithQueryParameters()
    }

    private fun getRequestWithQueryParameters(){
        // get data from album = find data with Album's userId
        val responseLiveData = liveData {
            val response = retrofitService.getSortedAlbums(3)
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
                    val result = " Album userId: ${albumItem.userId} / Album title: ${albumItem.title}\n\n"
                    binder.textView.append(result)
                }
            }
        })
    }
}