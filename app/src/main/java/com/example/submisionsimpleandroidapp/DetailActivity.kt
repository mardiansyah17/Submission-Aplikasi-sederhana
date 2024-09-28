package com.example.submisionsimpleandroidapp

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class DetailActivity : AppCompatActivity() {
    private val gson = Gson()


    companion object {
        const val ID_RECIPE = "id_recipe"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getIntExtra(ID_RECIPE, 0)
        val detailTitle: TextView = findViewById(R.id.detail_title)
        val detailDesc: TextView = findViewById(R.id.detail_description)
        val detailImage: ImageView = findViewById(R.id.detail_image)
        val btnShare: Button = findViewById(R.id.action_share)


        val file = resources.openRawResource(R.raw.recipes).reader().readText()

        val recipes = gson.fromJson(file, Array<Recipe>::class.java)
        val recipeTitle = recipes[id].title
        val recipeDescription = recipes[id].description
        val ingredients = recipes[id].ingredients
        detailTitle.text = recipeTitle
        detailDesc.text = recipeDescription
        detailImage.setImageResource(
            resources.getIdentifier(
                recipes[id].photo,
                "drawable",
                packageName
            )
        )


        val recyclerView: RecyclerView = findViewById(R.id.ingredients_list)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ListIndredientsAdapter(
            recipes[id].ingredients
        )

        btnShare.setOnClickListener {
            val recipeText = """
Resep: $recipeTitle
Deskripsi: $recipeDescription
    
Bahan-bahan:
${ingredients.joinToString("\n") { "- $it" }}
    
""".trimIndent()

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, recipeText)
                type = "text/plain"
            }

            startActivity(Intent.createChooser(shareIntent, "Bagikan resep ke:"))

        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("MainActivity", "onOptionsItemSelected: ${item.itemId} ")

        if (item.itemId == android.R.id.home) {

            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val file = resources.openRawResource(R.raw.recipes).reader().readText()

        val recipes = gson.fromJson(file, Array<Recipe>::class.java)

        menuInflater.inflate(R.menu.menu_main, menu)
        val id = intent.getIntExtra(ID_RECIPE, 0)

        supportActionBar?.title = recipes[id].title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFF")))

        return super.onCreateOptionsMenu(menu)
    }

}
