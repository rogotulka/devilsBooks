package ru.rogotulka.util;

import org.andengine.entity.sprite.Sprite;

public class Card {
	private int rankCard;
	private int suit;
	
	public Card( int rankCard, int suit) {
		this.rankCard = rankCard;
		this.suit = suit;
	}
	
	public int getRankCard() {
		return rankCard;
	}
	
	public void setRankCard(int rankCard) {
		this.rankCard = rankCard;
	}
	
	public int getSuit() {
		return suit;
	}
	
	public void setSuit(int suit) {
		this.suit = suit;
	}


}
