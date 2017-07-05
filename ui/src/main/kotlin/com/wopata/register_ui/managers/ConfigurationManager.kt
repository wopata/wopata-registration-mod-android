package com.wopata.register_ui.managers

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import com.wopata.register_core.models.RegisterSource
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

    var sources: Array<RegisterSource> = arrayOf(RegisterSource.NATIVE)

    var landingBackground: Drawable? = null
    var landingBrand: Drawable? = null
    var landingBrandMargin: Int = context.resources.getDimensionPixelSize(R.dimen.register_screen_spacing)
    var landingText: String? = null
    var landingTextFont: Typeface = Typeface.SANS_SERIF

    var ctaBackground: Drawable = ContextCompat.getDrawable(context, R.drawable.button_background_colors)
    var ctaTextColor: Int = Color.WHITE
    var ctaTextFont: Typeface = Typeface.DEFAULT_BOLD
}