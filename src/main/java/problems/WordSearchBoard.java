package problems;

import java.util.ArrayList;
import java.util.Collections;

/**
 * [
 * ["ABCE"],
 * ["SFCS"],
 * ["ADEE"]
 * ]
 * word = "ABCCED", -> returns 1,
 * word = "SEE", -> returns 1,
 * word = "ABCB", -> returns 1,
 * word = "ABFSAB" -> returns 1
 * word = "ABCD" -> returns 0
 */
public class WordSearchBoard {

    public class Position {
        public Position(int row, int column) {
            this.row = row;
            this.column = column;
        }

        int row;
        int column;
    }

    public int findWord(ArrayList<String> letters, Position positionInLetters, String word, int indexInWord) {
        char currentCharacter = letters.get(positionInLetters.row).charAt(positionInLetters.column);

        if (currentCharacter == word.charAt(indexInWord)) {
            if (indexInWord == word.length() - 1) {
                return 1;
            }
            indexInWord++;

            if (positionInLetters.row < letters.size() - 1) {
                Position below = new Position(positionInLetters.row + 1, positionInLetters.column);
                int ret = findWord(letters, below, word, indexInWord);
                if (ret == 1) {
                    return 1;
                }
            }

            if (positionInLetters.row > 0) {
                Position above = new Position(positionInLetters.row - 1, positionInLetters.column);
                int ret = findWord(letters, above, word, indexInWord);
                if (ret == 1) {
                    return 1;
                }
            }

            if (positionInLetters.column < letters.size() - 1) {
                Position right = new Position(positionInLetters.row, positionInLetters.column + 1);
                int ret = findWord(letters, right, word, indexInWord);
                if (ret == 1) {
                    return 1;
                }
            }

            if (positionInLetters.column > 0) {
                Position left = new Position(positionInLetters.row, positionInLetters.column - 1);
                int ret = findWord(letters, left, word, indexInWord);
                if (ret == 1) {
                    return 1;
                }
            }
        }

        return 0;
    }

    public int exist(ArrayList<String> a, String b) {
        for (int i = 0; i < a.size(); i++) {
            for (int j = 0; j < a.size(); j++) {
                int ret = findWord(a, new Position(i,j), b, 0);
                if (ret == 1) {
                    return 1;
                }
            }
        }
        return 0;
    }



    public static void main(String[] args) {
        WordSearchBoard kw = new WordSearchBoard();
        ArrayList<String> matrix = new ArrayList<>();
        //matrix.add("ABCE");
        //matrix.add("SFCS");
        //matrix.add("ADEE");

        String[] a = new String[] {"FEDCBECD", "FABBGACG", "CDEDGAEC", "BFFEGGBA", "FCEEAFDA", "AGFADEAC", "ADGDCBAA", "EAABDDFF"};
        Collections.addAll(matrix, a);

        System.out.println(kw.exist(matrix, "BCDCB"));
    }
}
