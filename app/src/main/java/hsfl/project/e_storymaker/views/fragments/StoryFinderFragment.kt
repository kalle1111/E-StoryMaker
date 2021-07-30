package hsfl.project.e_storymaker.views.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import android.view.View.GONE
import android.view.View.VISIBLE
import hsfl.project.e_storymaker.viewModels.MainVM
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.databinding.StoryFinderFragmentBinding
import hsfl.project.e_storymaker.viewModels.fragmentViewModels.StoryFinderFragVM

class StoryFinderFragment : Fragment() {

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.arrowButton.setOnClickListener{
            if (binding.expandableLayout.visibility == GONE){
                binding.expandableLayout.visibility = VISIBLE
            }else{
                binding.expandableLayout.visibility = GONE
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StoryFinderFragVM::class.java)
        // TODO: Use the ViewModel
    }

}