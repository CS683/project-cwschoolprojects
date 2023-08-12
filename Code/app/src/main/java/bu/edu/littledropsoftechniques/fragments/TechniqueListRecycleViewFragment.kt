package bu.edu.littledropsoftechniques.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
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
    lateinit var techniqueListAdapter: TechniqueListRecyclerViewAdapter
    private var _binding: FragmentTechniqueListRecyclerViewBinding? = null
    private val binding get() = _binding!!
    private var columnCount = 1
    private lateinit var viewModel: CurTechniqueViewModel
    private lateinit var listViewModel: TechniqueListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listViewModel =
            ViewModelProvider(this).get(TechniqueListViewModel::class.java)
        listViewModel.setInitialTechniques()

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
        var sharedPref = activity?.getSharedPreferences("favoritesOnlyOn", Context.MODE_PRIVATE)
        var showFavoritesOnly = sharedPref?.getBoolean("favoritesOnlyOn", false)?:false

        binding.favToggle.isChecked = showFavoritesOnly

        viewModel =
            ViewModelProvider(requireActivity()).get(CurTechniqueViewModel::class.java)
        listViewModel =
            ViewModelProvider(this).get(TechniqueListViewModel::class.java)

        binding.techniquelist.apply{
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }

            techniqueListAdapter = TechniqueListRecyclerViewAdapter(
                object : TechniqueListRecyclerViewAdapter.OnTechniqueClickListener {
                    override fun onTechniqueClick(technique: Technique) {
                        viewModel.setCurTechnique(technique)
                    }
                })

            this.adapter = techniqueListAdapter

            listViewModel.techniqueList.observe(viewLifecycleOwner, Observer {
                Log.d("debug", "in observer")
                techniqueListAdapter.replaceItems(it, showFavoritesOnly, binding.searchTechniques.query.toString())
                viewModel.initCurTechnique(techniqueListAdapter.getTechnique(0))
            })

            viewModel.curTechnique.observe(viewLifecycleOwner, Observer {
                techniqueListAdapter.notifyDataSetChanged()
            })
        }

        binding.addTechnique.setOnClickListener {
            val action = actionTechniqueListRecycleViewFragmentToAddFragment()
            it.findNavController().navigate(action)
            //Log.d("navigation", "Navigating to add technique page.")
        }

        binding.searchTechniques.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(msg: String): Boolean {
                // inside on query text change method we are
                // calling a method to filter our recycler view.

                val trimmedMsg = msg.trim()
//                filter(trimmedMsg)

                techniqueListAdapter.replaceItems(listViewModel.techniqueList.value, binding.favToggle.isChecked, trimmedMsg)

                return true
            }
        })

        binding.favToggle.setOnCheckedChangeListener { _, isChecked ->
            val editor = sharedPref?.edit()
            editor?.putBoolean("favoritesOnlyOn", isChecked)
            editor?.commit()

            techniqueListAdapter.replaceItems(listViewModel.techniqueList.value, isChecked, binding.searchTechniques.query.toString())
        }
    }
//
//    fun filter(text: String) {
//        // creating a new array list to filter our data.
//        var filteredTechniques: List<Technique> = mutableListOf()
//        val currentTechniques = listViewModel.techniqueList.value
//
////        listViewModel.techniqueList.observe(viewLifecycleOwner, Observer {
////            for (item in it) {
////                if (text == "" ||
////                    item.title.lowercase().contains(text.lowercase()) ||
////                    item.tags.toString().lowercase().contains(text.lowercase())) {
////                    filteredTechniques += listOf(item)
////                }
////            }
////        })
////        Log.d("debug", "filtered: " + filteredTechniques.toString())
//
//        Log.d("debug", "Current techniques (should be fav only)" + currentTechniques.toString())
//
//        if (currentTechniques != null) {
//            // running a for loop to compare elements.
//            for (item in currentTechniques) {
//                Log.d("debug", item.tags.toString())
//                if (text.toString() == "" ||
//                    item.title.lowercase().contains(text.lowercase()) ||
//                    item.tags.toString().lowercase().contains(text.lowercase())) {
//                    filteredTechniques += listOf(item)
//                }
//            }
//        }
//        if (filteredTechniques.isNotEmpty()) {
//            // at last we are passing that filtered
//            // list to our adapter class
//            Log.d("debug", "Replacing after filtering")
//            (binding.techniquelist.adapter as TechniqueListRecyclerViewAdapter?)?.replaceItems(filteredTechniques, showFavoritesOnly)
//        }
//    }

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