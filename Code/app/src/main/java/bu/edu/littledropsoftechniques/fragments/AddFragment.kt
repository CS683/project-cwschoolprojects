package bu.edu.littledropsoftechniques.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import bu.edu.littledropsofingredients.adapters.IngredientsListRecyclerViewAdapter
import bu.edu.littledropsofingredients.adapters.StepsListRecyclerViewAdapter
import bu.edu.littledropsofingredients.adapters.TagsListRecyclerViewAdapter
import bu.edu.littledropsoftechniques.datalayer.Technique.Technique
import bu.edu.littledropsoftechniques.viewmodels.TechniqueListViewModel
import bu.edu.littledropsoftechniques.databinding.FragmentAddBinding
import bu.edu.littledropsoftechniques.fragments.AddFragmentDirections.*
import bu.edu.littledropsoftechniques.viewmodels.CurTechniqueViewModel
import bu.edu.littledropsoftechniques.viewmodels.StringsListViewModel

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var listViewModel: TechniqueListViewModel
    private lateinit var viewModel: CurTechniqueViewModel
    private lateinit var stringsListViewModel: StringsListViewModel
    private lateinit var ingredientsListAdapter: IngredientsListRecyclerViewAdapter
    private lateinit var stepsListAdapter: StepsListRecyclerViewAdapter
    private lateinit var tagsListAdapter: TagsListRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        stringsListViewModel =
            ViewModelProvider(this).get(StringsListViewModel::class.java)
        stringsListViewModel.setTechniqueIngredientsAndSteps(-1) //to ensure it doesn't get any data since this is new
    }

    // On create view fragment override
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    // View created event override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        stringsListViewModel =
            ViewModelProvider(this).get(StringsListViewModel::class.java)
        listViewModel =
            ViewModelProvider(requireActivity()).get(TechniqueListViewModel::class.java)
        viewModel =
            ViewModelProvider(requireActivity()).get(CurTechniqueViewModel::class.java)

        binding.techniqueAddCurrentIngredientsList.apply{
            ingredientsListAdapter = IngredientsListRecyclerViewAdapter()
            this.adapter = ingredientsListAdapter

            stringsListViewModel.ingredientsList.observe(viewLifecycleOwner, Observer {
                ingredientsListAdapter.replaceIngredientItems(it)
            })
        }

        binding.techniqueAddCurrentStepsList.apply{
            stepsListAdapter = StepsListRecyclerViewAdapter()
            this.adapter = stepsListAdapter

            stringsListViewModel.stepsList.observe(viewLifecycleOwner, Observer {
                stepsListAdapter.replaceStepItems(it)
            })
        }

        binding.techniqueAddCurrentTagsList.apply{
            tagsListAdapter = TagsListRecyclerViewAdapter()
            this.adapter = tagsListAdapter

            stringsListViewModel.tagsList.observe(viewLifecycleOwner, Observer {
                tagsListAdapter.replaceTagItems(it)
            })
        }

        binding.addIngredientBtn.setOnClickListener {
            stringsListViewModel.addIngredient(binding.techniqueIngredientslistAdd.text.toString())
            ingredientsListAdapter.replaceIngredientItems(stringsListViewModel.addIngredientsList)
            binding.techniqueTagslistAdd.setText("")
        }

        binding.addStepBtn.setOnClickListener {
            stringsListViewModel.addStep(binding.techniqueStepslistAdd.text.toString())
            stepsListAdapter.replaceStepItems(stringsListViewModel.addStepsList)
            binding.techniqueTagslistAdd.setText("")
        }

        binding.addTagBtn.setOnClickListener {
            stringsListViewModel.addTag(binding.techniqueTagslistAdd.text.toString())
            tagsListAdapter.replaceTagItems(stringsListViewModel.addTagsList)
            binding.techniqueTagslistAdd.setText("")
        }

        //Log.d("debug", "Setting listeners")
        binding.submitAdd.setOnClickListener {

            //Log.d("debug", "Getting new technique values.")
            var newTitle = binding.techniqueTitleAdd.text.toString()
            var newDescription = binding.techniqueDescAdd.text.toString()
            var newAuthors = listOf(binding.techniqueAuthorsAdd.text.toString())
            var newIngredients = stringsListViewModel.addIngredientsList
            var newSteps = stringsListViewModel.addStepsList
            var newTags = stringsListViewModel.addTagsList

            //Log.d("debug", "Creating technique object")
            val newTechnique = Technique(
                0,
                newTitle,
                newDescription,
                newAuthors,
                newIngredients,
                newSteps,
                "",
                false,
                newTags
            )

            //Log.d("debug", "Adding technique to mutable list")
            listViewModel.addTechnique(newTechnique)

            //Log.d("debug", "Setting new technique to current project")
            viewModel.setCurTechnique(newTechnique)

            val action = actionAddFragmentToTechniqueListRecycleViewFragment()
            it.findNavController().navigate(action)
            //Log.d("navigation", "Navigating back to home page.")
        }

        binding.cancelAdd.setOnClickListener {
            //Log.d("debug", "User cancelled add page.")
            val action = actionAddFragmentToTechniqueListRecycleViewFragment()
            it.findNavController().navigate(action)
            //Log.d("navigation", "Navigating back to home page.")
        }
    }
}