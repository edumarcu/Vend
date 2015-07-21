package machine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.Before;
import org.junit.Ignore;

import java.lang.Exception;

/**
 * Unit tests for {@link VendingMachine}
 */
public class VendingMachineTest {

	VendingMachine machine;

	// Creating the machine for every Test
	@Before
	public void beforeTest() {
		System.out.println();
		System.out.println("New TEST");
		machine = new VendingMachine();
	}

	//----------------------------------------------------------------------------------------- ON / OFF
	@Test
	public void defaultStateIsOff() {
		assertFalse(machine.isOn());
	}
	
	@Test
	public void turnsOn() {
		machine.setOn();
		assertTrue(machine.isOn());
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.0, 0.001);
	}

	@Test
	public void turnsOff() {
		machine.setOn();
		machine.setOff();
		assertFalse(machine.isOn());
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.0, 0.001);
	}

	//--------------------------------------------------------------------------------------- COINS
	@Test
	public void insertCoin10pence() {
		machine.setOn();
		try {
			machine.insertCoin("10pence");
		} catch (Exception e){}
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.1, 0.001);
	}

	@Test
	public void insertCoin20pence() {
		machine.setOn();
		try {
			machine.insertCoin("20pence");
		} catch (Exception e){}
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.2, 0.001);
	}

	@Test
	public void insertCoin50pence() {
		machine.setOn();
		try {
			machine.insertCoin("50pence");
		} catch (Exception e){}
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.5, 0.001);
	}

	@Test
	public void insertCoin1pound() {
		machine.setOn();
		try {
			machine.insertCoin("1pound");
		} catch (Exception e){}
		assertEquals(machine.getCurrentlyInsertedMoney(), 1.0, 0.001);
	}

	@Test(expected=UnknownCoinException.class)
	public void insertCoin1euro() throws UnknownCoinException, Exception {
		machine.setOn();
		machine.insertCoin("1euro");
	}

	@Test
	public void insertDifferentCoins() {
		machine.setOn();
		try {
			machine.insertCoin("10pence");
			machine.insertCoin("50pence");
			machine.insertCoin("1pound");
			machine.insertCoin("20pence");
		} catch (Exception e){}
		assertEquals(machine.getCurrentlyInsertedMoney(), 1.8, 0.001);
	}

	@Test
	public void insertCoinsMachineOff() {
		try {
			machine.insertCoin("10pence");
		} catch (Exception e){}
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.0, 0.001);
	}

	@Test
	public void coinReturnButton() throws Exception{
		machine.setOn();
		machine.coinReturn();
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.0, 0.001);
	}

	@Test
	public void coinReturnButtonWithCoins() {
		machine.setOn();
		try {
			machine.insertCoin("10pence");
			machine.insertCoin("50pence");
			machine.insertCoin("50pence");
			machine.insertCoin("1pound");
			machine.insertCoin("20pence");
			machine.insertCoin("10pence");
			machine.insertCoin("10pence");
			machine.insertCoin("20pence");
			machine.coinReturn();
		} catch (Exception e){}
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.0, 0.001);
	}

	@Test
	public void hasCoinsAvailable() {
		assertFalse(machine.getCoinsAvailable() == null);
		assertTrue(machine.getCoinsAvailable("10pence") == 0);
		assertTrue(machine.getCoinsAvailable("20pence") == 0);
		assertTrue(machine.getCoinsAvailable("50pence") == 0);
		assertTrue(machine.getCoinsAvailable("1pound") == 0);
	}

	@Test
	public void addCoins10pence(){
		machine.setOn();
		assertTrue(machine.getCoinsAvailable("10pence") == 0);
		machine.addCoinsAvailable("10pence", 3);
		assertTrue(machine.getCoinsAvailable("10pence") == 3);
	}

	@Test
	public void addCoins20pence(){
		machine.setOn();
		assertTrue(machine.getCoinsAvailable("20pence") == 0);
		machine.addCoinsAvailable("20pence", 5);
		assertTrue(machine.getCoinsAvailable("20pence") == 5);
	}

	@Test
	public void addCoins50pence(){
		machine.setOn();
		assertTrue(machine.getCoinsAvailable("50pence") == 0);
		machine.addCoinsAvailable("50pence", 2);
		assertTrue(machine.getCoinsAvailable("50pence") == 2);
	}

	@Test
	public void addCoins1pound(){
		machine.setOn();
		assertTrue(machine.getCoinsAvailable("1pound") == 0);
		machine.addCoinsAvailable("1pound", 22);
		assertTrue(machine.getCoinsAvailable("1pound") == 22);
	}

	@Test
	public void addCoinsToChange() {
		machine.setOn();
		machine.refillCoins(30, 50, 100, 25);
		assertTrue(machine.getCoinsAvailable("10pence") == 30);
		assertTrue(machine.getCoinsAvailable("20pence") == 50);
		assertTrue(machine.getCoinsAvailable("50pence") == 100);
		assertTrue(machine.getCoinsAvailable("1pound") == 25);
		try {
			machine.insertCoin("10pence");
			machine.insertCoin("50pence");
			machine.insertCoin("50pence");
			machine.insertCoin("1pound");
			machine.insertCoin("20pence");
			machine.insertCoin("10pence");
			machine.insertCoin("10pence");
			machine.insertCoin("20pence");
		} catch (Exception e){}
		assertTrue(machine.getCoinsAvailable("10pence") == 33);
		assertTrue(machine.getCoinsAvailable("20pence") == 52);
		assertTrue(machine.getCoinsAvailable("50pence") == 102);
		assertTrue(machine.getCoinsAvailable("1pound") == 26);
	}
	//------------------------------------------------------------------------------------ ITEMS
	@Test
	public void hasItemCounter() {
		assertFalse(machine.getItemCounter() == null);
	}

	@Test
	public void itemCounterIsZeroIfNoRefill(){
		assertTrue(machine.getItemCounter("A") == 0);
		assertTrue(machine.getItemCounter("B") == 0);
		assertTrue(machine.getItemCounter("C") == 0);
	}

	@Test
	public void itemRefill(){
		// not necessary to be on the machien to be refilled
		machine.refillItems(1,1,1);
		assertTrue(machine.getItemCounter("A") == 1);
		assertTrue(machine.getItemCounter("B") == 1);
		assertTrue(machine.getItemCounter("C") == 1);
	}

	@Test
	public void getItemsPrice()throws Exception {
		assertEquals(machine.getItemPrice("A"), 0.6, 0.001);
		assertEquals(machine.getItemPrice("B"), 1.0, 0.001);
		assertEquals(machine.getItemPrice("C"), 1.7, 0.001);
	}

	@Test(expected=Exception.class)
	public void getPriceItemNotExistent()throws Exception {
		machine.getItemPrice("D");
	}

	@Test
	public void buyWithNoStockItemA () {
		machine.setOn();
		try {
			machine.insertCoin("10pence");
			machine.insertCoin("50pence");
		} catch (Exception e){}
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.6, 0.001);
		Integer itemCounterA = machine.getItemCounter("A");
		assertEquals(itemCounterA, new Integer(0));
		try {
			machine.getItem("A");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "NO more 'A' items");
		}
		assertEquals(machine.getItemCounter("A"), new Integer(0));
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.0, 0.001);
	}

	@Test
	public void buyWithNoStockItemB () {
		machine.setOn();
		machine.refillCoins(30, 50, 100, 25);
		try {
			machine.insertCoin("50pence");
			machine.insertCoin("50pence");
		} catch (Exception e){}
		assertEquals(machine.getCurrentlyInsertedMoney(), 1.0, 0.001);
		Integer itemCounterB = machine.getItemCounter("B");
		assertEquals(itemCounterB, new Integer(0));
		try {
			machine.getItem("B");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "NO more 'B' items");
		}
		assertEquals(machine.getItemCounter("B"), new Integer(0));
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.0, 0.001);
	}

	@Test
	public void buyWithNoStockItemC () {
		machine.setOn();
		machine.refillCoins(30, 50, 100, 25);
		try {
			machine.insertCoin("20pence");
			machine.insertCoin("50pence");
			machine.insertCoin("1pound");
		} catch (Exception e){}
		assertEquals(machine.getCurrentlyInsertedMoney(), 1.7, 0.001);
		Integer itemCounterC = machine.getItemCounter("C");
		assertEquals(itemCounterC, new Integer(0));
		try {
			machine.getItem("C");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "NO more 'C' items");
		}
		assertEquals(machine.getItemCounter("C"), new Integer(0));
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.0, 0.001);
	}



	@Test
	public void buyWithExactMoneyItemA() throws Exception{
		machine.setOn();
		machine.refillItems(1,1,1);
		assertTrue(machine.getItemCounter("A") > 0);
		try {
			machine.insertCoin("10pence");
			machine.insertCoin("50pence");
		} catch (UnknownCoinException e){}
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.6, 0.001);
		Integer itemCounterA = machine.getItemCounter("A");
		machine.getItem("A");
		assertEquals(machine.getItemCounter("A"), (Integer)(itemCounterA - 1));
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.0, 0.001);
	}

	@Test
	public void buyWithExactMoneyItemB() throws Exception{
		machine.setOn();
		machine.refillItems(1,1,1);
		assertTrue(machine.getItemCounter("B") > 0);
		try {
			machine.insertCoin("10pence");
			machine.insertCoin("50pence");
			machine.insertCoin("10pence");
			machine.insertCoin("10pence");
			machine.insertCoin("20pence");
		} catch (Exception e){}
		assertEquals(machine.getCurrentlyInsertedMoney(), 1.0, 0.001);
		Integer itemCounterB = machine.getItemCounter("B");
		machine.getItem("B");
		assertEquals(machine.getItemCounter("B"), (Integer)(itemCounterB - 1));
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.0, 0.001);
	}

	@Test
	public void buyWithExactMoneyItemC() throws Exception{
		machine.setOn();
		machine.refillItems(1,1,1);
		assertTrue(machine.getItemCounter("C") > 0);
		try {
			machine.insertCoin("10pence");
			machine.insertCoin("50pence");
			machine.insertCoin("1pound");
			machine.insertCoin("10pence");
		} catch (Exception e){}
		assertEquals(machine.getCurrentlyInsertedMoney(), 1.7, 0.001);
		Integer itemCounterC = machine.getItemCounter("C");
		machine.getItem("C");
		assertEquals(machine.getItemCounter("C"), (Integer)(itemCounterC - 1));
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.0, 0.001);
	}

	@Test
	public void buyWithNotEnoughMoneyItemA () {
		machine.setOn();
		machine.refillItems(1,1,1);
		assertTrue(machine.getItemCounter("A") > 0);
		try {
			machine.insertCoin("50pence");
		} catch (Exception e){}
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.5, 0.001);
		try {
			machine.getItem("A");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "You need " + ((char) 339) + "0.1 more for buying 'A' item");
		}
	}

	@Test
	public void buyWithNotEnoughMoneyItemB () {
		machine.setOn();
		machine.refillItems(1,1,1);
		assertTrue(machine.getItemCounter("B") > 0);
		try {
			machine.insertCoin("50pence");
		} catch (Exception e){}
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.5, 0.001);
		try {
			machine.getItem("B");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "You need " + ((char) 339) + "0.5 more for buying 'B' item");
		}
	}

	@Test
	public void buyWithNotEnoughMoneyItemC () {
		machine.setOn();
		machine.refillItems(1,1,1);
		assertTrue(machine.getItemCounter("C") > 0);
		try {
			machine.insertCoin("50pence");
		} catch (Exception e){}
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.5, 0.001);
		try {
			machine.getItem("C");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "You need " + ((char) 339) + "1.2 more for buying 'C' item");
		}
	}

	@Test
	public void buyWithMoreThanEnoughMoneyItemA () throws Exception {
		machine.setOn();
		machine.refillItems(1,1,1);
		machine.refillCoins(30, 50, 100, 25);
		assertTrue(machine.getItemCounter("A") > 0);
		machine.insertCoin("1pound");
		assertEquals(machine.getCurrentlyInsertedMoney(), 1.0, 0.001);
		Integer itemCounterA = machine.getItemCounter("A");
		machine.getItem("A");
		assertEquals(machine.getItemCounter("A"), (Integer)(itemCounterA - 1));
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.0, 0.001);
	}

	@Test
	public void buyWithMoreThanEnoughMoneyItemB () throws Exception {
		machine.setOn();
		machine.refillItems(1,1,1);
		machine.refillCoins(30, 50, 100, 25);
		assertTrue(machine.getItemCounter("B") > 0);
		machine.insertCoin("1pound");
		machine.insertCoin("1pound");
		assertEquals(machine.getCurrentlyInsertedMoney(), 2.0, 0.001);
		Integer itemCounterB = machine.getItemCounter("B");
		machine.getItem("B");
		assertEquals(machine.getItemCounter("B"), (Integer) (itemCounterB - 1));
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.0, 0.001);
	}

	@Test
	public void buyWithMoreThanEnoughMoneyItemC () throws Exception {
		machine.setOn();
		machine.refillItems(1,1,1);
		machine.refillCoins(30, 50, 100, 25);
		assertTrue(machine.getItemCounter("C") > 0);
		machine.insertCoin("1pound");
		machine.insertCoin("1pound");
		machine.insertCoin("1pound");
		assertEquals(machine.getCurrentlyInsertedMoney(), 3.0, 0.001);
		Integer itemCounterC = machine.getItemCounter("C");
		machine.getItem("C");
		assertEquals(machine.getItemCounter("C"), (Integer)(itemCounterC - 1));
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.0, 0.001);
	}

	@Test()
	public void buyWithMoreThanEnoughMoneyItemAndNotChangeAvailable () throws Exception {
		machine.setOn();
		machine.refillItems(1,1,1);
		assertTrue(machine.getItemCounter("C") > 0);
		machine.insertCoin("1pound");
		machine.insertCoin("1pound");
		machine.insertCoin("1pound");
		assertEquals(machine.getCurrentlyInsertedMoney(), 3.0, 0.001);
		Integer itemCounterC = machine.getItemCounter("C");
		try {
			machine.getItem("C");
		} catch (Exception e) {
			assertEquals(e.getMessage(), "NO more coins available, call the distribuitor for your change!");
		}
		assertEquals(machine.getItemCounter("C"), (Integer)(itemCounterC - 1));
		assertEquals(machine.getCurrentlyInsertedMoney(), 0.3, 0.001);
	}

}
