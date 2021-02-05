//Project Group: Troy and Gideon
import java.util.Arrays;
import java.util.Random;

public class LSFR {
    private boolean[] seed;
    private boolean[] coefficients;

    public LSFR(boolean[] arr, boolean[] coe) {
        //Constructor with seed and coefficients
        seed = arr;
        coefficients = coe;
    }

    public LSFR() {
        //Constructor without any arguments using Random
        Random rand = new Random();
        boolean[] args = new boolean[16];
        boolean[] coeffs = new boolean[16];
        for (int i = 0; i < 16; i++) {
            int arg = rand.nextInt(2);
            int coef = rand.nextInt(2);
            args[i] = false;
            coeffs[i] = false;
            if (arg != 0) {
                args[i] = true;
            }
            if (coef != 0) {
                coeffs[i] = true;
            }
        }
        seed = args;
        coefficients = coeffs;
    }

    public boolean[] getCoefficients() {
        return coefficients;
    }

    public boolean[] getSeed() {
        return seed;
    }

    private boolean feedbackLfsr() {
        //computes next bit of LSFR
        int maxlen = Math.max(this.getSeed().length, this.getCoefficients().length);
        int minlen = Math.min(this.getSeed().length, this.getCoefficients().length); //This is to allow for different length seed vs. coefs
        boolean combo = this.getSeed()[0] ^ this.getCoefficients()[0];
        for (int i = 1; i < maxlen; i++) {
            boolean product = (this.getSeed()[i % minlen] ^ this.getCoefficients()[i % minlen]); //If different lengths, then will loop through
            combo = (combo & product);
        }
        return combo;
    }

    public boolean feedbackUpdateLfsr() {
        //Computes next state of bit array
        boolean[] newVals = new boolean[this.getSeed().length + 1];
        for (int i = 0; i < this.getSeed().length; i++) {
            newVals[i + 1] = this.getSeed()[i];
        }
        boolean val = feedbackLfsr();
        newVals[0] = val;
        seed = newVals;
        return val;
    }
}