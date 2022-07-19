package com.example.recyclerviewgrid

import androidx.annotation.DrawableRes

sealed class ListItem(
    val Id: ViewType,
    val uniqueId: String
) {

    enum class ViewType {
        PRODUCT,
        BANNER
    }

    data class Product(
        val id: String,
        @DrawableRes val image: Int,
        val price: Int,
        val salePrice: Int?,
    ) : ListItem(ViewType.PRODUCT, id)

    data class Banner(
        val id: String,
        @DrawableRes val banner: Int,
    ) : ListItem(ViewType.BANNER, id)


}