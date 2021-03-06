package dev.yjyoon.kwnotice.presentation.ui.settings

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.yjyoon.kwnotice.domain.model.FcmTopic
import dev.yjyoon.kwnotice.domain.usecase.settings.GetFcmSubscriptionUseCase
import dev.yjyoon.kwnotice.domain.usecase.settings.SubscribeFcmTopicUseCase
import dev.yjyoon.kwnotice.domain.usecase.settings.UnsubscribeFcmTopicUseCase
import dev.yjyoon.kwnotice.presentation.fcm.FcmSubscription
import dev.yjyoon.kwnotice.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    getFcmSubscriptionUseCase: GetFcmSubscriptionUseCase,
    private val subscribeFcmTopicUseCase: SubscribeFcmTopicUseCase,
    private val unsubscribeFcmTopicUseCase: UnsubscribeFcmTopicUseCase,
    private val fcmSubscription: FcmSubscription
) : BaseViewModel() {

    val uiState: StateFlow<SettingsUiState> =
        getFcmSubscriptionUseCase().map { SettingsUiState.Success(it) }
            .stateIn(viewModelScope, SharingStarted.Eagerly, SettingsUiState.Loading)

    fun subscribeTo(topic: FcmTopic) {
        launch {
            subscribeFcmTopicUseCase(topic)
            fcmSubscription.subscribeTo(topic)
                .onFailure { unsubscribeFcmTopicUseCase(topic) }
        }
    }

    fun unsubscribeFrom(topic: FcmTopic) {
        launch {
            unsubscribeFcmTopicUseCase(topic)
            fcmSubscription.unsubscribeFrom(topic)
                .onFailure { subscribeFcmTopicUseCase(topic) }
        }
    }

    companion object {
        const val GITHUB_URL = "https://github.com/kw-notice/kw-notice-android-v2"
        const val PLAYSTORE_URL =
            "https://play.google.com/store/apps/details?id=dev.yjyoon.kwnotice"
    }
}
