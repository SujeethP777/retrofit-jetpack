package com.dqitech.productretrofit3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.dqitech.productretrofit3.ui.theme.ProductRetrofit3Theme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProductRetrofit3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductsScreen()
                }
            }
        }
    }
}

@Composable
fun ProductsScreen() {
    var products by remember { mutableStateOf(listOf<Product>()) }
    //var products by remember { mutableStateOf(Product()) }
    var fetchProducts by remember { mutableStateOf(false) }


    val objRetrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val productsService = objRetrofit.create(ProductsService::class.java)
    val fetchProductsApiCall = productsService.getProducts()
    fetchProductsApiCall.enqueue(object : Callback<ProductsResponse?> {
        override fun onResponse(
            call: Call<ProductsResponse?>,
            response: Response<ProductsResponse?>
        ) {
            val objProductsResponse = response.body()
            if (objProductsResponse != null) {
                products = objProductsResponse.products
                fetchProducts = true
            }
        }

        override fun onFailure(call: Call<ProductsResponse?>, t: Throwable) {
            Log.d("MainActivity", t.toString())
        }
    })

    if (fetchProducts) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(20.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
        )
        {
            items(products) { product ->
                Products(product)
            }
        }
    }
}

@Composable
fun Products(product : Product)
{
    val mContext = LocalContext.current
    var productDetailIntent : Intent

    Row (
        modifier = Modifier
            .clickable {
                productDetailIntent = Intent(mContext, ProductDetailActivity::class.java)
                productDetailIntent.putExtra("product_id", product.id)
                mContext.startActivity(productDetailIntent)
            }
            ){
       Image(painter = rememberAsyncImagePainter(product.images[0]),
           contentDescription = null,
           modifier = Modifier
               .padding(top = 10.dp)
               .size(150.dp)
       )
        Column {
            Text(
                text = product.title,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                    color = Color.Black
                )
            )
            Text(text = (product.price).toString(),
                 color = Color.Red,
                 style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal
            )
            )
            Text(
                text = product.brand,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    color = Color.Black
                )
            )
        }
    }
    Divider(
        color = Color.Gray,
        modifier = Modifier
            .fillMaxWidth()
    )
}


@Preview(showBackground = true)
@Composable
fun ProductsScreenPreview() {
    ProductRetrofit3Theme {
        ProductsScreen()
    }
}