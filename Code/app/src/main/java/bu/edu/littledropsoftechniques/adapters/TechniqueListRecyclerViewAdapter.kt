package bu.edu.littledropsoftechniques.adapters

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import bu.edu.littledropsoftechniques.databinding.FragmentTechniqueItemBinding
import bu.edu.littledropsoftechniques.datalayer.Technique.Technique
import bu.edu.littledropsoftechniques.fragments.TechniqueListRecycleViewFragmentDirections

class TechniqueListRecyclerViewAdapter(
        private val onTechniqueClickListener: OnTechniqueClickListener
    ): RecyclerView.Adapter<TechniqueListRecyclerViewAdapter.ViewHolder>() {

    private val techniques = mutableListOf<Technique>()
    fun replaceItems(myTechniques: List<Technique>) {
        techniques.clear()
        techniques.addAll(myTechniques)
        notifyDataSetChanged()
    }

    interface OnTechniqueClickListener {
        fun onTechniqueClick(technique: Technique);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FragmentTechniqueItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val technique = techniques[position]
//        holder.idView.text = (technique.id +1).toString()
        holder.contentView.text = technique.title.toString().uppercase()
        holder.cardView.setOnClickListener{
            onTechniqueClickListener.onTechniqueClick(technique)
            val action =
                TechniqueListRecycleViewFragmentDirections.actionTechniqueListRecycleViewFragmentToDetailFragment(
//                    technique.id
                )
            it.findNavController().navigate(action)
        }
//        holder.imageView.setImageIcon(technique.mainPhotoRef)
    }

    override fun getItemCount(): Int = techniques.size

    fun getTechnique(pos: Int): Technique {
        if (techniques.size > 0)
            return techniques[pos]
        else
            return Technique(0,"","", listOf(""), listOf(""), listOf(""),"", false, listOf(""))
    }

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
        techniques.clear()
        techniques.addAll(filteredTechniques)
        notifyDataSetChanged()
    }
}