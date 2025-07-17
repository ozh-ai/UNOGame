import java.util.*;
import java.io.*;

public class UNO {
    public static String mostColour(ArrayList <String> p) {
        int red = 0;
        int yellow = 0;
        int blue = 0;
        int green = 0;
        for (int i = 0; i < p.size(); i++) {
            if (p.get(i).indexOf("Red") != -1) {
                red += 1;
            }
            if (p.get(i).indexOf("Yellow") != -1) {
                yellow += 1;
            }
            if (p.get(i).indexOf("Blue") != -1) {
                blue += 1;
            }
            if (p.get(i).indexOf("Green") != -1) {
                green += 1;
            }
        }
        int highest = red;
        String highestColour = "Red";
        if (yellow > highest) {
            highest = yellow;
            highestColour = "Yellow";
        }
        if (blue > highest) {
            highest = blue;
            highestColour = "Blue";
        }
        if (green > highest) {
            highest = green;
            highestColour = "Green";
        }
        return highestColour;
    }

    public static String getColour(String currentColour, String card) {
        if (card.compareTo("Wild") != 0 || card.compareTo("Wild + 4") != 0) {    
            if (card.indexOf("Red") != -1) {
                return "Red";
            } else if (card.indexOf("Yellow") != -1) {
                return "Yellow";
            } else if (card.indexOf("Blue") != -1) {
                return "Blue";
            } else if (card.indexOf("Green") != -1) {
                return "Green";
            }
        }
        return currentColour;
    }

    public static String getValue(String currentValue, String card) {
        if (card.compareTo("Wild") != 0 && card.compareTo("Wild + 4") != 0) { 
            int end = 0;
            while (card.charAt(end + 1) != '(') {
                end += 1;
            }
            return card.substring(0, end);
        }
        return currentValue;
    }

    public static String play(ArrayList<String> p, int index, String topCard, ArrayList<String> back) {
        back.add(topCard);
        String newTopCard = p.get(index);
        p.remove(index);
        return newTopCard;
    }

    public static void draw(ArrayList<String> p, ArrayList<String> stack, ArrayList<String> back) {
        p.add(stack.get(0));
        stack.remove(0);

        if (stack.size() == 0) {
            Random rand = new Random();
            while (back.size() > 0) {
                int r = rand.nextInt(back.size());
                stack.add(back.get(r));
                back.remove(r);
            }
        }
    }

    public static ArrayList<String> getPlayer(int currentPlayer, ArrayList<String> p1, ArrayList<String> p2, ArrayList<String> p3, ArrayList<String> p4) {
        ArrayList<String> p = p1;
        if (currentPlayer == 2) {
            p = p2;
        }
        if (currentPlayer == 3) {
            p = p3;
        }
        if (currentPlayer == 4) {
            p = p4;
        }
        return p;
    }

    public static int nextPlayer(int currentPlayer, boolean reverse) {
        if (!reverse) {
            currentPlayer += 1;
            if (currentPlayer > 4) {
                currentPlayer = 1;
            }
        } else {
            currentPlayer -= 1;
            if (currentPlayer < 1) {
                currentPlayer = 4;
            }
        }
        return currentPlayer;
    }

    public static String convertColour(char c) {
        if (c == 'R') {
            return "Red";
        } 
        if (c == 'Y') {
            return "Yellow";
        } 
        if (c == 'G') {
            return "Green";
        }
        return "Blue";
    }

