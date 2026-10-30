package com.example.data

object FunoraData {

    val quizzes: List<Quiz> = listOf(
        Quiz(
            id = "space",
            title = "Space Explorer Quiz",
            category = "Science",
            description = "Test your knowledge of the universe, planets and the solar system.",
            questions = listOf(
                QuizQuestion(
                    question = "Which planet is known as the Red Planet?",
                    options = listOf("Venus", "Mars", "Jupiter", "Mercury"),
                    answerIndex = 1,
                    explanation = "Mars looks reddish because iron minerals in its soil have oxidized (rusted)."
                ),
                QuizQuestion(
                    question = "What is at the center of our solar system?",
                    options = listOf("Earth", "The Moon", "The Sun", "Jupiter"),
                    answerIndex = 2,
                    explanation = "The Sun is the star at the center of our solar system."
                ),
                QuizQuestion(
                    question = "Which is the largest planet in our solar system?",
                    options = listOf("Saturn", "Neptune", "Earth", "Jupiter"),
                    answerIndex = 3,
                    explanation = "Jupiter is the largest planet in our solar system, with a mass more than double all others combined."
                ),
                QuizQuestion(
                    question = "What do we call a rock from space that reaches the ground?",
                    options = listOf("Meteorite", "Comet", "Asteroid belt", "Nebula"),
                    answerIndex = 0,
                    explanation = "A space rock that survives its passage through the atmosphere and lands is a meteorite."
                ),
                QuizQuestion(
                    question = "Which object orbits Earth naturally?",
                    options = listOf("The Sun", "The Moon", "Mars", "Venus"),
                    answerIndex = 1,
                    explanation = "The Moon is Earth's only natural permanent satellite."
                )
            )
        ),
        Quiz(
            id = "nature",
            title = "Amazing Nature Quiz",
            category = "Nature",
            description = "How much do you know about Earth's wildlife, plants and natural wonders?",
            questions = listOf(
                QuizQuestion(
                    question = "What is the process by which plants make food using sunlight?",
                    options = listOf("Respiration", "Photosynthesis", "Fermentation", "Evaporation"),
                    answerIndex = 1,
                    explanation = "Photosynthesis converts light energy into chemical energy stored in glucose."
                ),
                QuizQuestion(
                    question = "Which is the largest animal on Earth?",
                    options = listOf("African elephant", "Blue whale", "Giraffe", "Whale shark"),
                    answerIndex = 1,
                    explanation = "The blue whale is the largest known animal ever to have lived, reaching up to 30 meters."
                ),
                QuizQuestion(
                    question = "What is a group of lions commonly called?",
                    options = listOf("A pride", "A pack", "A colony", "A flock"),
                    answerIndex = 0,
                    explanation = "A family group of lions is known as a pride."
                ),
                QuizQuestion(
                    question = "Which part of a tree usually absorbs water from soil?",
                    options = listOf("Leaves", "Flowers", "Roots", "Fruit"),
                    answerIndex = 2,
                    explanation = "Roots absorb moisture, water and essential minerals from the soil."
                ),
                QuizQuestion(
                    question = "What do bees collect from flowers?",
                    options = listOf("Sand", "Nectar and pollen", "Bark", "Seeds only"),
                    answerIndex = 1,
                    explanation = "Bees gather nectar for honey making and pollen for protein."
                )
            )
        ),
        Quiz(
            id = "logic",
            title = "Quick Logic Challenge",
            category = "Brain",
            description = "Warm up your brain with five clever deduction and pattern questions.",
            questions = listOf(
                QuizQuestion(
                    question = "What number comes next: 2, 4, 8, 16, ...?",
                    options = listOf("18", "24", "32", "30"),
                    answerIndex = 2,
                    explanation = "Each number doubles: 16 × 2 = 32."
                ),
                QuizQuestion(
                    question = "If all Zibs are Wugs and all Wugs are Lums, which must be true?",
                    options = listOf(
                        "All Lums are Zibs",
                        "All Zibs are Lums",
                        "No Zibs are Lums",
                        "Some Wugs are not Lums"
                    ),
                    answerIndex = 1,
                    explanation = "Transitive logic: Every Zib is a Wug, and every Wug is a Lum, so every Zib is a Lum."
                ),
                QuizQuestion(
                    question = "Which shape does not belong?",
                    options = listOf("Triangle", "Square", "Circle", "Rectangle"),
                    answerIndex = 2,
                    explanation = "A circle is curved with no straight edges or sharp corners."
                ),
                QuizQuestion(
                    question = "A baker's dozen equals how many?",
                    options = listOf("10", "12", "13", "14"),
                    answerIndex = 2,
                    explanation = "A traditional baker's dozen is 13 (one extra for luck or testing)."
                ),
                QuizQuestion(
                    question = "You have 3 apples and you take away 2. How many do you have?",
                    options = listOf("1", "2", "3", "0"),
                    answerIndex = 1,
                    explanation = "You took 2 apples, so you have the 2 apples you took!"
                )
            )
        ),
        Quiz(
            id = "inventions",
            title = "Great Discoveries",
            category = "History",
            description = "Surprising breakthroughs that changed the modern world.",
            questions = listOf(
                QuizQuestion(
                    question = "Who is credited with inventing the World Wide Web in 1989?",
                    options = listOf("Tim Berners-Lee", "Bill Gates", "Steve Jobs", "Alan Turing"),
                    answerIndex = 0,
                    explanation = "Sir Tim Berners-Lee invented the World Wide Web while working at CERN."
                ),
                QuizQuestion(
                    question = "What discovery happened when Alexander Fleming noticed mold in a petri dish?",
                    options = listOf("Aspirin", "Penicillin", "Insulin", "Vaccines"),
                    answerIndex = 1,
                    explanation = "Fleming discovered penicillin in 1928, revolutionizing antibiotics."
                ),
                QuizQuestion(
                    question = "Which precious metal is the best conductor of electricity at room temperature?",
                    options = listOf("Copper", "Gold", "Silver", "Platinum"),
                    answerIndex = 2,
                    explanation = "Silver is the best electrical conductor, though copper is more commonly used due to cost."
                )
            )
        )
    )

