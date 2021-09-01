package hsfl.project.e_storymaker.views.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.PermissionChecker
import androidx.navigation.fragment.findNavController
import hsfl.project.e_storymaker.viewModels.AuthVM
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.databinding.AuthActivityBinding
import hsfl.project.e_storymaker.databinding.RegisterFragmentBinding
import hsfl.project.e_storymaker.viewModels.fragmentViewModels.RegisterFragVM
import hsfl.project.e_storymaker.views.activities.AuthActivity
import hsfl.project.e_storymaker.views.activities.WritingActivity
import io.ktor.client.features.auth.*
import java.io.ByteArrayOutputStream

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
            if(viewModel.register(binding.registerUsername.text.toString(), binding.registerMail.text.toString(), binding.registerPassword.text.toString(), binding.registerPassword.text.toString(), imageToByteArray(binding.registerImage))){
                //Popup + switch to login
                findNavController().navigate(R.id.action_RegisterFragment_to_LoginFragment)
            }else{
                Log.d("RegisterFrag", "Failed to register!")
                //popup failure
            }
        }

        binding.registerSelectImageB.setOnClickListener {
            if (PermissionChecker.checkSelfPermission(
                    (requireActivity() as AuthActivity),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PermissionChecker.PERMISSION_DENIED
                || PermissionChecker.checkSelfPermission(
                    (requireActivity() as AuthActivity),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PermissionChecker.PERMISSION_DENIED){
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 1001)
                Log.d("WriteDetailsFrag", "PERMISSIONS REQUESTED!")
            }else{
                openGallery()
            }
        }
    }


    private fun openGallery(){
        val intent = Intent("android.intent.action.GET_CONTENT")
        intent.type = "image/*"
        startActivityForResult(intent, 2)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == 2){
            binding.registerImage.setImageURI(data?.data)
        }
    }

    private fun imageToByteArray(imageView: ImageView): ByteArray{
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

        return stream.toByteArray()
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