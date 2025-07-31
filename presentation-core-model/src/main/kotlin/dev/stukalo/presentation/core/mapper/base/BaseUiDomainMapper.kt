package dev.stukalo.presentation.core.mapper.base

import dev.stukalo.common.core.mapper.BaseMapper
import dev.stukalo.common.core.model.BaseModel
import dev.stukalo.presentation.core.model.base.BaseUiModel

interface BaseUiDomainMapper<Repository : BaseModel, Ui : BaseUiModel> : BaseMapper<Repository, Ui>