    val personalityTests: List<PersonalityTest> = listOf(
        PersonalityTest(
            id = "weekend",
            title = "Your Ideal Weekend",
            description = "Discover your fun-loving weekend archetype and rechargeable style.",
            questions = listOf(
                PersonalityQuestion(
                    question = "Your free Saturday starts. What sounds most inviting?",
                    options = listOf(
                        PersonalityOption("Explore a new town or trail", 0),
                        PersonalityOption("Build or craft something creative", 1),
                        PersonalityOption("Host or meet friends for a game", 2),
                        PersonalityOption("Cozy up with a captivating story", 3)
                    )
                ),
                PersonalityQuestion(
                    question = "Pick a spontaneous afternoon plan:",
                    options = listOf(
                        PersonalityOption("A scenic road trip to the unknown", 0),
                        PersonalityOption("A creative workshop or gallery", 1),
                        PersonalityOption("A lively group meetup or arcade", 2),
                        PersonalityOption("A peaceful cafe or movie marathon", 3)
                    )
                ),
                PersonalityQuestion(
                    question = "What is the first thing you pack for a picnic?",
                    options = listOf(
                        PersonalityOption("A camera and exploration map", 0),
                        PersonalityOption("A sketchbook or audio recorder", 1),
                        PersonalityOption("A frisbee, speaker or party game", 2),
                        PersonalityOption("A soft blanket and a good book", 3)
                    )
                ),
                PersonalityQuestion(
                    question = "Choose your ideal evening atmosphere:",
                    options = listOf(
                        PersonalityOption("A vibrant night market under neon lights", 0),
                        PersonalityOption("A maker studio buzzing with ideas", 1),
                        PersonalityOption("A banquet table surrounded by laughter", 2),
                        PersonalityOption("A warm fireplace in a quiet corner", 3)
                    )
                )
            ),
            results = listOf(
                PersonalityResult(
                    title = "The Explorer",
                    description = "You thrive on novelty, fresh horizons, and unexpected journeys. Routine bores you; the thrill of discovering unseen corners gives you boundless energy.",
                    badge = "🧭 ADVENTUROUS",
                    traits = listOf("Spontaneous", "Curious", "Bold", "Open-minded")
                ),
                PersonalityResult(
                    title = "The Creator",
                    description = "You turn imagination into tangible art, music, or code. You see possibilities everywhere and recharge by making something distinctly original.",
                    badge = "🎨 INNOVATIVE",
                    traits = listOf("Imaginative", "Focused", "Expressive", "Original")
                ),
                PersonalityResult(
                    title = "The Connector",
                    description = "You bring life to every circle. For you, experiences are magnified when shared with loved ones. You weave friendships effortlessly.",
                    badge = "🤝 SOCIAL CATALYST",
                    traits = listOf("Charismatic", "Warm", "Supportive", "Vibrant")
                ),
                PersonalityResult(
                    title = "The Dreamer",
                    description = "You find beauty in calm, reflective moments. Your inner world is rich and poetic. You recharge best with peace, stories, and mindful breathing.",
                    badge = "🌙 MINDFUL SOUL",
                    traits = listOf("Empathetic", "Introspective", "Calm", "Observant")
                )
            )
        ),
        PersonalityTest(
            id = "creative",
            title = "Your Creative Spark",
            description = "Find out how your imagination naturally likes to express itself.",
            questions = listOf(
                PersonalityQuestion(
                    question = "Given a completely free afternoon to make anything:",
                    options = listOf(
                        PersonalityOption("Map out a daring quest or expedition", 0),
                        PersonalityOption("Design a visual brand, poster or concept", 1),
                        PersonalityOption("Gather people to co-invent a new game", 2),
                        PersonalityOption("Write a short story or poetic reflections", 3)
                    )
                ),
                PersonalityQuestion(
                    question = "When facing an empty blank page, you:",
                    options = listOf(
                        PersonalityOption("Step outside to gather sensory clues", 0),
                        PersonalityOption("Experiment with bold geometry & colors", 1),
                        PersonalityOption("Bounce wild concepts off a friend", 2),
                        PersonalityOption("Wait for a poignant mood or character", 3)
                    )
                ),
                PersonalityQuestion(
                    question = "Which creative tool would you keep in your backpack?",
                    options = listOf(
                        PersonalityOption("A pocket compass & trail journal", 0),
                        PersonalityOption("A stylus & watercolor sketching kit", 1),
                        PersonalityOption("A deck of prompt cards & dice", 2),
                        PersonalityOption("A fountain pen & leather notebook", 3)
                    )
                ),
                PersonalityQuestion(
                    question = "What makes a project truly thrilling to you?",
                    options = listOf(
                        PersonalityOption("Pushing uncharted boundaries", 0),
                        PersonalityOption("Crafting a stunning visual aesthetic", 1),
                        PersonalityOption("Seeing an audience light up together", 2),
                        PersonalityOption("Evoking deep emotional resonance", 3)
                    )
                )
            ),
            results = listOf(
                PersonalityResult(
                    title = "The Pioneer",
                    description = "Your creativity comes alive in the field. You collect experiences like artifacts and turn them into brave new ventures.",
                    badge = "⚡ TRAILBLAZER",
                    traits = listOf("Fearless", "Inventive", "Dynamic")
                ),
                PersonalityResult(
                    title = "The Visual Crafter",
                    description = "You think in shapes, color harmony, and visual cadence. You can make even the mundane look mesmerizing.",
                    badge = "📐 AESTHETE",
                    traits = listOf("Detail-oriented", "Stylistic", "Visionary")
                ),
                PersonalityResult(
                    title = "The Collaborator",
                    description = "You are a master of synergy. Creative chemistry ignites when you bounce ideas back and forth with other minds.",
                    badge = "✨ SPARK MAKER",
                    traits = listOf("Inspirational", "Engaging", "Adaptive")
                ),
                PersonalityResult(
                    title = "The Storyteller",
                    description = "You are captivated by lore, depth, and world-building. You craft narratives that stick in people's hearts for years.",
                    badge = "📖 NARRATOR",
                    traits = listOf("Poetic", "Thoughtful", "Profound")
                )
            )
        )
    )

