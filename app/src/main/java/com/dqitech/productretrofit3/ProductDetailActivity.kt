package com.dqitech.productretrofit3

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

class ProductDetailActivity : ComponentActivity() {
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var productID : Int?
            ProductRetrofit3Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    productID = intent.extras?.getInt("product_id")
                    if(productID != null)
                    ProductDetailScreen(productID!!)
                }
            }
        }
    }
}

@Composable
fun ProductDetailScreen(productID : Int) {
    var product by remember { mutableStateOf<Product?>(null) }
    var fetchProducts by remember { mutableStateOf(false) }
    val objRetrofit = Retrofit.Builder()
        .baseUrl("https://dummyjson.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val productService = objRetrofit.create(ProductsService::class.java)
    val fetchProductsApiCall = productService.getProduct(productID)
    fetchProductsApiCall.enqueue(object : Callback<Product?> {
        override fun onResponse(call: Call<Product?>, response: Response<Product?>) {
            product = response.body()
            fetchProducts = true
        }

        override fun onFailure(call: Call<Product?>, t: Throwable) {
            Log.d("ProductDetailActivity", t.toString())
        }
    })

    if (fetchProducts && product != null) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(product?.images?.get(0)),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
            )
            Row {
                Text(
                    text = product!!.title,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        color = Color.Black
                    )
                )
                Text(
                    text = (product?.price).toString(),
                    modifier = Modifier
                        .padding(start = 270.dp),
                    textAlign = TextAlign.End,
                    color = Color.Red,
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal
                    )
                )
            }
            Spacer(
                modifier = Modifier
                    .height(5.dp)
            )
            Text(
                text = product!!.brand,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    color = Color.Black
                )
            )
            Spacer(
                modifier = Modifier
                    .height(5.dp)
            )
            Text(
                text = product!!.description,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    color = Color.Black
                )
            )
            Button(
                onClick = {},
                shape = RoundedCornerShape(20),
                modifier = Modifier
                    .padding(top = 50.dp)
            ) {
                Text(
                    text = "Buy"
                )
            }
        }
    }
}

