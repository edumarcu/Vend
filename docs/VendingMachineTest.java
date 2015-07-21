package machine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for {@link VendingMachine}
 */
public class VendingMachineTest {
	
	@Test
	public void defaultStateIsOff() {
		VendingMachine machine = new VendingMachine();
		assertFalse(machine.isOn());
	}
	
	@Test
	public void turnsOn() {
		VendingMachine machine = new VendingMachine();
		machine.setOn();
		assertTrue(machine.isOn());		
	}
}
