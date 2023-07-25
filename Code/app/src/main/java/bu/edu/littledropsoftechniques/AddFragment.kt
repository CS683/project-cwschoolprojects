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
    private lateinit var projTitleAdd: EditText
    private lateinit var projDescAdd: EditText
    private lateinit var projAuthorsAdd: EditText
    private lateinit var projUrlsAdd: EditText
    private lateinit var projKeywordsAdd: EditText
    private lateinit var projDateCreatedAdd: EditText
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
        projTitleAdd = view.findViewById(R.id.projTitleAdd)
        projDescAdd =  view.findViewById(R.id.projDescAdd)
        projAuthorsAdd =  view.findViewById(R.id.projAuthorsAdd)
        projUrlsAdd =  view.findViewById(R.id.projUrlsAdd)
        projKeywordsAdd =  view.findViewById(R.id.projKeywordsAdd)
        projDateCreatedAdd =  view.findViewById(R.id.projCreatedDateAdd)
        submitAdd = view.findViewById(R.id.submitAdd)
        cancelAdd = view.findViewById(R.id.cancelAdd)

        Log.d("debug", "Setting listeners")
        submitAdd.setOnClickListener {

            Log.d("debug", "Getting new project values.")
            var newTitle = projTitleAdd.text.toString()
            var newDescription = projDescAdd.text.toString()
            var newAuthors = projAuthorsAdd.text.toString()
            var newUrls = projUrlsAdd.text.toString()
            var newKeywords = projKeywordsAdd.text.toString()
            var newDateCreated = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

            Log.d("debug", "Creating project object")
            val newProject = Project(
                Project.projects.size,
                newTitle,
                newDescription,
                newAuthors,
                newUrls,
                false,
                newKeywords,
                newDateCreated
            )

            Log.d("debug", "Adding project to mutable list")
            Project.projects += listOf(newProject)

            val action = AddFragmentDirections.actionAddFragmentToProjListRecycleViewFragment()
            it.findNavController().navigate(action)
            Log.d("navigation", "Navigating back to home page.")
        }

        cancelAdd.setOnClickListener {
            Log.d("debug", "User cancelled add page.")
            val action = AddFragmentDirections.actionAddFragmentToProjListRecycleViewFragment()
            it.findNavController().navigate(action)
            Log.d("navigation", "Navigating back to home page.")
        }
    }
}