package com.kita.store

import android.app.Activity
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
import com.paypal.android.sdk.payments.PayPalConfiguration
import com.paypal.android.sdk.payments.PayPalPayment
import com.paypal.android.sdk.payments.PayPalService
import com.paypal.android.sdk.payments.PaymentActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pesan.*
import kotlinx.android.synthetic.main.activity_register.*
import java.math.BigDecimal

class ActivityPesan : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesan)
        name.text = GlobalData.names
        harga.text = "$ " + GlobalData.hargas.toString()
        deskripsi.text = GlobalData.deskripsis
        Picasso.get().load(GlobalData.photos).into(image)

        var pc:PayPalConfiguration = PayPalConfiguration().environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).clientId(GlobalData.client_id)

        var i = Intent(this, PayPalService::class.java)
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, pc)
        startService(i)

        pesan.setOnClickListener {
            var hargaproduct = GlobalData.hargas
            var edittextHarga = jumlah.text.toString()
            var catatan = catatan.text.toString()
            GlobalData.jumlah = edittextHarga.toInt()
            GlobalData.catatan = catatan.toString()
            var convertharga = edittextHarga.toInt()
            var kalikan = convertharga * hargaproduct.toInt()
            prosesPembayaran(kalikan.toString(), pc)

            Log.d("tampilkan", "${kalikan.toInt()}")
            Log.d("tampilkan", kalikan.toString())
        }
    }

    fun kirimData(str:String, jml:Int, catatan:String, namas_products:String){

        var registerUrl:String = "http://192.168.43.210/store/historyorder.php"

        var request: RequestQueue = Volley.newRequestQueue(applicationContext)
        var strRequest = StringRequest(Request.Method.GET,registerUrl+"?id_user="+str.toString()+"&jumlah="+jml.toInt()+
                "&catatan="+catatan.toString()+"&nama_product="+namas_products.toString(), Response.Listener { response ->

            if (response.equals("1")){
                var i = Intent(this,MainActivity::class.java)
                startActivity(i)
            }else{
                Toast.makeText(applicationContext, "Ada yang salah, ulangi lagi", Toast.LENGTH_LONG).show();
            }

        }, Response.ErrorListener { error ->
            Log.d("ErrorApps", error.toString())
        })

        request.add(strRequest)
    }

    fun prosesPembayaran(str:String, pcc:PayPalConfiguration) {
        var ppy: PayPalPayment = PayPalPayment(BigDecimal.valueOf(str.toString().toDouble()), "USD", "Kita Store", PayPalPayment.PAYMENT_INTENT_SALE)
        var i = Intent(this, PaymentActivity::class.java)
        i.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, pcc)
        i.putExtra(PaymentActivity.EXTRA_PAYMENT, ppy)
        startActivityForResult(i, 123)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123 && resultCode == Activity.RESULT_OK){
            Toast.makeText(applicationContext, "Transaksi berhasil, Terimakasih", Toast.LENGTH_LONG).show()
            kirimData(GlobalData.email, GlobalData.jumlah, GlobalData.catatan, GlobalData.names)
        }else{
            Toast.makeText(applicationContext, "Transaksi gagal", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        stopService(Intent(this, PayPalService::class.java))
        super.onDestroy()
    }
}