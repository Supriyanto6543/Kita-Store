package com.kita.store.payments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kita.store.GlobalData
import com.kita.store.R
import com.midtrans.sdk.corekit.callback.TransactionFinishedCallback
import com.midtrans.sdk.corekit.core.MidtransSDK
import com.midtrans.sdk.corekit.core.TransactionRequest
import com.midtrans.sdk.corekit.core.themes.CustomColorTheme
import com.midtrans.sdk.corekit.models.BillingAddress
import com.midtrans.sdk.corekit.models.CustomerDetails
import com.midtrans.sdk.corekit.models.ShippingAddress
import com.midtrans.sdk.corekit.models.snap.ItemDetails
import com.midtrans.sdk.corekit.models.snap.TransactionResult
import com.midtrans.sdk.uikit.SdkUIFlowBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_pesan.*

class PaymentsMidtrans : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pesan)
        name.text = GlobalData.names
        harga.text = "$ " + GlobalData.hargas.toString()
        deskripsi.text = GlobalData.deskripsis
        Picasso.get().load(GlobalData.photos).into(image)

        SdkUIFlowBuilder.init()
            .setClientKey("your key")
            .setContext(applicationContext)
            .setTransactionFinishedCallback(TransactionFinishedCallback {
                result ->
                // this is logic
            })
            .setMerchantBaseUrl("your merchant base url")
            .enableLog(true)
            .setColorTheme(CustomColorTheme("#FFE51255", "#B61548", "#FFE51255"))
            .setLanguage("id")
            .buildSDK()

        pesan.setOnClickListener {
            val hargaproduct = GlobalData.hargas
            val edittextHarga = jumlah.text.toString()
            val catatan = catatan.text.toString()
            GlobalData.jumlah = edittextHarga.toInt()
            GlobalData.catatan = catatan.toString()
            val convertharga = edittextHarga.toInt()
            val kalikan = convertharga * hargaproduct.toDouble()

            val transactionRequest = TransactionRequest("Kita-Store-"+System.currentTimeMillis().toString() + "", kalikan)
            val detail = com.midtrans.sdk.corekit.models.ItemDetails("NamaItemId", GlobalData.hargas.toDouble(), edittextHarga.toInt(), "Kursus Kotlin (Nama)")
            val itemDetails = ArrayList<com.midtrans.sdk.corekit.models.ItemDetails>()
            itemDetails.add(detail)
            uiKitDetails(transactionRequest)
            transactionRequest.itemDetails = itemDetails
            MidtransSDK.getInstance().transactionRequest = transactionRequest
            MidtransSDK.getInstance().startPaymentUiFlow(this)


        }
    }

    fun uiKitDetails(transactionRequest: TransactionRequest){

        val customerDetails = CustomerDetails()
        customerDetails.customerIdentifier = "Supriyanto"
        customerDetails.phone = "082345678999"
        customerDetails.firstName = "Supri"
        customerDetails.lastName = "Yanto"
        customerDetails.email = "supriyanto6543@gmail.com"
        val shippingAddress = ShippingAddress()
        shippingAddress.address = "Baturan, Gantiwarno"
        shippingAddress.city = "Klaten"
        shippingAddress.postalCode = "51193"
        customerDetails.shippingAddress = shippingAddress
        val billingAddress = BillingAddress()
        billingAddress.address  = "Baturan, Gantiwarno"
        billingAddress.city = "Klaten"
        billingAddress.postalCode = "51193"
        customerDetails.billingAddress = billingAddress

        transactionRequest.customerDetails = customerDetails
    }
}