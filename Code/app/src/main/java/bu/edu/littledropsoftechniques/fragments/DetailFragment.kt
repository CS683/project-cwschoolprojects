package bu.edu.littledropsoftechniques.fragments
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import bu.edu.littledropsofingredients.adapters.IngredientsListRecyclerViewAdapter
import bu.edu.littledropsofingredients.adapters.StepsListRecyclerViewAdapter
import bu.edu.littledropsofingredients.adapters.TagsListRecyclerViewAdapter
import bu.edu.littledropsoftechniques.R
import bu.edu.littledropsoftechniques.viewmodels.TechniqueListViewModel
import bu.edu.littledropsoftechniques.databinding.FragmentDetailBinding
import bu.edu.littledropsoftechniques.viewmodels.CurTechniqueViewModel
import bu.edu.littledropsoftechniques.viewmodels.StringsListViewModel

//Detail View Fragment
class DetailFragment : Fragment() {
    private var columnCount = 1
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CurTechniqueViewModel
    private lateinit var listViewModel: TechniqueListViewModel
    private lateinit var stringsListViewModel: StringsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel =
            ViewModelProvider(requireActivity()).get(CurTechniqueViewModel::class.java)

        stringsListViewModel =
            ViewModelProvider(this).get(StringsListViewModel::class.java)

        val curTechniqueID = viewModel.curTechnique.value?.id
        if (curTechniqueID != null) {
            stringsListViewModel.setTechniqueIngredientsAndSteps(curTechniqueID.toLong())
        }

        arguments?.let {
            columnCount = it.getInt(DetailFragment.ARG_COLUMN_COUNT)
        }
    }

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

        listViewModel =
            ViewModelProvider(requireActivity()).get(TechniqueListViewModel::class.java)
        viewModel =
            ViewModelProvider(requireActivity()).get(CurTechniqueViewModel::class.java)

        //Log.d("debug", "Gathering all Detail UI components")
        var liked = view.findViewById<ImageButton>(R.id.btnLike)
        var disliked = view.findViewById<ImageButton>(R.id.btnDislike)

        //Log.d("debug", "Setting values of project")
        viewModel.curTechnique.observe(viewLifecycleOwner, Observer {
            binding.techniqueTitle.text =  it?.title?:"".uppercase()
            binding.techniqueDesc.text = it?.description?:""
            binding.techniqueAuthors.text = it?.authors?.joinToString(",")
            DownloadImageFromInternet().execute(it?.mainPhotoRef)
            changeLikedImage(view)
        })

        binding.techniqueIngredientslist.apply{
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }

            val ingredientsListAdapter = IngredientsListRecyclerViewAdapter(null)
            this.adapter = ingredientsListAdapter

            stringsListViewModel.ingredientsList.observe(viewLifecycleOwner, Observer {
                ingredientsListAdapter.replaceIngredientItems(it)
            })
        }

        binding.techniqueStepslist.apply{
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }

            val stepsListAdapter = StepsListRecyclerViewAdapter(null)
            this.adapter = stepsListAdapter

            stringsListViewModel.stepsList.observe(viewLifecycleOwner, Observer {
                stepsListAdapter.replaceStepItems(it)
            })
        }

        binding.techniqueTagslist.apply{
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }

            val tagsListAdapter = TagsListRecyclerViewAdapter(null)
            this.adapter = tagsListAdapter

            stringsListViewModel.tagsList.observe(viewLifecycleOwner, Observer {
                tagsListAdapter.replaceTagItems(it)
            })
        }

        liked.setOnClickListener { onClickedStar(view) }
        disliked.setOnClickListener { onClickedStar(view) }
    }
    private fun onClickedStar(view: View) {
        //Log.d("event", "Clicked Star")
        var currentLikedStatus = viewModel.curTechnique.value?.isLiked?:true

        viewModel.updateCurTechnique(
            viewModel.curTechnique.value?.title?:"".uppercase(),
            viewModel.curTechnique.value?.description?:"",
            viewModel.curTechnique.value?.authors?:listOf(),
            viewModel.curTechnique.value?.ingredients?:listOf(),
            viewModel.curTechnique.value?.steps?:listOf(),
            viewModel.curTechnique.value?.mainPhotoRef?:"",
            !currentLikedStatus,
            viewModel.curTechnique.value?.tags?:listOf()
        )
        changeLikedImage(view)
    }

    // method that changes the image per the value of the project isLiked field
    private fun changeLikedImage(view: View){
        val projLiked =  view.findViewById<ImageButton>(R.id.btnLike)
        val projDisliked =  view.findViewById<ImageButton>(R.id.btnDislike)
        if (viewModel.curTechnique.value?.isLiked?:true) {
            //Log.d("changes", "Project liked")
            projLiked.visibility = View.VISIBLE
            projDisliked.visibility = View.INVISIBLE
        }
        else {
            //Log.d("changes", "Project unliked")
            projLiked.visibility = View.INVISIBLE
            projDisliked.visibility = View.VISIBLE
        }
    }

    @SuppressLint("StaticFieldLeak")
    @Suppress("DEPRECATION")
    private inner class DownloadImageFromInternet() : AsyncTask<String, Void, Bitmap?>() {
        override fun doInBackground(vararg urls: String): Bitmap? {
            val imageURL = urls[0]
            var image: Bitmap? = null
            try {
                val `in` = java.net.URL(imageURL).openStream()
                val largeBitmap = BitmapFactory.decodeStream(`in`)
                val h = binding.mainThumbnail.height // height in pixels
                val w = binding.mainThumbnail.width // width in pixels
                image = Bitmap.createScaledBitmap(largeBitmap, w, h, true)
            }
            catch (e: Exception) {
            }
            return image
        }
        override fun onPostExecute(result: Bitmap?) {
            if (result != null) {
                binding.mainThumbnail.setImageBitmap(result)
            }
        }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            TechniqueListRecycleViewFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}