package com.example.data.repository

import com.example.data.model.CurrentAffairArticle
import com.example.data.model.MainsPracticeItem
import com.example.data.model.OneLinerFact
import com.example.data.model.QuizQuestion

object SeedDataProvider {

  fun getInitialArticles(): List<CurrentAffairArticle> {
    return listOf(
      // --- 01 OCT 2026 ---
      CurrentAffairArticle(
        id = 1,
        title = "Election Commission of India issues Model Code Guidelines on Deepfakes and AI Misinformation",
        date = "2026-10-01",
        displayDate = "01 Oct 2026",
        topicCategory = "POLITY",
        examFocus = "UPSC & State PCS",
        syllabusTag = "GS Paper-II: Electoral Reforms, Article 324, Statutory Powers & Tech in Governance",
        summary = "The Election Commission of India (ECI) notified binding transparency guidelines directing political parties and candidates to label all synthetic audio-visual campaign materials and take down malicious deepfakes within 3 hours.",
        whyInNews = "With rapid advances in generative AI, synthetic clones and manipulated voice clips have increasingly surfaced during regional assembly elections, posing threats to free and fair elections.",
        keyPoints = """
          • Statutory mandate invoked under Article 324 (Superintendence, direction, and control of elections) and Section 126A of Representation of the People Act, 1951.
          • Parties must clearly water-mark and disclose any AI-generated synthetic content with visible 'AI Generated' badges.
          • Intermediary escalation protocol: Digital platforms designated nodal officers to review flagged election deepfakes within a 3-hour statutory window.
          • Violations will attract action under Section 171G (False statement in connection with an election) of the Bharatiya Nyaya Sanhita (BNS) and the IT Act 2000.
        """.trimIndent(),
        prelimsPointers = """
          • Constitutional Body: ECI established under Article 324 on January 25, 1950 (celebrated as National Voters' Day).
          • Chief Election Commissioner & ECs: Appointed by President based on selection committee (PM, Union Cabinet Minister, and Leader of Opposition / single largest party in Lok Sabha).
          • RPA 1951 deals with conduct of elections; RPA 1950 deals with voter lists and allocation of seats.
          • Section 126 of RPA 1951 imposes a 48-hour election silence period prior to conclusion of polling.
        """.trimIndent(),
        mainsAngle = """
          • Significance: Safeguards electoral integrity, ensures voter autonomy, and curbs misinformation in an era of asymmetric digital weaponization.
          • Challenges: Attribution problem of anonymous proxy handles, cross-border hosted servers, and balancing free political satire with regulatory overreach.
          • Way Forward: Implement algorithmic watermarking (C2PA standard), strengthen capacity of State Election Commissions, and promote digital voter literacy.
        """.trimIndent(),
        sscOneLiner = "ECI mandated 3-hour takedown deadline for AI deepfakes during elections under Article 324.",
        readTimeMinutes = 4,
        importanceTag = "CRITICAL"
      ),

      CurrentAffairArticle(
        id = 2,
        title = "Reserve Bank of India expands Central Bank Digital Currency (CBDC-R) Offline Offline-Mesh Transactions",
        date = "2026-10-01",
        displayDate = "01 Oct 2026",
        topicCategory = "ECONOMY",
        examFocus = "ALL",
        syllabusTag = "GS Paper-III: Indian Economy, Financial Inclusion, Monetary Policy & FinTech",
        summary = "The RBI rolled out offline peer-to-peer and merchant transacting protocols for the Digital Rupee (e₹-R) using proximity BLE and NFC technology for hilly and remote rural pockets.",
        whyInNews = "To bridge the digital payment divide in low-bandwidth regions and remote border villages where cellular internet connectivity remains intermittent.",
        keyPoints = """
          • Enables dual-offline capability: neither payer nor payee requires active internet at the point of exchange.
          • Secure cryptographic hardware elements in smartphones and feature phones store balance tokens locally.
          • Maximum per-transaction cap pegged at ₹2,000 to prevent fraud while ensuring daily convenience.
          • Implemented across pilot banks including SBI, ICICI, HDFC, and Punjab National Bank.
        """.trimIndent(),
        prelimsPointers = """
          • Definition: CBDC is legal tender issued by a central bank in digital form. It represents direct sovereign liability on RBI's balance sheet.
          • Legal Backing: Reserve Bank of India Act, 1934 was amended via Finance Act 2022 (Section 22 and Section 2) to include digital currency as banknotes.
          • Difference from UPI: UPI is an overlay payment interface that transfers bank deposits; CBDC is sovereign currency itself (no bank account required).
          • RBI Governor: Statutory head of central banking institution founded on April 1, 1935 under the Hilton Young Commission recommendations.
        """.trimIndent(),
        mainsAngle = """
          • Advantages: Drastically slashes physical currency printing and logistics costs (which exceeds ₹4,000 Cr annually), bolsters financial inclusion in tier-4 towns.
          • Systemic Risks: Threat of bank disintermediation during panic runs; cybersecurity and ledger validation in completely offline environments.
          • Way Forward: Interoperability with UPI QR codes, strict data anonymization up to standard transaction thresholds.
        """.trimIndent(),
        sscOneLiner = "RBI introduced offline Digital Rupee transactions with a cap of ₹2,000 via NFC/BLE.",
        readTimeMinutes = 4,
        importanceTag = "HIGH"
      ),

      CurrentAffairArticle(
        id = 3,
        title = "India adds 3 New Wetlands to Ramsar List; Total tally reaches 88",
        date = "2026-10-01",
        displayDate = "01 Oct 2026",
        topicCategory = "ENVIRONMENT",
        examFocus = "UPSC & State PCS",
        syllabusTag = "GS Paper-III: Biodiversity, Wetland Conservation, Montreux Record & Ecology",
        summary = "India declared three new wetland sites in Madhya Pradesh and Odisha as Wetlands of International Importance under the Ramsar Convention, elevating India's total Ramsar count to 88.",
        whyInNews = "Ministry of Environment, Forest and Climate Change (MoEFCC) received formal designation certificates from the Ramsar Secretariat in Gland, Switzerland.",
        keyPoints = """
          • Newly designated sites support critical staging grounds along the Central Asian Flyway (CAF) for migratory birds.
          • High endemism in ichthyofauna (freshwater fish species) and natural flood retention buffers for surrounding villages.
          • India ranks first in South Asia and third globally in total designated Ramsar sites.
          • State-level wetland authorities tasked with drafting Integrated Management Plans (IMPs).
        """.trimIndent(),
        prelimsPointers = """
          • Ramsar Convention: Adopted on February 2, 1971 in Ramsar, Iran; World Wetlands Day celebrated on February 2.
          • India became a contracting party on February 1, 1982.
          • Largest Ramsar site in India: Sundarbans (West Bengal).
          • Smallest Ramsar site in India: Renuka Wetland (Himachal Pradesh).
          • First Ramsar sites in India: Chilika Lake (Odisha) and Keoladeo National Park (Rajasthan) in 1981.
          • Montreux Record: Register of wetland sites on the Ramsar list where changes in ecological character have occurred or are likely to occur (currently Keoladeo NP and Loktak Lake are in the record).
        """.trimIndent(),
        mainsAngle = """
          • Ecological Role: Blue carbon sequestration, groundwater recharge, flood attenuation, and livelihood support for traditional fisher communities.
          • Threats: Siltation, encroachment by peri-urban real estate, pesticide run-off, and invasive weed infestation (e.g. Eichhornia crassipes).
          • Policy Framework: Wetlands (Conservation and Management) Rules, 2017 decentralize powers to State Wetland Authorities.
        """.trimIndent(),
        sscOneLiner = "India's total Ramsar sites rose to 88 with 3 new wetland additions in MP and Odisha.",
        readTimeMinutes = 3,
        importanceTag = "HIGH"
      ),

      CurrentAffairArticle(
        id = 4,
        title = "ISRO tests Semi-Cryogenic Pre-Burner Ignition Engine for Next-Gen Launch Vehicle (NGLV)",
        date = "2026-10-01",
        displayDate = "01 Oct 2026",
        topicCategory = "SCIENCE_TECH",
        examFocus = "ALL",
        syllabusTag = "GS Paper-III: Science & Tech, Indigenous Space Capability, Propellants & Cryogenics",
        summary = "The Indian Space Research Organisation (ISRO) successfully conducted the static hot test of its indigenous 2000 kN thrust semi-cryogenic engine at Mahendragiri Propulsion Complex.",
        whyInNews = "The SCE-200 engine will replace the liquid core stages of heavy lift launch vehicles, doubling payload injection capability to Geostationary Transfer Orbit (GTO).",
        keyPoints = """
          • Engine utilizes environmentally benign Isrosene (aviation grade kerosene) as fuel and liquid oxygen (LOX) as oxidizer.
          • Staged combustion cycle provides significantly higher specific impulse (ISP) compared to hypergolic fuels like UDMH/N2O4.
          • Directly supports India's planned Bharatiya Antariksh Station (BAS) and Gaganyaan-2 extended orbital missions.
          • Built in partnership with Godrej Aerospace and L&T.
        """.trimIndent(),
        prelimsPointers = """
          • Propellants: Semi-cryogenic uses refined kerosene (liquid at ambient temperature) and LOX (stored at -183°C).
          • Full Cryogenic engine (CE-20) uses Liquid Hydrogen (-253°C) and Liquid Oxygen (-183°C).
          • ISRO Propulsion Complex (IPRC) is located at Mahendragiri, Tirunelveli district, Tamil Nadu.
          • ISRO established: August 15, 1969; Headquarters: Bengaluru, Karnataka.
        """.trimIndent(),
        mainsAngle = """
          • Strategic Autonomy: Decreases reliance on foreign commercial heavy lifters like Ariane-6 for launching 6-8 ton communication satellites.
          • Commercial Competitiveness: NewSpace India Limited (NSIL) can offer lower cost per kilogram to low Earth orbit (LEO).
        """.trimIndent(),
        sscOneLiner = "ISRO tested SCE-200 semi-cryogenic engine using kerosene (Isrosene) and LOX at Mahendragiri.",
        readTimeMinutes = 3,
        importanceTag = "HIGH"
      ),

      // --- 30 SEP 2026 ---
      CurrentAffairArticle(
        id = 5,
        title = "Finance Ministry notifies Revised PM-KUSUM Scheme Guidelines with Solar Micro-Grids",
        date = "2026-09-30",
        displayDate = "30 Sep 2026",
        topicCategory = "GOVT_SCHEMES",
        examFocus = "UPSC & State PCS",
        syllabusTag = "GS Paper-II & III: Government Policies, Renewable Energy, Agriculture & Farmer Incomes",
        summary = "Ministry of New and Renewable Energy (MNRE) expanded PM-KUSUM to integrate solar water pumps with decentralized mini-grids, allowing farmers to sell surplus power directly to rural distribution companies.",
        whyInNews = "To meet India's COP30 pledge of 500 GW non-fossil capacity by 2030 and reduce agricultural power subsidy burden on state DISCOMs.",
        keyPoints = """
          • 3 Components: Component A (Small solar plants on barren land), Component B (Standalone solar pumps), Component C (Solarisation of grid-connected pumps).
          • Central Financial Assistance (CFA) enhanced to 35% for North Eastern and Himalayan States.
          • Integration with PM Surya Ghar Muft Bijli Yojana to cross-leverage net-metering smart meters.
          • Target: De-dieselization of 3.5 million irrigation pumps across drought-prone agricultural belts.
        """.trimIndent(),
        prelimsPointers = """
          • PM-KUSUM: Pradhan Mantri Kisan Urja Suraksha evam Utthaan Mahabhiyan, launched in 2019 by MNRE.
          • Implementing Agency: State Nodal Agencies (SNAs) for renewable energy along with DISCOMs.
          • India's target: 50% cumulative electric power installed capacity from non-fossil fuel-based energy resources by 2030 (achieved in 2025 ahead of schedule).
        """.trimIndent(),
        mainsAngle = """
          • Dual Benefits: Secures agrarian water sovereignty while creating supplementary non-farm income for cultivators via feed-in tariffs.
          • Bottlenecks: Groundwater overexploitation due to zero fuel marginal cost of pumping; delayed state subsidy disbursals to vendors.
          • Policy Remedy: Mandate drip irrigation sensors paired with solar pumps to curb water wastage.
        """.trimIndent(),
        sscOneLiner = "PM-KUSUM is administered by Ministry of New and Renewable Energy (MNRE) for solar agricultural pumps.",
        readTimeMinutes = 3,
        importanceTag = "MEDIUM"
      ),

      CurrentAffairArticle(
        id = 6,
        title = "Joint Maritime Exercise 'Varuna-2026' concludes between Indian and French Navies in Mediterranean",
        date = "2026-09-30",
        displayDate = "30 Sep 2026",
        topicCategory = "DEFENCE",
        examFocus = "ALL",
        syllabusTag = "GS Paper-II: Bilateral Maritime Cooperation, Indo-Pacific & Defence Diplomacy",
        summary = "The 24th edition of the bilateral naval exercise 'Varuna' concluded off the coast of Toulon, featuring guided missile destroyers, Scorpene submarines, and carrier strike groups.",
        whyInNews = "Showcased high interoperability and tactical coordination under the India-France Strategic Partnership.",
        keyPoints = """
          • Indian frontline stealth frigate INS Tabar and P-8I Long Range Maritime Patrol aircraft participated.
          • Included complex anti-submarine warfare (ASW) drills and air-defence combat simulations.
          • Reaffirmed commitment to open, rules-based maritime order in the Mediterranean and Western Indian Ocean.
        """.trimIndent(),
        prelimsPointers = """
          • Exercise Varuna: Bilateral naval exercise between India and France (initiated in 1983, named Varuna in 2001).
          • Other India-France Exercises: Exercise Shakti (Army), Exercise Garuda (Air Force), Desert Knight (Air Force).
          • First bilateral logistics agreement signed by India was with France in 2018.
        """.trimIndent(),
        mainsAngle = """
          • Strategic Importance: France possesses territorial sovereign islands in the Indian Ocean (Reunion, Mayotte), making it a resident power and vital resident partner in maritime domain awareness (MDA).
        """.trimIndent(),
        sscOneLiner = "Varuna-2026 is a bilateral naval exercise conducted between India and France.",
        readTimeMinutes = 3,
        importanceTag = "MEDIUM"
      ),

      // --- 29 SEP 2026 ---
      CurrentAffairArticle(
        id = 7,
        title = "Supreme Court delivers landmark ruling on Right to Clean Air under Article 21",
        date = "2026-09-29",
        displayDate = "29 Sep 2026",
        topicCategory = "POLITY",
        examFocus = "UPSC & State PCS",
        syllabusTag = "GS Paper-II: Judicial Precedents, Fundamental Rights, Article 21 & Public Trust Doctrine",
        summary = "A constitutional bench held that freedom from the adverse impacts of air pollution is a distinct fundamental entitlement emanating from the right to life and equality.",
        whyInNews = "Hearing public interest litigations on stubble burning, vehicular emissions, and winter smog in the National Capital Region.",
        keyPoints = """
          • Reaffirmed MC Mehta vs Union of India environmental jurisprudence.
          • Directed Commission for Air Quality Management (CAQM) to enforce strict graded response actions without executive discretion.
          • Applied Inter-generational Equity and Precautionary Principle to industrial emissions.
        """.trimIndent(),
        prelimsPointers = """
          • Article 21: Protection of life and personal liberty; available to both citizens and non-citizens.
          • CAQM: Commission for Air Quality Management in NCR and Adjoining Areas Act, 2021 (Statutory Body).
          • Air (Prevention and Control of Pollution) Act, 1981: Passed under Article 253 to implement decisions of the 1972 Stockholm Conference.
        """.trimIndent(),
        mainsAngle = """
          • Judicial Activism vs Governance: Strengthens civil rights against municipal inertia, but raises questions on institutional capacity to monitor compliance.
        """.trimIndent(),
        sscOneLiner = "Supreme Court recognized Right to Clean Air as an integral facet of Article 21 (Right to Life).",
        readTimeMinutes = 4,
        importanceTag = "HIGH"
      )
    )
  }

