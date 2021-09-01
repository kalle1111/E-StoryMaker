package hsfl.project.e_storymaker.views.fragments

import android.media.Rating
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.databinding.ReviewWriteFragmentBinding
import hsfl.project.e_storymaker.viewModels.fragmentViewModels.ProfileFragVM
import hsfl.project.e_storymaker.viewModels.fragmentViewModels.ReviewWriteFragVM
import hsfl.project.e_storymaker.views.activities.ReviewActivity


class ReviewWriteFragment : Fragment(), RatingBar.OnRatingBarChangeListener {

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
            viewModel.SendReview("SName?", binding.reviewWriteTitle.text.toString(), binding.reviewWriteOverall.rating, binding.reviewWriteStyle.rating,
                binding.reviewWriteStory.rating, binding.reviewWriteGrammar.rating, binding.reviewWriteChar.rating, binding.reviewWriteText.text.toString())
            (requireActivity() as ReviewActivity).finish()
            //findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReviewWriteFragVM::class.java)
        val storyID: String? = arguments?.getString("storyID")
        viewModel.setApplicationContext((requireActivity() as ReviewActivity).application, storyID!!)
        //binding.viewmodel = viewmodel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
        TODO("Not yet implemented")
    }
}