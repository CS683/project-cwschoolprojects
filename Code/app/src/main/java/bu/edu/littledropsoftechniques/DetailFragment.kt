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

        Log.d("debug", "Setting values of project")
        binding.projTitle.text =  Project.projects[position].title
        binding.projDesc.text = Project.projects[position].description
        binding.projAuthors.text =  "Authors: " + Project.projects[position].authors
        binding.projUrls.text =  "URLs: " + Project.projects[position].urls
        changeLikedImage(view, position)
        binding.projKeywords.text = "Keywords: " + Project.projects[position].keywords
        binding.projCreatedDate.text = "Created on: " + Project.projects[position].dateCreated

        Log.d("debug", "Setting listeners")
        binding.editProj.setOnClickListener{
            val action = DetailFragmentDirections.actionDetailFragmentToEditFragment(position)
            it.findNavController().navigate(action)
            Log.d("navigation", "Navigated to edit screen")
        }
        liked.setOnClickListener { onClickedStar(view, position) }
        disliked.setOnClickListener { onClickedStar(view, position) }
    }

    // method that toggles the value of project isLiked field based on a click event
    private fun onClickedStar(view: View, position:Int) {
        Log.d("event", "Clicked Star")
        Project.projects[position].isLiked = !Project.projects[position].isLiked
        changeLikedImage(view, position)
    }

    // method that changes the image per the value of the project isLiked field
    private fun changeLikedImage(view: View, position:Int){
        val projLiked =  view.findViewById<ImageButton>(R.id.btnLike)
        val projDisliked =  view.findViewById<ImageButton>(R.id.btnDislike)
        if (Project.projects[position].isLiked) {
            Log.d("changes", "Project liked")
            projLiked.visibility = View.VISIBLE
            projDisliked.visibility = View.INVISIBLE
        }
        else {
            Log.d("changes", "Project unliked")
            projLiked.visibility = View.INVISIBLE
            projDisliked.visibility = View.VISIBLE
        }
    }
}