public class Exercise2 {
    private LSFR[] lsfrArr = new LSFR[10];

    public Exercise2() {
        for(int i = 0; i < 10; i++) {
            lsfrArr[i] = new LSFR();
        }
    }
    public Exercise2(boolean[] arr, boolean[] coefs) {
        for(int i = 0; i < 10; i++) {
            lsfrArr[i] = new LSFR(arr, coefs);
        }
    }
    public Exercise2(LSFR[] list) {
        lsfrArr = list;
    }

    public LSFR[] getLsfrArr(){
        return lsfrArr;
    }

    static char bitArray2char(boolean[] arr) {
        int[] inBinary = new int[arr.length];
        for(int i = 0; i < arr.length; i++) {
            inBinary[i] = arr[i] ? 1 : 0;
        }
        int inDec = 0;
        double exponent;
        double place;
        for(int i = 0; i < inBinary.length; i++) {
            exponent = inBinary.length - i - 1;
            place = Math.pow(2, exponent);
            inDec += inBinary[i] * place;
        }
        char bitArray2char = (char)inDec;
        return bitArray2char;
    }

    static boolean[] char2bitArray(char c) {
        int num = c;
        String Bina = Integer.toBinaryString(num);
        for(int i = Bina.length(); i < 16; i++) {
            Bina = "0" + Bina;
        }
        char[] charBina = Bina.toCharArray();
        int len = charBina.length;
        boolean[] sixteenBit = new boolean[len];
        for(int i = 0; i < len; i++) {
            sixteenBit[i] = (charBina[i] == '1');
        }
        return sixteenBit;
    }


        private char encodeCharLfsr(char c) {
        boolean[] charInBitArray = char2bitArray(c);
        boolean encoder = lsfrArr[0].getSeed()[0];
        for(int i = 0; i < this.getLsfrArr().length; i++) {
            LSFR current = lsfrArr[i];
            encoder = encoder && current.feedbackUpdateLfsr();
        }
        boolean[] code = this.getLsfrArr()[0].getSeed();
        boolean[] coef = this.getLsfrArr()[0].getCoefficients();
        int minlen = Math.min(code.length, coef.length); //This is to allow for different length encoder than 16
        for(int i=0; i<charInBitArray.length; i++){
            charInBitArray[i] = charInBitArray[i] ^ code[i % minlen];
        }
        char encodedC = bitArray2char(charInBitArray);
        return encodedC;
    }


    private char[] encodeCharArrayLfsr(char[] chars) {
        for(int i=0; i<chars.length; i++){
            chars[i] = this.encodeCharLfsr(chars[i]);
        }
        return chars;
    }


    public void testLfsr(String text) {
        LSFR[] LSFRCopy = this.getLsfrArr().clone();
        Exercise2 copy = new Exercise2(LSFRCopy);
        char[] textInChars = text.toCharArray();
        char[] textEncoded = encodeCharArrayLfsr(textInChars);
        char[] textDecoded = copy.encodeCharArrayLfsr(textEncoded);
        String decodedStr = "";
        for (int i = 0; i < textDecoded.length; i++) {
            decodedStr = decodedStr + textDecoded[i];
        }
        System.out.println(decodedStr);
    }

    public static void main(String[] args) {
        Exercise2 testing = new Exercise2();
        testing.testLfsr("It works!");
        testing.testLfsr("Hello world");
        testing.testLfsr("Yes!!!!");
    }
}
