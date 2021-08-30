package hsfl.project.e_storymaker.views.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import hsfl.project.e_storymaker.viewModels.AuthVM
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.databinding.AuthActivityBinding
import hsfl.project.e_storymaker.databinding.RegisterFragmentBinding
import hsfl.project.e_storymaker.viewModels.fragmentViewModels.RegisterFragVM
import hsfl.project.e_storymaker.views.activities.AuthActivity
import io.ktor.client.features.auth.*

class RegisterFragment : Fragment() {

    private var _binding: RegisterFragmentBinding? = null

    private val binding get() = _binding!!

    companion object {
        fun newInstance() = RegisterFragment()
    }

    private lateinit var viewModel: RegisterFragVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = RegisterFragmentBinding.inflate(inflater, container, false)
        return binding.root
    //return inflater.inflate(R.layout.register_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.button2.setOnClickListener {
            if(viewModel.register(binding.registerUsername.text.toString(), binding.registerMail.text.toString(), binding.registerPassword.text.toString(), binding.registerPassword.text.toString())){
                //Popup + switch to login
                findNavController().navigate(R.id.action_RegisterFragment_to_LoginFragment)
            }else{
                Log.d("RegisterFrag", "Failed to register!")
                //popup failure
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterFragVM::class.java)
        viewModel.setApplicationContext((requireActivity() as AuthActivity).application)
        // TODO: Use the ViewModel
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}