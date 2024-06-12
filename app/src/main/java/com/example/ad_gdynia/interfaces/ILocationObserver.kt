package com.example.ad_gdynia.interfaces

import com.example.ad_gdynia.dataclasses.Vector2

//Initially i used this for Main Activity to be updated about location changes
interface ILocationObserver {
    fun observerUpdate(location: Vector2)
}