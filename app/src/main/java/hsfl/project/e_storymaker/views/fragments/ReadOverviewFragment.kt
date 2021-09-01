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
import android.widget.RatingBar
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.databinding.ReadOverviewFragmentBinding

import hsfl.project.e_storymaker.viewModels.fragmentViewModels.ReadOverviewFragVM
import hsfl.project.e_storymaker.views.activities.MainActivity
import hsfl.project.e_storymaker.views.activities.ReadingActivity
import hsfl.project.e_storymaker.views.activities.ReviewActivity

class ReadOverviewFragment : Fragment(), RatingBar.OnRatingBarChangeListener {

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
            val bundle = bundleOf("storyID" to viewModel.storyID)
            findNavController().navigate(R.id.action_ReadOverview_to_ReadChapter, bundle)
        }

        binding.readOverviewReviewsB.setOnClickListener {
            val intent = Intent((requireActivity() as ReadingActivity), ReviewActivity::class.java)
            startActivity(intent)
        }

        binding.readOverviewAuthorB.setOnClickListener {
            val intent = Intent((requireActivity() as ReadingActivity), MainActivity::class.java)
            intent.putExtra("userToView", viewModel.username())
            startActivity(intent)
            (requireActivity() as ReadingActivity).finish()
        }

    }

    private fun byteArrayToBitmap(data: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(data, 0, data.size)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReadOverviewFragVM::class.java)
        val storyID: String? = (requireActivity() as ReadingActivity).intent.extras?.getString("storyID")
        viewModel.setApplicationContext((requireActivity() as ReadingActivity).application, storyID)
        binding.viewmodel = viewModel
        (requireActivity() as ReadingActivity).supportActionBar!!.title = viewModel.title()

        binding.imageView2.setImageBitmap(byteArrayToBitmap(viewModel.storyCover()))
    }

    override fun onRatingChanged(p0: RatingBar?, p1: Float, p2: Boolean) {
        //TODO("Not yet implemented")
    }

}