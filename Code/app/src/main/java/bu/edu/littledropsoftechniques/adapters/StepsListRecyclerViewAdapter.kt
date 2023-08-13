package bu.edu.littledropsofingredients.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bu.edu.littledropsoftechniques.databinding.FragmentStringItemBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class StepsListRecyclerViewAdapter(
    private val onDeleteClickListener: StepsListRecyclerViewAdapter.OnDeleteClickListener?
    ): RecyclerView.Adapter<StepsListRecyclerViewAdapter.ViewHolder>() {

    private val steps = mutableListOf<String>()

    fun replaceStepItems(mySteps: List<String>) {
        val stepsSplit = mutableListOf<String>()
        val stepsSplitFormatted = mutableListOf<String>()

        steps.clear()

        for (step in mySteps) {
            stepsSplit.addAll(step.split(",").toList())
        }

        for (step in stepsSplit){
            stepsSplitFormatted.add(step.replace("]", "").replace("[", "").replace("\"", "").trim())
        }

        steps.addAll(stepsSplitFormatted)
        notifyDataSetChanged()
    }

    interface OnDeleteClickListener {
        fun onDeleteClick(step: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FragmentStringItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val step = steps[position]
        holder.contentView.text = step

        if (onDeleteClickListener != null) {
            holder.deleteBtn.show()
            holder.deleteBtn.setOnClickListener {
                onDeleteClickListener?.onDeleteClick(step)
            }
        }
        else {
            holder.deleteBtn.hide()
        }
    }

    override fun getItemCount(): Int = steps.size

    inner class ViewHolder(binding: FragmentStringItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.nameInCard
        val deleteBtn: FloatingActionButton = binding.deleteItem

        override fun toString(): String {
            return super.toString() + " '" + contentView.text.toString() + "'"
        }

    }
}