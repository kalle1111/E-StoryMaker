package hsfl.project.e_storymaker.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.databinding.ReviewReadFragmentBinding
import hsfl.project.e_storymaker.roomDB.Entities.rating.Rating
import hsfl.project.e_storymaker.viewModels.fragmentViewModels.ReviewReadFragVM
import hsfl.project.e_storymaker.views.activities.ReviewActivity


class ReviewReadFragment : Fragment() {

    private var _binding: ReviewReadFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ReviewReadFragment()
    }

    private lateinit var viewModel: ReviewReadFragVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ReviewReadFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    private fun populateReviewList(reviewList: List<Rating>){
        binding.reviewReviewReadReviewCont.removeAllViews()
        reviewList.forEach {
            val cardLayout: View = layoutInflater.inflate(R.layout.review_card, null, false)
            cardLayout.findViewById<TextView>(R.id.reviewCard_title).text = "TO_DO_TITLE"
            cardLayout.findViewById<TextView>(R.id.reviewCard_descr).text = "TO_DO_DESCRIPTION"
            cardLayout.findViewById<ImageButton>(R.id.arrow_button).setOnClickListener{
                var expL: ConstraintLayout = cardLayout.findViewById(R.id.expandable_layout)
                if (expL.visibility == GONE){
                    expL.visibility = VISIBLE
                }else{
                    expL.visibility = GONE
                }
            }
            binding.reviewReviewReadReviewCont.addView(cardLayout)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as ReviewActivity).supportActionBar!!.title = "Reviews of: " +  "TODO: STORY_NAME"

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_ReviewRead_to_ReviewWrite)
        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReviewReadFragVM::class.java)
        viewModel.setApplicationContext((requireActivity() as ReviewActivity).application)
        //binding.viewModel = viewModel

        populateReviewList(viewModel.CurrentReviewList())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}