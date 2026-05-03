package com.example.pmuprojekat.core.model


enum class StepType(
    val id: String
) {
    SINGLE_CHOICE("single_choice"),
    MULTI_CHOICE("multi_choice"),
    ORDERED_CARDS("ordered_cards"),
    CATEGORIZATION("categorization"),
    CODE_COMPLETION("code_completion"),
    ROLE_MAPPING("role_mapping"),
    VISUAL_MAPPING("visual_mapping"),
    HOTSPOT("hotspot"),
    FREE_TEXT("free_text"),
    MINI_ADR("mini_adr")
}