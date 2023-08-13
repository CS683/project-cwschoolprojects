package bu.edu.littledropsofingredients.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import bu.edu.littledropsoftechniques.databinding.FragmentStringItemBinding

class StepsListRecyclerViewAdapter(
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FragmentStringItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val step = steps[position]
        holder.contentView.text = step
    }

    override fun getItemCount(): Int = steps.size

    inner class ViewHolder(binding: FragmentStringItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.nameInCard

        override fun toString(): String {
            return super.toString() + " '" + contentView.text.toString() + "'"
        }

    }
}