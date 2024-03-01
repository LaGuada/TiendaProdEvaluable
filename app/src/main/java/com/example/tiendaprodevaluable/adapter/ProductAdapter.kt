package com.example.tiendaprodevaluable.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tiendaprodevaluable.databinding.ItemProductBinding
import com.example.tiendaprodevaluable.model.Product
class ProductAdapter(
    private val productList: List<Product>,
    private val onAddToCartClicked: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding, onAddToCartClicked)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = productList.size

    class ProductViewHolder(
        private val binding: ItemProductBinding,
        private val onAddToCartClicked: (Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.nombreProducto.text = product.title
            binding.precioProducto.text = "${product.price}"
            Glide.with(binding.imagenProducto.context).load(product.thumbnail).into(binding.imagenProducto)

            binding.botonAAdirCarrito.setOnClickListener {
                onAddToCartClicked(product)
            }
        }
    }
}