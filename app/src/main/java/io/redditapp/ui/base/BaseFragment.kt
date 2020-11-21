package io.redditapp.ui.base

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.redditapp.R

open class BaseFragment : Fragment() {

    fun showDialog(ctx: Context, title: String?, message: String) {
        val mDialog = AlertDialog.Builder(ctx)
        mDialog.setCancelable(true)
        mDialog.setMessage(message)
        if (!title.isNullOrBlank())
            mDialog.setTitle(title)
        mDialog.setPositiveButton(ctx.getString(R.string.ok_)) { dialog, which ->
            dialog.dismiss()
        }
        mDialog.create().show()
    }

    inline infix fun <T> LiveData<T>.observe(crossinline action: (T) -> Unit) =
        observe(viewLifecycleOwner, Observer { it?.let(action) })

}