package hsfl.project.e_storymaker.views.fragments

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import hsfl.project.e_storymaker.viewModels.MainVM
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.databinding.ProfileFragmentBinding
import hsfl.project.e_storymaker.viewModels.fragmentViewModels.ProfileFragVM
import hsfl.project.e_storymaker.views.activities.MainActivity
import hsfl.project.e_storymaker.views.activities.ReadingActivity

class ProfileFragment : Fragment() {

    private var _binding: ProfileFragmentBinding? = null

    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileFragVM


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button.setOnClickListener{
            val intent = Intent((requireActivity() as MainActivity), ReadingActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val userToView: String? = (requireActivity() as MainActivity).intent.extras?.getString("userToView")
        Log.d("ProfileFrag", "Profile for user: " + userToView)
        viewModel = ViewModelProvider(this).get(ProfileFragVM::class.java)
        viewModel.setApplicationContext((requireActivity() as MainActivity).application, userToView)
        binding.viewmodel = viewModel

        (requireActivity() as MainActivity).findViewById<TextView>(R.id.mToolbarTitle).setText(viewModel.username())
    //(requireActivity() as MainActivity).supportActionBar!! .title = viewModel.username()


    }

}