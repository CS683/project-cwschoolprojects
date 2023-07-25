package bu.edu.littledropsoftechniques
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import bu.edu.littledropsoftechniques.databinding.FragmentEditBinding

// Edit View Fragment
class EditFragment : Fragment() {

    // Initializing variables
    private var _binding: FragmentEditBinding? = null
    private val binding get() = _binding!!

    // On create view fragment override
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    // View created event override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        val position:Int = arguments?.getInt("position")?:0
        Log.d("debug", "position: $position")

        Log.d("debug", "Set bind values")
        binding.projTitleEdit.setText(Project.projects[position].title)
        binding.projDescEdit.setText(Project.projects[position].description)
        binding.projAuthorsEdit.setText(Project.projects[position].authors)
        binding.projUrlsEdit.setText(Project.projects[position].urls)
        binding.projKeywordsEdit.setText(Project.projects[position].keywords)
        binding.projCreatedDateEdit.setText(Project.projects[position].dateCreated)

        Log.d("debug", "Setting listeners")
        binding.submit.setOnClickListener {

            Log.d("debug", "Setting text per changes to edit page.")
            Project.projects[position].title = binding.projTitleEdit.text.toString()
            Project.projects[position].description = binding.projDescEdit.text.toString()
            Project.projects[position].authors = binding.projAuthorsEdit.text.toString()
            Project.projects[position].urls = binding.projUrlsEdit.text.toString()
            Project.projects[position].keywords = binding.projKeywordsEdit.text.toString()
            Project.projects[position].dateCreated = binding.projCreatedDateEdit.text.toString()

            val action = EditFragmentDirections.actionEditFragmentPop(position)
            it.findNavController().navigate(action)

            Log.d("navigation", "Navigating back to details page.")
        }
        binding.cancel.setOnClickListener {
            Log.d("debug", "User cancelled edit page.")
            val action = EditFragmentDirections.actionEditFragmentPop(position)
            it.findNavController().navigate(action)

            Log.d("navigation", "Navigating back to details page.")
        }
    }
}