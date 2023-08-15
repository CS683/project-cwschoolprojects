package bu.edu.littledropsoftechniques.adapters

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import bu.edu.littledropsoftechniques.R
import bu.edu.littledropsoftechniques.databinding.FragmentTechniqueItemBinding
import bu.edu.littledropsoftechniques.datalayer.Technique.Technique
import bu.edu.littledropsoftechniques.fragments.TechniqueListRecycleViewFragmentDirections

class TechniqueListRecyclerViewAdapter(
        private val onTechniqueClickListener: OnTechniqueClickListener,
        private val onFavoriteClickListener: OnFavoriteClickListener
    ): RecyclerView.Adapter<TechniqueListRecyclerViewAdapter.ViewHolder>() {

    private val techniques = mutableListOf<Technique>()
    fun replaceItems(myTechniques: List<Technique>?, showFavoritesOnly: Boolean?, searchText: String?) {
        if (myTechniques != null) {
            var showFavoritesOnly = showFavoritesOnly ?: false
            var filterText = searchText ?: ""
            val filteredProjects = mutableListOf<Technique>()

            if (showFavoritesOnly) {
                myTechniques.forEachIndexed { index, project ->
                    if (project.isLiked && (filterText == "" ||
                        project.title.lowercase().contains(filterText.lowercase()) ||
                        project.tags.toString().lowercase().contains(filterText.lowercase()))
                    ) {
                        filteredProjects.add(project)
                    }
                }
                Log.d("debug", "filtered: " + filteredProjects.toString())
                techniques.clear()
                techniques.addAll(filteredProjects)
            } else {
                myTechniques.forEachIndexed { index, project ->
                    if (filterText == "" ||
                        project.title.lowercase().contains(filterText.lowercase()) ||
                        project.tags.toString().lowercase().contains(filterText.lowercase())
                    ) {
                        filteredProjects.add(project)
                    }
                }
                Log.d("debug", "filtered: " + filteredProjects.toString())
                techniques.clear()
                techniques.addAll(filteredProjects)
            }

            notifyDataSetChanged()
        }
    }

    interface OnTechniqueClickListener {
        fun onTechniqueClick(technique: Technique)
    }

    interface OnFavoriteClickListener {
        fun onFavoriteClick(technique: Technique)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(FragmentTechniqueItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val technique = techniques[position]
        holder.contentView.text = technique.title.uppercase()

        changeLikedStatus(technique, holder)

        holder.dislikeBtn.setOnClickListener {
            Log.d("debug", "disliked")
            onFavoriteClickListener.onFavoriteClick(technique)
            changeLikedStatus(technique, holder)
            notifyDataSetChanged()
        }

        holder.likeBtn.setOnClickListener {
            Log.d("debug", "liked")
            onFavoriteClickListener.onFavoriteClick(technique)
            changeLikedStatus(technique, holder)
            notifyDataSetChanged()
        }

        holder.cardView.setOnClickListener{
            onTechniqueClickListener.onTechniqueClick(technique)
            val action =
                TechniqueListRecycleViewFragmentDirections.actionTechniqueListRecycleViewFragmentToDetailFragment()
            it.findNavController().navigate(action)
        }
        DownloadImageFromInternet(holder).execute(technique.mainPhotoRef)
    }

    override fun getItemCount(): Int = techniques.size

    fun getTechnique(pos: Int): Technique {
        if (techniques.size > 0)
            for (technique in techniques) {
                Log.d("debug", technique.id.toString())
                Log.d("debug", pos.toString())
                if (technique.id == pos) {
                    return technique
                }
            }

        return Technique(0,"","", listOf(""), listOf(""), listOf(""),"", false, listOf(""))
    }

    inner class ViewHolder(binding: FragmentTechniqueItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.techniqueTitleinCard
        val cardView: CardView = binding.techniqueCard
        val imageView: ImageView = binding.techniqueThumbnail
        val dislikeBtn: ImageButton = binding.btnDislike
        val likeBtn: ImageButton = binding.btnLike

        override fun toString(): String {
            return super.toString() + " '" + contentView.text.toString().uppercase() + "'"
        }

    }

    @SuppressLint("StaticFieldLeak")
    @Suppress("DEPRECATION")
    private inner class DownloadImageFromInternet(var holder: ViewHolder) : AsyncTask<String, Void, Bitmap?>() {
        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {
                val `in` = java.net.URL(imageURL).openStream()
                val largeBitmap = BitmapFactory.decodeStream(`in`)
                val h = holder.imageView.height // height in pixels
                val w = holder.imageView.width // width in pixels
                image = Bitmap.createScaledBitmap(largeBitmap, w, h, true)
            }
            catch (e: Exception) {
            }
            return image
        }
        override fun onPostExecute(result: Bitmap?) {
            if (result != null) {
                holder.imageView.setImageBitmap(result)
            }
        }
    }

    fun changeLikedStatus(technique: Technique, holder: ViewHolder) {
        if (technique.isLiked) {
            holder.dislikeBtn.visibility = View.INVISIBLE
            holder.likeBtn.visibility = View.VISIBLE
        }
        else {
            holder.dislikeBtn.visibility = View.VISIBLE
            holder.likeBtn.visibility = View.INVISIBLE
        }
    }
}