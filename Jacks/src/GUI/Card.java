package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;

public class Card extends JButton{

	private static final long serialVersionUID = 1L;
	
	private CardData cardData;
	private BufferedImage suitImge;
	private String lbl;
	
	int tableIndx = -1;
	
	private Font CARDS_FONT  = new Font("Arial",1,15);
	private int ROUND_RADIUS = 10;
	int EDGE_SHIFT = 1;
	
	public Card(CardData cardData) {
		this.cardData = cardData;
		
		processImage();		
		processLable();
	}
	
	private void processImage()
	{	if      ( this.cardData.getSuit() <= 1)this.suitImge = SuitImages.getSpade();
		else if ( this.cardData.getSuit() == 2)this.suitImge = SuitImages.getHeart();
		else if ( this.cardData.getSuit() == 3)this.suitImge = SuitImages.getTref();
		else if ( this.cardData.getSuit() >= 4)this.suitImge = SuitImages.getDiamond();
	}
	
	private void processLable()
	{
		if       (cardData.getValue() == 14) this.lbl = "A";
		else if  (cardData.getValue() == 11) this.lbl = "J";
		else if  (cardData.getValue() == 12) this.lbl = "Q";
		else if  (cardData.getValue() == 13) this.lbl = "K";
		else     			   this.lbl = ""+cardData.getValue();
	}
	
	
	public String getLbl() {
		return lbl;
	}
	
	@Override
	public String getText() {
		// TODO Auto-generated method stub
	return this.lbl;
	}
	
	public int getTableIndx() {
		return tableIndx;
	}
	
	public void setTableIndx(int tableIndx) {
		this.tableIndx = tableIndx;
	}
	
	
	public BufferedImage getSuitImge() {
		return suitImge;
	}
	
	public CardData getCardData() {
		return cardData;
	}
	
	
	
	
	@Override
	protected void paintComponent(Graphics gOld) {
		
		Graphics2D g = (Graphics2D) gOld;		
	 	paintCardBody(g);
		
	}
	
	@Override
	protected void paintBorder(Graphics gOld) {
		// TODO Auto-generated method stub
		//Frame
		Graphics2D g = (Graphics2D) gOld;
		
		Point p = (this.getMousePosition());
		if(p != null){
			if(this.contains(p) && this.tableIndx ==-1)
			{
				g.setColor(Color.yellow);		
				g.drawRoundRect(0, 0, this.getWidth() - (EDGE_SHIFT), this.getHeight() - (EDGE_SHIFT ), ROUND_RADIUS, ROUND_RADIUS);
		
			}
			
		}
		
	}
	
	private void paintCardBody(Graphics2D g)
	{	
		
		//Body
		GradientPaint bodyGradient = new GradientPaint(200 - this.getWidth()/2,0, Color.white, 200- this.getWidth()/2, this.getHeight() + 250, Color.yellow);
		g.setPaint(bodyGradient);
		g.fillRoundRect(0, 0, this.getWidth() - (EDGE_SHIFT), this.getHeight() - (EDGE_SHIFT), ROUND_RADIUS, ROUND_RADIUS);
		
		//Frame
		g.setColor(Color.BLACK);		
		g.drawRoundRect(0, 0, this.getWidth() - (EDGE_SHIFT), this.getHeight() - (EDGE_SHIFT ), ROUND_RADIUS, ROUND_RADIUS);
	
		//Image
		double factor = 75/100.0;
		int imgDim;
		if   (this.getWidth() < this.getHeight()) imgDim =(int) (this.getWidth() * factor);
		else imgDim =(int) (this.getHeight() * factor);
		
		int xPos =  1 + (this.getWidth() - imgDim) / 2;
		int yPos =  1 + (this.getHeight() - imgDim) / 2;
		g.drawImage(this.suitImge, xPos, yPos, imgDim, imgDim, this);
		
		//Label		
		int sxPos1  = 5;
		int syPos1 = 15;
		int sxPos2  = this.getWidth() - (15 + ((lbl.length()-1) * 5)) ;
		int syPos2 = this.getHeight() - 5;
		g.setFont(CARDS_FONT);
		g.setColor(Color.black);
		g.drawString(this.lbl, sxPos1, syPos1);
		g.drawString(this.lbl, sxPos2, syPos2);
		
	}


	@Override
	public Dimension getPreferredSize() {
		return new Dimension(50, 70);
	}
	
	@Override
	public String toString() {
		return this.cardData.getValue() +","+ this.cardData.getSuit();
	}
	
	@Override
	protected void processComponentEvent(ComponentEvent e) {
		
		if(e.getID() == ComponentEvent.COMPONENT_RESIZED)
		{
				
		}
	}

}
