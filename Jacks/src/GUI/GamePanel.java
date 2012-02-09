package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import Logic.ICallBack;
import Logic.Logic;
import Network.Connection;

public class GamePanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	JPanel cardsPanel,tablePanel;
	ArrayList<CardData> cards;
	GameFrame gameFrame;
	
	
	public void CardClick(ActionEvent e)
	{
		Card cTmp = (Card)e.getSource();
		//tablePanel.setComponentZOrder(cTmp, f++);
		this.cardsPanel.remove(cTmp);
		this.cardsPanel.repaint();
		this.tablePanel.repaint();
		this.revalidate();
		//this.tablePanel.revalidate();
		//this.cardsPanel.revalidate();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		
		if(gameFrame.getCurrentPlayer() == gameFrame.getIndex())
		{

			Card cTmp = (Card)e.getSource();
			//tablePanel.setComponentZOrder(cTmp, f++);
			if(   gameFrame.getTableSuite() == -1 ||
				  cTmp.getCardData().getSuit() == gameFrame.getTableSuite()||
				  !Logic.has(gameFrame.getTableSuite(), this.getCards())					
					)
			{

				
				this.cardsPanel.remove(cTmp);
				this.cardsPanel.repaint();
				this.tablePanel.repaint();
				this.revalidate();
				gameFrame.Send("C_CPD"+ cTmp.toString());
				Logic.sleep(1000);
				gameFrame.setCurrentPlayer(-1);
			}
		}
		
	}
	
	public GamePanel(GameFrame gmfrm ) {
		
		this.gameFrame = gmfrm;
		this.setLayout(new BorderLayout(5, 5));
		
		this.cardsPanel = new JPanel();
		this.cardsPanel.setBackground(Color.DARK_GRAY);
		this.cardsPanel.setLayout(new FlowLayout(FlowLayout.CENTER,3,10));
		
		this.tablePanel = new JPanel();
		this.tablePanel.setBackground(Color.orange);
		this.tablePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		//this.tablePanel.setLayout(new TableLayOut(10, false));
		
		
		this.add(cardsPanel,BorderLayout.SOUTH);
		this.add(tablePanel,BorderLayout.CENTER);
		
		System.out.println("GPanel Created");
	}
	
	public void ReDistributeCards(ArrayList<CardData> cards)
	{
		this.cardsPanel.removeAll();
		this.cards = cards;
		
		for(CardData card : this.cards)
		{
			Card c = new Card(card);
			this.cardsPanel.add(c);
			c.addActionListener(this);
		}
		this.revalidate();
	}
	
	public JPanel getCardsPanel() {
		return cardsPanel;
	}
	
	public void addCard(Card card)
	{
		
		this.tablePanel.add(card);
		tablePanel.repaint();
		tablePanel.revalidate();
		this.repaint();
		this.revalidate();
		System.out.println("Card Adaded");
	}
	

	public ArrayList<CardData> getCards()
	{
		ArrayList<CardData> cards = new ArrayList<CardData>();
		for(int i = 0;i<cardsPanel.getComponentCount();i++)
		{
			if(cardsPanel.getComponent(i) instanceof Card)
			{
				cards.add(((Card)cardsPanel.getComponent(i)).getCardData());
			}
		}
		return cards;
	}

}
