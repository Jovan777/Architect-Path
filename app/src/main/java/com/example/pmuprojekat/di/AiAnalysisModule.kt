package com.example.pmuprojekat.di

import com.example.pmuprojekat.BuildConfig
import com.example.pmuprojekat.ai.AiChatService
import com.example.pmuprojekat.ai.AiAnalysisService
import com.example.pmuprojekat.ai.AiSketchAnalysisService
import com.example.pmuprojekat.ai.EncyclopediaAiService
import com.example.pmuprojekat.ai.MockAiChatService
import com.example.pmuprojekat.ai.MockAiAnalysisService
import com.example.pmuprojekat.ai.MockAiSketchAnalysisService
import com.example.pmuprojekat.ai.MockEncyclopediaAiService
import com.example.pmuprojekat.ai.OpenAiCompatibleAiChatService
import com.example.pmuprojekat.ai.OpenAiCompatibleAiAnalysisService
import com.example.pmuprojekat.ai.OpenAiCompatibleEncyclopediaAiService
import com.example.pmuprojekat.ai.OpenAiCompatibleSketchAnalysisService
import com.example.pmuprojekat.ai.OpenAiChatCompletionsClient
import com.example.pmuprojekat.ai.MockVsAiService
import com.example.pmuprojekat.ai.OpenAiCompatibleVsAiService
import com.example.pmuprojekat.ai.VsAiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AiAnalysisModule {

    @Provides
    @Singleton
    fun provideOpenAiChatCompletionsClient(): OpenAiChatCompletionsClient {
        return OpenAiChatCompletionsClient(
            apiKey = BuildConfig.OPENAI_API_KEY,
            endpoint = BuildConfig.OPENAI_API_BASE_URL
        )
    }

    @Provides
    @Singleton
    fun provideAiAnalysisService(
        client: OpenAiChatCompletionsClient
    ): AiAnalysisService {
        val apiKey = BuildConfig.OPENAI_API_KEY.trim()

        return if (apiKey.isBlank()) {
            MockAiAnalysisService()
        } else {
            OpenAiCompatibleAiAnalysisService(
                client = client,
                model = BuildConfig.OPENAI_MODEL.trim()
            )
        }
    }

    @Provides
    @Singleton
    fun provideAiSketchAnalysisService(
        client: OpenAiChatCompletionsClient
    ): AiSketchAnalysisService {
        val apiKey = BuildConfig.OPENAI_API_KEY.trim()

        return if (apiKey.isBlank()) {
            MockAiSketchAnalysisService()
        } else {
            OpenAiCompatibleSketchAnalysisService(
                client = client,
                model = BuildConfig.OPENAI_VISION_MODEL.trim()
                    .ifBlank { BuildConfig.OPENAI_MODEL.trim() }
            )
        }
    }

    @Provides
    @Singleton
    fun provideEncyclopediaAiService(
        client: OpenAiChatCompletionsClient
    ): EncyclopediaAiService {
        val apiKey = BuildConfig.OPENAI_API_KEY.trim()

        return if (apiKey.isBlank()) {
            MockEncyclopediaAiService()
        } else {
            OpenAiCompatibleEncyclopediaAiService(
                client = client,
                model = BuildConfig.OPENAI_MODEL.trim()
            )
        }
    }

    @Provides
    @Singleton
    fun provideAiChatService(
        client: OpenAiChatCompletionsClient
    ): AiChatService {
        val apiKey = BuildConfig.OPENAI_API_KEY.trim()

        return if (apiKey.isBlank()) {
            MockAiChatService()
        } else {
            OpenAiCompatibleAiChatService(
                client = client,
                model = BuildConfig.OPENAI_MODEL.trim()
            )
        }
    }

    @Provides
    @Singleton
    fun provideVsAiService(
        client: OpenAiChatCompletionsClient
    ): VsAiService {
        val apiKey = BuildConfig.OPENAI_API_KEY.trim()

        return if (apiKey.isBlank()) {
            MockVsAiService()
        } else {
            OpenAiCompatibleVsAiService(
                client = client,
                model = BuildConfig.OPENAI_MODEL.trim()
            )
        }
    }
}
