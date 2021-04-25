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
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        register.setOnClickListener {
            var i = Intent(this,Register::class.java)
            startActivity(i)
        }
        var url:String = "http://192.168.43.183/store/login.php"
        login.setOnClickListener {
            var request:RequestQueue = Volley.newRequestQueue(applicationContext)

            var stringRequest = StringRequest(Request.Method.GET, url+"?email="+username.text.toString()+"&password="+password.text.toString(), Response.Listener { response ->
                if (response.equals("0")){
                    var i = Intent(this,MainActivity::class.java)
                    GlobalData.email = username.text.toString()
                    startActivity(i)
                }else{
                    Toast.makeText(applicationContext, "Gagal login", Toast.LENGTH_LONG).show();
                }
            }, Response.ErrorListener { error ->
                Log.d("errorApp", error.toString());
            })
            request.add(stringRequest)
        }
    }
}