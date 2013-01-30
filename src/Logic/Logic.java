package Logic;



import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import GUI.CardData;

public class Logic {
		
	private static ArrayList<CardData> generateCardsSet()
	{
		ArrayList<CardData>  cards = new ArrayList<CardData>();
		
		for(int i = 0 ; i < 4 ; i ++)
		{
			for (int j = 0; j < 13; j++) {
				cards.add( new CardData(j+2,i+1));
				
			}
		}
		return cards;
	}
		
	
		
	public  static ArrayList<ArrayList<CardData>> generatePlayersCardSets()
	{
		ArrayList<ArrayList<CardData>> playersSets = new ArrayList<ArrayList<CardData>>();
		ArrayList<CardData> allCards  = generateCardsSet();
		
		
		boolean [] selected = new boolean[52];
		
		for(int i = 0 ;i<52; i ++) selected[i]= false;
		
		Random rnd = new Random(new Date().getTime());
		int choice = rnd.nextInt(52);
		
		for(int i = 0; i < 4 ; i ++ )
		{
			ArrayList<CardData> tmp = new ArrayList<CardData>();
			
			for(int j = 0 ; j<13 ; j++)
			{	
				while(selected[choice])
				{
					choice = rnd.nextInt(52);
				}
				
				tmp.add(allCards.get(choice));
				selected[choice]= true;
			}
			
			playersSets.add(SortToSuits(QuickSort(tmp)));
		}
		
		
		return playersSets;
		
		
	}
	
	private static ArrayList<CardData> QuickSort(ArrayList<CardData> lst)

	{
		if(lst.size() <=1) return lst;
		
		ArrayList<CardData> less = new ArrayList<CardData>();
		ArrayList<CardData> more = new ArrayList<CardData>();
		CardData me = lst.get(0);
		
		for(int i = 1 ; i < lst.size() ; i++)
		{
			if   (me.getValue() > lst.get(i).getValue()) less.add(lst.get(i));
			else more.add(lst.get(i));
		}
		
		more = QuickSort(more);
		less = QuickSort(less);
		
		more.add(me);
		more.addAll(less);
		
		return (more);
	}
	
	public static ArrayList<CardData> SortToSuits(ArrayList<CardData> unSorted)
	{
		// Dirty Sorting Better algorithm needed.
			
		ArrayList<CardData> Sorted = new ArrayList<CardData>();
		int indx = 0;
		
		for(int i = 0 ; i < 4; i ++)
		{
			for(int j = 0 ; j < unSorted.size();j++)
			{
				if((i + 1) == unSorted.get(j).getSuit())
				{
					Sorted.add( unSorted.get(j));
				}
			}		
			
			indx++;
		}
		
		
		return Sorted;
		
	}
	
	public static ArrayList<CardData> DeSerializeCards(String StrCards)
	{
		ArrayList<CardData> Cards = new ArrayList<CardData>();
		
		String[] clst = StrCards.split("-");
		
		for(int i = 0 ; i<clst.length;i++)
		{
			int value = Integer.parseInt(clst[i].split(",")[0]);
			int suit = Integer.parseInt(clst[i].split(",")[1]);
			Cards.add(new CardData(value, suit));
		}
		
		return Cards;
	}
	

	public static String SerializeCards(ArrayList<CardData> Cards)
	{
		String strCards = "";
		for(CardData card : Cards)
		{
			strCards += "" + card.getValue() + "," + card.getSuit()+ "-";
		}
		return strCards;
	}
	
	public static void sleep(long time)
	{
		try
		{
			Thread.sleep(time);
		}
		catch(Exception ex)
		{
			
		}
	}
	
	public static boolean has(int suit , ArrayList<CardData> cards )
	{
		boolean result = false;
		for(CardData card :cards)
		{
			result = result || (card.getSuit() ==  suit);
		}
		return result;
	}	
	
	public static boolean Dokk(ArrayList<CardData> cards , ArrayList<Integer> Modes)
	{
		
		if(Modes.contains(new Integer(2)))//King of Hearts
		{
			int heartCount = 0;
			boolean hasKing = false;
			for(CardData card :cards) if(card.getSuit() == 2)
				{
					heartCount ++;
					hasKing = (card.getValue()==13);
					
				}
			if(heartCount<= 2 && hasKing) return true;
			
		}
		
		if(Modes.contains(new Integer(5)))//Suns
		{
			
			int points = 0;
			for(CardData card :cards)
				{
					if(card.getValue()> 10)
						points += card.getValue()-10;
				}
			if(points >=16) return true;
		}
		
		return false;
	}
	
	public static int HandWinner(int tableSuit , CardData[]Cards)
	{
		int max = 0;
		int indx =0;
		for(int i = 0 ; i<Cards.length;i++)
		{
			if(Cards[i].getSuit() == tableSuit)
			{
				if(Cards[i].getValue() > max)
				{
					max = Cards[i].getValue();
					indx = i;
				}
			}
		}
		return indx;
	}
}
