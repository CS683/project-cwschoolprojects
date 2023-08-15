package bu.edu.littledropsoftechniques.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import bu.edu.littledropsoftechniques.adapters.TechniqueListRecyclerViewAdapter
import bu.edu.littledropsoftechniques.databinding.FragmentTechniqueListRecyclerViewBinding
import bu.edu.littledropsoftechniques.datalayer.Technique.Technique
import bu.edu.littledropsoftechniques.fragments.TechniqueListRecycleViewFragmentDirections.actionTechniqueListRecycleViewFragmentToAddFragment
import bu.edu.littledropsoftechniques.viewmodels.CurTechniqueViewModel
import bu.edu.littledropsoftechniques.viewmodels.TechniqueListViewModel


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
        var showFavoritesOnly = sharedPref?.getBoolean("favoritesOnlyOn", false) ?: false

        binding.favToggle.isChecked = showFavoritesOnly

        viewModel =
            ViewModelProvider(requireActivity()).get(CurTechniqueViewModel::class.java)
        listViewModel =
            ViewModelProvider(this).get(TechniqueListViewModel::class.java)

        binding.techniquelist.apply {
            layoutManager = when {
                columnCount <= 1 -> LinearLayoutManager(context)
                else -> GridLayoutManager(context, columnCount)
            }

            techniqueListAdapter = TechniqueListRecyclerViewAdapter(
                object : TechniqueListRecyclerViewAdapter.OnTechniqueClickListener {
                    override fun onTechniqueClick(technique: Technique) {
                        viewModel.setCurTechnique(technique)
                    }
                },
                object : TechniqueListRecyclerViewAdapter.OnFavoriteClickListener {
                    override fun onFavoriteClick(technique: Technique) {
                        var position = technique.id
                        viewModel.setCurTechnique(techniqueListAdapter.getTechnique(position))
                        var currentLikedStatus = viewModel.curTechnique.value?.isLiked ?: true

                        viewModel.updateCurTechnique(
                            viewModel.curTechnique.value?.title ?: "".uppercase(),
                            viewModel.curTechnique.value?.description ?: "",
                            viewModel.curTechnique.value?.authors ?: listOf(),
                            viewModel.curTechnique.value?.ingredients ?: listOf(),
                            viewModel.curTechnique.value?.steps ?: listOf(),
                            viewModel.curTechnique.value?.mainPhotoRef ?: "",
                            !currentLikedStatus,
                            viewModel.curTechnique.value?.tags ?: listOf()
                        )
                    }
                }
            )

            this.adapter = techniqueListAdapter

            listViewModel.techniqueList.observe(viewLifecycleOwner, Observer {
                Log.d("debug", "in observer")
                techniqueListAdapter.replaceItems(
                    it,
                    showFavoritesOnly,
                    binding.searchTechniques.query.toString()
                )
                viewModel.initCurTechnique(techniqueListAdapter.getTechnique(1))
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
                techniqueListAdapter.replaceItems(
                    listViewModel.techniqueList.value,
                    binding.favToggle.isChecked,
                    trimmedMsg
                )

                return true
            }
        })

        binding.favToggle.setOnCheckedChangeListener { _, isChecked ->
            val editor = sharedPref?.edit()
            editor?.putBoolean("favoritesOnlyOn", isChecked)
            editor?.commit()

            techniqueListAdapter.replaceItems(
                listViewModel.techniqueList.value,
                isChecked,
                binding.searchTechniques.query.toString()
            )
        }

        binding.searchTechniques.setOnQueryTextFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            Log.d("debug", "Made it")
            if (!hasFocus) {
                val inputManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.hideSoftInputFromWindow(
                    binding.searchTechniques.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS
                )
            }
        })

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