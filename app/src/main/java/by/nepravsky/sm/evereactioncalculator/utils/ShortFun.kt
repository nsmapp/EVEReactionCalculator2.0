package by.nepravsky.sm.evereactioncalculator.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showSnackBarSort(root: View, message: String) =
    Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show()

fun showSnackBarLong(root: View, message: String) =
    Snackbar.make(root, message, Snackbar.LENGTH_LONG).show()

