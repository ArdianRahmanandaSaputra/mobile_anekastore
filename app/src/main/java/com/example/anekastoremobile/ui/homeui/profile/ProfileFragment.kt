package com.example.anekastoremobile.ui.homeui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.anekastoremobile.R
import com.example.anekastoremobile.data.remote.response.ProfileResponse
import com.example.anekastoremobile.data.remote.response.TransactionHistory
import com.example.anekastoremobile.data.remote.retrofit.ApiConfig
import com.example.anekastoremobile.databinding.FragmentProfileBinding
import com.example.anekastoremobile.ui.MainActivity
import com.example.anekastoremobile.ui.UserPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var userPreferences: UserPreferences
    private lateinit var adapter: ProfileAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar()
        userPreferences = UserPreferences(requireContext())

        _binding?.rvProfile?.layoutManager = LinearLayoutManager(requireContext())
        _binding?.rvProfile?.setHasFixedSize(true)

        getData()
        getTransactionHistory()
    }

    private fun toolbar() {
        _binding?.toolbar?.setNavigationIcon(R.drawable.baseline_arrow_back_ios_new_24)
        _binding?.toolbar?.setNavigationOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }
        _binding?.toolbar?.inflateMenu(R.menu.menu_profile)
        _binding?.toolbar?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.logout -> {
                    val builder = AlertDialog.Builder(requireContext())
                        .setCancelable(true)
                        .setTitle("Keluar Akun!")
                        .setMessage("Apakah Anda yakin ingin keluar?")
                        .setNegativeButton("Batal", null)
                        .setPositiveButton("Ya") { dialog, _ ->
                            userPreferences.clearCredential()
                            dialog.dismiss()
                            val i = Intent(requireActivity(), MainActivity::class.java)
                            i.flags =
                                Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(i)
                            requireActivity().finish()
                        }
                    val dialog = builder.create()
                    dialog.show()
                    true
                }

                else -> {
                    @Suppress("DEPRECATION")
                    super.onOptionsItemSelected(it)
                }
            }
        }
    }

    private fun getData() {
        showLoading(true)
        val client = ApiConfig.getService(requireContext()).profile()
        client.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(p0: Call<ProfileResponse>, p1: Response<ProfileResponse>) {
                showLoading(false)
                val responseBody = p1.body()
                if (p1.isSuccessful && responseBody != null) {
                    viewUI(responseBody)
                }
            }

            override fun onFailure(p0: Call<ProfileResponse>, p1: Throwable) {
                showLoading(false)
                Log.e("ProfileFragment", p1.message.toString())
            }
        })
    }

    private fun viewUI(data: ProfileResponse) {
        _binding?.profileImage?.let {
            Glide.with(requireContext())
                .load(data.detail?.photo)
                .error(R.drawable.baseline_person_24)
                .into(it)
        }
        _binding?.name?.text = data.user?.name
        _binding?.email?.text = data.user?.email

        _binding?.nameUser?.text = data.user?.name
        _binding?.phone?.text = data.detail?.phone
        _binding?.address?.text =
            buildString {
                append(data.detail?.detailAddress)
                append(", ")
                append(data.detail?.city)
                append(", ")
                append(data.detail?.province)
                append(" ")
                append(data.detail?.postalCode)
            }
    }

    private fun getTransactionHistory() {
        val client = ApiConfig.getService(requireContext()).transactionHistory()
        client.enqueue(object : Callback<TransactionHistory> {
            override fun onResponse(
                p0: Call<TransactionHistory>,
                p1: Response<TransactionHistory>
            ) {
                val responseBody = p1.body()
                if (p1.isSuccessful && responseBody != null) {
                    val filter = responseBody.order.filter { it.statusPembayaran == "SUCCESS" }
                    adapter = ProfileAdapter(requireContext(), filter)
                    _binding?.rvProfile?.adapter = adapter
                }
            }

            override fun onFailure(p0: Call<TransactionHistory>, p1: Throwable) {
                Log.e("ProfileFragment", p1.message.toString())
            }
        })
    }

    fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}