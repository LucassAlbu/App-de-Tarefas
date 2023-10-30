package com.albuquerque.app_de_tarefas.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.albuquerque.app_de_tarefas.R
import com.albuquerque.app_de_tarefas.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

fun Fragment.initToolbar(toolbar: androidx.appcompat.widget.Toolbar) {
    (activity as AppCompatActivity).setSupportActionBar(toolbar)
    (activity as AppCompatActivity).title = ""
    (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    toolbar.setNavigationOnClickListener { activity?.onBackPressed() }
}

fun Fragment.showBottomSheet(
    titleDialog: Int? = null,
    titleButton: Int? = null,
    message: String,
    onClick: () -> Unit = {}
) {
    val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialog)
    val binding: BottomSheetBinding =
        BottomSheetBinding.inflate(layoutInflater, null, false)

    binding.tvTitle.text = getText(titleDialog ?: R.string.bottom_sheet_tv_warning)
    binding.tvMessage.text = message
    binding.btnOk.text = getText(titleButton ?: R.string.bottom_sheet_btn_warning)
    binding.btnOk.setOnClickListener {
        onClick()
        bottomSheetDialog.dismiss()
    }
    bottomSheetDialog.setContentView(binding.root)
    bottomSheetDialog.show()

}