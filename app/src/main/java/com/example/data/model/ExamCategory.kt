package com.example.data.model

enum class ExamCategory(val displayName: String, val shortBadge: String) {
  ALL("All Exams", "All"),
  UPSC("UPSC CSE", "UPSC"),
  SSC("SSC CGL / CHSL", "SSC"),
  STATE_PCS("State PCS", "State PCS")
}

enum class TopicCategory(val displayName: String, val iconName: String) {
  POLITY("Polity & Governance", "Gavel"),
  ECONOMY("Economy & Banking", "TrendingUp"),
  ENVIRONMENT("Environment & Ecology", "Forest"),
  SCIENCE_TECH("Science & Tech", "Science"),
  INTERNATIONAL_RELATIONS("IR & Global Affairs", "Public"),
  DEFENCE("Defence & Security", "Shield"),
  GOVT_SCHEMES("Schemes & Policies", "AccountBalance"),
  ART_CULTURE("Art & Heritage", "Palette")
}
