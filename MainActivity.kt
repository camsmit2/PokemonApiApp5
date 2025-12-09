package com.example.pokemonapiapp


import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import com.example.pokemonapiapp.R


class MainActivity : AppCompatActivity() {

    private lateinit var ivSprite: ImageView
    private lateinit var tvName: TextView
    private lateinit var tvId: TextView
    private lateinit var tvTypes: TextView
    private lateinit var btnRandom: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivSprite = findViewById(R.id.ivSprite)
        tvName = findViewById(R.id.tvName)
        tvId = findViewById(R.id.tvId)
        tvTypes = findViewById(R.id.tvTypes)
        btnRandom = findViewById(R.id.btnRandom)

        // Button: get a random Pokémon
        btnRandom.setOnClickListener {
            fetchRandomPokemon()
        }

        // Optional: load Pikachu on startup
        fetchPokemonById(25)
    }

    private fun fetchRandomPokemon() {
        // First generation: 1..151; you can expand if you want
        val randomId = (1..151).random()
        fetchPokemonById(randomId)
    }

    private fun fetchPokemonById(id: Int) {
        val client = AsyncHttpClient()
        val url = "https://pokeapi.co/api/v2/pokemon/$id"

        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Headers,
                json: JSON
            ) {
                try {
                    val jsonObject = json.jsonObject

                    // name
                    val name = jsonObject.getString("name")

                    // id
                    val pokeId = jsonObject.getInt("id")

                    // sprite
                    val spriteUrl = jsonObject
                        .getJSONObject("sprites")
                        .getString("front_default")

                    // types array
                    val typesArray = jsonObject.getJSONArray("types")
                    val typesList = mutableListOf<String>()
                    for (i in 0 until typesArray.length()) {
                        val typeObj = typesArray.getJSONObject(i)
                        val typeName = typeObj
                            .getJSONObject("type")
                            .getString("name")
                        typesList.add(typeName)
                    }
                    val typesText = typesList.joinToString(", ")

                    // Update UI
                    tvName.text = "Name: " + name.replaceFirstChar { it.uppercase() }
                    tvId.text = "ID: $pokeId"
                    tvTypes.text = "Type(s): $typesText"

                    Glide.with(this@MainActivity)
                        .load(spriteUrl)
                        .into(ivSprite)

                } catch (e: Exception) {
                    e.printStackTrace()
                    Toast.makeText(
                        this@MainActivity,
                        "Error parsing Pokémon data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Toast.makeText(
                    this@MainActivity,
                    "Failed to load Pokémon (code $statusCode)",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
