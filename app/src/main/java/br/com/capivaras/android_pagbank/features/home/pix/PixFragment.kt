package br.com.capivaras.android_pagbank.features.home.pix

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import br.com.capivaras.android_pagbank.R
import br.com.capivaras.android_pagbank.databinding.PixFragmentBinding

class PixFragment : Fragment(), PixTransferOrPayAdapter.Buttons{

    private var _binding: PixFragmentBinding? = null
    private val binding: PixFragmentBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PixFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pixRecyclerView.adapter = PixTransferOrPayAdapter(this)

        binding.topAppBar.setNavigationOnClickListener{
            requireActivity().finish()
        }

        setButtons()
    }

    private fun setButtons() {
        binding.textViewManageKeys.setNotImplementationClickListener()

        binding.shareButton.setNotImplementationClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onButtonClicked(position: Int) {
        if(position == TITLE_QRCODE){
            view?.findNavController()?.navigate(R.id.action_pixFragment_to_pixInitialFragment)
        } else {
            Toast.makeText(requireContext(), getString(R.string.not_yet_implemented), Toast.LENGTH_SHORT).show()
        }
    }

    fun View.setNotImplementationClickListener(){
        setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.not_yet_implemented), Toast.LENGTH_SHORT).show()
        }
    }
}
