package hsfl.project.e_storymaker.views.fragments

import android.content.Intent
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
import hsfl.project.e_storymaker.views.activities.ReadingActivity
import hsfl.project.e_storymaker.views.activities.ReviewActivity

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

        binding.readOverviewReviewsB.setOnClickListener {
            val intent = Intent((requireActivity() as ReadingActivity), ReviewActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReadOverviewFragVM::class.java)
        viewModel.setApplicationContext((requireActivity() as ReadingActivity).application)
        binding.viewmodel = viewModel
    }

}