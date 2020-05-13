package ticTacToe;

import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;
import java.util.InputMismatchException;

public class TicTacToeClass {
    private static String lastMover;

    public static String getLastMover(){
        return lastMover;
    }

    public static void setLastMover(String lastMoverLoc){
        lastMover = lastMoverLoc;
    }

    private static enum State {
        GAME_NOT_FINISHED("Game not finished"),
        DRAW("Draw"),
        X_WINS("X wins"),
        O_WINS("O wins"),
        IMPOSSIBLE("Impossible");

        private String str;
        State(String str) {
            this.str = str;
        }

        @Override
        public String toString() {
            return str;
        }
    }

    private static enum ErrorMessages {
        OCCUPIED("This cell is occupied! Choose another one!"),
        NOT_NUMBERS("You should enter numbers!"),
        OUT_OF_RANGE("Coordinates should be from 1 to 3!");

        private String str;
        ErrorMessages(String str) {
            this.str = str;
        }
        @Override
        public String toString() {
            return str;
        }
    }

    public static void create_cube(String cells) {
        //String[][] arr2 = new String[3][3];
        String[][] arr2 = {{"_","_","_"},{"_","_","_"},{"_","_","_"}};
        int countX = 0;
        int countO = 0;
        int countArr = 0;

        System.out.println("---------");
        for (int i = 0; i < arr2.length; i++) {
            for (int j = 0; j< arr2[i].length; j++) {

                arr2[i][j] = String.valueOf(cells.charAt(countArr));
                countArr++;

                if ((j + 3) % 3 == 0){
                    System.out.print("| " + arr2[i][j]);
                } else if ((j + 1) % 3 == 0){
                    System.out.println(arr2[i][j] + " |");
                } else {
                    System.out.print(" " + arr2[i][j] + " ");
                }

                switch(arr2[i][j]) {
                    case "X":
                        countX++;
                        break;
                    case "O":
                        countO++;
                }
            }
        }

        System.out.println("---------");

        determineState(arr2, countX, countO, cells);
    }

    public static void determineState(String[][] arr, int countX, int countO, String cells) {
        String winsTeam = "";
        int wins = 0;

        if (Math.abs(countX - countO) >= 2) {
            System.out.println(State.IMPOSSIBLE);
        } else {
            for (int  i = 0; i< arr.length; i++) {
                if ( arr[i][0].equals(arr[i][1]) && arr[i][1].equals(arr[i][2]) ) {
                    if (!arr[i][0].equals("_")) {  wins++; winsTeam = arr[i][0]; }
                }
                if (arr[0][i].equals(arr[1][i]) && arr[1][i].equals(arr[2][i])) {
                    if (!arr[0][i].equals("_")) { wins++; winsTeam = arr[0][i]; }
                }
            }
            if(arr[0][0].equals(arr[1][1]) && arr[1][1].equals(arr[2][2])) {
                if (!arr[0][0].equals("_")) { wins++; winsTeam = arr[0][0]; }
            }
            if(arr[0][2].equals(arr[1][1]) && arr[1][1].equals(arr[2][0])) {
                if (!arr[0][2].equals("_")) { wins++;  winsTeam = arr[0][2]; }
            }


            if ((wins > 1) && (!winsTeam.equals(""))){
                System.out.println(State.IMPOSSIBLE);
            } else if ((wins == 1 && (!winsTeam.equals("")))) {
                System.out.println(winsTeam + " wins");
            } else if ((wins == 0) && (countO + countX < 9)) {
                create_cube(modifyString(cells)); //game not finished
            } else if ((wins == 0) && (countO + countX == 9)) {
                System.out.println(State.DRAW);
            }
        }
    }

    public static String modifyString(String str) {
        String strCorrect;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the coordinates: ");

        try {
            int coord1 = scanner.nextInt();
            int coord2 = scanner.nextInt();

            if ((coord1 > 3 | coord2 > 3) | (coord1 < 1 | coord2 < 1)){
                System.out.println(ErrorMessages.OUT_OF_RANGE);
                return modifyString(str);
            }

           int strNo = 9 - ((coord2 - 1) * 3) - (3 - coord1);

            if (str.charAt(strNo - 1) == '_') {
                if (getLastMover().equals("O")) {
                    strCorrect = str.substring(0, strNo - 1) + "X" + str.substring(strNo);
                    setLastMover("X");
                } else {
                    strCorrect = str.substring(0, strNo - 1) + "O" + str.substring(strNo);
                    setLastMover("O");
                }
                return strCorrect;
            } else {
                System.out.println(ErrorMessages.OCCUPIED);
                return modifyString(str);
            }
        }
        catch (InputMismatchException e) {
            System.out.println(ErrorMessages.NOT_NUMBERS);
            scanner.nextLine();
            return modifyString(str);
        }
    }

    public static void main(String[] args) {
        //Scanner scanner = new Scanner(System.in);
        //System.out.println("Enter cells: ");
        //String cellsMain = scanner.nextLine();
        setLastMover("O");
        create_cube("_________");

    }
}

/* another valid way of printing the cube:
    private void print() {
        System.out.println("---------");
        for (char[] row : board) {
            System.out.format("| %c %c %c |%n", row[0], row[1], row[2]);
        }
        System.out.println("---------");
    }
 */