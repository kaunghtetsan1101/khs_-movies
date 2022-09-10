package com.khs.movies.cache.mapper.person

import com.khs.movies.cache.db.entity.person.PersonEntity
import com.khs.movies.domain.mapper.BiMapper
import com.khs.movies.domain.model.person.ListingPerson
import javax.inject.Inject

class ListingPersonEntityMapper @Inject constructor() : BiMapper<PersonEntity, ListingPerson> {

    override fun map(item: PersonEntity): ListingPerson = with(item) {
        ListingPerson(
            id = id,
            profilePath = profilePath,
            adult = adult,
            name = name,
            popularity = popularity
        )
    }

    override fun reverseMap(item: ListingPerson): PersonEntity = with(item) {
        PersonEntity(
            id = id,
            profilePath = profilePath,
            adult = adult,
            name = name,
            popularity = popularity,
            detailJsonStr = null
        )
    }
}