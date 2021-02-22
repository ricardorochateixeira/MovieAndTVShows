package com.ricardoteixeira.movietvshowsexplorer.app.framework.network.mappers

import com.ricardoteixeira.movietvshowsexplorer.app.framework.network.UserProfileFirestore
import com.ricardoteixeira.movietvshowsexplorer.app.utils.DateUtil
import com.ricardoteixeira.movietvshowsexplorer.app.utils.EntityMapper
import com.ricardoteixeira.movietvshowsexplorer.domain.models.UserProfile
import javax.inject.Inject

class NetworkMapper
@Inject constructor(private val dateUtil: DateUtil): EntityMapper<UserProfileFirestore, UserProfile>{
    override fun mapFromEntity(entity: UserProfileFirestore): UserProfile {
        return UserProfile(id = entity.id, name = entity.name, email = entity.email, imageUri = entity.imageUri, dateCreated = dateUtil.convertFirebaseTimestampToStringDate(entity.dateCreated))
    }

    override fun mapToEntity(domainModel: UserProfile): UserProfileFirestore {
        return UserProfileFirestore(id = domainModel.id, name = domainModel.name, email = domainModel.email, imageUri = domainModel.imageUri, dateUtil.convertStringDateToFirebaseTimestamp(domainModel.dateCreated))
    }
}