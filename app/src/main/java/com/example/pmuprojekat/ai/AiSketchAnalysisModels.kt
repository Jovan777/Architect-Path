package com.example.pmuprojekat.ai

data class AiSketchAnalysisRequest(
    val questionId: String,
    val title: String,
    val systemDescription: String,
    val drawingInstruction: String,
    val drawingChecklist: String,
    val internalRubric: String,
    val imageBytes: ByteArray,
    val imageMimeType: String
)

data class AiSketchAnalysisPrompt(
    val systemPrompt: String,
    val taskContextPrompt: String
)
