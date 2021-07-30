package hsfl.project.e_storymaker.views.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.databinding.ReadOverviewFragmentBinding
import hsfl.project.e_storymaker.viewModels.fragmentViewModels.ReadOverviewFragVM

class ReadOverviewFragment : Fragment() {

    private var _binding: ReadOverviewFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ReadOverviewFragment()
    }

    private lateinit var viewModel: ReadOverviewFragVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ReadOverviewFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button5.setOnClickListener{
            findNavController().navigate(R.id.action_ReadOverview_to_ReadChapter)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReadOverviewFragVM::class.java)
        // TODO: Use the ViewModel
    }

}