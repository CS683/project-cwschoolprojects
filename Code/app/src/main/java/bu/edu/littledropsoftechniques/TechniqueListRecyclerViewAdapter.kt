package bu.edu.littledropsoftechniques

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import bu.edu.littledropsoftechniques.databinding.FragmentTechniqueItemBinding

class TechniqueListRecyclerViewAdapter(
    private var techniques: List<Technique>)
    : RecyclerView.Adapter<TechniqueListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FragmentTechniqueItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val technique = techniques[position]
//        holder.idView.text = (technique.id +1).toString()
        holder.contentView.text = technique.title.toString().uppercase()
        holder.cardView.setOnClickListener{
            val action = TechniqueListRecycleViewFragmentDirections.actionTechniqueListRecycleViewFragmentToDetailFragment(technique.id)
            it.findNavController().navigate(action)
        }
//        holder.imageView.setImageIcon(technique.mainPhotoRef)
    }

    override fun getItemCount(): Int = techniques.size

    inner class ViewHolder(binding: FragmentTechniqueItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
//        val idView: TextView = binding.techniqueIdView
        val contentView: TextView = binding.techniqueTitleinCard
        val cardView: CardView = binding.techniqueCard
        val imageView: ImageView = binding.techniqueThumbnail

        override fun toString(): String {
            return super.toString() + " '" + contentView.text.toString().uppercase() + "'"
        }

    }
    fun filterList(filteredTechniques: List<Technique>) {
        techniques = filteredTechniques
        notifyDataSetChanged()
    }
}