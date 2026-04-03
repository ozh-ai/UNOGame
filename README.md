# UNO Card Game

A Java implementation of the classic UNO card game featuring a human player competing against three AI opponents.

## Overview

This project is a command-line UNO game where you (Player 4) play against three AI players. The game follows standard UNO rules including colored cards, number cards, action cards (Skip, Reverse, +2), and Wild cards.

## Files

- **UNO.java** - Main game logic and engine
- **UNODeck.txt** - Card deck definition containing all 112 UNO cards
  - Number cards (0-9) in Red, Yellow, Blue, Green
  - Action cards: Skip, Reverse, +2
  - Wild cards: Wild, Wild +4

## How to Play

### Compilation
```bash
javac UNO.java
```

### Running the Game
```bash
java UNO
```

## Game Rules

- **4 Players**: You are Player 4. Players 1-3 are AI-controlled
- **Starting Hand**: Each player receives 7 cards
- **Card Matching**: Play a card that matches either:
  - The color of the top card, OR
  - The value/action of the top card
- **Draw**: If you can't play a matching card, draw one from the deck
- **Special Cards**:
  - **Skip**: Next player loses a turn
  - **Reverse**: Changes play direction (clockwise ↔ counterclockwise)
  - **+2**: Next player draws 2 cards and loses a turn
  - **Wild**: Change the current color (AI chooses most common color in hand)
  - **Wild +4**: Change the current color + next player draws 4 cards and loses a turn
- **Winning**: First player to empty their hand wins

## Game Features

- **AI Decision Making**: AI players automatically choose valid cards to play, with Wild cards selecting the color they have most of
- **Game State Display**: Shows remaining card count for all players
- **Human Input**: You select which card to play or choose to draw
- **Dynamic Deck Management**: When the draw pile runs out, discard cards are shuffled back in

## Code Structure

### Key Functions

- `play()` - Plays a card from a player's hand to the discard pile
- `draw()` - Draws a card from the deck; reshuffles discards if needed
- `getColour()` - Extracts card color
- `getValue()` - Extracts card value/action
- `mostColour()` - Determines the most common color in a player's hand
- `getPlayer()` - Returns the current player's hand
- `nextPlayer()` - Calculates the next player in turn order

## Requirements

- Java 8 or higher
- `UNODeck.txt` file must be in the same directory as the compiled class

## Notes

- The game uses text-based input/output
- Card format in deck: `Value (Color)` e.g., "5 (Red)", "Skip (Yellow)"
- Players are represented by numbers 1-4, with 4 being you
