/**
 * Captures the different state of a puzzle piece
 * i.e. either empty (unfilled),
 * filled (already covered by a pentamino) or
 * blocked (can't be covered by a pentamino)
 */
public enum PuzzlePieceMarking {
    EMPTY, // empty tile marked with "." in the input
    FILLED, // tile covered with one of the pentaminoes
    BLOCKED // tile which can't be covered with any pentaminoes, marked with "*" in the input
}
