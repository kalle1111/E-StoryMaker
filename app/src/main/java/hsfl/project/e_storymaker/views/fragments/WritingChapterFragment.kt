package hsfl.project.e_storymaker.views.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hsfl.project.e_storymaker.viewModels.WritingViewModel
import hsfl.project.e_storymaker.R

class WritingChapterFragment : Fragment() {

    companion object {
        fun newInstance() = WritingChapterFragment()
    }

    private lateinit var viewModel: WritingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.writing_chapter_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WritingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}