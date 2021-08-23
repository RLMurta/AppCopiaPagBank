package br.com.capivaras.android_pagbank.features.intro.login

import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getMainExecutor
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import br.com.capivaras.android_pagbank.R
import br.com.capivaras.android_pagbank.databinding.LoginFragmentBinding
import br.com.capivaras.android_pagbank.features.intro.login.loginApi.viewModel.LoginViewModel
import br.com.capivaras.android_pagbank.features.loading.LoadingDialog
import br.com.capivaras.android_pagbank.util.ViewModelFactory

class LoginFragment : Fragment() {

    private var _binding: LoginFragmentBinding? = null
    private val binding: LoginFragmentBinding get() = _binding!!
    private lateinit var viewModel: LoginViewModel
    private lateinit var loadingDialog: LoadingDialog
    private var cancellationSignal: CancellationSignal? = null
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    notifyUser("Erro de autenticação: $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    notifyUser("Autenticação realizada com sucesso!")
                    val (email, password) = viewModel.getAccountOnSharedPref()
                    binding.fieldCpfCnpjEmail.setText(email)
                    binding.fieldPassword.setText(password)
                    viewModel.onLoginClicked()
                }
            }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingDialog = LoadingDialog()
        checkBiometricSupport()

        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(requireContext())
        ).get(LoginViewModel::class.java)

        viewModel.getSwitchState()

        subscribeObserver()

        binding.buttonLogin.setOnClickListener {
            viewModel.onLoginClicked()
        }

        binding.fieldCpfCnpjEmail.addTextChangedListener {
            viewModel.onEmailChanged(it.toString())
        }

        binding.fieldPassword.addTextChangedListener {
            viewModel.onPasswordChanged(it.toString())
        }

        binding.buttonNewAccount.setNotImplementationClickListener()

        binding.buttonForgotMyPassword.setNotImplementationClickListener()

        binding.switchRememberMyAccount.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setSwitchState(isChecked)
        }
    }

    private fun subscribeObserver() {
        viewModel.loading.observe(viewLifecycleOwner, { isLoading ->
            if (isLoading) {
                loadingDialog.startLoadingDialog(requireActivity())
            } else {
                loadingDialog.dismissDialog()
            }
        })

        viewModel.success.observe(viewLifecycleOwner, {
            goToHomeActivity()
        })
        viewModel.emailError.observe(viewLifecycleOwner, { errorMessage ->
            binding.boxCpfCnpjEmail.error = errorMessage
        })
        viewModel.passwordError.observe(viewLifecycleOwner, { errorMessage ->
            binding.boxPassword.error = errorMessage
        })
        viewModel.error.observe(viewLifecycleOwner, { errorMessage ->
            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        })

        viewModel.switchIsEnabled.observe(requireActivity(), { switchIsEnabled ->
            if(switchIsEnabled != binding.switchRememberMyAccount.isChecked) {
                binding.switchRememberMyAccount.isChecked = switchIsEnabled
                @RequiresApi(Build.VERSION_CODES.P)
                if (viewModel.isAccountOnSharedPrefEmpty()) {
                    viewModel.setSwitchState(false)
                    binding.switchRememberMyAccount.isChecked = false
                } else if (switchIsEnabled) {
                    biometricPromptBuilder()
                }
            }
        })
    }

    private fun goToHomeActivity() {
        view?.findNavController()?.navigate(R.id.action_loginFragment3_to_homeActivity)
    }

    fun View.setNotImplementationClickListener() {
        setOnClickListener {
            Toast.makeText(
                requireContext(),
                getString(R.string.not_yet_implemented),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    private fun biometricPromptBuilder() {
        val biometricPrompt = BiometricPrompt.Builder(requireContext())
            .setTitle("Login por biometria")
            .setSubtitle("É necessário realizar login")
            .setDescription("Esse app utiliza proteção biometrica para manter seus dados seguros")
            .setNegativeButton(
                "Cancelar",
                getMainExecutor(requireContext()),
                DialogInterface.OnClickListener { dialog, which ->
                    notifyUser("Autenticação cancelada")
                }).build()

        biometricPrompt.authenticate(
            getCancellationSignal(),
            getMainExecutor(requireContext()),
            authenticationCallback
        )
    }

    private fun checkBiometricSupport(): Boolean {
        val keyguardManager =
            requireActivity().getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (!keyguardManager.isKeyguardSecure) {
            notifyUser("A autenticação por sensor biometrico não foi ativada nas configurações")
            return false
        }
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.USE_BIOMETRIC
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            notifyUser("A autenticação por sensor biometrico não foi permitida")
        }
        return if (requireActivity().packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else {
            true
        }
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            notifyUser("A autenticação foi cancelada pelo usuário")
        }
        return cancellationSignal as CancellationSignal
    }

    private fun notifyUser(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
