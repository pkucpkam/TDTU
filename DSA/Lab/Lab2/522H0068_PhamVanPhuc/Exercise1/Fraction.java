public class Fraction {
    private int numer;
    private int denom;

    public Fraction () {
        this.numer = 0;
        this.denom = 1;
    }
    public Fraction(int x, int y) {
        this.numer = x;
        this.denom = y;
    }

    public Fraction(Fraction f) {
        this.numer = f.numer;
        this.denom = f.denom;
    }

    public String toString() {
        return this.numer +"/" + this.denom;
    }

    public boolean equals(Object f) {
        if (f instanceof Fraction) {
            Fraction fr = (Fraction)f;
            if (this.numer*fr.denom == this.denom*fr.numer) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }
}
