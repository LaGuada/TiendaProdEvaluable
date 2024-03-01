package com.example.tiendaprodevaluable.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tiendaprodevaluable.R
import com.example.tiendaprodevaluable.databinding.ItemCartProductBinding
import com.example.tiendaprodevaluable.model.Product

class CartAdapter(
    private val cartProducts: List<Product>,
    private val onItemClick: (Product) -> Unit = {} // Callback opcional por si necesitas manejar clics en los items
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(cartProducts[position])
    }

    override fun getItemCount(): Int = cartProducts.size

    class CartViewHolder(
        private val binding: ItemCartProductBinding,
        private val onItemClick: (Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.nombreProducto.text = product.title
            binding.precioProducto.text = "${product.price}"
            Glide.with(binding.cartProductImage.context).load(product.thumbnail).into(binding.cartProductImage)

            itemView.setOnClickListener { onItemClick(product) }
        }
    }
}