  fun getInitialQuizzes(): List<QuizQuestion> {
    return listOf(
      QuizQuestion(
        id = 1,
        date = "2026-10-01",
        examTarget = "UPSC Prelims",
        topic = "Polity",
        questionText = "Consider the following statements regarding the Election Commission of India (ECI):\n1. The superintendence, direction, and control of elections is vested in the ECI under Article 324.\n2. The Chief Election Commissioner is appointed solely at the pleasure of the Prime Minister.\n3. The Model Code of Conduct (MCC) is a statutory law enacted by the Parliament.\n\nWhich of the statements given above is/are correct?",
        optionA = "1 only",
        optionB = "1 and 3 only",
        optionC = "2 and 3 only",
        optionD = "1, 2 and 3",
        correctOptionIndex = 0,
        explanation = "Statement 1 is correct: Article 324 vests election superintendence in ECI.\nStatement 2 is incorrect: CEC and ECs are appointed by the President on the recommendation of a Selection Committee (PM, Union Cabinet Minister, and Leader of Opposition/Single Largest Party in Lok Sabha).\nStatement 3 is incorrect: The Model Code of Conduct (MCC) is NOT an Act of Parliament; it is a consensus-driven code of conduct formulated by political parties, though certain clauses have judicial enforcement through RPA 1951 and BNS."
      ),

      QuizQuestion(
        id = 2,
        date = "2026-10-01",
        examTarget = "UPSC Prelims",
        topic = "Economy",
        questionText = "With reference to Central Bank Digital Currency (CBDC) in India, consider the following statements:\n1. Digital Rupee (e₹) is a direct claim on commercial banks rather than the Reserve Bank of India.\n2. Amendment to the RBI Act, 1934 under the Finance Act 2022 gave legal tender status to CBDC.\n3. Offline CBDC transactions require active SIM data connection for real-time ledger settlement.\n\nWhich of the statements given above is/are correct?",
        optionA = "1 and 3 only",
        optionB = "2 only",
        optionC = "2 and 3 only",
        optionD = "1, 2 and 3",
        correctOptionIndex = 1,
        explanation = "Statement 2 is correct: The RBI Act, 1934 was amended by the Finance Act, 2022 to include digital currency within the definition of banknotes.\nStatement 1 is incorrect: CBDC is sovereign currency and a direct liability on the Reserve Bank of India's balance sheet, not commercial banks.\nStatement 3 is incorrect: The offline mode utilizes local cryptographic secure elements via BLE/NFC without needing active data/internet connectivity."
      ),

      QuizQuestion(
        id = 3,
        date = "2026-10-01",
        examTarget = "UPSC & State PCS",
        topic = "Environment",
        questionText = "Which of the following wetland sites are currently included in the 'Montreux Record' from India?\n1. Chilika Lake\n2. Loktak Lake\n3. Keoladeo National Park\n\nSelect the correct answer using the code given below:",
        optionA = "1 and 2 only",
        optionB = "2 and 3 only",
        optionC = "1 and 3 only",
        optionD = "1, 2 and 3",
        correctOptionIndex = 1,
        explanation = "The Montreux Record is a register of wetland sites on the Ramsar List where ecological character has changed or is likely to change. In India, Keoladeo National Park (Rajasthan) and Loktak Lake (Manipur) are in the Montreux Record. Chilika Lake was placed in the record in 1993 but was successfully removed in 2002 after rehabilitation."
      ),

      QuizQuestion(
        id = 4,
        date = "2026-10-01",
        examTarget = "SSC CGL",
        topic = "Science & Tech",
        questionText = "The ISRO Semi-Cryogenic Engine (SCE-200) tested at the Mahendragiri facility utilizes which combination of fuel and oxidizer?",
        optionA = "Liquid Hydrogen and Liquid Oxygen",
        optionB = "Isrosene (Refined Kerosene) and Liquid Oxygen",
        optionC = "Hydrazine and Nitrogen Tetroxide",
        optionD = "Solid Hydroxyl-terminated polybutadiene (HTPB)",
        correctOptionIndex = 1,
        explanation = "Correct Answer is (B). A semi-cryogenic engine uses refined kerosene (aviation grade kerosene called Isrosene) as fuel at normal temperature and Liquid Oxygen (LOX) as oxidizer stored at cryogenic temperature (-183°C)."
      ),

      QuizQuestion(
        id = 5,
        date = "2026-10-01",
        examTarget = "SSC CGL / State PCS",
        topic = "Defence",
        questionText = "Bilateral maritime exercise 'Varuna' is conducted between the navies of India and which of the following countries?",
        optionA = "Japan",
        optionB = "United Kingdom",
        optionC = "France",
        optionD = "Russia",
        correctOptionIndex = 2,
        explanation = "Correct Answer is (C) France. Varuna is the bilateral naval exercise between the Indian Navy and French Navy. (Bilateral Army exercise is 'Shakti' and Air Force exercise is 'Garuda')."
      )
    )
  }

