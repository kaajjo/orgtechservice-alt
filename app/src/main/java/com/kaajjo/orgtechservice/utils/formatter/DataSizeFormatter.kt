package com.kaajjo.orgtechservice.utils.formatter

import android.content.Context
import com.kaajjo.orgtechservice.R
import java.text.DecimalFormat
import kotlin.math.log10
import kotlin.math.pow

class DataSizeFormatter {
    private val unitsShort = listOf(
        R.string.unit_data_short_byte,
        R.string.unit_data_short_kibibyte,
        R.string.unit_data_short_mebibyte,
        R.string.unit_data_short_gibibyte,
        R.string.unit_data_short_tebibyte,
        R.string.unit_data_short_pebibyte,
        R.string.unit_data_short_exbibyte,
        R.string.unit_data_short_zebibyte,
        R.string.unit_data_short_yobibyte
    )

    /**
     * Formats size in [bytes] into a human-readable form: B, KiB, KB, MiB, etc.
     * @param bytes size in bytes
     * @return formatted string
     */
    fun bytesReadable(
        bytes: Float,
        context: Context,
        decimalFormat: DecimalFormat = DecimalFormat("0.##")
    ): String {
        val digitsGroup = (log10(bytes) / log10(1024f)).toInt()

        return decimalFormat.format(bytes / 1024.0.pow(digitsGroup.toDouble())) + " " + context.getString(
            unitsShort[digitsGroup]
        )
    }
}