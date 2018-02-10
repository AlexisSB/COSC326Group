package pentominoes;

import java.util.*;

/**
 * A sparse matrix that implements Knuth's dancing links.
 * 
 * @author Anthony Dickson
 */
public class SparseMatrix {
    Cell head;
    List<Cell> colHeaders = new ArrayList<>();
    List<Cell> rowHeaders = new ArrayList<>();

    public SparseMatrix() {
        head = new Header("ROOT_" + this.hashCode());
    }
    
    public Header addHeader(String name) {
        Header h = new Header(name);

        Cell end = getRowEnd(head);
        linkHorizontally(h, end, head);
        colHeaders.add(h);
        return h;
    }

    public Header getHeader(String name) {
        Header curr = (Header) head;

        do {
            if (curr.name.equals(name)) return curr;
            curr = (Header) curr.right;
        } while (curr != head);

        return null;
    }
    
    public Cell addRow(Cell...cells) {
        Cell startOfRow = cells[0];

        // Link row cells.
        for (Cell next : cells) {
            // Find end of column.
            Cell endOfCol = getColEnd(next.header);
            linkVertically(next, endOfCol, next.header);
            ((Header) next.header).count++;
            // Find end of row
            Cell endOfRow = getRowEnd(startOfRow);
            linkHorizontally(next, endOfRow, startOfRow);
        }

        rowHeaders.add(startOfRow);
        return startOfRow;
    }
    
    private Cell getColEnd(Cell header) {
        Cell endOfCol = header;
        
        while (endOfCol.down != header) {
            // System.out.print(endOfCol + " ");
            endOfCol = endOfCol.down;
        }
        
        return endOfCol;
    }
    
    private Cell getRowEnd(Cell startOfRow) {
        Cell endOfRow = startOfRow;
        
        while (endOfRow.right != startOfRow) {
            // System.out.print(endOfCol + " ");
            endOfRow = endOfRow.right;
        }
        
        return endOfRow;
    }
    
    private void linkHorizontally(Cell toLink, Cell left, Cell right) {
        left.right = toLink;
        toLink.left = left;
        toLink.right = right;
        right.left = toLink;
    }
    
    private void linkVertically(Cell toLink, Cell above, Cell below) {
        above.down = toLink;
        toLink.up = above;
        toLink.down = below;
        below.up = toLink;
    }

    public Header hideColumn(String headerName) {
        return hideColumn(getHeader(headerName));
    }

    public Header hideColumn(Header header) {
        if (header == null) return null;

        // Remove header
        header.left.right = header.right;
        header.right.left = header.left;

        if (header.count == 0) return header;

        // Remove each row associated with this col.
        for (Cell row = header.down; row != header; row = row.down) {
            for (Cell col = row.right; col != row; col = col.right) {
                col.up.down = col.down;
                col.down.up = col.up;
                ((Header) col.header).count--;
            }
        }
        
        return header;
    }
    
    public void unhideColumn(Header header) {        
        // Restore each row associated with this col.
        for (Cell row = header.up; row != header; row = row.up) {
            for (Cell col = row.left; col != row; col = col.left) {
                ((Header) col.header).count++;
                col.down.up = col;
                col.up.down = col;
            }
        }

        // Restore the header
        header.right.left = header;
        header.left.right = header;
    }

    public void hideColumns(Cell row) {
        List<Header> headers = new ArrayList<>();
        headers.add((Header) row.header);

        for (Cell cell = row.right; cell != row; cell = cell.right) {
            headers.add((Header) cell.header);
        }

        for (Header header : headers) {
            hideColumn(header);
        }
    }

    public void unhideColumns(Cell row) {
        List<Header> headers = new ArrayList<>();
        headers.add((Header) row.header);

        for (Cell cell = row.right; cell != row; cell = cell.right) {
            headers.add((Header) cell.header);
        }

        Map<Header, Integer> counts = new HashMap<>();

        for (Header header : headers) {
            unhideColumn(header);
            counts.put(header, header.count);
        }

        for (Header header : headers) {
            header.count = counts.get(header);
        }
    }

    public void printHeaders() {
        for (Cell curr = head.right; curr != head; curr = curr.right) {
            System.out.print(curr + " ");
        }

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

    public void printRows() {
        for (Cell row : rowHeaders) {
            printRow(row);
        }
    }

    public void printCol(Cell column) {
        System.out.print(column + " ");

        for (Cell row = column.down; row != column; row = row.down) {
            System.out.print(row + " ");
        }
        
        System.out.println();
    }

    public void printCols() {
        for (Cell col = head.right; col != head; col = col.right) {
            printCol(col);
        }

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

        @Override
        public String toString() {
            return "CELL_" + this.hashCode(); 
        }
    }
    
    static class Header extends Cell {
        final String name;
        int count;

        public Header(String name) {
            super();

            this.name = name;
            this.count = 0;
        }

        @Override
        public String toString() {
            return String.format("%s_%d (%d)", name, hashCode(), count);
        }
    }

    public static void main(String[] args) {
        SparseMatrix sm = new SparseMatrix();
        Header headerA = sm.addHeader("A");
        Header headerB = sm.addHeader("B");
        Header headerC = sm.addHeader("C");
        sm.printHeaders();

        Cell a1 = new Cell(headerA);
        Cell c1 = new Cell(headerC);
        sm.addRow(a1, c1);
        sm.printCols();

        Cell b2 = new Cell(headerB);
        sm.addRow(b2);
        sm.printCols();

        Cell a3 = new Cell(headerA);
        Cell c3 = new Cell(headerC);
        sm.addRow(a3, c3);
        sm.printCols();

        Cell b4 = new Cell(headerB);
        Cell c4 = new Cell(headerC);
        sm.addRow(b4, c4);
        sm.printCols();

        System.out.println("Rows:");
        sm.printRow(a1);
        sm.printRow(b2);
        sm.printRow(a3);
        sm.printRow(b4);
        System.out.println();

        System.out.println("Hiding col A...");
        sm.hideColumn(headerA);
        sm.printCols();
        
        System.out.println("Unhiding col A...");
        sm.unhideColumn(headerA);
        sm.printCols();
        
        System.out.println("Hiding cols based on row a1...");
        sm.hideColumns(a1);
        sm.printCols();
        
        System.out.println("Unhiding cols based on row a1...");
        sm.unhideColumns(a1);
        sm.printCols();
    }
}