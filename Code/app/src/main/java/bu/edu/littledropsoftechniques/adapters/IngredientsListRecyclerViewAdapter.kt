package bu.edu.littledropsofingredients.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bu.edu.littledropsoftechniques.databinding.FragmentStringItemBinding

class IngredientsListRecyclerViewAdapter(
    ): RecyclerView.Adapter<IngredientsListRecyclerViewAdapter.ViewHolder>() {

    private val ingredients = mutableListOf<String>()
    fun replaceIngredientItems(myIngredients: List<String>) {
        val ingredientsSplit = mutableListOf<String>()

        ingredients.clear()

        for (ingredient in myIngredients) {
            ingredientsSplit.addAll(ingredient.replace("]", "").replace("[", "").replace("\"", "").split(",").toList())
        }
        ingredients.addAll(ingredientsSplit)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FragmentStringItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.contentView.text = ingredient
    }

    override fun getItemCount(): Int = ingredients.size

    inner class ViewHolder(binding: FragmentStringItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.nameInCard

        override fun toString(): String {
            return super.toString() + " '" + contentView.text.toString() + "'"
        }

    }
}