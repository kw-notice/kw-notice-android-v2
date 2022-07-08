package dev.yjyoon.kwnotice.domain.usecase

import dev.yjyoon.kwnotice.domain.model.FcmTopic
import dev.yjyoon.kwnotice.domain.repository.SettingsRepository
import javax.inject.Inject

class SubscribeFcmTopicUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {

    suspend operator fun invoke(topic: FcmTopic): Result<Unit> =
        settingsRepository.subscribeFcmTopic(topic)
}
