package com.listing.movie.profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.bumptech.glide.Glide
import com.listing.movie.R
import com.listing.movie.databinding.FragmentProfileBinding
import com.listing.movie.utils.BlurWorker
import com.listing.movie.utils.DateUtils
import com.listing.movie.utils.Utils.convertUriToBase64
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding

    private val viewModel: ProfileViewModel by viewModels()
    private var photo: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentProfileBinding.inflate(inflater,container,false)
        with(binding){

            viewModel.user.observe(viewLifecycleOwner) {
                etUsername.setText(it.username)
                etNama.setText(it.nama)
                etAlamat.setText(it.alamat)
                Glide.with(requireContext())
                    .asBitmap()
                    .load(it.photoByte())
                    .placeholder(R.drawable.baseline_add_box_24)
                    .into(ivProfile)
                it.tanggalLahir?.let {date ->
                    etTanggalLahir.setText(DateUtils.formatDate(date))
                }
            }

            ivProfile.setOnClickListener {
                galleryLauncher.launch("image/*")
            }

            btnLogout.setOnClickListener{
                viewModel.logout()
                showMessage("Berhasil Logout")
                findNavController().navigate(R.id.action_profileFragment_to_loginFragment)
            }

            btnUpdate.setOnClickListener{
                if (isValid() && viewModel.user.isInitialized) {
                    btnUpdate.isVisible = false
                    pbLoading.isVisible = true

                    val newUserName = etUsername.text.toString()
                    val newName = etNama.text.toString()
                    val newTanggalLahir = etTanggalLahir.text.toString()
                    val newAlamat = etAlamat.text.toString()

                    viewModel.updateUserData(
                        photo = photo, userName = newUserName,
                        name = newName, tanggalLahir = newTanggalLahir,
                        alamat = newAlamat
                    )
                    showMessage("Berhasil Ubah Data")
                    btnUpdate.isVisible = true
                    pbLoading.isVisible = false
                }
            }
            etTanggalLahir.setOnClickListener{
                showDatePickerDialog()
            }
        }
        return binding.root
    }
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                binding.etTanggalLahir.setText(DateUtils.formatDate(DateUtils.getDate(selectedYear,selectedMonth,selectedDay)))
            },
            year, month, day
        )

        datePickerDialog.show()
    }

    private fun isValid(): Boolean {
        var valid = true
        with(binding){
            if (etUsername.text.isEmpty()) {
                tilUsername.error = "Username tidak boleh kosong"
                valid = false
            } else {
                tilUsername.isErrorEnabled = false
            }

            if (etNama.text.isEmpty()) {
                tilNama.error = "Nama lengkap tidak boleh kosong"
                valid = false
            } else {
                tilNama.isErrorEnabled = false
            }

            if (etTanggalLahir.text.isEmpty()) {
                tilTanggalLahir.error = "Tanggal lahir tidak boleh kosong"
                valid = false
            } else {
                tilTanggalLahir.isErrorEnabled = false
            }

            if (etAlamat.text.isEmpty()) {
                tilAlamat.error = "Alamat tidak boleh kosong"
                valid = false
            } else {
                tilAlamat.isErrorEnabled = false
            }
        }

        return valid
    }

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        try{
            it?.let {
                val inputData = Data.Builder()
                    .putString(BlurWorker.KEY_IMAGE_URI, it.toString())
                    .build()
                val blurRequest = OneTimeWorkRequestBuilder<BlurWorker>()
                    .setInputData(inputData)
                    .build()
                WorkManager.getInstance(requireContext()).enqueue(blurRequest)
                WorkManager.getInstance(requireContext())
                    .getWorkInfoByIdLiveData(blurRequest.id)
                    .observe(viewLifecycleOwner) { workInfo ->
                        if (workInfo != null && workInfo.state.isFinished) {
                            val outputUri = workInfo.outputData.getString(BlurWorker.KEY_IMAGE_URI)
                            if (outputUri != null) {
                                Glide.with(requireContext())
                                    .load(it)
                                    .placeholder(R.drawable.baseline_add_box_24)
                                    .into(binding.ivProfile)
                            }
                        }
                    }
                photo = convertUriToBase64(it, requireContext())
            }
        }catch(e:Exception){
            e.printStackTrace()
        }
    }

    private fun showMessage(text: String) {
        Toast.makeText(requireContext(),text,Toast.LENGTH_LONG).show()
    }
}