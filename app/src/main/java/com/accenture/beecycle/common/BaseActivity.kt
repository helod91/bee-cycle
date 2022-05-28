package com.accenture.beecycle.common

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import io.github.inflationx.viewpump.ViewPumpContextWrapper


abstract class BaseActivity<V : ViewBinding, STATE : ViewState, INTENT : ViewIntent,
        ACTION : ViewAction, M : BaseViewModel<STATE, INTENT, ACTION>> : AppCompatActivity() {

    protected lateinit var binding: V

    protected abstract val viewModel: M

    protected open var showBackButton = false

    abstract fun presentBinding(): V

    abstract fun render(state: STATE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = presentBinding()

        supportActionBar?.setDisplayHomeAsUpEnabled(showBackButton)

        setContentView(binding.root)

        viewModel.state.observe(this) { render(it) }
    }

    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    protected fun showGenericError() {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage("Something went wrong")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    protected fun dispatchIntent(intent: INTENT) {
        viewModel.dispatchIntent(intent)
    }
}