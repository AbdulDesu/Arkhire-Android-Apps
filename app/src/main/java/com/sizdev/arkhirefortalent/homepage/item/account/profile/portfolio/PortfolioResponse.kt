package com.sizdev.arkhirefortalent.homepage.item.account.profile.portfolio

import com.google.gson.annotations.SerializedName

data class PortfolioResponse(val success: String, val message: String, val data: List<PortfolioResponse.Portfolio>) {

    data class Portfolio(@SerializedName("portfolioID") val portfolioID: String,
                         @SerializedName("portfolio_owner") val portfolioOwner: String,
                         @SerializedName("portfolio_title") val portfolioTitle: String,
                         @SerializedName("portfolio_desc") val portfolioDesc: String,
                         @SerializedName("portfolio_repository") val portfolioRepository: String,
                         @SerializedName("portfolio_image") val portfolioImage: String)
}