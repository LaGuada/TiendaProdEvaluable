package com.example.tiendaprodevaluable.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.tiendaprodevaluable.adapter.ProductAdapter
import com.example.tiendaprodevaluable.data.CarritoSingleton
import com.example.tiendaprodevaluable.databinding.ActivityMainBinding
import com.example.tiendaprodevaluable.model.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val productList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        cargarCategorias()
        cargarProductos()

        binding.botonCarrito.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

    }


    private fun setupRecyclerView() {
        binding.productRecyclerView.layoutManager = LinearLayoutManager(this)
        val productAdapter = ProductAdapter(productList) { product ->
            onAddToCartClicked(product)
        }
        binding.productRecyclerView.adapter = productAdapter
    }

    private fun onAddToCartClicked(product: Product) {
        CarritoSingleton.productosEnCarrito.add(product)
        Toast.makeText(this, "${product.title} añadido al carrito", Toast.LENGTH_SHORT).show()
    }

    private fun cargarProductos() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://dummyjson.com/products"

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                val productsArray = response.getJSONArray("products")
                val gson = Gson()
                val itemType = object : TypeToken<List<Product>>() {}.type
                val products: List<Product> = gson.fromJson(productsArray.toString(), itemType)

                productList.clear()
                productList.addAll(products)
                binding.productRecyclerView.adapter?.notifyDataSetChanged()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error al cargar productos: $error", Toast.LENGTH_LONG).show()
            })

        queue.add(jsonObjectRequest)
    }

    private fun cargarCategorias() {
        val queue = Volley.newRequestQueue(this)
        val url = "https://dummyjson.com/products/categories"

        val jsonArrayRequest = JsonArrayRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                val categories = List(response.length()) { i -> response.getString(i) }
                val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.categorySpinner.adapter = adapter
            },
            Response.ErrorListener {
                Toast.makeText(this, "Error al cargar categorías", Toast.LENGTH_LONG).show()
            })

        queue.add(jsonArrayRequest)
    }
}


