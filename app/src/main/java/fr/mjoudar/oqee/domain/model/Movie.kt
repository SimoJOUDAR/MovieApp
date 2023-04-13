package fr.mjoudar.oqee.domain.model

import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie (
    private var _name: String,
    private var _synopsis: String,
    private var _cover: String,
    private var _genre: List<String>,
    private var _releaseDate: String,
    private var _duration: String,
    private var _trailer: String,
    private var _officialUrl: String,
    private var _copyright: String,
    private var _castCrew: CastCrew
) : Parcelable, BaseObservable()  {

    @get:Bindable
    var name
        get() = _name
        set(value) {
            _name = value
        }

    @get:Bindable
    var synopsis
        get() = _synopsis
        set(value) {
            _synopsis = value
        }

    @get:Bindable
    var cover
        get() = _cover
        set(value) {
            _cover = value
        }

    @get:Bindable
    var genre
        get() = _genre
        set(value) {
            _genre = value
        }

    @get:Bindable
    var releaseDate
        get() = _releaseDate
        set(value) {
            _releaseDate = value
        }

    @get:Bindable
    var duration
        get() = _duration
        set(value) {
            _duration = value
        }

    @get:Bindable
    var trailer
        get() = _trailer
        set(value) {
            _trailer = value
        }

    @get:Bindable
    var officialUrl
        get() = _officialUrl
        set(value) {
            _officialUrl = value
        }

    @get:Bindable
    var copyright
        get() = _copyright
        set(value) {
            _copyright = value
        }

    @get:Bindable
    var castCrew
        get() = _castCrew
        set(value) {
            _castCrew = value
        }
}