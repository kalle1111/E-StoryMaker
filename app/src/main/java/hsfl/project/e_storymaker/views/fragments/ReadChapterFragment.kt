package hsfl.project.e_storymaker.views.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hsfl.project.e_storymaker.databinding.ReadChapterFragmentBinding
import hsfl.project.e_storymaker.viewModels.fragmentViewModels.ReadChapterFragVM
import hsfl.project.e_storymaker.views.activities.ReadingActivity

class ReadChapterFragment : Fragment() {

    private var _binding: ReadChapterFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ReadChapterFragment()
    }

    private lateinit var viewModel: ReadChapterFragVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ReadChapterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReadChapterFragVM::class.java)
        viewModel.setApplicationContext((requireActivity() as ReadingActivity).application)

        binding.viewmodel = viewModel
    }

}