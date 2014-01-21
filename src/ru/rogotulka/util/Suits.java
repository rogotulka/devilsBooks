package ru.rogotulka.util;

public enum Suits {
	SPADES(0), CLUBS(1), HEARTS(2), DIAMONDS(3);
	private int id;
	Suits(int id){
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