  fun getInitialMains(): List<MainsPracticeItem> {
    return listOf(
      MainsPracticeItem(
        id = 1,
        date = "2026-10-01",
        question = "Discuss the socio-legal implications of generative artificial intelligence and synthetic media on electoral democracies. Evaluate the efficacy of the Election Commission of India's regulatory frameworks.",
        paperTag = "UPSC GS Paper-II: Electoral Reforms, Freedom of Speech & Constitutional Machinery",
        wordLimit = 250,
        marks = 15,
        introApproach = "Define generative AI and synthetic media (deepfakes). Highlight how the 21st-century information ecosystem has altered democratic discourse, transitioning from physical booth management to cognitive narrative management.",
        bodyApproach = """
          1. Threats to Electoral Integrity:
             - Polarization & Disinformation: Tailored micro-targeting and synthetic audio/video clips erode voter agency.
             - Plausible Deniability: Corrupt or controversial actors can claim authentic footage is 'AI deepfakes'.
             - Time Asymmetry: Fake news propagates 6x faster than rebuttals, distorting silence period norms.
          2. Evaluation of ECI's Directives:
             - Article 324 authority and mandatory watermarking create initial accountability.
             - Rapid 3-hour takedown mechanism engages platform nodal officers.
             - Limitations: Anonymity over decentralized networks (e.g. WhatsApp/Telegram), offshore servers beyond jurisdiction, and risk of stifling genuine political satire.
        """.trimIndent(),
        conclusionApproach = "Conclude with an approach synthesizing institutional muscle (ECI + Law Commission), technological solutions (cryptographic content provenance C2PA), and widespread digital civic education.",
        quotesAndCommittees = "Cite: Law Commission 255th Report on Electoral Reforms; SC verdict in Shreya Singhal (2015) & Puttaswamy (2017); C2PA standard."
      ),
      MainsPracticeItem(
        id = 2,
        date = "2026-09-30",
        question = "Analyze how the transition to decentralized solar irrigation under PM-KUSUM impacts India's groundwater sustainability and agrarian economy. Suggest policy safeguards.",
        paperTag = "UPSC GS Paper-III: Agriculture, Renewable Energy & Water Resource Management",
        wordLimit = 150,
        marks = 10,
        introApproach = "Contextualize PM-KUSUM as a flagship vehicle addressing the water-energy-agriculture nexus by replacing diesel pumps with solar energy.",
        bodyApproach = """
          - Positive Economic Dividends: Decouples farmers from erratic diesel expenses; enables sale of surplus electricity to DISCOMs via net metering.
          - The Jevons Paradox (Ecological Hazard): Zero marginal extraction cost of solar power can accelerate rampant over-pumping of critical aquifers.
          - Policy Reforms Needed: Mandate sensor-based micro-irrigation (drip/sprinkler); rational tariff pricing for fed-in solar units.
        """.trimIndent(),
        conclusionApproach = "Emphasize sustainable water budgeting and conjunctive water usage as vital to achieving both SDG 7 (Clean Energy) and SDG 6 (Clean Water).",
        quotesAndCommittees = "Cite: Mihir Shah Committee on Water Management; NITI Aayog Composite Water Management Index."
      )
    )
  }

