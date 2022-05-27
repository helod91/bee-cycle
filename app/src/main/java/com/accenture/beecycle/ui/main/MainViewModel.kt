package com.accenture.beecycle.ui.main

import com.accenture.beecycle.common.BaseViewModel
import com.accenture.beecycle.ui.main.MainIntent
import com.accenture.beecycle.ui.main.MainState

class MainViewModel : BaseViewModel<MainState, MainIntent, MainAction>() {

    override fun intentToAction(intent: MainIntent): MainAction {
        return MainAction()
    }

    override fun handleAction(action: MainAction) {

    }
}
