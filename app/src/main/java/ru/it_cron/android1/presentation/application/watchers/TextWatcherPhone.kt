package ru.it_cron.android1.presentation.application.watchers

import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import com.google.android.material.textfield.TextInputEditText

class TextWatcherPhone(
    private val editText: TextInputEditText,
    private val action: (String) -> Unit,
) : PhoneNumberFormattingTextWatcher(
) {
    private var backspacingFlag = false
    private var editedFlag = false
    private var cursorComplement = 0

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        cursorComplement = s?.length?.minus(editText.selectionStart) ?: 0
        backspacingFlag = count > after
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        super.onTextChanged(s, start, before, count)
    }

    override fun afterTextChanged(s: Editable?) {
        val string = s.toString()
        val phone = string.replace("\\D".toRegex(), "")
        if (!editedFlag) {
            if (phone.length >= 10 && !backspacingFlag) {
                editedFlag = true
                val ans = "+7(" + phone.substring(1, 4) + ")-" + phone.substring(
                    4, 7
                ) + "-" + phone.substring(7, 9) + "-" + phone.substring(9)
                editText.setText(ans)
                editText.setSelection(
                    editText.getText()?.length?.minus(cursorComplement) ?: 0
                )
            } else if (phone.length >= 4 && !backspacingFlag) {
                editedFlag = true
                val ans = "+7(" + phone.substring(1, 4) + ") " + phone.substring(4)
                editText.setText(ans)
                editText.setSelection(
                    editText.getText()?.length?.minus(cursorComplement) ?: 0
                )
            }
        } else {
            editedFlag = false
        }
        action(string)
    }
}