import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.stream.Collectors.*;


public class SubstCiphDecryption {
    
    //static SubstCiphGeneral object = new SubstCiphGeneral();

    public static void main(String[] args) {
        
        String copyPlainText = "Jackson didn’t run in that 1996 race. The power of incumbency is strong enough to deter most challengers.";
        String plainText = copyPlainText.replaceAll("[^A-Za-z0-9\\s]", "").toLowerCase();

        System.out.println("PLaintext is: " + plainText + "\n");
        
        String ciphText = SubstCiphGeneral.randSubstCiphEncrypt(plainText);

        System.out.println("The cipher text is : " + ciphText +"\n");

        Map<Character , Integer> letFrequency = calcLetterFrequency(ciphText);

        System.out.println("The letter frequency in the cipher text is : " + letFrequency +"\n");

        Map<String , Integer> threeLett = threeWord(ciphText);

        System.out.println("The list of 3 letter words in the \ncipher text (sorted descending) is : " + threeLett +"\n");

        Map<String , Integer> lettPairing = letterPairing(ciphText);

        System.out.println("The list of most common letter pairings in the \ncipher text (sorted descending : " + lettPairing +"\n");

        //Compare the results of frequency analysis and update ciphertext if parameters met
        int params = 0;
        char mostCommonLetter = letFrequency.keySet().iterator().next();
        //System.out.println(mostCommonLetter);

        String mostCommon3Word = threeLett.keySet().iterator().next();
        //System.out.println(mostCommon3Word);
        String first2of3 = "" + mostCommon3Word.charAt(0) + "" + mostCommon3Word.charAt(1);

        String mostCommonPairing = lettPairing.keySet().iterator().next();
        //System.out.println(mostCommonPairing);


        if(mostCommonLetter == mostCommon3Word.charAt(2)) {
            System.out.println("Common letter matched with 3 letter word : 'e\n");
            params++;
        }
        if(mostCommonPairing == first2of3) {
            System.out.println("Common letter pairing matched with 3 letter word : 'th\n");
            params++;
        }

        if(params > 0) {
            StringBuilder cipherText = new StringBuilder();
            for(int i = 0; i < ciphText.length(); i++) {
                char current = ciphText.charAt(i);
    
                if(current == mostCommon3Word.charAt(0)) {
                    cipherText.append('T');
                } else if(current == mostCommon3Word.charAt(1)) {
                    cipherText.append('H');
                } else if(current == mostCommon3Word.charAt(2)) {
                    cipherText.append('E');
                } else {
                    cipherText.append(current);
                }
            }
            System.out.println(params + "/2 parameters met. The updated cipher text (T,H,E) is : " + cipherText.toString());
        } else {
            System.out.println("0/2 parameters met. The word \"the\" could not be located in the ciphertext.");
        }
    }
        
    public static Map<Character , Integer> calcLetterFrequency(String ciphText) {
        
        Map<Character , Integer> frequency = new HashMap<>();
        
        for(int i = 0; i < ciphText.length(); i++) {
            
            //Keep map of a character and how many times it has occured in cipherText (Char , Int)
            if(frequency.containsKey(ciphText.charAt(i))) {
                frequency.put(ciphText.charAt(i) , frequency.get(ciphText.charAt(i)) + 1);
            } else if(ciphText.charAt(i) == ' ') {
                //do nothing for ' ' -> we don't want to count space character
            } else {
                frequency.put(ciphText.charAt(i) , 1);
            }

        }
        
        //Method chanin to sort the most frequent letters from most used to least
        Map<Character , Integer> sorted = frequency.entrySet().stream()
        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        return sorted;
    }

    public static Map<String , Integer> threeWord(String ciphText) {
        
        ArrayList<String> three = new ArrayList<>();
        String[] wordList = ciphText.split("\\W+");

        //Find 3 chars bookended by spaces (three letter word) and add to variable length array list
        for(int i = 0; i < wordList.length; i++) {
            if(wordList[i].length() == 3) {
                three.add(wordList[i]);
            }
        }
        
        Map<String , Integer> threeFrequency = new HashMap<>();
        
        //Loop through list and map the word to it's frequency of occurrence (String , Int)
        for(int i = 0; i < three.size(); i++) {
            
            if(threeFrequency.containsKey(three.get(i))) {
                threeFrequency.put(three.get(i) , threeFrequency.get(three.get(i)) + 1);
            } else {
                threeFrequency.put(three.get(i) , 1);
            }
        }
        
        //Method chain to sort the map of 3 letter words in descending order of frequency
        Map<String , Integer> sorted = threeFrequency.entrySet().stream()
        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        return sorted;

    }

    public static Map<String , Integer> letterPairing(String ciphText) {
        
        Map<String , Integer> frequency = new HashMap<>();

        /*Loop through cipher text character by character, and create a map of each letter pairing (current character and the character in front
        of it) with it's frequency of occurrence (String , Int)*/
        for(int i = 0; i < ciphText.length() - 1; i++) {
            
            String temp = "" + ciphText.charAt(i) + ciphText.charAt(i + 1);
            
            if(ciphText.charAt(i) == ' ' || ciphText.charAt(i + 1) == ' ') {
                //do nothing for ' ' -> we don't want to count space character in letter pairing
            } else if(frequency.containsKey(temp)) {
                frequency.put(temp , frequency.get(temp) + 1);
            } else {
                frequency.put(temp , 1);
            }
        }
        
        //Method chain to sort the map of most frequent letter pairings in descending order
        Map<String , Integer> sorted = frequency.entrySet().stream()
        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        return sorted;
    }
}
