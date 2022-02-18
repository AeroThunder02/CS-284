package Maze;

public class PairInt {
	private int x;
	private int y;
	
	public PairInt(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
    public boolean equals(Object pair) {
        return (pair instanceof PairInt) && this.x == ((PairInt) pair).x && this.y == ((PairInt) pair).y;
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }

    public PairInt copy() {
        return new PairInt(x, y);
    }
}
