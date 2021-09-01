package hsfl.project.e_storymaker.views.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.databinding.StoryFinderFragmentBinding
import hsfl.project.e_storymaker.roomDB.Entities.story.Story
import hsfl.project.e_storymaker.viewModels.fragmentViewModels.StoryFinderFragVM
import hsfl.project.e_storymaker.views.activities.MainActivity
import hsfl.project.e_storymaker.views.activities.ReadingActivity

class StoryFinderFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: StoryFinderFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = StoryFinderFragment()
    }

    private lateinit var viewModel: StoryFinderFragVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = StoryFinderFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }
    private fun byteArrayToBitmap(data: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(data, 0, data.size)
    }

    private fun populateStoryList(storylist: List<Story>){
        binding.mainStoryFinderStoryCont.removeAllViews()
        storylist.forEach {
            val cardLayout: View = layoutInflater.inflate(R.layout.story_overview_card, null, false)
            cardLayout.findViewById<ImageView>(R.id.storyCard_image).setImageBitmap(byteArrayToBitmap(it.cover))
            cardLayout.findViewById<TextView>(R.id.storyCard_Tags).text = viewModel.storyTags(it.story_uuid)
            cardLayout.findViewById<TextView>(R.id.storyCard_title).text = it.storyTitle
            cardLayout.findViewById<TextView>(R.id.storyFinder_tagList).text = it.description
            cardLayout.findViewById<ImageButton>(R.id.arrow_button).setOnClickListener {
                //Log.d("LibraryFrag", "CLICKED EXP_L")
                var expL: ConstraintLayout = cardLayout.findViewById(R.id.expandable_layout)
                if (expL.visibility == GONE){
                    expL.visibility = VISIBLE
                    //Log.d("LibraryFrag", "Toggled VIS ON")
                }else{
                    expL.visibility = GONE
                    //Log.d("LibraryFrag", "Toggled VIS OFF")
                }
            }

            val storyUUID: String = it.story_uuid
            cardLayout.findViewById<Button>(R.id.storyCard_readB).setOnClickListener {
                val intent = Intent((requireActivity() as MainActivity), ReadingActivity::class.java)
                intent.putExtra("storyID", storyUUID)
                startActivity(intent)
            }

            binding.mainStoryFinderStoryCont.addView(cardLayout)
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        binding.arrowButton.setOnClickListener{
            if (binding.expandableLayout.visibility == GONE){
                binding.expandableLayout.visibility = VISIBLE
            }else{
                binding.expandableLayout.visibility = GONE
            }
        }

        binding.storyFinderSearchB.setOnClickListener{
            viewModel.search(binding.storyFinderSearchTitle.text.toString())

            binding.expandableLayout.visibility = GONE

            //viewModel.getStories()
            populateStoryList(viewModel.CurrentStoryList())
        }

        binding.storyFinderTagAddB.setOnClickListener {
            addTag()
        }

        binding.storyFinderTagsReset.setOnClickListener {
            viewModel.selectedTags.clear()
            binding.storyFinderTagList.text = "No tags selected!"
        }



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StoryFinderFragVM::class.java)
        viewModel.setApplicationContext((requireActivity() as MainActivity).application)
        (requireActivity() as MainActivity).findViewById<TextView>(R.id.mToolbarTitle).setText("Story Finder")
        // TODO: Use the ViewModel

        ArrayAdapter<String>((requireActivity() as MainActivity), android.R.layout.simple_spinner_item, viewModel.allTags()).also {
                adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.storyFinderTagSelector.adapter = adapter
        }
        binding.storyFinderTagSelector.onItemSelectedListener = this
    }

    private fun addTag(){
        val tagToAdd: String = binding.storyFinderTagSelector.selectedItem.toString()
        if (tagToAdd != null && !viewModel.selectedTags.contains(tagToAdd)){
            viewModel.selectedTags.add(binding.storyFinderTagSelector.selectedItem.toString())
            binding.storyFinderTagList.text = viewModel.selectedTags.toString()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        //TODO("Not yet implemented")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //TODO("Not yet implemented")
    }

}