    val facts: List<FunFact> = listOf(
        FunFact(1, "Octopuses have three hearts: two pump blood to the gills, while one pumps it to the rest of the body.", "Biology", "ANIMALS"),
        FunFact(2, "A day on Venus is longer than its entire year! It takes Venus 243 Earth days to rotate once, but only 225 Earth days to orbit the Sun.", "Astronomy", "SPACE"),
        FunFact(3, "Wombat droppings are famously cube-shaped! This prevents their scat from rolling away on steep rocks.", "Nature", "ANIMALS"),
        FunFact(4, "Pure honey can remain edible for thousands of years. Archaeologists found 3,000-year-old honey in Egyptian tombs that is still perfectly preserved.", "Food Science", "SCIENCE"),
        FunFact(5, "Koala fingerprints are so remarkably similar to human fingerprints that they could confuse crime scene analysts.", "Biology", "ANIMALS"),
        FunFact(6, "A gathering of flamingos is officially called a 'flamboyance'.", "Language", "NATURE"),
        FunFact(7, "The Eiffel Tower can grow up to 15 cm (6 inches) taller during the heat of summer due to thermal expansion of the iron.", "Physics", "SCIENCE"),
        FunFact(8, "Sharks existed before trees! Sharks have been swimming in oceans for over 400 million years, while early trees appeared roughly 350 million years ago.", "History", "EARTH"),
        FunFact(9, "Botanically speaking, bananas are berries, but strawberries are not true botanical berries!", "Botany", "PLANTS"),
        FunFact(10, "The shortest war in recorded history was the Anglo-Zanzibar War of 1896, lasting between 38 and 45 minutes.", "History", "HISTORY"),
        FunFact(11, "Sea otters hold hands while sleeping so they do not drift away from each other in ocean currents.", "Zoology", "ANIMALS"),
        FunFact(12, "Hot water can freeze faster than cold water under certain thermodynamic conditions — a phenomenon called the Mpemba effect.", "Physics", "SCIENCE"),
        FunFact(13, "Cleopatra lived closer in time to the Moon landing than to the construction of the Great Pyramid of Giza.", "Chronology", "HISTORY"),
        FunFact(14, "Butterflies can taste with their feet! Sensors on their tarsi detect dissolved sugars when they land on plants.", "Entomology", "NATURE")
    )

