package hsfl.project.e_storymaker.views.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hsfl.project.e_storymaker.viewModels.MainVM
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.viewModels.fragmentViewModels.StoryFinderFragVM

class StoryFinderFragment : Fragment() {

    companion object {
        fun newInstance() = StoryFinderFragment()
    }

    private lateinit var viewModel: StoryFinderFragVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.story_finder_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StoryFinderFragVM::class.java)
        // TODO: Use the ViewModel
    }

}