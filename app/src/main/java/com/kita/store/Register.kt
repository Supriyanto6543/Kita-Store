package com.kita.store

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_register.*

class Register : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var registerUrl:String = "http://192.168.43.183/store/register.php"

        register.setOnClickListener {
            if (username.text.toString().isEmpty() || email.text.toString().isEmpty() || alamat.text.toString().isEmpty() || password.text.toString().isEmpty()){
                Toast.makeText(applicationContext, "Lengkapi data terlebih dahulu", Toast.LENGTH_LONG).show()
            }else{
                var request: RequestQueue = Volley.newRequestQueue(applicationContext)
                var strRequest = StringRequest(Request.Method.GET,registerUrl+"?user="+username.text.toString()+"&email="+email.text.toString()+
                "&phone="+alamat.text.toString()+"&password="+password.text.toString(), Response.Listener { response ->

                    if (response.equals("1")){
                        var i = Intent(this,Login::class.java)
                        startActivity(i)
                    }else{
                        Toast.makeText(applicationContext, "Sudah di gunakan", Toast.LENGTH_LONG).show();
                    }

                }, Response.ErrorListener { error ->
                    Log.d("ErrorApps", error.toString())
                })
                request.add(strRequest)
            }
        }

    }

}