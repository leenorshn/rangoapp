package com.avenir.rangoapp.ui.screens.home

import com.avenir.rangoapp.data.models.UserModel


data class HomeState(
    val user: UserModel?=null,
    val error:String?=null,
    val isLoading:Boolean=false
)


sealed class HomeEvent {
    data object OnLoadVideo:HomeEvent()
    //data class OnLikeVideo(val videoId:String):HomeEvent()
}