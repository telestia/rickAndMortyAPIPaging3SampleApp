package com.example.rickandmortypagingtestmvvmapp.ui.base

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.rickandmortypagingtestmvvmapp.R
import com.mellon.newssampleharun.common.tryOrLog
import com.mellon.newssampleharun.ui.common.navigation.NavigationData

abstract class BaseActivity<DB : ViewDataBinding> : AppCompatActivity() {

    lateinit var binding: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutResId())
    }

    @LayoutRes
    protected abstract fun getLayoutResId(): Int

    open fun buildNavOptions(
        @AnimRes @AnimatorRes enterAnim: Int? = null,
        @AnimRes @AnimatorRes exitAnim: Int? = null,
        @AnimRes @AnimatorRes popEnterAnim: Int? = null,
        @AnimRes @AnimatorRes popExitAnim: Int? = null,
        @IdRes popupTo: Int? = null,
        inclusive: Boolean
    ): NavOptions {
        val builder = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(enterAnim ?: androidx.navigation.ui.R.anim.nav_default_enter_anim)
            .setExitAnim(exitAnim ?: androidx.navigation.ui.R.anim.nav_default_exit_anim)
            .setPopEnterAnim(popEnterAnim ?: androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
            .setPopExitAnim(popExitAnim ?: androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
        popupTo?.let { builder.setPopUpTo(it, inclusive) }
        return builder.build()
    }

    open fun navigateToDestination(navController: NavController, navigationData: NavigationData) {
        tryOrLog {
            navController.navigate(
                navigationData.destinationId,
                navigationData.args,
                buildNavOptions(
                    enterAnim = navigationData.navigationAnimator?.enterAnim ?: R.anim.slide_in_left_anim,
                    exitAnim = navigationData.navigationAnimator?.exitAnim ?: R.anim.slide_out_left_anim,
                    popEnterAnim = navigationData.navigationAnimator?.popEnterAnim ?: R.anim.slide_in_right_anim,
                    popExitAnim = navigationData.navigationAnimator?.popExitAnim ?: R.anim.slide_out_right_anim,
                    popupTo = navigationData.popupTo,
                    inclusive = navigationData.popupToInclusive
                )
            )
        }
    }

    @SuppressWarnings("DEPRECATION")
    open fun setFullScreenFlag() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    @SuppressWarnings("DEPRECATION")
    open fun clearFullScreenFlag() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.show(WindowInsets.Type.statusBars())
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }
}