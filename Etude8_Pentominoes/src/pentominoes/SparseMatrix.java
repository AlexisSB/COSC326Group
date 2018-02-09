package pentominoes;

/**
 * A sparse matrix that implements Knuth's dancing links.
 */
public class SparseMatrix {
    Cell head;
    int xdim;
    int ydim;

    public Header addHeader(String name) {
        Header h = new Header(name);

        if (head == null) {
            head = h;
        } else {
            Cell end = head;
            
            while (end.right != head) {
                end = end.right;
            }
    
            end.right = h;
            h.left = end;
            h.right = head;
            head.left = h;
        }

        xdim++;
        return h;
    }

    public void addRow(Cell...cells) {
        Cell next = cells[0];
        // Find the col header of the first cell in the list
        Cell startOfCol = next.header;

        // Find the cell at the end of this column.
        Cell endOfCol = startOfCol;

        while (endOfCol.down != startOfCol) {
            // System.out.print(endOfCol + " ");
            endOfCol = endOfCol.down;
        }

        // Link the end of the col with new cell, and the new cell and the header
        endOfCol.down = next;
        next.up = endOfCol;
        next.down = next.header;
        next.header.up = next;

        Cell startOfRow = next;

        // Link row cells.
        for (int i = 1; i < cells.length; i++) {
            next = cells[i];
            startOfCol = next.header;

            // Find end of column.
            endOfCol = startOfCol;

            while (endOfCol.down != startOfCol) {
                // System.out.print(endOfCol + " ");
                endOfCol = endOfCol.down;
            }

            endOfCol.down = next;
            next.up = endOfCol;
            next.down = next.header;
            next.header.up = next;

            // Find end of row
            Cell endOfRow = startOfRow;

            while (endOfRow.right != startOfRow) {
                // System.out.print(endOfRow + " ");
                endOfRow = endOfRow.right;
            }

            endOfRow.right = next;
            next.left = endOfRow;
            next.right = startOfRow;
            startOfRow.left = next;
        }
    }

    public void print() {
        Cell curr = head;

        do {
            System.out.print(curr + " ");
            curr = curr.right;
        } while (curr != head);

        System.out.println();
    }

    public void printRow(Cell startOfRow) {
        Cell curr = startOfRow;

        do {
            System.out.print(curr.header + " " + curr + " ");
            curr = curr.right;
        } while (curr != startOfRow);

        System.out.println();
    }

    public void printCols() {
        Cell curr = head;

        do {
            
            Cell cell = curr;
            do {
                System.out.print(cell + " ");
                cell = cell.down;
            } while (cell != curr);
            
            System.out.print("\n");
            curr = curr.right;
        } while (curr != head);

        System.out.println();
    }
    
    static class Cell {
        Cell up, down, left, right, header;

        public Cell() {
            up = this;
            down = this;
            left = this;
            right = this;
            header = this;
        }

        public Cell(Header header) {
            this();

            this.header = header;
        }
    }
    
    static class Header extends Cell {
        final String name;

        public Header(String name) {
            super();

            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static void main(String[] args) {
        SparseMatrix sm = new SparseMatrix();
        Header headerA = sm.addHeader("A");
        Header headerB = sm.addHeader("B");
        Header headerC = sm.addHeader("C");
        sm.print();

        Cell a1 = new Cell();
        a1.header = headerA;
        Cell c1 = new Cell();
        c1.header = headerC;
        sm.addRow(a1, c1);
        sm.printCols();

        Cell b2 = new Cell();
        b2.header = headerB;
        sm.addRow(b2);
        sm.printCols();

        Cell a3 = new Cell(headerA);
        Cell c3 = new Cell(headerC);
        sm.addRow(a3, c3);
        sm.printCols();

        sm.printRow(a1);
        sm.printRow(b2);
        sm.printRow(a3);
    }
}