  fun getInitialOneLiners(): List<OneLinerFact> {
    return listOf(
      OneLinerFact(
        id = 1,
        date = "2026-10-01",
        category = "Appointments",
        title = "New Chief of Naval Staff / Statutory Head",
        factDetails = "Admiral Dinesh Tripathi is the current Chief of the Naval Staff of India.",
        examScope = "SSC CGL / Defence Exams"
      ),
      OneLinerFact(
        id = 2,
        date = "2026-10-01",
        category = "Summits",
        title = "BRICS Annual Summit",
        factDetails = "BRICS members discussed alternative local currency clearing mechanisms and green corridor trade.",
        examScope = "UPSC / State PCS"
      ),
      OneLinerFact(
        id = 3,
        date = "2026-10-01",
        category = "Indexes",
        title = "Global Innovation Index 2026",
        factDetails = "India maintains top 40 standing published by the World Intellectual Property Organization (WIPO), Geneva.",
        examScope = "SSC / State PCS"
      ),
      OneLinerFact(
        id = 4,
        date = "2026-10-01",
        category = "Days & Themes",
        title = "International Day of Older Persons",
        factDetails = "Observed every year on October 1st by the United Nations General Assembly.",
        examScope = "SSC / RRB / Bank PO"
      ),
      OneLinerFact(
        id = 5,
        date = "2026-10-01",
        category = "Defence",
        title = "Exercise Mitra Shakti",
        factDetails = "Joint military exercise conducted annually between the Indian Army and Sri Lankan Army.",
        examScope = "SSC CGL / CDS / NDA"
      ),
      OneLinerFact(
        id = 6,
        date = "2026-09-30",
        category = "Awards",
        title = "Dadasaheb Phalke Lifetime Achievement Award",
        factDetails = "India's highest civilian cinema accolade presented annually by the Directorate of Film Festivals.",
        examScope = "SSC CGL / State PCS"
      ),
      OneLinerFact(
        id = 7,
        date = "2026-09-30",
        category = "Sports",
        title = "FIDE Chess Olympiad Gold",
        factDetails = "Indian Open and Women teams scripted historic clean-sweep gold medal double victories.",
        examScope = "SSC / Railways / State PCS"
      )
    )
  }
}
