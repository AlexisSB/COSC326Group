package iota;

/**
 * Captures the colour of an Iota card
 * @author MichaelAlbert
 */
public enum Colour {
    
    BLUE {
        @Override
        public String toString() { return "B"; }
    },
    GREEN{
        @Override
        public String toString() { return "G"; }
    },
    RED{
        @Override
        public String toString() { return "R"; }
    },
    YELLOW{
        @Override
        public String toString() { return "Y"; }
    }
    
}
