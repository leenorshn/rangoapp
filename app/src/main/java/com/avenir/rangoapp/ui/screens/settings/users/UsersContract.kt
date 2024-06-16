package com.avenir.rangoapp.ui.screens.settings.users

import com.avenir.rangoapp.data.models.UserModel


val listOfUser= listOf<UserModel>(
    UserModel(
        name = "Victor",
        phone = "0978154328",
        id = "1",
        isBlocked = false,
        role = "Admin"
    ),
    UserModel(
        name = "Jeannot",
        phone = "0998982167",
        id = "2",
        isBlocked = false,
        role = "Agent"
    ),
    UserModel(
        name = "Magi",
        phone = "0978154000",
        id = "3",
        isBlocked = false,
        role = "Agent"
    ),
)