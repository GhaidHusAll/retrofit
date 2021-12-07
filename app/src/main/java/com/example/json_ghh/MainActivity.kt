package com.example.json_ghh

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.json_ghh.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.AdapterView

import android.view.View

import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    private lateinit var  binding : ActivityMainBinding
    private val curArray = arrayListOf("ada", "usd", "aud", "sar", "cny", "jpy")
    lateinit var currencyDetails: Eur
    private lateinit var mySpinner : Spinner
     var result = 0.0
    var selectedCurrency = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        retrofit()
        binding.tvResult.text = result.toString()
        set()
        binding.btnConvert.setOnClickListener {
            println("$selectedCurrency")
            if (binding.etUserInput.text.isEmpty()){
                Toast.makeText(this,"please enter a number",Toast.LENGTH_SHORT).show()
            }else {
                calculate(selectedCurrency)
            }
        }
    }

    private fun retrofit(){
        // first we get our API Client
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        // here we use the enqueue callback to make sure that we get the data before we update the Recycler View
        // enqueue gives us async functionality like coroutines, later we will replace this with coroutines
        apiInterface?.getCurrencies()?.enqueue(object: Callback<Currencies>{
            override fun onResponse(call: Call<Currencies>, response: Response<Currencies>) {
                // we use a try block to make sure that our app doesn't crash if the data is incomplete
                try{
                    // we will also print the full car information here to mimic the previous lesson
                        binding.tvDate.text = response.body()!!.date
                    currencyDetails = response.body()!!.eur

                }catch(e: Exception){
                    Log.d("MAIN", "ISSUE: $e")
                }
            }

            override fun onFailure(call: Call<Currencies>, t: Throwable) {
                Log.d("MAIN", "Unable to get data ${t.localizedMessage}")
            }

        })
    }

    fun set() {
        mySpinner = binding.spinner
        if (mySpinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, curArray
            )
            mySpinner.adapter = adapter

        mySpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View, position: Int, id: Long
            ) {
              selectedCurrency = position
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action

            }
        }
    }
    }
    private fun calc(i: Double?, sel: Double): Double {
        var s = 0.0
        if (i != null) {
            s = (i * sel)
        }
        return s
    }
private fun calculate(position: Int){
    println("hdhdhd $position")
    var sel = binding.etUserInput.text.toString()
    var currency: Double = sel.toDouble()
    when (position) {
        0 -> {
            result = calc(currencyDetails?.ada.toDouble(), currency)
        }
        1 -> {
            result = calc(currencyDetails?.usd.toDouble(), currency)
        }
        2 -> {
            result = calc(currencyDetails?.aud.toDouble(), currency)
        }
        3 -> {
            result = calc(currencyDetails?.sar.toDouble(), currency)
        }
        4 -> {
            result = calc(currencyDetails?.cny.toDouble(), currency)
        }
        5 -> {
            result = calc(currencyDetails?.jpy.toDouble(), currency)
        }
    }
    binding.tvResult.text = "Result: $result"
}
    }





