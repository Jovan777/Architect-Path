package com.example.pmuprojekat.core.model


enum class QuestionType(
    val id: String,
    val displayName: String
) {
    PATTERN_RECOGNITION("pattern_recognition", "Prepoznavanje obrasca"),
    VISUAL_MAPPING("visual_mapping", "Visual Mapping"),
    MULTI_STEP_UNDERSTANDING("multi_step_understanding", "Višekoračno razumevanje"),
    PATTERN_COMPARISON("pattern_comparison", "Razlikovanje obrazaca"),

    CODE_COMPLETION("code_completion", "Dopuna pseudo-koda"),
    ROLE_MAPPING("role_mapping", "Mapiranje uloga"),
    REASONING("reasoning", "Rezonovanje"),
    REFACTORING("refactoring", "Refaktorisanje"),
    ERROR_DETECTION("error_detection", "Prepoznavanje greške"),

    SEQUENCE_LOGIC("sequence_logic", "Sequence Logic"),
    SYSTEM_REQUIREMENT_MAPPING("system_requirement_mapping", "Zahtevi i obrasci"),
    CONSTRAINT_DECISION("constraint_decision", "Izbor pod ograničenjima"),
    CONSEQUENCE_ANALYSIS("consequence_analysis", "Analiza posledica"),

    PRODUCTION_DIAGNOSIS("production_diagnosis", "Produkcijska dijagnostika"),
    OPTIMIZATION_STRATEGY("optimization_strategy", "Strategija optimizacije"),
    INCIDENT_ANALYSIS("incident_analysis", "Incident analiza"),
    TRADE_OFF("trade_off", "Trade-off evaluacija"),
    PRIORITIZATION("prioritization", "Prioritizacija"),

    ARCHITECTURE_EXTENSION("architecture_extension", "Projektovanje proširenja"),
    ARCHITECTURE_STYLE("architecture_style", "Izbor arhitektonskog stila"),
    ARCHITECTURE_REVIEW("architecture_review", "Revizija arhitekture"),
    ARCHITECTURE_COMPOSITION("architecture_composition", "Sastavljanje arhitekture"),
    SCALING_ASSESSMENT("scaling_assessment", "Procena skaliranja"),
    ARCHITECTURAL_COMPROMISE("architectural_compromise", "Arhitektonski kompromis"),
    ARCHITECTURE_SKETCH("architecture_sketch", "Arhitektonsko skiciranje sistema")
}
