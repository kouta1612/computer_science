type PlayType = "hamlet" | "as-like" | "othello"

interface Player {
    name: string,
    type: string
}

type Plays = {
    [key in PlayType]: Player
}

export { Plays, Player }
