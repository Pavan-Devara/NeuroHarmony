package com.neuro.neuroharmony.ui.login

import android.widget.EditText


class Validator {
    companion object {

        private fun getText(data: Any): String {
            var str = ""
            if (data is EditText) {
                str = data.text.toString()
            } else if (data is String) {
                str = data
            }
            return str
        }

        /**
         * Checks if the name is valid.
         * @param data - can be EditText or String
         * @param updateUI - if true and if data is EditText, the function sets error to the EditText or its TextInputLayout
         * @return - true if the name is valid.
         */
        fun isValidName(data: Any): Boolean {
            val name = data.toString()
            val regexStr = "[a-zA-Z]+".toRegex()
            return name.matches(regexStr)
        }

        fun isValidFName(data: Any): Boolean {
            val fname = data.toString()
            val regexStr = "^[a-zA-Z]+\\s[a-zA-Z]+\$".toRegex()
            return fname.matches(regexStr)
        }

        fun isValidDigit(data: Any): Boolean {
            val digit = data.toString()
            val regexStr = "[0-9]+".toRegex()
            return digit.matches(regexStr)
        }

        /**
         * Checks if the email is valid.
         * @param data - can be EditText or String
         * @param updateUI - if true and if data is EditText, the function sets error to the EditText or its TextInputLayout
         * @return - true if the email is valid.
         */
        fun isValidEmail(data: Any, updateUI: Boolean = true): Boolean {
            val name = data.toString()
            val regexStr = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))\$".toRegex()
            return name.matches(regexStr)
        }

        /**
         * Checks if the phone is valid.
         * @param data - can be EditText or String
         * @param updateUI - if true and if data is EditText, the function sets error to the EditText or its TextInputLayout
         * @return - true if the phone is valid.
         */
        fun isValidPhone(data: Any): Boolean {
            val phone = data.toString()
            val regexStr = "[0-9]{10}".toRegex()
            val phonevalid = phone.matches(regexStr)
            return phonevalid
        }
        /**
         * Checks if the password is valid as per the following password policy.
         * Password should be minimum minimum 8 characters long.
         * Password should contain at least one number.
         * Password should contain at least one capital letter.
         * Password should contain at least one small letter.
         * Password should contain at least one special character.
         * Allowed special characters: "~!@#$%^&*()-_=+|/,."';:{}[]<>?"
         *
         * @param data - can be EditText or String
         * @param updateUI - if true and if data is EditText, the function sets error to the EditText or its TextInputLayout
         * @return - true if the password is valid as per the password policy.
         */
        fun isValidPassword(data: Any): Boolean {
            val password = data.toString()
            var passwordvalid = true
            if (password.length < 8) {
                passwordvalid = false
            }
            var exp = ".*[0-9].*".toRegex()
            if (!password.matches(exp)) {
                passwordvalid = false
            }
            exp = ".*[A-Z].*".toRegex()
            if (!password.matches(exp)) {
                passwordvalid = false
            }
            exp = ".*[a-z].*".toRegex()
            if (!password.matches(exp)) {
                passwordvalid = false
            }
            // exp = ".*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\}];:'\",<.>/?].*".toRegex()
            exp = ".*[~!@#\$%\\^&*()\\-_=+\\|\\[{\\]};:'\",<.>/?`].*".toRegex() //  ` and \
            if (!password.matches(exp)) {
                passwordvalid = false
            }
            return passwordvalid
        }
    }
}