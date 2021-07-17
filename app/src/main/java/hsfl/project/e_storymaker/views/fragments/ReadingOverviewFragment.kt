package hsfl.project.e_storymaker.views.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import hsfl.project.e_storymaker.viewModels.ReadingViewModel
import hsfl.project.e_storymaker.R

class ReadingOverviewFragment : Fragment() {

    companion object {
        fun newInstance() = ReadingOverviewFragment()
    }

    private lateinit var viewModel: ReadingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.reading_overview_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReadingViewModel::class.java)
        // TODO: Use the ViewModel
    }

}