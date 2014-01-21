package ru.rogotulka.util;

public enum RankCards {
	ACE(0), KING(1), QUEEN(2), JACK(3), TEN(4), NINE(5), EIGHT(6), SEVEN(7),
	SIX(8), FIVE(9), FOUR(10), THREE(11), TWO(12);
	private int id;
	RankCards(int id){
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
