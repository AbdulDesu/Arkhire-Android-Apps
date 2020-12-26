package com.sizdev.arkhirefortalent.networking

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response = chain.run{
        val tokenAuth = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhY2NvdW50SUQiOjgsImFjY291bnRfbmFtZSI6Ik1vb25hIEhvc2hpbm92YSIsImFjY291bnRfZW1haWwiOiJNb29uYWZpa0Bob2xvbGl2ZS5pZCIsInByaXZpbGVnZSI6MCwiaWF0IjoxNjA5MDAwNjUwLCJleHAiOjE2MDkwNDM4NTB9.e8s24ZLeNViRVspYe7tTafKlTxjm1H_UME_j7qBImcQ"
        proceed(
            request().newBuilder()
                .addHeader("Authorization", "Bearer $tokenAuth")
                .build()
        )
    }
}