    public static void main(String[] args) throws FileNotFoundException {
        // read file contents into ArrayList deck
        ArrayList <String> deck = new ArrayList<>();
        Scanner sc = new Scanner(new File("UNODeck.txt"));
        while (sc.hasNext()) {
            deck.add(sc.nextLine());
        }
        sc.close();
        // for (int i = 0; i < deck.size(); i++) {
        //     System.out.println(deck.get(i));
        // }

        // distribute cards to 4 players
        ArrayList <String> p1 = new ArrayList<>();
        ArrayList <String> p2 = new ArrayList<>();
        ArrayList <String> p3 = new ArrayList<>();
        ArrayList <String> p4 = new ArrayList<>();
        Random rand = new Random();
        int r1 = 0;

        for (int i =  0; i < 7; i++) {
            r1 = rand.nextInt(deck.size());
            p1.add(deck.get(r1));
            deck.remove(r1);

            r1 = rand.nextInt(deck.size());
            p2.add(deck.get(r1));
            deck.remove(r1);

            r1 = rand.nextInt(deck.size());
            p3.add(deck.get(r1));
            deck.remove(r1);

            r1 = rand.nextInt(deck.size());
            p4.add(deck.get(r1));
            deck.remove(r1);
        }

        // randomise remaining cards into a stack
        ArrayList <String> stack = new ArrayList<>();
        while (deck.size() > 0) {
            r1 = rand.nextInt(deck.size());
            stack.add(deck.get(r1));
            deck.remove(r1);
        }

        // Game start
        System.out.println("YOUR CARDS:");
        for (int i = 1; i <= p4.size(); i++) {
            System.out.println(i + ": " + p4.get(i - 1));
        }
        System.out.println();

        int currentPlayer = 1;
        String topCard = stack.get(0);
        stack.remove(0);
        ArrayList<String> back = new ArrayList<>();
        String currentColour = null;
        String currentValue = "none";
        boolean reverse = false;
        System.out.println("Top Card: " + topCard + "\n");

        // 1st turn
        System.out.println("Player 1's turn:");
        if (topCard.compareTo("Wild") == 0 || topCard.compareTo("Wild + 4") == 0) {
            currentColour = mostColour(p1);
            System.out.println("Playing Colour: " + currentColour);
        } else {
            currentColour = getColour(currentColour, topCard);
            currentValue = getValue(currentValue, topCard); 
        }

        boolean played = false;
        for (int i = 0; i < p1.size(); i++) {
            if (getColour(currentColour, p1.get(i)).compareTo(currentColour) == 0 
            || getValue(currentValue, p1.get(i)).compareTo(currentValue) == 0) {
                played = true;
                System.out.println("Player 1 places down " + p1.get(i) + "\n");
                topCard = play(p1, i, topCard, back);
                if (topCard.compareTo("Wild") == 0 || topCard.compareTo("Wild + 4") == 0) {
                    currentColour = mostColour(p1);
                    currentValue = "none";
                    System.out.println("Player 1 changes the colour to " + currentColour);
                }
                break;
            }
        }
        if (!played) {
            draw(p1, stack, back);
            System.out.println("Player 1 draws 1 card\n");
        }

        // Rest of game
        while (p1.size() > 0 && p2.size() > 0 && p3.size() > 0 && p4.size() > 0) {
            sc = new Scanner(System.in);
            sc.nextLine();

            System.out.println("\nPlayer 1: " + p1.size() + " cards remaining");
            System.out.println("Player 2: " + p2.size() + " cards remaining");
            System.out.println("Player 3: " + p3.size() + " cards remaining");
            System.out.println("You:      " + p4.size() + " cards remaining");

            System.out.println("\nTop Card: " + topCard + "\n");

            if (played) {
                currentColour = getColour(currentColour, topCard);
                currentValue = getValue(currentValue, topCard); 

                // Skip
                if (currentValue.compareTo("Skip") == 0) {
                    currentPlayer = nextPlayer(currentPlayer, reverse);
                    if (currentPlayer == 4) {
                        System.out.println("You skip a turn.\n");
                    } else {
                        System.out.println("Player " + currentPlayer + " skips a turn.\n");
                    }

                // Reverse
                } else if (currentValue.compareTo("Reverse") == 0) {
                    reverse = !reverse;
                    if (!reverse) {
                        System.out.println("Order: Clockwise (1 > 2 > 3 > 4)\n");
                    } else {
                        System.out.println("Order: Anti-clockwise (1 > 4 > 3 > 2)\n");
                    }

                // + 2
                } else if (currentValue.compareTo("+ 2") == 0) {
                    currentPlayer = nextPlayer(currentPlayer, reverse);
                    if (currentPlayer == 4) {
                        System.out.println("You draw 2 cards, a skip a turn.");
                    } else {
                        System.out.println("Player " + currentPlayer + " draws 2 cards, and skips a turn.");
                    }

                    for (int i = 0; i < 2; i++) {
                        if (currentPlayer == 4) {
                            System.out.println("You have drawn " + stack.get(0));
                        }
                        draw(getPlayer(currentPlayer, p1, p2, p3, p4), stack, back);
                    }
                    System.out.println();

                // Wild + 4
                } else if (topCard.compareTo("Wild + 4") == 0) {
                    currentPlayer = nextPlayer(currentPlayer, reverse);
                    if (currentPlayer == 4) {
                        System.out.println("You draw 4 cards, a skip a turn.");
                    } else {
                        System.out.println("Player " + currentPlayer + " draws 4 cards, and skips a turn.");
                    }

                    for (int i = 0; i < 4; i++) {
                        if (currentPlayer == 4) {
                            System.out.println("You have drawn " + stack.get(0));
                        }
                        draw(getPlayer(currentPlayer, p1, p2, p3, p4), stack, back);
                    }
                    System.out.println();
                }
            }
            currentPlayer = nextPlayer(currentPlayer, reverse);
            
            System.out.println("Player " + currentPlayer + "'s turn:");
            ArrayList <String> p = getPlayer(currentPlayer, p1, p2, p3, p4);
 
            played = false;
            if (currentPlayer != 4) {   
                for (int i = 0; i < p.size(); i++) {
                    if (getColour(currentColour, p.get(i)).compareTo(currentColour) == 0 
                    || getValue(currentValue, p.get(i)).compareTo(currentValue) == 0) {
                        played = true;
                        System.out.println("Player " + currentPlayer + " places down " + p.get(i) + "\n");
                        topCard = play(p, i, topCard, back);
                        if (topCard.compareTo("Wild") == 0 || topCard.compareTo("Wild + 4") == 0) {
                            currentColour = mostColour(p);
                            currentValue = "none";
                            System.out.println("Player " + currentPlayer + " has changed the colour to " + currentColour);
                        }
                        if (p.size() == 1) {
                            System.out.println("\nUNO!");
                        }
                        break;
                    }
                }
                if (!played) {
                    draw(p, stack, back);
                    System.out.println("Player " + currentPlayer + " draws 1 card\n");
                }
            } else {
                System.out.println("YOUR CARDS:");
                for (int i = 1; i <= p4.size(); i++) {
                    System.out.println(i + ": " + p4.get(i - 1));
                }
                System.out.println();

                int index = -1;
                boolean valid = false;
                do {
                    System.out.print("Enter a number (Type '0' to draw a card): ");
                    index = sc.nextInt();

                    if (index < 0 || index > p4.size()) {
                        System.out.println("Invalid Input!");
                        valid = false;
                    } else if (index != 0 && getColour(currentColour, p4.get(index - 1)).compareTo(currentColour) != 0 
                    && getValue(currentValue, p.get(index - 1)).compareTo(currentValue) != 0) {
                        System.out.println("Card placed does not match top card!");
                        valid = false;
                    } else {
                        valid = true;
                    }
                } while (!valid);

                if (index == 0) {
                    System.out.println("You have drawn 1 card: " + stack.get(0));
                    draw(p4, stack, back);
                } else {
                    played = true;
                    System.out.println("You have placed down " + p4.get(index - 1));
                    topCard = play(p4, index - 1, topCard, back);
                    if (topCard.compareTo("Wild") == 0 || topCard.compareTo("Wild + 4") == 0) {
                        System.out.print("Enter a colour (R, Y, B, G): ");
                        sc = new Scanner(System.in);
                        char letter = sc.nextLine().charAt(0);
                        while (letter != 'R' && letter != 'Y' && letter != 'B' && letter != 'G') {
                            System.out.println("Invalid colour!");
                            System.out.print("Enter a colour (R, Y, B, G): ");
                            sc = new Scanner(System.in);
                            letter = sc.nextLine().charAt(0);
                        }  
                        currentColour = convertColour(letter);
                        currentValue = "none";
                        System.out.println("You have changed the colour to " + currentColour);
                    }

                    if (p4.size() == 1) {
                        System.out.println("\nUNO!");
                    }
                }
            }
        }

        System.out.println("\nPlayer 1: " + p1.size() + " cards remaining");
        System.out.println("Player 2: " + p2.size() + " cards remaining");
        System.out.println("Player 3: " + p3.size() + " cards remaining");
        System.out.println("You:      " + p4.size() + " cards remaining");

        System.out.println();
        if (p4.size() == 0) {
            System.out.println("Congratulations! You have won!\n");
        } else if (p1.size() == 0) {
            System.out.println("Player 1 has won!\n");
        } else if (p2.size() == 0) {
            System.out.println("Player 2 has won!\n");
        } else if (p3.size() == 0) {
            System.out.println("Player 3 has won!\n");
        }
    }
}
    
