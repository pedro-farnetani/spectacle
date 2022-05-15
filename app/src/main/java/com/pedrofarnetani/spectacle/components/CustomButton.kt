package com.pedrofarnetani.spectacle.components

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import com.pedrofarnetani.spectacle.R

internal class CustomButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private val iconImageView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById<AppCompatImageView>(R.id.iconImageView)
    }
    private val titleTextView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById<AppCompatTextView>(R.id.titleTextView)
    }
    private val descriptionTextView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById<AppCompatTextView>(R.id.descriptionTextView)
    }

    init {
        inflate(context, R.layout.custom_button, this)

        context.theme.obtainStyledAttributes(attrs, R.styleable.CustomButton, defStyle, 0)
            .use { typedAttributes ->
                setupIcon(typedAttributes)
                setTitle(typedAttributes.getString(R.styleable.CustomButton_customTitle))
                setDescription(typedAttributes.getString(R.styleable.CustomButton_customDescription))
            }
    }

    private fun setupIcon(attributes: TypedArray) {
        val iconDrawable = TypedValue()
        attributes.getValue(R.styleable.CustomButton_customIcon, iconDrawable)
        setIcon(iconDrawable.resourceId)
    }

    private fun setIcon(@DrawableRes drawable: Int) {
        iconImageView.setImageResource(drawable)
        iconImageView.isVisible = drawable != 0
    }

    private fun setTitle(title: CharSequence?) {
        titleTextView.text = title
    }

    private fun setDescription(description: CharSequence?) {
        descriptionTextView.text = description
    }
}