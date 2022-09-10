package com.khs.movies.network.mapper.person

import com.khs.movies.domain.mapper.UniMapper
import com.khs.movies.domain.model.person.ListingPerson
import com.khs.movies.network.response.person.ListingPersonJson
import javax.inject.Inject

class ListingPersonJsonMapper @Inject constructor() : UniMapper<ListingPersonJson, ListingPerson> {

    override fun map(item: ListingPersonJson): ListingPerson = with(item) {
        ListingPerson(
            id = id,
            profilePath = profile_path,
            adult = adult,
            name = name,
            popularity = popularity
        )
    }
}