package bu.edu.littledropsoftechniques.fragments
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import bu.edu.littledropsoftechniques.R
import bu.edu.littledropsoftechniques.viewmodels.TechniqueListViewModel
import bu.edu.littledropsoftechniques.databinding.FragmentDetailBinding
import bu.edu.littledropsoftechniques.viewmodels.CurTechniqueViewModel

//Detail View Fragment
class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CurTechniqueViewModel
    private lateinit var listViewModel: TechniqueListViewModel

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

        val position:Int = arguments?.getInt("position")?:1
        Log.d("debug", "position: $position")

        listViewModel =
            ViewModelProvider(requireActivity()).get(TechniqueListViewModel::class.java)
        viewModel =
            ViewModelProvider(requireActivity()).get(CurTechniqueViewModel::class.java)

        Log.d("debug", "position: $position")
        Log.d("debug", "Gathering all Detail UI components")
        var liked = view.findViewById<ImageButton>(R.id.btnLike)
        var disliked = view.findViewById<ImageButton>(R.id.btnDislike)

        Log.d("debug", viewModel.curTechnique.value?.id.toString())

        Log.d("debug", "Setting values of project")
        viewModel.curTechnique.observe(viewLifecycleOwner, Observer {
            binding.techniqueTitle.text =  it?.title?:""
            binding.techniqueDesc.text = it?.description?:""
            binding.techniqueAuthors.text = (it?.authors?:"").toString()
            binding.techniqueIngredientslist.text = (it?.ingredients?:"").toString()
            binding.techniqueStepslist.text = (it?.steps?:"").toString()
//            binding.mainThumbnail.setImageResource(it?.mainPhotoRef?:"")
            changeLikedImage(view)
            binding.techniqueTagslist.text = (it?.tags?:"").toString()
        })

//        Log.d("debug", "Setting listeners")
//        binding.editTechnique.setOnClickListener{
//            val action = DetailFragmentDirections.actionDetailFragmentToEditFragment()
//            it.findNavController().navigate(action)
//            Log.d("navigation", "Navigated to edit screen")
//        }
        liked.setOnClickListener { onClickedStar(view) }
        disliked.setOnClickListener { onClickedStar(view) }
    }
    private fun onClickedStar(view: View) {
        Log.d("event", "Clicked Star")
        var currentLikedStatus = viewModel.curTechnique.value?.isLiked?:true

        viewModel.updateCurTechnique(
            viewModel.curTechnique.value?.title?:"",
            viewModel.curTechnique.value?.description?:"",
            viewModel.curTechnique.value?.authors?:arrayOf(),
            viewModel.curTechnique.value?.ingredients?:arrayOf(),
            viewModel.curTechnique.value?.steps?:arrayOf(),
            viewModel.curTechnique.value?.mainPhotoRef?:"",
            !currentLikedStatus,
            viewModel.curTechnique.value?.tags?:arrayOf()
        )
        changeLikedImage(view)
    }

    // method that changes the image per the value of the project isLiked field
    private fun changeLikedImage(view: View){
        val projLiked =  view.findViewById<ImageButton>(R.id.btnLike)
        val projDisliked =  view.findViewById<ImageButton>(R.id.btnDislike)
        if (viewModel.curTechnique.value?.isLiked?:true) {
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