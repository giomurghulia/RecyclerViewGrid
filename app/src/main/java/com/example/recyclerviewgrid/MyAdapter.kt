package com.example.recyclerviewgrid

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewgrid.databinding.LayoutBannerBinding
import com.example.recyclerviewgrid.databinding.LayoutSimpleProductBinding

class MyAdapter : ListAdapter<ListItem, RecyclerView.ViewHolder>(MyDiffUtil<ListItem>()) {
    private var callBack: CallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ListItem.ViewType.PRODUCT.ordinal -> {
                return ProductViewHolder(
                    LayoutSimpleProductBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                return BannerViewHolder(
                    LayoutBannerBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)

        when (holder) {
            is ProductViewHolder -> holder.bind(item as ListItem.Product)
            is BannerViewHolder -> holder.bind(item as ListItem.Banner)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).Id.ordinal
    }

    fun setCallBack(callBack: CallBack) {
        this.callBack = callBack
    }

    inner class ProductViewHolder(
        private val binding: LayoutSimpleProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: ListItem.Product) {
            binding.productImageImage.setImageResource(item.image)
            binding.priceText.text = "₾${item.price.toString()}"
            if (item.salePrice != null) {
                binding.salePriceText.text = "₾${item.salePrice.toString()}"
                binding.salePriceText.visibility = View.VISIBLE

                binding.bookmarkImage.visibility = View.VISIBLE
            } else {
                binding.salePriceText.visibility = View.GONE
                binding.bookmarkImage.visibility = View.GONE
            }

            binding.removeImage.setOnClickListener {
                callBack?.onDeleteClick(item.uniqueId)
            }

        }
    }

    inner class BannerViewHolder(
        private val binding: LayoutBannerBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ListItem.Banner) {
            binding.productImageImage.setImageResource(item.banner)

            binding.removeImage.setOnClickListener {
                callBack?.onDeleteClick(item.uniqueId)
            }

        }
    }

    interface CallBack {
        fun onDeleteClick(id: String)
    }

}
