package hsfl.project.e_storymaker.views.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hsfl.project.e_storymaker.viewModels.WritingVM
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.databinding.WriteDetailsFragmentBinding
import hsfl.project.e_storymaker.viewModels.fragmentViewModels.WriteDetailsFragVM
import hsfl.project.e_storymaker.views.activities.WritingActivity

class WriteDetailsFragment : Fragment() {

    private var _binding: WriteDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = WriteDetailsFragment()
    }

    private lateinit var viewModel: WriteDetailsFragVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WriteDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button8.setOnClickListener {

            if (viewModel.createStory(null, binding.writeDetailsTitle.text.toString(),binding.writeDetailsDescription.text.toString())){
                findNavController().navigate(R.id.action_WriteOverview_to_WriteChapter)
            }else{
                //TODO("SHOW THE USER AN ERROR MESSAGE")
            }
        }

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WriteDetailsFragVM::class.java)

    }


}