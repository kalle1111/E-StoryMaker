package hsfl.project.e_storymaker.views.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hsfl.project.e_storymaker.viewModels.MainVM
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.viewModels.fragmentViewModels.LibraryFragVM

class LibraryFragment : Fragment() {

    companion object {
        fun newInstance() = LibraryFragment()
    }

    private lateinit var viewModel: LibraryFragVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.library_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LibraryFragVM::class.java)
        // TODO: Use the ViewModel
    }

}