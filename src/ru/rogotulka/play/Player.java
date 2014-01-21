package ru.rogotulka.play;

import java.util.List;

import ru.rogotulka.util.Card;

public class Player {
	
	private List<Card> onHand;
	
	public void setOnHand(List<Card> onHand) {
		this.onHand = onHand;
	}
	
	public List<Card> getOnHand() {
		return onHand;
	}

}
