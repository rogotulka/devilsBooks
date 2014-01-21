package ru.rogotulka.util;

public enum RankCards {
	ACE(0), KING(1), QUEEN(2), JACK(3), TEN(4), NINE(5), EIGHT(6), SEVEN(7),
	SIX(6), FIVE(5), FOUR(4), THREE(3), TWO(2);
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
