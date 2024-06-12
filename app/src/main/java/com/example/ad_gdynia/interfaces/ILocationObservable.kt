package com.example.ad_gdynia.interfaces

import com.example.ad_gdynia.dataclasses.Vector2

//Initially i used this to update the location changes
interface ILocationObservable {
    var observers: ArrayList<ILocationObserver>

    fun addObserver(observer: ILocationObserver) {
        observers.add(observer)
    }

    fun removeObserver(observer: ILocationObserver) {
        observers.remove(observer)
    }

    fun notifyObservers(location: Vector2) {
        for(observer in observers) {
            observer.observerUpdate(location)
        }
    }
}