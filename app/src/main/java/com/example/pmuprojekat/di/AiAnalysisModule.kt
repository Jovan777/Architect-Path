package com.example.pmuprojekat.di

import com.example.pmuprojekat.BuildConfig
import com.example.pmuprojekat.ai.AiAnalysisService
import com.example.pmuprojekat.ai.AiSketchAnalysisService
import com.example.pmuprojekat.ai.MockAiAnalysisService
import com.example.pmuprojekat.ai.MockAiSketchAnalysisService
import com.example.pmuprojekat.ai.OpenAiCompatibleAiAnalysisService
import com.example.pmuprojekat.ai.OpenAiCompatibleSketchAnalysisService
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
    fun provideAiAnalysisService(): AiAnalysisService {
        val apiKey = BuildConfig.OPENAI_API_KEY.trim()

        return if (apiKey.isBlank()) {
            MockAiAnalysisService()
        } else {
            OpenAiCompatibleAiAnalysisService(
                apiKey = apiKey,
                endpoint = BuildConfig.OPENAI_API_BASE_URL,
                model = BuildConfig.OPENAI_MODEL
            )
        }
    }

    @Provides
    @Singleton
    fun provideAiSketchAnalysisService(): AiSketchAnalysisService {
        val apiKey = BuildConfig.OPENAI_API_KEY.trim()

        return if (apiKey.isBlank()) {
            MockAiSketchAnalysisService()
        } else {
            OpenAiCompatibleSketchAnalysisService(
                apiKey = apiKey,
                endpoint = BuildConfig.OPENAI_API_BASE_URL,
                model = BuildConfig.OPENAI_VISION_MODEL
            )
        }
    }
}
