import java.security.SecureRandom;
import java.util.HashMap;

class SubstCiphGeneral {
    
    public static char[] scrambleAlphabet(String input) {
        
        char[] alph = input.toCharArray();
        //Random rnd = new Random();
        SecureRandom rnd = new SecureRandom();

        //Randomize the letters for the key
        for(int i = 0; i < 26; i++) {
            int k = rnd.nextInt(26);
            char temp = alph[k];
            alph[k] = alph[i];
            alph[i] = temp;
         }
         return alph;
    }

    public static HashMap<Character , Character> createMapping(char[] alph) {
        
        //Map plaintext letter to ciphertext letter (key)
        HashMap<Character , Character> alphMap = new HashMap<>();
        
        String temporary = "abcdefghijklmnopqrstuvwxyz";
        char[] alphKey = temporary.toCharArray();

        for(int i = 0; i < 26; i++) {
            alphMap.put(alphKey[i], alph[i]);
        }
        return alphMap;
    }

    public static String randSubstCiphEncrypt(String plainText) {
        
        StringBuilder cipherText = new StringBuilder();
        String input = "abcdefghijklmnopqrstuvwxyz";
        //String plainText = "I am Dan";
        char[] scramble = scrambleAlphabet(input);
        HashMap<Character , Character> encryptMap = createMapping(scramble);

        //Swap plaintext characters with corresponding char from key
        for(int i = 0; i < plainText.length(); i++) {
            char current = plainText.charAt(i);

            if(encryptMap.containsKey(current)) {
                cipherText.append(encryptMap.get(current));
            } else {
                cipherText.append(current);
            }
        }
        
        System.out.println("Encryption key is : " + encryptMap + "\n");
        
        return cipherText.toString();
    }
}