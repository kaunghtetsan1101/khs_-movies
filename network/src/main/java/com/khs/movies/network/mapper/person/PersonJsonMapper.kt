package com.khs.movies.network.mapper.person

import com.khs.movies.domain.mapper.UniMapper
import com.khs.movies.domain.model.person.Person
import com.khs.movies.domain.model.person.PersonDetail
import com.khs.movies.network.response.person.PersonJson
import javax.inject.Inject

class PersonJsonMapper @Inject constructor() : UniMapper<PersonJson, Person> {

    override fun map(item: PersonJson): Person = with(item) {
        Person(
            id = id,
            profilePath = profile_path,
            adult = adult,
            name = name,
            popularity = popularity,
            detail = PersonDetail(
                birthday = birthday,
                knownForDepartment = known_for_department,
                placeOfBirth = place_of_birth,
                alsoKnownAs = also_known_as,
                biography = biography
            )
        )
    }
}