package com.wopata.register_ui.managers

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.wopata.register_ui.R

/**
 * Created by stephenvinouze on 28/06/2017.
 */
class ConfigurationManager private constructor(context: Context) {

    companion object {
        private var instance: ConfigurationManager? = null

        fun sharedInstance(context: Context): ConfigurationManager {
            if (instance == null)
                instance = ConfigurationManager(context)
            return instance!!
        }
    }

    var landingBackground: Drawable? = null
    var landingBrand: Drawable? = null
    var landingText: String? = null
    var landingTextFont: Typeface = Typeface.SANS_SERIF

    var ctaBackground: Drawable = ContextCompat.getDrawable(context, R.drawable.button_background_colors)
    var ctaTextColor: Int = Color.WHITE
    var ctaTextFont: Typeface = Typeface.DEFAULT_BOLD
}