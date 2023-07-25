package bu.edu.littledropsoftechniques

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import bu.edu.littledropsoftechniques.databinding.FragmentTechniqueItemBinding


class TechniqueListRecyclerViewAdapter(
        private val techniques: List<Technique>)
    : RecyclerView.Adapter<TechniqueListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    return ViewHolder(FragmentTechniqueItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val technique = techniques[position]
        holder.idView.text = (technique.id +1).toString()
        holder.contentView.text = technique.title
        holder.cardView.setOnClickListener{
            val action = TechniqueListRecycleViewFragmentDirections.actionTechniqueListRecycleViewFragmentToDetailFragment(position)
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = techniques.size

    inner class ViewHolder(binding: FragmentTechniqueItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val idView: TextView = binding.techniqueIdView
        val contentView: TextView = binding.techniqueTitleinCard
        val cardView: CardView = binding.techniqueCard

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }

    }

}