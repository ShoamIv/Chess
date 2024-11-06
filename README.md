Chess-Like Game
This project is a Java-based chess-like game with a graphical user interface (GUI) and basic game logic. It includes classes for representing various chess pieces, players, and game logic.

Project Structure
src/main/java: Contains the main game code.
test/resources: Contains resources used for testing purposes.
Key Classes
ConcretePiece.java: Represents a concrete implementation of a generic chess piece. This class likely extends a base Piece class and includes specific details about the type and movement rules of each piece.

ConcretePlayer.java: Represents an individual player in the game, managing player-specific data such as pieces, turn order, and game status.

GUI_for_chess_like_games.java: Contains the main graphical user interface (GUI) for the game. This class manages the visual representation of the board, pieces, and user interactions.

GameLogic.java: Encapsulates the core logic of the game, including piece movements, turn-taking, and rule enforcement.

GameLogicTest.java: A test class containing unit tests for verifying the functionality of GameLogic. This helps ensure that the game's rules and mechanics work as intended.

King.java: Represents the King piece in the game, with unique movement and capture abilities following chess-like rules.

Main.java: The main entry point of the program. This class initializes the game, sets up the GUI, and starts the game loop.

Pawn.java: Represents the Pawn piece, with movement and capture rules specific to chess-like games.

Piece.java: A base class that defines general properties and methods for all pieces, such as position, color, and basic movement.

PlayableLogic.java: A helper class that may assist in enforcing playable logic for pieces, moves, or other game aspects.

Player.java: Represents a player in the game and manages player actions, pieces, and status.

Position.java: Manages and represents a position on the game board, using coordinates for placing and moving pieces.

Features
Chess-Like Pieces: Includes pieces such as King, Pawn, and others that follow the movement patterns similar to chess.

Game Logic: Implements the fundamental rules for a chess-like game, including turn-based movement, capturing, and checking victory conditions.

GUI Interface: Provides a graphical user interface to visualize the board and interact with the game. Players can move pieces on the board through mouse clicks or other GUI interactions
