package main.java.Day21;

import main.java.Common.AbstractDay;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Day extends AbstractDay {

    int playerOneStart = 3;
    int playerTwoStart = 5;

    int partOneWinScore = 1000;
    int partTwoWinScore = 21;
    int partTwoDiceSides = 3;

    long winOneCounter = 0;
    long winTwoCounter = 0;

    public static void main(String[] args) {
        Day day = new Day();
        day.solveDay();
    }

    public void solveDay() {
        solveFirstQuestion();
        solveSecondQuestion();
    }

    private void solveFirstQuestion() {
        boolean playerOneTurn = true;
        int turnNumber = 0;
        Quadrupel<Integer> game = new Quadrupel<>(playerOneStart, playerTwoStart, 0, 0);
        while (game._2 < partOneWinScore && game._3 < partOneWinScore) {

            int currentDiceNumber = 9 * (turnNumber) + 6;
            game = play(game, playerOneTurn, currentDiceNumber);
            playerOneTurn = !playerOneTurn;
            turnNumber++;
        }

        System.out.println("Part 1:");
        System.out.println(Math.min(game._2, game._3) * turnNumber * 3);
    }

    private void solveSecondQuestion() {
        // Positions:
        // 0: Position 1
        // 1: Position 2
        // 2: Score Player 1
        // 3: Score Player 2
        // 4: Player One's turn

        // Output:
        // Number of existing games with that description
        Map<Quadrupel<Integer>, Long> games = new HashMap<>();
        Quadrupel<Integer> firstGame = new Quadrupel<>(playerOneStart, playerTwoStart, 0, 0);
        games.put(firstGame, 1L);

        while (!games.keySet().isEmpty()) {
            games = playAndMemorizeGames(games, true);
            games = playAndMemorizeGames(games, false);
        }

        System.out.println("Part 2:");
        System.out.println(Math.max(winOneCounter, winTwoCounter));
    }

    private Map<Quadrupel<Integer>, Long> playAndMemorizeGames(Map<Quadrupel<Integer>, Long> games, boolean playerOne) {
        Set<Quadrupel<Integer>> keys = Collections.unmodifiableSet(games.keySet());
        Map<Quadrupel<Integer>, Long> newGames = new HashMap<>();
        for (Quadrupel<Integer> key : keys) {
            if (games.get(key) == 0) {
                continue;
            }
            long numberOfGamesForKey = games.get(key);
            int scorePlayerOne = key._2;
            int scorePlayerTwo = key._3;

            if (scorePlayerOne >= partTwoWinScore) {
                winOneCounter += numberOfGamesForKey;
            } else if (scorePlayerTwo >= partTwoWinScore) {
                winTwoCounter += numberOfGamesForKey;
            } else {
                splitUniverse(newGames, key, numberOfGamesForKey, playerOne);
            }
        }
        return newGames;
    }

    private void splitUniverse(Map<Quadrupel<Integer>, Long> games, Quadrupel<Integer> key, long number, boolean playerOne) {
        for (int i = 1; i <= partTwoDiceSides; i++) {
            for (int j = 1; j <= partTwoDiceSides; j++) {
                for (int k = 1; k <= partTwoDiceSides; k++) {
                    int diceSum = i + j + k;
                    Quadrupel<Integer> newGame = play(key, playerOne, diceSum);
                    if (games.containsKey(newGame)) {
                        games.put(newGame, games.get(newGame) + number);
                    } else {
                        games.put(newGame, number);
                    }
                }
            }
        }
    }

    private Quadrupel<Integer> play(Quadrupel<Integer> key, boolean playerOne, int diceSum) {
        Quadrupel<Integer> newGame = new Quadrupel<>();
        if (playerOne) {
            // Player one plays
            int playerPos = wrapAround10(key._0 + diceSum);
            newGame._0 = playerPos;
            newGame._2 = key._2 + playerPos;
            newGame._1 = key._1;
            newGame._3 = key._3;
        } else {
            // Player two plays
            int playerPos = wrapAround10(key._1 + diceSum);
            newGame._1 = playerPos;
            newGame._3 = key._3 + playerPos;
            newGame._0 = key._0;
            newGame._2 = key._2;
        }
        return newGame;
    }

    private int wrapAround10(int value) {
        return value % 10 == 0 ? 10 : value % 10;
    }
}
