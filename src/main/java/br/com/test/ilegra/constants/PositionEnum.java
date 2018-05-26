package br.com.test.ilegra.constants;

public enum PositionEnum {
	ZERO(0),
	ONE(1),
	TWO(2),
	TREE(3),
	FOUR(4),
	FIVE(5),
	SIX(6),
	SEVEN(7);
	
	private int position;

	PositionEnum(int position) {
		this.position = position;
	}

	public int getPosition() {
		return position;
	}

}