    val numberPuzzles: List<NumberPuzzle> = listOf(
        NumberPuzzle(
            sequence = "3,  6,  12,  24,  ?",
            options = listOf("36", "42", "48", "52"),
            answerIndex = 2,
            explanation = "Each number doubles: 24 × 2 = 48."
        ),
        NumberPuzzle(
            sequence = "1,  4,  9,  16,  ?",
            options = listOf("20", "24", "25", "36"),
            answerIndex = 2,
            explanation = "These are consecutive square numbers: 1², 2², 3², 4², and 5² = 25."
        ),
        NumberPuzzle(
            sequence = "5,  10,  8,  16,  14,  ?",
            options = listOf("18", "20", "28", "24"),
            answerIndex = 2,
            explanation = "The pattern alternates between × 2 and − 2: 14 × 2 = 28."
        ),
        NumberPuzzle(
            sequence = "2,  3,  5,  8,  13,  ?",
            options = listOf("18", "20", "21", "22"),
            answerIndex = 2,
            explanation = "Fibonacci sequence: Each number is the sum of the previous two (8 + 13 = 21)."
        ),
        NumberPuzzle(
            sequence = "1,  8,  27,  64,  ?",
            options = listOf("100", "125", "128", "144"),
            answerIndex = 1,
            explanation = "Cubes of consecutive integers: 1³, 2³, 3³, 4³, and 5³ = 125."
        )
    )

    val dailyQuestionsPool: List<DailyQuestion> = listOf(
        DailyQuestion(
            id = "d1",
            question = "Which planet is famous for its extensive, bright ring system?",
            options = listOf("Mars", "Saturn", "Mercury", "Venus"),
            answerIndex = 1,
            explanation = "Saturn is world-renowned for its dazzling system of rings made mostly of water ice particles and rocky debris."
        ),
        DailyQuestion(
            id = "d2",
            question = "How many sides does a regular hexagon have?",
            options = listOf("Five", "Six", "Seven", "Eight"),
            answerIndex = 1,
            explanation = "A hexagon has six straight sides and six interior angles summing to 720 degrees."
        ),
        DailyQuestion(
            id = "d3",
            question = "Which famous reptile is celebrated for shifting its skin color to camouflage and communicate?",
            options = listOf("Chameleon", "Panda", "Gecko", "Komodo dragon"),
            answerIndex = 0,
            explanation = "Chameleons alter their skin coloration using specialized nanocrystal cells called iridophores to communicate mood and blend in."
        ),
        DailyQuestion(
            id = "d4",
            question = "What is the largest and deepest ocean on planet Earth?",
            options = listOf("Atlantic", "Indian", "Pacific", "Arctic"),
            answerIndex = 2,
            explanation = "The Pacific Ocean is the largest ocean basin on Earth, covering more than 30% of the planet's surface."
        ),
        DailyQuestion(
            id = "d5",
            question = "What is the hardest known natural mineral on Earth?",
            options = listOf("Quartz", "Diamond", "Topaz", "Ruby"),
            answerIndex = 1,
            explanation = "Diamond rates a 10 on the Mohs hardness scale, making it the hardest known natural substance."
        )
    )
}
