package com.example.tiendaprodevaluable.ui.activities
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tiendaprodevaluable.R
import com.example.tiendaprodevaluable.adapter.CartAdapter
import com.example.tiendaprodevaluable.data.CarritoSingleton
import com.example.tiendaprodevaluable.databinding.ActivitySecondBinding
import com.example.tiendaprodevaluable.model.Product
import com.google.android.material.snackbar.Snackbar
class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        updateTotalPrice()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setupRecyclerView() {
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)
        // Usa directamente CarritoSingleton.productosEnCarrito para el adaptador
        cartAdapter = CartAdapter(CarritoSingleton.productosEnCarrito)
        binding.cartRecyclerView.adapter = cartAdapter
    }

    private fun updateTotalPrice() {
        // Calcula el total usando CarritoSingleton.productosEnCarrito
        val totalPrice = CarritoSingleton.productosEnCarrito.sumOf { it.price }
        binding.viewTotalPrecio.text = getString(R.string.total_price, String.format("%.2f", totalPrice))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_confirmar_compra -> {
                // Uso CarritoSingleton.productosEnCarrito para calcular el total
                val totalPrice = CarritoSingleton.productosEnCarrito.sumOf { it.price }
                Snackbar.make(binding.root, getString(R.string.purchase_confirmation, String.format("%.2f", totalPrice)), Snackbar.LENGTH_LONG).show()
                true
            }
            R.id.menu_vaciar_carrito -> {
                // Limpia CarritoSingleton.productosEnCarrito y notifica al adaptador
                CarritoSingleton.productosEnCarrito.clear()
                cartAdapter.notifyDataSetChanged()
                updateTotalPrice()
                Snackbar.make(binding.root, R.string.cart_emptied, Snackbar.LENGTH_LONG).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}