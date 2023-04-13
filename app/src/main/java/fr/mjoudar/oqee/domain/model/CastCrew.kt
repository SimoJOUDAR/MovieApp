package fr.mjoudar.oqee.domain.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CastCrew (
    private val _writers : List<String>,
    private val _directors : List<String>,
    private val _actors : List<String>
) : Parcelable, BaseObservable() {

    @get:Bindable
    val writers
        get() = _writers

    @get:Bindable
    val directors
        get() = _directors

    @get:Bindable
    val actors
        get() = _actors

    fun toCastMemberList(): List<CrewMember> {
        val list: MutableList<CrewMember> = mutableListOf()
        for (i in writers) list.add(CrewMember(i, "Writer"))
        for (i in directors) list.add(CrewMember(i, "Director"))
        for (i in actors) list.add(CrewMember(i, "Actor"))
        return list.toList()
    }
}