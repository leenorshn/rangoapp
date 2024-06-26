package com.avenir.rangoapp.ui.screens.settings.users

import com.avenir.rangoapp.data.models.UserModel


val listOfUser= listOf<UserModel>(
    UserModel(
        name = "Victor",
        phone = "0978154328",
        uid = "1",
        isBlocked = false,
        role = "Admin"
    ),
    UserModel(
        name = "Jeannot",
        phone = "0998982167",
        uid = "2",
        isBlocked = false,
        role = "Agent"
    ),
    UserModel(
        name = "Magi",
        phone = "0978154000",
        uid = "3",
        isBlocked = false,
        role = "Agent"
    ),
)