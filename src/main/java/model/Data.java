package model;

public class Data {
    /**
     * String value to save winner player name.
     */
    private String stringValue;
    /**
     * Int value to save the number of turns does the game took.
     */
    private int intValue;

    /**
     * Default constructor for the class.
     */
    public Data() {

    }

    /**
     * Constructor for the class that initializes
     * the String and the integer value.
     * @param winner the name of the winner.
     * @param turnCounter the number of turn does the game took.
     */
    public Data(final String winner, final int turnCounter) {
        this.stringValue = winner;
        this.intValue = turnCounter;
    }

    /**
     * Returns the stringValue.
     * @return the name of the winner.
     */
    public String getStringValue() {
        return stringValue;
    }

    /**
     * Returns the intValue.
     * @return the number of turns does the game took.
     */
    public int getIntValue() {
        return intValue;
    }
}

