package bu.edu.littledropsoftechniques.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import bu.edu.littledropsoftechniques.datalayer.Technique.Technique
import bu.edu.littledropsoftechniques.adapters.TechniqueListRecyclerViewAdapter
import bu.edu.littledropsoftechniques.viewmodels.TechniqueListViewModel
import bu.edu.littledropsoftechniques.databinding.FragmentTechniqueListRecyclerViewBinding
import bu.edu.littledropsoftechniques.fragments.TechniqueListRecycleViewFragmentDirections.*
import bu.edu.littledropsoftechniques.viewmodels.CurTechniqueViewModel

/**
 * A fragment representing a list of Techniques.
 */
class TechniqueListRecycleViewFragment : Fragment() {
//    lateinit var adapter: TechniqueListRecyclerViewAdapter
    private var _binding: FragmentTechniqueListRecyclerViewBinding? = null
    private val binding get() = _binding!!
    private var columnCount = 1
    private lateinit var viewModel: CurTechniqueViewModel
    private lateinit var listViewModel: TechniqueListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentTechniqueListRecyclerViewBinding.inflate(inflater,
            container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.techniquelist?.apply{
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }
//            adapter = TechniqueListRecyclerViewAdapter(Technique.techniques)

            viewModel =
                ViewModelProvider(requireActivity()).get(CurTechniqueViewModel::class.java)
            listViewModel =
                ViewModelProvider(requireActivity()).get(TechniqueListViewModel::class.java)
//                ViewModelProvider(this).get(TechniqueListViewModel::class.java)

            val techniqueListAdapter = TechniqueListRecyclerViewAdapter(
                object : TechniqueListRecyclerViewAdapter.OnTechniqueClickListener {
                    override fun onTechniqueClick(technique: Technique) {
                        viewModel.setCurTechnique(technique)
                    }
                })

            this.adapter = techniqueListAdapter

//            listViewModel.projectList.observe(viewLifecycleOwner, Observer {
//                techniqueListAdapter.replaceItems(it)
//                viewModel.initCurProject(techniqueListAdapter.getTechnique(0))
//            })
        }

        binding.addTechnique.setOnClickListener {
            val action = actionTechniqueListRecycleViewFragmentToAddFragment()
            it.findNavController().navigate(action)
            Log.d("navigation", "Navigating to add technique page.")
        }

        binding.searchTechniques.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(msg: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(msg)
                return true
            }
        })
    }

    fun filter(text: String) {
        // creating a new array list to filter our data.
        var filteredTechniques: List<Technique> = mutableListOf()
        val currentTechniques = listViewModel.techniqueList.value

        if (currentTechniques != null) {
            // running a for loop to compare elements.
            for (item in currentTechniques) {
                // checking if the entered string matched with any item of our recycler view.
                if (item.title.lowercase().contains(text.lowercase())) {
                    // if the item is matched we are
                    // adding it to our filtered list.
                    filteredTechniques += listOf(item)
                }
            }
        }
        if (filteredTechniques.isNotEmpty()) {
            // at last we are passing that filtered
            // list to our adapter class
            (binding.techniquelist.adapter as TechniqueListRecyclerViewAdapter?)?.filterList(filteredTechniques)
        }
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"
//
//        // TODO: Customize parameter initialization
//        @JvmStatic
//        fun newInstance(columnCount: Int) =
//                TechniqueListRecycleViewFragment().apply {
//                    arguments = Bundle().apply {
//                        putInt(ARG_COLUMN_COUNT, columnCount)
//                    }
//                }
    }
}