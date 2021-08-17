package hsfl.project.e_storymaker.views.fragments

import android.content.Intent
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import hsfl.project.e_storymaker.viewModels.MainVM
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.databinding.LibraryFragmentBinding
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.viewModels.fragmentViewModels.LibraryFragVM
import hsfl.project.e_storymaker.views.activities.MainActivity
import hsfl.project.e_storymaker.views.activities.WritingActivity
import java.util.zip.Inflater

class LibraryFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: LibraryFragmentBinding? = null
    private val binding get() = _binding!!



    companion object {
        fun newInstance() = LibraryFragment()
    }

    private lateinit var viewModel: LibraryFragVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LibraryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun populateStoryList(storylist: List<Story>){
        binding.mainLibraryStoryCont.removeAllViews()
        storylist.forEach {
            val cardLayout: View = layoutInflater.inflate(R.layout.story_overview_card, null, false)
            cardLayout.findViewById<TextView>(R.id.storyCard_title).text = it.storyTitle
            binding.mainLibraryStoryCont.addView(cardLayout)
        }
    }

    fun switchActiveButtons(buttonPressed: Int){

        binding.mainLibraryOriginalB.setBackgroundColor(Color.parseColor("#0A25B8"))
        binding.mainLibraryOriginalB.setTextColor(Color.WHITE)

        binding.mainLibraryFavoriteB.setBackgroundColor(Color.parseColor("#0A25B8"))
        binding.mainLibraryFavoriteB.setTextColor(Color.WHITE)

        binding.mainLibraryReviewedB.setBackgroundColor(Color.parseColor("#0A25B8"))
        binding.mainLibraryReviewedB.setTextColor(Color.WHITE)

        when (buttonPressed){
            1 -> {
                binding.mainLibraryOriginalB.setBackgroundColor(Color.WHITE)
                binding.mainLibraryOriginalB.setTextColor(Color.parseColor("#0A25B8"))
            }

            2 -> {
                binding.mainLibraryFavoriteB.setBackgroundColor(Color.WHITE)
                binding.mainLibraryFavoriteB.setTextColor(Color.parseColor("#0A25B8"))
            }

            3 -> {
                binding.mainLibraryReviewedB.setBackgroundColor(Color.WHITE)
                binding.mainLibraryReviewedB.setTextColor(Color.parseColor("#0A25B8"))
            }

            else -> {

            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mainLibraryOriginalB.setOnClickListener{
            if (viewModel.libraryMode == 1){
                viewModel.libraryMode = 0
            }else{
                viewModel.libraryMode = 1
            }
            switchActiveButtons(viewModel.libraryMode)
            viewModel.getStories()
            populateStoryList(viewModel.CurrentStoryList())
        }

        binding.mainLibraryFavoriteB.setOnClickListener {
            if (viewModel.libraryMode == 2){
                viewModel.libraryMode = 0
            }else{
                viewModel.libraryMode = 2
            }
            switchActiveButtons(viewModel.libraryMode)
            viewModel.getStories()
            populateStoryList(viewModel.CurrentStoryList())
        }

        binding.mainLibraryReviewedB.setOnClickListener {
            if (viewModel.libraryMode == 3){
                viewModel.libraryMode = 0
            }else{
                viewModel.libraryMode = 3

            }
            switchActiveButtons(viewModel.libraryMode)
            viewModel.getStories()
            populateStoryList(viewModel.CurrentStoryList())
        }

        binding.button12.setOnClickListener {
            val intent = Intent((requireActivity() as MainActivity), WritingActivity::class.java)
            startActivity(intent)
        }

        ArrayAdapter.createFromResource(
            (requireActivity() as MainActivity),
            R.array.sort_spinner_arr,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.mainLibrarySpinner.adapter = adapter
        }
        binding.mainLibrarySpinner.onItemSelectedListener = this
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LibraryFragVM::class.java)
        viewModel.setApplicationContext((requireActivity() as MainActivity).application)
        binding.viewmodel = viewModel
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        //TODO("Not yet implemented")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        //TODO("Not yet implemented")
    }

}