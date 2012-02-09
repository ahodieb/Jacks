package GUI;

public class CardData {
	
	private int value;
	private int suit;
	
	public CardData(int Value , int Suit) {
		if (Suit  > 4 ) Suit = 4;
		if (Suit  < 1 ) Suit = 1;
		if (Value > 14) Value = 14;
		if (Value < 2 ) Value = 2;
		this.value = Value;this.suit = Suit;
	}
	
	public int getSuit() {
		return suit;
	}
	public int getValue() {
		return value;
	}

}
