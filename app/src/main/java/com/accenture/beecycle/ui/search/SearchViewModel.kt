package com.accenture.beecycle.ui.search

import com.accenture.beecycle.common.BaseViewModel

class SearchViewModel : BaseViewModel<SearchState, SearchIntent, SearchAction>() {
    override fun intentToAction(intent: SearchIntent): SearchAction {
        return SearchAction()
    }

    override fun handleAction(action: SearchAction) {

    }
}
