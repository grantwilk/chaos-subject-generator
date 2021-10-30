package chaos.util;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.*;

public class subjectGenerator {

    /**
     * A list of adjectives used for subject generation
     */
    private ArrayList<String> adjectives = new ArrayList<>();

    /**
     * A list of nouns used for subject generation
     */
    private ArrayList<String> nouns = new ArrayList<>();

    /**
     * A list of verbs used for subject generation
     */
    private ArrayList<String> verbs = new ArrayList<>();

    /**
     * A random number generator
     */
    private Random rand = new Random();

    /**
     * A list of vowels
     */
    private static final List<Character> VOWELS = Arrays.asList('a', 'e', 'i', 'o', 'u');

    /**
     * Constructor
     */
    public subjectGenerator(){
        loadList(adjectives, getClass().getClassLoader().getResourceAsStream("text/adj.txt"));
        loadList(nouns, getClass().getClassLoader().getResourceAsStream("text/noun.txt"));
        loadList(verbs, getClass().getClassLoader().getResourceAsStream("text/verb.txt"));
    }

    /**
     * Sets the seed of the subject generator's internal random number generator
     * @param seed - the seed to be set
     */
    public void setSeed(long seed){
        rand.setSeed(seed);
    }

    /**
     * Loads words from an input stream to a list
     * @param wordList - the list to load the words to
     * @param in - the input stream to read from
     */
    private void loadList(List<String> wordList, InputStream in) {
        try (Scanner parser = new Scanner(in)) {
            while (parser.hasNextLine()) {
                String line = parser.nextLine();
                if ((line.length() > 0) && (line.charAt(0) != '#')) {
                    wordList.add(line);
                }
            }
        }
    }

    /**
     * Generates a random subject in a randomly selected form
     * @return the subject as a string
     */
    public String generateRandom() {
        String subject;

        if (rand.nextInt(2) == 0) {
            subject = generateAdjNounCombo();
        } else {
            subject = generateNounNounCombo();
        }

        return subject;
    }

    /**
     * Generates a random subject in the form of an adjective-noun combo (e.g. "klutzy barbarian")
     * @return the subject as a string
     */
    public String generateAdjNounCombo() {
        String adj = adjectives.get(rand.nextInt(adjectives.size()));
        String noun = nouns.get(rand.nextInt(nouns.size()));
        return (adj + " " + noun).toUpperCase();
    }

    /**
     * Generates a random subject in the form of a noun-noun combo (e.g. "spork hospital")
     * @return the subject as a string
     */
    public String generateNounNounCombo() {
        String nounOne = nouns.get(rand.nextInt(nouns.size()));

        // continue generating a second noun until we find something that isn't a proper noun or our first noun
        String nounTwo;
        do {
             nounTwo = nouns.get(rand.nextInt(nouns.size()));
        } while (Character.isUpperCase(nounTwo.charAt(0)) || nounTwo.equals(nounOne));

        return (nounOne + " " + nounTwo).toUpperCase();
    }

    /**
     * Generates a random subject in the form of a verb-noun combo (e.g. "stegg fighting a(n) banjo")
     * @return the subject as a string
     */
    public String generateVerbNounCombo() {
        String nounOne = nouns.get(rand.nextInt(nouns.size()));
        String verb = verbs.get(rand.nextInt(verbs.size()));

        // continue generating a second noun until we find something that isn't a proper noun or our first nounn
        String nounTwo;
        do {
            nounTwo = nouns.get(rand.nextInt(nouns.size()));
        } while (Character.isUpperCase(nounTwo.charAt(0)) || nounTwo.equals(nounOne));

        String subject;

        // if the first character is a vowel
        if (VOWELS.contains(Character.toLowerCase(nounTwo.charAt(0)))) {
            subject = (nounOne + " " + verb + " an " + nounTwo).toUpperCase();
        } else {
            subject = (nounOne + " " + verb + " a " + nounTwo).toUpperCase();
        }

        return subject;
    }

}
