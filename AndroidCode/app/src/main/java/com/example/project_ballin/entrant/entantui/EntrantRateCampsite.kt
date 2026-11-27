
                if (rating > 0 && confirmRating && noPreviousRatingGiven) {
                    ConfirmActionDialog().ConfirmAction(
                        message = "Are you sure you want to rate campsite ${rating}?",
                        confirm = { confirmed ->
                            if (confirmed) {
                                val newRating = Rating(entrant.userID, rating, campsiteID)
                                onAdd(newRating)
                                navController.popBackStack()
                            }
                            rating = 0
                            confirmRating = false
                        }
                    )
                }

            }
        }
    }

    /**
     * A button that allows the entrant to give a rating to a campsite
     * Number of stars indicate the rating, 1-star being the worst and 5-star being the best
     *
     * @param number the number of the rating
     * @param onSelectedRating the function that is called when the button is clicked
     * @param modifier the modifier for the button
     * @param change the boolean that determines if the icon button should be big or small, and diff color,
     * it depends on whatever rating the entrant has given
     */
    @Composable
    fun RatingButton(
        number: Int,
        onSelectedRating: (Int) -> Unit,
        modifier: Modifier = Modifier,
        change: Boolean
    ) {
        val iconSize = if (change) 68.dp else 24.dp

        /**
         * Source Used: https://brahimoubbad.com/posts/how-to-use-material-icons-in-jetpack-compose/
         * Title: How to use Material Icons in Jetpack Compose - (A Developer's Complete Guide 2026)
         * Author: Brahim Oubbad
         * Published: May 22, 2025
         */
        IconButton(
            onClick = {
                onSelectedRating(number)
            },
            modifier = modifier
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Star Icon",
                modifier = Modifier.size(iconSize),
                tint = if (change) MaterialTheme.colorScheme.primary else Color.Gray
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun EntrantGiveCampsiteRatingnoPreviousRatingGivenPreview() {
        val navController = rememberNavController()

        EntrantRateCampsite().EntrantGiveCampsiteRatingScreen(
            navController,
            Entrant("1", "John", "Doe"),
            "123456789",
            onAdd = {},
            noPreviousRatingGiven = true
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun EntrantGiveCampsiteRatingPermissionDeniedPreview() {
        val navController = rememberNavController()

        EntrantRateCampsite().EntrantGiveCampsiteRatingScreen(
            navController,
            Entrant("1", "John", "Doe"),
            "123456789",
            onAdd = {},
            noPreviousRatingGiven = false
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun Entrant3StarRatingPreview() {
        Row{
            for (i in 1..5) {
                RatingButton(
                    i,
                    onSelectedRating = {},
                    change = (i <= 3)
                )
            }
        }
    }
}