package bu.edu.littledropsofingredients.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bu.edu.littledropsoftechniques.adapters.TechniqueListRecyclerViewAdapter
import bu.edu.littledropsoftechniques.databinding.FragmentStringItemBinding
import bu.edu.littledropsoftechniques.datalayer.Technique.Technique
import com.google.android.material.floatingactionbutton.FloatingActionButton

class IngredientsListRecyclerViewAdapter(
    private val onDeleteClickListener: IngredientsListRecyclerViewAdapter.OnDeleteClickListener?
    ): RecyclerView.Adapter<IngredientsListRecyclerViewAdapter.ViewHolder>() {

    private val ingredients = mutableListOf<String>()
    fun replaceIngredientItems(myIngredients: List<String>) {
        val ingredientsSplit = mutableListOf<String>()
        val ingredientsSplitFormatted = mutableListOf<String>()

        ingredients.clear()

        for (ingredient in myIngredients) {
            ingredientsSplit.addAll(ingredient.split(",").toList())
        }

        for (step in ingredientsSplit){
            ingredientsSplitFormatted.add(step.replace("]", "").replace("[", "").replace("\"", "").trim())
        }

        ingredients.addAll(ingredientsSplitFormatted)
        notifyDataSetChanged()
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(ingredient: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FragmentStringItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = ingredients[position]
        holder.contentView.text = ingredient

        if (onDeleteClickListener != null) {
            holder.deleteBtn.show()
            holder.deleteBtn.setOnClickListener {
                onDeleteClickListener?.onDeleteClick(ingredient)
            }
        }
        else {
            holder.deleteBtn.hide()
        }
    }

    override fun getItemCount(): Int = ingredients.size

    inner class ViewHolder(binding: FragmentStringItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.nameInCard
        val deleteBtn: FloatingActionButton = binding.deleteItem

        override fun toString(): String {
            return super.toString() + " '" + contentView.text.toString() + "'"
        }

    }
}