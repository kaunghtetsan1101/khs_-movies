package com.khs.movies.cache.mapper.person

import com.khs.movies.cache.db.entity.person.PersonEntity
import com.khs.movies.cache.di.CacheMoshi
import com.khs.movies.domain.mapper.BiMapper
import com.khs.movies.domain.model.person.Person
import com.khs.movies.domain.model.person.PersonDetail
import com.squareup.moshi.Moshi
import javax.inject.Inject

class PersonEntityMapper @Inject constructor(
    @CacheMoshi private val moshi: Moshi
) : BiMapper<PersonEntity, Person> {

    override fun map(item: PersonEntity): Person = with(item) {
        Person(
            id = id,
            profilePath = profilePath,
            adult = adult,
            name = name,
            popularity = popularity,
            detail = detailJsonStr?.let {
                moshi.adapter(PersonDetail::class.java).fromJson(it)
            }
        )
    }

    override fun reverseMap(item: Person): PersonEntity = with(item) {
        PersonEntity(
            id = id,
            profilePath = profilePath,
            adult = adult,
            name = name,
            popularity = popularity,
            detailJsonStr = detail?.let {
                moshi.adapter(PersonDetail::class.java).toJson(it)
            }
        )
    }
}