package machine;

import java.lang.Exception;
import java.lang.String;
import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Encapsulates the state of a vending machine and the operations that can be performed on it
 */
public class VendingMachine {


	private boolean on;
	private HashMap<String,Integer> itemCounter; // # of items remaining
	private static double[] itemPrice = new double[]{0.6, 1.0, 1.7}; // price of items A, B, C
	private double currentlyInsertedMoney;
	private HashMap<String,Integer> coinsAvailable; // coins of 10,20,50 pence and 1 pound

	//---------------------------------------------------------------------- Contructor
	public VendingMachine() {
		this.setOff();
		this.setCurrentlyInsertedMoney(0.0);
		if(this.getItemCounter() == null) {
			this.setItemCounter(new HashMap<String, Integer>());
		}
		if(this.getCoinsAvailable() == null) {
			this.setCoinsAvailable(new HashMap<String, Integer>());
		}
	}

	//--------------------------------------------------------------------- ON/OFF
	public boolean isOn() {
		return this.on;
	}
	
	public void setOn() {
		if(!this.isOn()) {
			this.on = true;
		}
		System.out.println("Vending Machine ON");
	}
	
	public void setOff() {
		if(this.isOn()) {
			this.on = false;
		}
		System.out.println("Vending Machine OFF");
	}

	//------------------------------------------------------------------------------------------- COINS
	public void setCurrentlyInsertedMoney(double amount) {
		this.currentlyInsertedMoney = amount;
	}

	public double getCurrentlyInsertedMoney() {return this.currentlyInsertedMoney;}

	public void addCurrentlyInsertedMoney(double amount) {
		this.currentlyInsertedMoney = this.round(this.currentlyInsertedMoney + amount);
		System.out.println("Currently inserted: " + ((char) 339) + getCurrentlyInsertedMoney());
	}

	public HashMap<String, Integer> getCoinsAvailable() {
		return coinsAvailable;
	}

	public Integer getCoinsAvailable(String item) {
		return (Integer) this.coinsAvailable.get(item);
	}

	public void setCoinsAvailable(HashMap<String, Integer> coinsAvailable) {
		this.coinsAvailable = coinsAvailable;
		this.setCoinsAvailable("10pence", 0);
		this.setCoinsAvailable("20pence", 0);
		this.setCoinsAvailable("50pence", 0);
		this.setCoinsAvailable("1pound", 0);
	}

	public void setCoinsAvailable(String item, Integer amount) {
		this.coinsAvailable.put(item, amount);
	}

	public void addCoinsAvailable(String item, Integer amount) {
		this.setCoinsAvailable(item, this.getCoinsAvailable(item) + amount);
	}

	public void refillCoins (Integer nCoins10pence, Integer nCoins20pence, Integer nCoins50pence, Integer nCoins1pound) {
		this.addCoinsAvailable("10pence", nCoins10pence);
		this.addCoinsAvailable("20pence", nCoins20pence);
		this.addCoinsAvailable("50pence", nCoins50pence);
		this.addCoinsAvailable("1pound", nCoins1pound);
	}

	public void insertCoin(String coin) throws UnknownCoinException, Exception {
		// could be added class coin but this way for simplicity
		// could be parameter double but assuming machine will identify the coin
		// shows the inserted amount, as in the display
		switch(coin) {
			case "10pence":
				this.addCurrentlyInsertedMoney(0.1);
				this.addCoinsAvailable("10pence", 1);
				break;

			case "20pence":
				this.addCurrentlyInsertedMoney(0.2);
				this.addCoinsAvailable("20pence", 1);
				break;

			case "50pence":
				this.addCurrentlyInsertedMoney(0.5);
				this.addCoinsAvailable("50pence", 1);
				break;

			case "1pound":
				this.addCurrentlyInsertedMoney(1.0);
				this.addCoinsAvailable("1pound", 1);
				break;

			default:
				throw new UnknownCoinException(coin);
		}

		// if it is off, return the money
		if (!this.isOn()) {
			this.coinReturn();
		}
	}

	public void coinReturn() throws Exception{
		while((this.getCurrentlyInsertedMoney() - 1.0) > 0.0 && this.getCoinsAvailable("1pound") > 0) {
			this.addCurrentlyInsertedMoney(-1.0);
			System.out.println("Coin returned: 1pound");
		}
		while((this.getCurrentlyInsertedMoney() - 0.5) > 0.0 && this.getCoinsAvailable("50pence") > 0) {
			this.addCurrentlyInsertedMoney(-0.5);
			System.out.println("Coin returned: 50pence");
		}
		while((this.getCurrentlyInsertedMoney() - 0.2) > 0.0 && this.getCoinsAvailable("20pence") > 0) {
			this.addCurrentlyInsertedMoney(-0.2);
			System.out.println("Coin returned: 20pence");
		}
		while((this.getCurrentlyInsertedMoney() - 0.1) >= 0.0 && this.getCoinsAvailable("10pence") > 0) {
			this.addCurrentlyInsertedMoney(-0.1);
			System.out.println("Coin returned: 10pence");
		}

		if(this.getCurrentlyInsertedMoney() > 0) {
			throw new Exception("NO more coins available, call the distribuitor for your change!");
		}
	}

	//------------------------------------------------------------------------------------ ITEMS
	public void setItemCounter(HashMap<String, Integer> itemCounter) {
		this.itemCounter = itemCounter;
		this.setItemCounter("A", 0);
		this.setItemCounter("B", 0);
		this.setItemCounter("C", 0);
	}

	public void setItemCounter(String item, Integer amount) {
		this.itemCounter.put(item, amount);
	}

	public void addItemCounter(String item, Integer amount) {
		this.setItemCounter(item, this.getItemCounter(item) + amount);
	}

	public HashMap<String, Integer> getItemCounter() {
		return this.itemCounter;
	}

	public Integer getItemCounter(String item) {
		return (Integer) this.itemCounter.get(item);
	}

	public void refillItems (Integer nItemsA, Integer nItemsB, Integer nItemsC) {
		this.addItemCounter("A", nItemsA);
		this.addItemCounter("B", nItemsB);
		this.addItemCounter("C", nItemsB);
	}

	public double getItemPrice(String item) throws Exception {
		double price;
		switch (item) {
			case "A":
				price = this.itemPrice[0];
				break;
			case "B":
				price = this.itemPrice[1];
				break;
			case "C":
				price = this.itemPrice[2];
				break;
			default:
				throw new Exception("Item '" + item + "' non existent!");
		}

		return price;
	}

	public void getItem(String item) throws Exception {
		if (this.getItemCounter(item) > 0) {
			if (this.getCurrentlyInsertedMoney() >= this.getItemPrice(item)) {
				this.addItemCounter(item, -1);
				this.addCurrentlyInsertedMoney(-this.getItemPrice(item));
				this.coinReturn(); // change return
				System.out.println("Item '" + item + "' served");
			} else {
				double remaining = VendingMachine.round(this.getItemPrice(item) - this.getCurrentlyInsertedMoney());
				throw new Exception("You need " + ((char) 339) + remaining + " more for buying '" + item + "' item");
			}
		} else {
			coinReturn();
			throw new Exception("NO more '" + item + "' items");
		}
	}

	//---------------------------------------------------------------------------------- AUX
	private static double round(double unrounded)
	{
		BigDecimal bd = new BigDecimal(unrounded);
		BigDecimal rounded = bd.setScale(1,  BigDecimal.ROUND_HALF_UP);
		return rounded.doubleValue();
	}
}
