package bu.edu.littledropsoftechniques

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import bu.edu.littledropsoftechniques.databinding.FragmentProjListRecyclerViewBinding

/**
 * A fragment representing a list of Items.
 */
class ProjListRecycleViewFragment : Fragment() {
    private var _binding: FragmentProjListRecyclerViewBinding? = null
    private val binding get() = _binding!!

    private var columnCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentProjListRecyclerViewBinding.inflate(inflater,
            container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.projlist?.apply{
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
            adapter = MyProjListRecyclerViewAdapter(Project.projects)
        }

        binding.addProject.setOnClickListener {
            val action = ProjListRecycleViewFragmentDirections.actionProjListRecycleViewFragmentToAddFragment()
            it.findNavController().navigate(action)
            Log.d("navigation", "Navigating to add project page.")
        }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
                ProjListRecycleViewFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_COLUMN_COUNT, columnCount)
                    }
                }
    }
}