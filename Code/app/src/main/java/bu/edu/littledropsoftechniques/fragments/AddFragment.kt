package bu.edu.littledropsoftechniques.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import bu.edu.littledropsoftechniques.datalayer.Technique.Technique
import bu.edu.littledropsoftechniques.viewmodels.TechniqueListViewModel
import bu.edu.littledropsoftechniques.databinding.FragmentAddBinding
import bu.edu.littledropsoftechniques.fragments.AddFragmentDirections.*
import bu.edu.littledropsoftechniques.viewmodels.CurTechniqueViewModel

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private lateinit var listViewModel: TechniqueListViewModel
    private lateinit var viewModel: CurTechniqueViewModel

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

        listViewModel =
            ViewModelProvider(requireActivity()).get(TechniqueListViewModel::class.java)
        viewModel =
            ViewModelProvider(requireActivity()).get(CurTechniqueViewModel::class.java)

        Log.d("debug", "Setting listeners")
        binding.submitAdd.setOnClickListener {

            Log.d("debug", "Getting new technique values.")
            var newTitle = binding.techniqueTitleAdd.text.toString()
            var newDescription = binding.techniqueDescAdd.text.toString()
            var newAuthors = listOf(binding.techniqueAuthorsAdd.text.toString())
            var newIngredients = listOf(binding.techniqueIngredientslistAdd.text.toString())
            var newSteps = listOf(binding.techniqueStepslistAdd.text.toString())
            var newTags = listOf(binding.techniqueTagslistAdd.text.toString())

            Log.d("debug", "Creating technique object")
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

            Log.d("debug", "Adding technique to mutable list")
            listViewModel.addTechnique(newTechnique)

            Log.d("debug", "Setting new technique to current project")
            viewModel.setCurTechnique(newTechnique)

            val action = actionAddFragmentToTechniqueListRecycleViewFragment()
            it.findNavController().navigate(action)
            Log.d("navigation", "Navigating back to home page.")
        }

        binding.cancelAdd.setOnClickListener {
            Log.d("debug", "User cancelled add page.")
            val action = actionAddFragmentToTechniqueListRecycleViewFragment()
            it.findNavController().navigate(action)
            Log.d("navigation", "Navigating back to home page.")
        }
    }
}