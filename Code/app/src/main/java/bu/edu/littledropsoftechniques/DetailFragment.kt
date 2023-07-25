package bu.edu.littledropsoftechniques
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.findNavController
import bu.edu.littledropsoftechniques.databinding.FragmentDetailBinding

//Detail View Fragment
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    // On create view fragment override
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Detail View Fragment On Created event override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        val position:Int = arguments?.getInt("position")?:0

        Log.d("debug", "position: $position")
        Log.d("debug", "Gathering all Detail UI components")
        var liked = view.findViewById<ImageButton>(R.id.btnLike)
        var disliked = view.findViewById<ImageButton>(R.id.btnDislike)

        Log.d("debug", "Setting values of technique")
        binding.techniqueTitle.text =  Technique.techniques[position].title
        binding.techniqueDesc.text = Technique.techniques[position].description
        binding.techniqueAuthors.text =  "Authors: " + Technique.techniques[position].authors
//        binding.techniqueIngredientslist.text =  mutableListOf(Technique.techniques[position].ingredients)
//        binding.techniqueStepslist.text = mutableListOf(Technique.techniques[position].steps)
        // add thumbnail binding
        changeLikedImage(view, position)
//        binding.techniqueTagslist.text = mutableListOf(Technique.techniques[position].tags)

        Log.d("debug", "Setting listeners")
//        binding.editTechnique.setOnClickListener{
//            val action = DetailFragmentDirections.actionDetailFragmentToEditFragment(position)
//            it.findNavController().navigate(action)
//            Log.d("navigation", "Navigated to edit screen")
//        }
        liked.setOnClickListener { onClickedStar(view, position) }
        disliked.setOnClickListener { onClickedStar(view, position) }
    }

    // method that toggles the value of technique isLiked field based on a click event
    private fun onClickedStar(view: View, position:Int) {
        Log.d("event", "Clicked Star")
        Technique.techniques[position].isLiked = !Technique.techniques[position].isLiked
        changeLikedImage(view, position)
    }

    // method that changes the image per the value of the technique isLiked field
    private fun changeLikedImage(view: View, position:Int){
        val techniqueLiked =  view.findViewById<ImageButton>(R.id.btnLike)
        val techniqueDisliked =  view.findViewById<ImageButton>(R.id.btnDislike)
        if (Technique.techniques[position].isLiked) {
            Log.d("changes", "technique liked")
            techniqueLiked.visibility = View.VISIBLE
            techniqueDisliked.visibility = View.INVISIBLE
        }
        else {
            Log.d("changes", "technique unliked")
            techniqueLiked.visibility = View.INVISIBLE
            techniqueDisliked.visibility = View.VISIBLE
        }
    }
}