package dev.stukalo.usecase.core.mapper.base

import dev.stukalo.common.core.mapper.BaseMapper
import dev.stukalo.data.repository.core.model.base.BaseRepositoryModel
import dev.stukalo.usecase.core.model.base.BaseDomainModel

interface BaseRepositoryDomainMapper<Repository : BaseRepositoryModel, Domain : BaseDomainModel> :
    BaseMapper<Repository, Domain>
