package bu.edu.littledropsofingredients.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bu.edu.littledropsoftechniques.databinding.FragmentStringItemBinding

class TagsListRecyclerViewAdapter(
    ): RecyclerView.Adapter<TagsListRecyclerViewAdapter.ViewHolder>() {

    private val tags = mutableListOf<String>()
    fun replaceTagItems(myTags: List<String>) {
        val tagsSplit = mutableListOf<String>()

        tags.clear()

        for (tag in myTags) {
            tagsSplit.addAll(tag.replace("]", "").replace("[", "").replace("\"", "").split(",").toList())
        }
        tags.addAll(tagsSplit)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FragmentStringItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingredient = tags[position]
        holder.contentView.text = ingredient
    }

    override fun getItemCount(): Int = tags.size

    inner class ViewHolder(binding: FragmentStringItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.nameInCard

        override fun toString(): String {
            return super.toString() + " '" + contentView.text.toString() + "'"
        }

    }
}