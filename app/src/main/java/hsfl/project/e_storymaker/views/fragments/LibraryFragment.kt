package hsfl.project.e_storymaker.views.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hsfl.project.e_storymaker.viewModels.MainVM
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.databinding.LibraryFragmentBinding
import hsfl.project.e_storymaker.viewModels.fragmentViewModels.LibraryFragVM
import hsfl.project.e_storymaker.views.activities.MainActivity
import hsfl.project.e_storymaker.views.activities.WritingActivity

class LibraryFragment : Fragment() {

    private var _binding: LibraryFragmentBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = LibraryFragment()
    }

    private lateinit var viewModel: LibraryFragVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LibraryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button12.setOnClickListener {
            val intent = Intent((requireActivity() as MainActivity), WritingActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LibraryFragVM::class.java)
        // TODO: Use the ViewModel
    }

}