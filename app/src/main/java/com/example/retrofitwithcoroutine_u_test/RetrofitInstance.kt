package com.example.retrofitwithcoroutine_u_test

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    // RetrofitInstance는 한 번만 생성하면된다
    companion object{
        // HttpLoggingInterceptor 설정
        // HttpLoggingInterceptor를 사용할 경우 전체 JSON 데이터뿐 아니라 통신 연결 결과에 따른 Response 결과도 볼 수 있다
        val interceptor = HttpLoggingInterceptor().apply {
            // BASIC: Logs request and response lines.
            // BODY: Logs request and response lines and their respective headers and bodies (if present).
            // HEADERS: Logs request and response lines and their respective headers.
            // NONE: No logs.
            this.level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }.build()

        val BASE_URL = "https://jsonplaceholder.typicode.com/"
        // 이 부분은 보일러 플레이트임 = 그냥 복사해서 다른 Retrofit 쓰는 플젝에 사용가능
        // 역직렬화를 통해 JSON 데이터를 Kotlin이 읽을 수 있게 한다
        fun getRetrofitInstance(): Retrofit {
            // add HttpLoggingInterceptor client object
            return Retrofit.Builder().baseUrl(BASE_URL).client(client).
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).
            build()
        }
    }
}