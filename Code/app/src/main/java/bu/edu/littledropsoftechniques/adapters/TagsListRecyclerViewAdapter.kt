package bu.edu.littledropsofingredients.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bu.edu.littledropsoftechniques.databinding.FragmentStringItemBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TagsListRecyclerViewAdapter(
    private val onDeleteClickListener: TagsListRecyclerViewAdapter.OnDeleteClickListener?
    ): RecyclerView.Adapter<TagsListRecyclerViewAdapter.ViewHolder>() {

    private val tags = mutableListOf<String>()
    fun replaceTagItems(myTags: List<String>) {
        val tagsSplit = mutableListOf<String>()
        val tagsSplitFormatted = mutableListOf<String>()

        tags.clear()

        for (tag in myTags) {
            tagsSplit.addAll(tag.split(",").toList())
        }

        for (tag in tagsSplit){
            tagsSplitFormatted.add(tag.replace("]", "").replace("[", "").replace("\"", "").trim())
        }

        tags.addAll(tagsSplitFormatted)
        notifyDataSetChanged()
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(tag: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FragmentStringItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tag = tags[position]
        holder.contentView.text = tag

        if (onDeleteClickListener != null) {
            holder.deleteBtn.show()
            holder.deleteBtn.setOnClickListener {
                onDeleteClickListener?.onDeleteClick(tag)
            }
        }
        else {
            holder.deleteBtn.hide()
        }
    }

    override fun getItemCount(): Int = tags.size

    inner class ViewHolder(binding: FragmentStringItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.nameInCard
        val deleteBtn: FloatingActionButton = binding.deleteItem

        override fun toString(): String {
            return super.toString() + " '" + contentView.text.toString() + "'"
        }

    }
}