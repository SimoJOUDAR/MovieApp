package fr.mjoudar.oqee.domain.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CrewMember (
    private val _name: String,
    private val _status: String
) : Parcelable, BaseObservable() {

        @get:Bindable
        val name
                get() = _name

        @get:Bindable
        val status
                get() = _status
}