package com.example.json_ghh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.json_ghh.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityMainBinding
    private var isPause = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofit()
    }

    fun retrofit(){
        // first we get our API Client
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        // here we use the enqueue callback to make sure that we get the data before we update the Recycler View
        // enqueue gives us async functionality like coroutines, later we will replace this with coroutines
        apiInterface?.getCurrencies()?.enqueue(object: Callback<currencies>{
            override fun onResponse(call: Call<currencies>, response: Response<currencies>) {
                // we use a try block to make sure that our app doesn't crash if the data is incomplete
                try{
                    // we will also print the full car information here to mimic the previous lesson
                    println("HERRRE: ${response.body()!!.eur.ada}")

                }catch(e: Exception){
                    Log.d("MAIN", "ISSUE: $e")
                }
            }

            override fun onFailure(call: Call<currencies>, t: Throwable) {
                Log.d("MAIN", "Unable to get data ${t.localizedMessage}")
            }

        })
    }

    }