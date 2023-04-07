package com.example.readingnotesapp.feature_note.domain.util

sealed class NoteOrder(val orderType: OrderType){
    class Title(orderType: OrderType): NoteOrder(orderType)
    class Date(orderType: OrderType): NoteOrder(orderType)
    class Color(orderType: OrderType): NoteOrder(orderType)


    //create a copy function for noteOrder. This will allow us to pass the new orderType
    //so we will keep the NoteOrder but we change the orderType
    //and then return the new noteOrder
    fun copy(orderType: OrderType) : NoteOrder{
        return when(this){
            is Title ->Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}
