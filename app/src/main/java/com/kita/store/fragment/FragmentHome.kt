package com.kita.store.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.kita.store.R
import com.kita.store.adapter.ProductAdapter
import com.kita.store.modal.Product
import kotlinx.android.synthetic.main.fragment_home.*

class FragmentHome : Fragment() {

    var list = ArrayList<Product>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        getProduct()
        return view
    }

    private fun getProduct(){
        var queue: RequestQueue = Volley.newRequestQueue(activity)
        var reques = JsonArrayRequest(Request.Method.GET, "http://192.168.43.183/store/apiproduct.php", null, Response.Listener { response ->
            for (s in 0..response.length() - 1){
                var job = response.getJSONObject(s)
                var id = job.getInt("id")
                var name = job.getString("name")
                var harga = job.getInt("harga")
                var photo = job.getString("photo").replace("localhost", "192.168.43.183")
                var deskripsi = job.getString("deskripsi")

                list.add(Product(id, name, harga, deskripsi, photo))
                var adapterku = ProductAdapter(requireContext(), list)
                recycler.layoutManager = LinearLayoutManager(requireContext())
                recycler.adapter = adapterku
            }
        }, Response.ErrorListener { error ->
            Log.d("showError", error.toString())
        })
        queue.add(reques)
    }
}