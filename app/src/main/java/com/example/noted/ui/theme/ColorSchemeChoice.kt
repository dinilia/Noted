package com.example.noted.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import com.example.noted.utils.ColorPalletName
import com.example.noted.utils.ColorSchemeName

object ColorSchemeChoice {
    fun getColorScheme(colorScheme: String, colorPallet: String): ColorScheme {
        return when (colorScheme) {
            ColorSchemeName.DARK_MODE -> {
                when (colorPallet) {
                    ColorPalletName.CRIMSON -> DarkCrimsonScheme
                    ColorPalletName.CADMIUM_GREEN -> DarkCadmiumGreenScheme
                    ColorPalletName.COBALT_BLUE -> DarkCobaltBlueScheme
                    else -> DarkPurpleScheme
                }
            }

            else -> {
                when (colorPallet) {
                    ColorPalletName.CRIMSON -> LightCrimsonScheme
                    ColorPalletName.CADMIUM_GREEN -> LightCadmiumGreenScheme
                    ColorPalletName.COBALT_BLUE -> LightCobaltBlueScheme
                    else -> LightPurpleScheme
                }
            }
        }
    }

    private val DarkPurpleScheme = darkColorScheme(
        primary = PurpleDarkPrimary,
        secondary = PurpleDarkSecondary,
        tertiary = DarkTertiary,
        background = DarkBackground,
        surface = DarkTertiary,
        onPrimary = DarkTertiary,
        onSurface = DarkOnSurface
    )

    private val LightPurpleScheme = lightColorScheme(
        primary = PurpleLightPrimary,
        secondary = PurpleLightSecondary,
        tertiary = LightTertiary,
        background = LightBackground,
        surface = LightTertiary,
        onPrimary = LightTertiary,
        onSurface = LightOnSurface
    )

    private val DarkCrimsonScheme = darkColorScheme(
        primary = CrimsonDarkPrimary,
        secondary = CrimsonDarkSecondary,
        tertiary = DarkTertiary,
        background = DarkBackground,
        surface = DarkTertiary,
        onPrimary = DarkTertiary,
        onSurface = DarkOnSurface
    )

    private val LightCrimsonScheme = lightColorScheme(
        primary = CrimsonLightPrimary,
        secondary = CrimsonLightSecondary,
        tertiary = LightTertiary,
        background = LightBackground,
        surface = LightTertiary,
        onPrimary = LightTertiary,
        onSurface = LightOnSurface
    )

    private val DarkCadmiumGreenScheme = darkColorScheme(
        primary = CadmiumGreenDarkPrimary,
        secondary = CadmiumGreenDarkSecondary,
        tertiary = DarkTertiary,
        background = DarkBackground,
        surface = DarkTertiary,
        onPrimary = DarkTertiary,
        onSurface = DarkOnSurface
    )

    private val LightCadmiumGreenScheme = lightColorScheme(
        primary = CadmiumGreenLightPrimary,
        secondary = CadmiumGreenLightSecondary,
        tertiary = LightTertiary,
        background = LightBackground,
        surface = LightTertiary,
        onPrimary = LightTertiary,
        onSurface = LightOnSurface
    )

    private val DarkCobaltBlueScheme = darkColorScheme(
        primary = CobaltBlueDarkPrimary,
        secondary = CobaltBlueDarkSecondary,
        tertiary = DarkTertiary,
        background = DarkBackground,
        surface = DarkTertiary,
        onPrimary = DarkTertiary,
        onSurface = DarkOnSurface
    )

    private val LightCobaltBlueScheme = lightColorScheme(
        primary = CobaltBlueLightPrimary,
        secondary = CobaltBlueLightSecondary,
        tertiary = LightTertiary,
        background = LightBackground,
        surface = LightTertiary,
        onPrimary = LightTertiary,
        onSurface = LightOnSurface
    )
}