package com.example.recyclerviewgrid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.GridLayoutManager
import com.example.recyclerviewgrid.databinding.ActivityMainBinding
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val myAdapter = MyAdapter()

    private var itemList = mutableListOf<ListItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        handleWindowInset()

        binding.productRecycleView.layoutManager =
            GridLayoutManager(this, 2)
        binding.productRecycleView.adapter = myAdapter

        binding.swipeRefresh.setOnRefreshListener {
            refresh()
        }

//        itemList.addAll(ITEMS)

        updateAdapter()

        myAdapter.setCallBack(object : MyAdapter.CallBack {
            override fun onDeleteClick(id: String) {
                deleteUser(id)
            }
        })

        binding.productAddButton.setOnClickListener {
            try {
                val price = binding.priceEditText.text.toString().toInt()
                val check = binding.saleRiceEditText.text?.toString()
                if (check != null && check.isNotBlank()) {
                    val salePrice = binding.saleRiceEditText.text.toString().toInt()
                    addUser(price, salePrice)
                } else {
                    addUser(price, null)
                }
            } catch (e: Exception) {
            }
        }

        binding.bannerAddButton.setOnClickListener {
            addBanner()
        }

    }

    private fun updateAdapter() {
        myAdapter.submitList(itemList.toList())
    }

    private fun deleteUser(id: String) {
        val itemIndex = itemList.indexOfFirst { item -> item.uniqueId == id }

        if (itemIndex == -1) return

        itemList.removeAt(itemIndex)
        updateAdapter()
    }

    private fun addBanner() {
        val banner = ListItem.Banner(generateId(), R.drawable.ic_banner)
        itemList.add(banner)
        updateAdapter()
    }

    private fun addUser(price: Int, salePrice: Int?) {
        val product = ListItem.Product(generateId(), R.drawable.ic_product1, price, salePrice)
        itemList.add(product)
        updateAdapter()
    }

    private fun refresh() {
        binding.swipeRefresh.isRefreshing = false
        itemList.clear()
        updateAdapter()
    }

    private fun handleWindowInset() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { _, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())

            binding.root.updatePadding(bottom = insets.bottom)

            windowInsets

        }
    }
}

val ITEMS = listOf(
    ListItem.Product(generateId(), R.drawable.ic_product1, 100, 80),
    ListItem.Product(generateId(), R.drawable.ic_product2, 400, 80),
    ListItem.Product(generateId(), R.drawable.ic_product1, 100, null),
    ListItem.Product(generateId(), R.drawable.ic_product2, 400, 80),
    ListItem.Banner(generateId(), R.drawable.ic_banner),
    ListItem.Product(generateId(), R.drawable.ic_product3, 100, 80),
    ListItem.Banner(generateId(), R.drawable.ic_banner),
    ListItem.Product(generateId(), R.drawable.ic_product4, 100, 50),
    ListItem.Product(generateId(), R.drawable.ic_product3, 100, null),
    ListItem.Product(generateId(), R.drawable.ic_product1, 100, 80),
    ListItem.Product(generateId(), R.drawable.ic_product2, 400, 80),
    ListItem.Product(generateId(), R.drawable.ic_product3, 100, null),
    ListItem.Banner(generateId(), R.drawable.ic_banner),
    ListItem.Banner(generateId(), R.drawable.ic_banner),
    ListItem.Product(generateId(), R.drawable.ic_product4, 100, 50),
    ListItem.Product(generateId(), R.drawable.ic_product4, 100, 50),
    ListItem.Product(generateId(), R.drawable.ic_product1, 100, null),
    ListItem.Product(generateId(), R.drawable.ic_product2, 400, 80),
    ListItem.Banner(generateId(), R.drawable.ic_banner),
    ListItem.Product(generateId(), R.drawable.ic_product3, 100, 80),
    ListItem.Product(generateId(), R.drawable.ic_product4, 100, null),
)

private fun generateId(): String {
    return UUID.randomUUID().toString()
}