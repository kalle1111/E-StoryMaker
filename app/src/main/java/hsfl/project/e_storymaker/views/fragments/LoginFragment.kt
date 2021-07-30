package hsfl.project.e_storymaker.views.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import hsfl.project.e_storymaker.viewModels.AuthVM
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.databinding.LoginFragmentBinding

import hsfl.project.e_storymaker.viewModels.fragmentViewModels.LoginFragVM
import hsfl.project.e_storymaker.views.activities.AuthActivity

class LoginFragment : Fragment() {


    private var _binding: LoginFragmentBinding? = null

    private val binding get() = _binding!!

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginFragVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root

        //return inflater.inflate(R.layout.login_fragment, container, false) //old return, if new has problem
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AuthActivity).supportActionBar!!.hide()

        binding.registerButton.setOnClickListener{
            (requireActivity() as AuthActivity).supportActionBar!!.show()
            findNavController().navigate(R.id.action_loginFragment_to_RegisterFragment)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginFragVM::class.java)
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}