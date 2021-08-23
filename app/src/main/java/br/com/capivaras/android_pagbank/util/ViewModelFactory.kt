package br.com.capivaras.android_pagbank.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.capivaras.android_pagbank.features.home.HomeActivityRepository
import br.com.capivaras.android_pagbank.features.home.HomeActivityViewModel
import br.com.capivaras.android_pagbank.features.home.HomeViewModel
import br.com.capivaras.android_pagbank.features.home.extract.extractApi.repository.ExtractRepository
import br.com.capivaras.android_pagbank.features.home.extract.extractApi.repository.FilterRepository
import br.com.capivaras.android_pagbank.features.home.extract.filter.FilterViewModel
import br.com.capivaras.android_pagbank.features.home.extract.initialScreen.ExtractViewModel
import br.com.capivaras.android_pagbank.features.home.homeApi.repository.HomeRepository
import br.com.capivaras.android_pagbank.features.home.pix.pixOnBoarding.PixOnBoardingViewModel
import br.com.capivaras.android_pagbank.features.home.pix.transfer.PixInsertEmailScreen.PixInsertEmailViewModel
import br.com.capivaras.android_pagbank.features.home.pix.transfer.pixConfirmationApi.repository.PixConfirmationRepository
import br.com.capivaras.android_pagbank.features.home.pix.transfer.pixConfirmationScreen.PixConfirmationViewModel
import br.com.capivaras.android_pagbank.features.home.pix.transfer.pixFinalScreen.PixFinalViewModel
import br.com.capivaras.android_pagbank.features.home.pix.transfer.pixInsertValueScreen.PixInsertValueViewModel
import br.com.capivaras.android_pagbank.features.home.pix.transfer.pixValidationApi.repository.PixValidationRepository
import br.com.capivaras.android_pagbank.features.intro.login.loginApi.repository.LoginRepository
import br.com.capivaras.android_pagbank.features.intro.login.loginApi.viewModel.LoginViewModel
import br.com.capivaras.android_pagbank.features.intro.splash.SplashViewModel

class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass == LoginViewModel::class.java) {
            return providerLoginViewModel() as T
        } else if (modelClass == SplashViewModel::class.java) {
            return providerSplashViewModel() as T
        } else if (modelClass == HomeViewModel::class.java) {
            return providerHomeViewModel() as T
        } else if (modelClass == ExtractViewModel::class.java) {
            return providerExtractViewModel() as T
        } else if (modelClass == FilterViewModel::class.java) {
            return providerFilterViewModel() as T
        } else if (modelClass == PixOnBoardingViewModel::class.java) {
            return providerPixOnBoardingViewModel() as T
        } else if (modelClass == PixInsertEmailViewModel::class.java) {
            return providerPixInsertEmailViewModel() as T
        } else if (modelClass == PixInsertValueViewModel::class.java) {
            return providerPixInsertValueViewModel() as T
        } else if (modelClass == PixConfirmationViewModel::class.java) {
            return providerPixValidationViewModel() as T
        } else if (modelClass == PixFinalViewModel::class.java) {
            return providerPixFinalViewModel() as T
        } else if (modelClass == HomeActivityViewModel::class.java) {
            return providerHomeActivityViewModel() as T
        } else {
            throw Exception("View Model não definido")
        }
    }

    private fun providerExtractViewModel(): ExtractViewModel {
        return ExtractViewModel(
            ExtractRepository(
                providerApiInterface()
            )
        )
    }

    private fun providerLoginViewModel(): LoginViewModel {
        return LoginViewModel(
            LoginRepository(providerApiInterface()),
            SharedPref.getInstance(context)
        )
    }

    private fun providerSplashViewModel(): SplashViewModel {
        return SplashViewModel(SharedPref.getInstance(context))
    }

    private fun providerHomeViewModel(): HomeViewModel {
        return HomeViewModel(
            HomeRepository(
                providerApiInterface()
            )
        )
    }

    private fun providerFilterViewModel(): FilterViewModel {
        return FilterViewModel(FilterRepository(SharedPref.getInstance(context)))
    }

    private fun providerPixOnBoardingViewModel(): PixOnBoardingViewModel {
        return PixOnBoardingViewModel(SharedPref.getInstance(context))
    }

    private fun providerPixInsertEmailViewModel(): PixInsertEmailViewModel {
        return PixInsertEmailViewModel()
    }

    private fun providerPixInsertValueViewModel(): PixInsertValueViewModel {
        return PixInsertValueViewModel(
            HomeRepository(
                providerApiInterface()
            )
        )
    }

    private fun providerPixValidationViewModel(): PixConfirmationViewModel {
        return PixConfirmationViewModel(
            PixValidationRepository(
                providerApiInterface()
            )
        )
    }

    private fun providerPixFinalViewModel(): PixFinalViewModel {
        return PixFinalViewModel(
            PixConfirmationRepository(
                providerApiInterface()
            )
        )
    }

    private fun providerApiInterface(): ApiInterface {
        return ApiInterface.create(SharedPref.getInstance(context))
    }

    private fun providerHomeActivityViewModel(): HomeActivityViewModel {
        return HomeActivityViewModel(
            HomeActivityRepository(
                providerApiInterface(
                )
            )
        )
    }
}
