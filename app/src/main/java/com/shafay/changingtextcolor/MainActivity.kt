package com.shafay.changingtextcolor

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.shafay.changingtextcolor.data.remote.ApiClient
import retrofit2.Callback
import com.shafay.changingtextcolor.data.remote.RetrofitClient
import com.shafay.changingtextcolor.databinding.ActivityMainBinding
import com.shafay.changingtextcolor.model.Colors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        CoroutineScope(IO).launch {
            fetchAndChangeColor()
        }

    }


    private fun fetchAndChangeColor(){
        val call = ApiClient.apiService.getPostById()

        call.enqueue(object : Callback<Colors> {
            override fun onResponse(call: Call<Colors>, response: Response<Colors>) {
                if (response.isSuccessful) {
                    val post = response.body()
                    Log.d("TAG", "onResponseSuccess: $post")

                    post?.let {

                        CoroutineScope(Main).launch{
                            binding.tv.setTextColor(Color.parseColor(it.hex))
                        }

                    }

                    fetchAndChangeColor()

                } else {
                    // Handle error
                    Log.d("TAG", "onResponseFailure")

                    fetchAndChangeColor()

                }
            }

            override fun onFailure(call: Call<Colors>, t: Throwable) {
                // Handle failure
                Log.d("TAG", "onFailure: ${t.stackTrace}")

                fetchAndChangeColor()

            }
        })

    }

}