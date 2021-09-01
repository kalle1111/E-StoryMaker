package hsfl.project.e_storymaker.views.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import hsfl.project.e_storymaker.viewModels.WritingVM
import hsfl.project.e_storymaker.R
import hsfl.project.e_storymaker.databinding.WriteDetailsFragmentBinding
import hsfl.project.e_storymaker.viewModels.fragmentViewModels.WriteDetailsFragVM
import hsfl.project.e_storymaker.views.activities.WritingActivity
import kotlinx.serialization.descriptors.PrimitiveKind
import java.io.ByteArrayOutputStream

class WriteDetailsFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private var _binding: WriteDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private var mURI: Uri? = null

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

            val sID: String? = viewModel.createStory(imageToByteArray(binding.imageView3), binding.writeDetailsTitle.text.toString(),binding.writeDetailsDescription.text.toString())
            if (sID != null){
                val bundle = bundleOf("storyID" to sID)
                findNavController().navigate(R.id.action_WriteOverview_to_WriteChapter, bundle)
            }else{
               Log.e("WriteDetailsFrag", "Server refused to create!")
            }
        }

        binding.writeDetailsSelectImage.setOnClickListener {
            if (checkSelfPermission((requireActivity() as WritingActivity), Manifest.permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_DENIED
                || checkSelfPermission((requireActivity() as WritingActivity), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_DENIED){
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 1001)
                Log.d("WriteDetailsFrag", "PERMISSIONS REQUESTED!")
            }else{
                openGallery()
            }
        }

        binding.writeDetailsResetTags.setOnClickListener {
            viewModel.selectedTags.clear()
            binding.writeDetailsTagList.text = "No tags selected!"
        }



    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val addChapter: Boolean? = (requireActivity() as WritingActivity).intent.extras?.getBoolean("addChapter")
        val sID: String? = (requireActivity() as WritingActivity).intent.extras?.getString("storyID")
        if (addChapter != null && addChapter == true){
            val bundle = bundleOf("storyID" to sID)
            findNavController().navigate(R.id.action_WriteOverview_to_WriteChapter, bundle)

        }
        val storyToEdit: String? = (requireActivity() as WritingActivity).intent.extras?.getString("storyToEditID")
        viewModel = ViewModelProvider(this).get(WriteDetailsFragVM::class.java)
        viewModel.setApplicationContext((requireActivity() as WritingActivity).application, storyToEdit)
        binding.viewmodel = viewModel


        /*
        ArrayAdapter.createFromResource(
            (requireActivity() as WritingActivity),
            R.array.tag_spinner_arr,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.writeDetailsTagSelector.adapter = adapter
        }*/
        binding.writeDetailsTagSelector.onItemSelectedListener = this

        ArrayAdapter<String>((requireActivity() as WritingActivity), android.R.layout.simple_spinner_item, viewModel.allTags()).also {
            adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.writeDetailsTagSelector.adapter = adapter
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
            binding.imageView3.setImageURI(data?.data)
        }
    }

    private fun imageToByteArray(imageView: ImageView): ByteArray{
        val bitmap = (imageView.drawable as BitmapDrawable).bitmap
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)

        return stream.toByteArray()
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if(!viewModel.selectedTags.contains(binding.writeDetailsTagSelector.selectedItem.toString())){
            viewModel.selectedTags.add(binding.writeDetailsTagSelector.selectedItem.toString())
            Log.d("TEG", binding.writeDetailsTagSelector.selectedItem.toString())
            binding.writeDetailsTagList.text = viewModel.selectedTags.toString()
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //TODO("Not yet implemented")
    }
}