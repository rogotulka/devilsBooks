package ru.rogotulka.play;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ru.rogotulka.util.Card;
import ru.rogotulka.util.RankCards;
import ru.rogotulka.util.Suits;

public class GameInstance {
	
	private List<Card> cards;
	private Player mainPlayer;
	private static final int CARDS_NUMBER = 52;
	
	public GameInstance(){
		mainPlayer = new Player();
		cards = new ArrayList<Card>(CARDS_NUMBER);
		for (Suits suit : Suits.values()) {
			for(RankCards rankCard : RankCards.values()){
				cards.add(new Card(rankCard.getId(), suit.getId()));
				
			}
		}
		//simple random
		Collections.shuffle(cards);
		//player cards
		mainPlayer.setOnHand(cards.subList(0, 6));
	}
	
	public List<Card> getCards() {
		return cards;
	}
	
	public void setCards(List<Card> cards) {
		this.cards = cards;
	}
	
	public Player getMainPlayer() {
		return mainPlayer;
	}
	
	public void setMainPlayer(Player mainPlayer) {
		this.mainPlayer = mainPlayer;
	}

}
