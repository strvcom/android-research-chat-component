package com.strv.chat.library.core.ui.chat.sending.style

import android.content.Context
import android.util.AttributeSet
import com.strv.chat.library.R
import com.strv.chat.library.core.ui.Style

class SendWidgetStyle private constructor(
    context: Context
) : Style(context) {

    companion object {
        fun parse(context: Context, attrs: AttributeSet): SendWidgetStyle {
            val style = SendWidgetStyle(context)
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SendWidget)

            style.backgroundColor = typedArray.getColor(
                R.styleable.SendWidget_sw_backgroundColor,
                style.defaultBackgroundColor()
            )
            style.sendIconTint =
                typedArray.getColor(R.styleable.SendWidget_sw_sendIconTint, style.defaultIconTint())

            typedArray.recycle()

            return style
        }
    }

    var backgroundColor: Int = -1
    var sendIconTint: Int = -1

    private fun defaultBackgroundColor() =
        systemBackgroundColor()

    private fun defaultIconTint() =
        systemAccentColor()
}
