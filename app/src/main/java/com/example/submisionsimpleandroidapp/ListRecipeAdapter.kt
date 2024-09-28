package com.example.submisionsimpleandroidapp

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListRecipeAdapter(private val listRecipe: ArrayList<Recipe>) :
    RecyclerView.Adapter<ListRecipeAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe_row, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listRecipe.size

    @SuppressLint("DiscouragedApi")
    private fun getImageResource(context: Context, imageName: String): Int {
        return context.resources.getIdentifier(imageName, "drawable", context.packageName)
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (title, description, photo) = listRecipe[position]
        if (photo != null) {
            holder.imgPhoto.setImageResource(getImageResource(holder.itemView.context, photo))
        }
        holder.recipeName.text = title
        holder.desc.text = description
        holder.itemView.setOnClickListener {
            val move = Intent(holder.itemView.context, DetailActivity::class.java)
            move.putExtra(DetailActivity.ID_RECIPE, position)
            holder.itemView.context.startActivity(move)
        }
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val recipeName: TextView = itemView.findViewById(R.id.recipe_name)
        val desc: TextView = itemView.findViewById(R.id.recipe_desc)
    }


}
