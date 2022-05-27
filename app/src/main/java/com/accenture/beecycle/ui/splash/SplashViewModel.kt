package com.accenture.beecycle.ui.splash

import com.accenture.beecycle.common.BaseViewModel

class SplashViewModel : BaseViewModel<SplashState, SplashIntent, SplashAction>() {

    override fun intentToAction(intent: SplashIntent): SplashAction {
        return SplashAction()
    }

    override fun handleAction(action: SplashAction) {

    }
}