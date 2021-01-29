package com.solar.firebase

/**
 * Copyright 2020 Kenneth
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **/
class Sample {
    fun getUsername(firstName: String, result: (userImage: String) -> Unit): String {
        val lastName = "Song"
        val fullName = firstName + lastName

        result(fullName)
        return fullName
    }

    inline fun getUserAge(num: Int, lambda: (result: Int) -> Unit) : Int {
        val result = num * 2
        lambda.invoke(result)
        return result
    }

    fun getUserInfo() {
        getUsername("Kenneth") { username ->
            println("Username: $username")
        }

        getUserAge(5) { println("Age is $it")}
    }
}