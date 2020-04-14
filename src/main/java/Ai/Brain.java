/*
BSD 3-Clause License
Copyright (c) 2020, Alaa Ben Fatma
All rights reserved.
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
1. Redistributions of source code must retain the above copyright notice, this
   list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice,
   this list of conditions and the following disclaimer in the documentation
   and/or other materials provided with the distribution.
3. Neither the name of the copyright holder nor the names of its
   contributors may be used to endorse or promote products derived from
   this software without specific prior written permission.
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package Ai;

import java.io.*;
import java.util.ArrayList;
import Backend.Jeu;
import Backend.Turn;

public class Brain {
    private static Coup nextMove = null;
    private static Turn tempPlayer = Jeu.tour;

    public static Coup nextMove() {
        tempPlayer = Jeu.tour;
        if (Jeu.gameOver(Jeu.terrain())) {
            nextMove = new Coup(0, 0);
            return nextMove;
        } else {
            if (!Jeu.isFree(1, 0)) {
                Jeu.occupe(0, 1);
                return new Coup(0, 0);
            } else if (!Jeu.isFree(0, 1)) {
                Jeu.occupe(1, 0);
                return new Coup(0, 0);
            }

        }
        minimax(Jeu.copyOfTerrain(), 5, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        return nextMove;
    }
    
    public static int minimax(boolean[][] terrain, int depth, int alpha, int beta, boolean maximizing) {

        if (Jeu.gameOver(terrain)) {
            if (tempPlayer != Turn.Player1) {
                return 10;
            } else {
                return -10;
            }
        }
        if (maximizing) {
            int bestScore = Integer.MIN_VALUE;
            ArrayList<Coup> coups = Jeu.coupsPossibles(terrain);
            for (Coup coup : coups) {
                boolean[][] xterrain = Jeu.copyOfTerrain(terrain);
                xterrain = futureTerrain(xterrain, coup);
                int score = minimax(xterrain, depth - 1, alpha, beta, false);
                alpha = Math.max(alpha, score);
                if (score >= bestScore) {
                    bestScore = score;
                    nextMove = coup;
                }
                if (beta <= alpha) {
                    break;
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            ArrayList<Coup> coups = Jeu.coupsPossibles(terrain);
            for (Coup coup : coups) {
                boolean[][] xterrain = Jeu.copyOfTerrain(terrain);
                xterrain = futureTerrain(xterrain, coup);
                int score = minimax(xterrain, depth - 1, alpha, beta, true);
                beta = Math.min(beta, score);
                if (score <= bestScore) {
                    bestScore = score;
                    nextMove = coup;
                }
                if (beta <= alpha) {
                    break;
                }
            }
            return bestScore;
        }
    }

    private static boolean[][] futureTerrain(boolean[][] terrain, Coup coup) {
        for (int i = coup.i; i < Jeu.longueur(); i++) {
            for (int j = coup.j; j < Jeu.largeur(); j++) {
                terrain[i][j] = false;
            }
        }
        if (tempPlayer == Turn.Player1) {
            tempPlayer = Turn.Player2;
        } else {
            tempPlayer = Turn.Player1;
        }
        return terrain;
    }
}
