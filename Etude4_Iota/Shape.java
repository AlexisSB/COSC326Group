package iota;

/**
 * Captures the shapes of Iota cards
 *
 * @author Michael Albert
 */
public enum Shape {

    CIRCLE{
        @Override
        public String toString() { return "()"; }
    },
    CROSS{
        @Override
        public String toString() { return "++"; }
    },
    SQUARE{
        @Override
        public String toString() { return "[]"; }
    },
    TRIANGLE{
        @Override
        public String toString() { return "/\\"; }
    }
    
}
