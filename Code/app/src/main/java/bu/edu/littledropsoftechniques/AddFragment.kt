package bu.edu.littledropsoftechniques

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.findNavController
import bu.edu.littledropsoftechniques.databinding.FragmentAddBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddFragment : Fragment() {

    // Initializing variables
    private lateinit var techniqueTitleAdd: EditText
    private lateinit var techniqueDescAdd: EditText
    private lateinit var techniqueAuthorsAdd: EditText
    private lateinit var techniqueIngredientsAdd: EditText
//    private lateinit var thumbnail: EditText
    private lateinit var techniqueStepsAdd: EditText
    private lateinit var techniqueTagsAdd: EditText
    private lateinit var submitAdd:Button
    private lateinit var cancelAdd:Button

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

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

        Log.d("debug", "Gathering all Edit UI components")
        techniqueTitleAdd = view.findViewById(R.id.techniqueTitleAdd)
        techniqueDescAdd =  view.findViewById(R.id.techniqueDescAdd)
        techniqueAuthorsAdd =  view.findViewById(R.id.techniqueAuthorsAdd)
        techniqueIngredientsAdd =  view.findViewById(R.id.techniqueIngredientslistAdd)
        techniqueStepsAdd =  view.findViewById(R.id.techniqueStepslistAdd)
        techniqueTagsAdd =  view.findViewById(R.id.techniqueTagslistAdd)
        submitAdd = view.findViewById(R.id.submitAdd)
        cancelAdd = view.findViewById(R.id.cancelAdd)

        Log.d("debug", "Setting listeners")
        submitAdd.setOnClickListener {

            Log.d("debug", "Getting new technique values.")
            var newTitle = techniqueTitleAdd.text.toString()
            var newDescription = techniqueDescAdd.text.toString()
            var newAuthors = techniqueAuthorsAdd.text.toString()
            var newIngredients = mutableListOf(techniqueIngredientsAdd.text.toString())
            var newSteps = mutableListOf(techniqueStepsAdd.text.toString())
            var newTags = mutableListOf(techniqueTagsAdd.text.toString())

            Log.d("debug", "Creating technique object")
            val newTechnique = Technique(
                Technique.techniques.size,
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
            Technique.techniques += listOf(newTechnique)

            val action = AddFragmentDirections.actionAddFragmentToTechniqueListRecycleViewFragment()
            it.findNavController().navigate(action)
            Log.d("navigation", "Navigating back to home page.")
        }

        cancelAdd.setOnClickListener {
            Log.d("debug", "User cancelled add page.")
            val action = AddFragmentDirections.actionAddFragmentToTechniqueListRecycleViewFragment()
            it.findNavController().navigate(action)
            Log.d("navigation", "Navigating back to home page.")
        }
    }
}