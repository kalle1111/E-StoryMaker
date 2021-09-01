package hsfl.project.e_storymaker.views.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.databinding.WriteChapterFragmentBinding
import hsfl.project.e_storymaker.viewModels.fragmentViewModels.WriteChapterFragVM
import hsfl.project.e_storymaker.views.activities.WritingActivity

class WriteChapterFragment : Fragment() {


    private var _binding: WriteChapterFragmentBinding? = null
    private val binding get() = _binding!!


    companion object {
        fun newInstance() = WriteChapterFragment()
    }

    private lateinit var viewModel: WriteChapterFragVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WriteChapterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button9.setOnClickListener {

            if (viewModel.createChapter(viewModel.storyID!!, binding.writeChapterTitle.text.toString(), binding.writeChapterContent.text.toString(), 0)){
                (requireActivity() as WritingActivity).finish()
                //Log.d("WriteChapterFrag", "SUCCESS")
            }else{
                //TODO("FIX INDEXING!")
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WriteChapterFragVM::class.java)
        viewModel.setApplicationContext((requireActivity() as WritingActivity).application)
        binding.viewmodel = viewModel

        viewModel.storyID = arguments?.getString("storyID")
    }

}