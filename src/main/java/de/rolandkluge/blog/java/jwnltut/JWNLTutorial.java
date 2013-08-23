package de.rolandkluge.blog.java.jwnltut;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.dictionary.Dictionary;

/**
 * Demonstration of the core features of Java WordNet library by John Didion
 *
 * @author Roland Kluge
 *
 */
public class JWNLTutorial
{
    public static void main(final String[] args)
    {
        try {
            /*
             * Read properties file and initialize the dictionary for lookup
             */
            JWNL.initialize(new FileInputStream("src/main/resources/properties.xml"));
            final Dictionary dictionary = Dictionary.getInstance();

            // Specify the lemma for which we want to retrieve all containing synsets
            final String lemma = "lousy";
            final POS pos = POS.ADJECTIVE;

            /*
             * Try to find the query lemma+POS combination as is, otherwise try to
             * stem the lemma (see properties.xml)
             */
            final IndexWord indexWord = dictionary.lookupIndexWord(pos, lemma);
            if (null != indexWord) {

                final Synset[] senses = indexWord.getSenses();

                int i = 1;
                for (final Synset synset : senses) {

                    final String gloss = synset.getGloss();

                    final List<String> words = new ArrayList<String>();
                    for (final Word word : synset.getWords())
                    {
                        words.add(word.getLemma() + "/" + word.getPOS().getKey());
                    }

                    System.out.println(String.format("%2d Lemmas: %s (Gloss: %s)", i++, words, gloss));
                }
            }
            else
            {
                System.out.println("No synsets found for '" + lemma
                         + "/" + pos.getKey() + "'");
            }
        }
        catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (final JWNLException e) {
            e.printStackTrace();
        }
    }
}
