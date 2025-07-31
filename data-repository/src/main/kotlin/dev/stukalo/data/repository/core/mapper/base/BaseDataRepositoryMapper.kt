package dev.stukalo.data.repository.core.mapper.base

import dev.stukalo.common.core.mapper.BaseMapper
import dev.stukalo.data.network.core.model.base.BaseDataModel
import dev.stukalo.data.repository.core.model.base.BaseRepositoryModel

interface BaseDataRepositoryMapper<Data : BaseDataModel, Repository : BaseRepositoryModel> :
    BaseMapper<Data, Repository>
