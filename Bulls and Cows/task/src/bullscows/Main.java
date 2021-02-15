package bullscows;


import java.util.Scanner;
import java.util.regex.Pattern;


public class Main {

    private static int getSecretCodeLength(Scanner sc) {
        System.out.println("Please, enter the secret code's length:");
        int l = 0;
        String str = sc.nextLine();
        if (Pattern.matches("[0-9]+", str)) {
            l = Integer.parseInt(str);
            if (l > 36 || l <= 0) {
                System.out.println("Error: can't generate a secret number with a length of " + l + " because there aren't enough unique digits.");
            }
        } else {
            System.out.println("Error: \"" + str + "\" isn't a valid number.");
        }
        return l;
    }

    private static int getNumberOfPossibleSymbols(Scanner sc, int secretCodeLength) {
        System.out.println("Input the number of possible symbols in the code:");
        int numberSymbols = 0;
        String str = sc.nextLine();
        if (Pattern.matches("[0-9]+", str)) {
            numberSymbols = Integer.parseInt(str);
            if (numberSymbols > 36) {
                System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
                return numberSymbols = 0;
            }
            if (numberSymbols < secretCodeLength) {
                System.out.println("Error: it's not possible to generate a code with a length of " + secretCodeLength + " with " + numberSymbols + " unique symbols.");
                return numberSymbols = 0;
            }
        } else {
            System.out.println("Error: \"" + str + "\" isn't a valid number.");
        }
        return numberSymbols;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String secretNumber;
        int secretCodeLength = getSecretCodeLength(sc);
        if (secretCodeLength <= 36 && secretCodeLength > 0) {
            int numberSymbols = getNumberOfPossibleSymbols(sc, secretCodeLength);
            if (numberSymbols > 0) {
                StringBuilder ps = getPossibleSymbols(numberSymbols);
                secretNumber = getUniqueCode(ps, secretCodeLength);
                //System.out.println(secretNumber);
                String range = getRangeSymbols(ps, numberSymbols);
                String stars = secretNumber.replaceAll("\\p{ASCII}", "*");
                System.out.println("The secret is prepared: " + stars + " (" + range + ").");
                System.out.println("Okay, let's start a game!");
                grader(secretNumber, secretCodeLength, sc, range);
            }
        }

    }

/*    public static void main(String args) {
        System.out.println("Please, enter the secret code's length:");
        Scanner sc = new Scanner(System.in);
        int l = sc.nextInt();
        String secretNumber;
        if (l > 10) {
            System.out.println("Error: can't generate a secret number with a length of " + l + " because there aren't enough unique digits.");
        } else {
            String pseudoRandomNumber = "" + System.nanoTime();
            System.out.println("Okay, let's start a game!");
            secretNumber = getUniqueNumber(pseudoRandomNumber, l);
            //grader(secretNumber, l,sc,range);
        }
    }*/

    private static String getRangeSymbols(StringBuilder possibleSymbols, int numberSymbols) {
        StringBuilder sb = new StringBuilder("0");
        if (numberSymbols <= 10) {
            sb.append("-").append(possibleSymbols.charAt(numberSymbols - 1));
        } else {
            if (numberSymbols == 11) {
                sb.append("-9, a");
            } else {
                sb.append("-9, a-").append(possibleSymbols.charAt(numberSymbols - 1));
            }
        }
        return sb.toString();
    }

    private static StringBuilder getPossibleSymbols(int numberSymbols) {
        StringBuilder possibleSymbols = new StringBuilder("0123456789abcdefghijklmnopqrstuvwxyz");
        int codeSymLength = possibleSymbols.length();
        possibleSymbols.delete(numberSymbols, codeSymLength);
        return possibleSymbols;
    }

    private static String getUniqueCode(StringBuilder possibleSymbols, int length) {
        int codeSymLength = possibleSymbols.length();
        char[] d = possibleSymbols.toString().toCharArray();
        StringBuilder ps = new StringBuilder();
        while (ps.length() != codeSymLength) {
            int rnd = (int) (Math.random() * (codeSymLength - 0)) + 0;
            if (ps.indexOf("" + d[rnd]) == -1) {
                ps.append(d[rnd]);
            }
        }
        return ps.substring(0, length);
    }


    /*private static String getUniqueNumber(String pseudoRandomNumber, int length) {
        pseudoRandomNumber = pseudoRandomNumber.substring(pseudoRandomNumber.length() - length);
        StringBuilder ps = new StringBuilder(pseudoRandomNumber);
        ps.reverse();
        StringBuilder uni = new StringBuilder();
        char[] d = {'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
        for (char c : d) {
            if (ps.indexOf("" + c) == -1) {
                uni.append(c);
            } else
                while (ps.indexOf("" + c) != ps.lastIndexOf("" + c)) {
                    ps.delete(ps.lastIndexOf("" + c), ps.lastIndexOf("" + c) + 1);
                }
        }
        if (ps.charAt(0) == '0') {
            ps.reverse();
        }
        return ps.append(uni).subSequence(0, length).toString();
*//*    ///////another example
        Scanner sc = new Scanner(System.in);
        long numb = sc.nextLong();
        ArrayList<Long> uniqueDigit = new ArrayList<>();
        while (uniqueDigit.size() < numb && numb <= 10) {
            Long value = (long) (Math.random() * 10);
            if (!uniqueDigit.contains(value)) {
                uniqueDigit.add(value);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Long aLong : uniqueDigit) {
            sb.append(aLong);
        }*//*


    }*/


    public static void grader(String secretCodeS, int secretCodeLength, Scanner sc, String range) {
        int cow;
        int bull;
        StringBuilder cowsB = new StringBuilder();
        StringBuilder bullsB = new StringBuilder();
        StringBuilder outAB = new StringBuilder();
        String inputS;
        char[] secretCodeArray = secretCodeS.toCharArray();
        int turn = 1;
        do {
            cowsB.delete(0, cowsB.length());
            bullsB.delete(0, bullsB.length());
            outAB.delete(0, outAB.length());
            bull = 0;
            cow = 0;
            System.out.println("Turn " + turn + ":");
            inputS = sc.next();
            if (Pattern.matches("[" + range + "]+", inputS) && inputS.length() == secretCodeLength) {
                char[] inputArray = inputS.toCharArray();
                for (int i = 0; i < secretCodeLength; i++) {
                    for (int j = 0; j < secretCodeLength; j++) {
                        if (secretCodeArray[i] == inputArray[j]) {
                            if (i == j) {
                                bull++;
                            } else {
                                cow++;
                            }
                        }
                    }
                }
                bullsB.append(bull == 0 ? "" : bull == 1 ? bull + " bull" : bull + " bulls");
                cowsB.append(cow == 0 ? "" : cow == 1 ? cow + " cow" : cow + " cows");
                outAB.append(((bullsB.length() + cowsB.length()) == 0 ? "None" : bullsB.length() != 0 ? cowsB.length() == 0 ? "" + bullsB : bullsB + " and " + cowsB : "" + cowsB));
            } else {
                System.out.println("Error: contains invalid symbols or wrong range ");
                outAB.append("None");
            }
            System.out.println("Grade: " + outAB);
            turn++;
        } while (!secretCodeS.equals(inputS));
        System.out.println("Congratulations! You guessed the secret code.");
    }
}