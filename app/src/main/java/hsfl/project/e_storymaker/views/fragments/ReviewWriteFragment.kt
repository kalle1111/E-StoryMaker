package hsfl.project.e_storymaker.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.databinding.ReviewWriteFragmentBinding
import hsfl.project.e_storymaker.viewModels.fragmentViewModels.ProfileFragVM
import hsfl.project.e_storymaker.viewModels.fragmentViewModels.ReviewWriteFragVM


class ReviewWriteFragment : Fragment() {

    private var _binding: ReviewWriteFragmentBinding? = null

    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ReviewWriteFragment()
    }

    private lateinit var viewModel: ReviewWriteFragVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = ReviewWriteFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReviewWriteFragVM::class.java)
        //viewmodel. set Context
        //binding.viewmodel = viewmodel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}