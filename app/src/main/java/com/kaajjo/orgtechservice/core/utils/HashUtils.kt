package com.kaajjo.orgtechservice.core.utils

import java.math.BigInteger
import java.security.MessageDigest

class HashUtils {
    companion object {
        fun createMD5(input: String): String {
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
        }
    }
}