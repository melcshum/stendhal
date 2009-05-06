package games.stendhal.server.entity.player;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.item.RingOfLife;
import games.stendhal.server.maps.MockStendlRPWorld;

import org.junit.BeforeClass;
import org.junit.Test;

import utilities.PlayerTestHelper;
import utilities.RPClass.CorpseTestHelper;

public class PlayerDieerTest {

	@BeforeClass
	public static void setUpBeforeClass() {
		MockStendlRPWorld.get();
		CorpseTestHelper.generateRPClasses();
	}

	@Test
	public void testPlayerDieer() {
		final Player hasRingGood = PlayerTestHelper.createPlayer("bob");
		hasRingGood.setXP(10000);
		hasRingGood.put("karma", 200.0);
                final Player hasRingBad = PlayerTestHelper.createPlayer("bob");
                hasRingBad.setXP(10000);
		hasRingBad.put("karma", -200.0);
		
		final Player hasNoRingGood = PlayerTestHelper.createPlayer("bob");
		hasNoRingGood.setXP(10000);
		hasNoRingGood.put("karma", 200.0);
		final Player hasNoRingBad = PlayerTestHelper.createPlayer("bob");
                hasNoRingBad.setXP(10000);
		hasNoRingBad.put("karma", -200.0);

		final StendhalRPZone zone = new StendhalRPZone("testzone");
		zone.add(hasRingGood);
		zone.add(hasRingBad);
		zone.add(hasNoRingGood);
                zone.add(hasNoRingBad);
		
		final RingOfLife ring = new RingOfLife();
		hasRingGood.equip("bag", ring);
		final RingOfLife ring2 = new RingOfLife();
		hasRingBad.equip("bag", ring2);

		assertFalse(ring.isBroken());
		assertFalse(ring2.isBroken());

		final PlayerDieer dierWithRingGood = new PlayerDieer(hasRingGood);
		dierWithRingGood.onDead(new Entity() {
		});

                final PlayerDieer dierWithRingBad = new PlayerDieer(hasRingBad);
                dierWithRingBad.onDead(new Entity() {
		    });

		final PlayerDieer dierWithoutRingGood = new PlayerDieer(hasNoRingGood);
		dierWithoutRingGood.onDead(new Entity() {
		});
                final PlayerDieer dierWithoutRingBad = new PlayerDieer(hasNoRingBad);
                dierWithoutRingBad.onDead(new Entity() {
		    });

		assertTrue(ring.isBroken());
                assertTrue(ring2.isBroken());
		
		assertThat("ring wearer, good loses max 1 percent", hasRingGood.getXP(), greaterThan(9899));
		assertThat("ring wearer, good loses min 0 percent", hasRingGood.getXP(), lessThan(10001));

		assertThat("ring wearer, bad loses max 2 percent", hasRingBad.getXP(), greaterThan(9799));

                assertThat("normal player, good loses max 10 percent", hasNoRingGood.getXP(), greaterThan(8999));
		assertThat("normal player, good loses min 0 percent", hasNoRingGood.getXP(), lessThan(10001));

		assertThat("normal player, bad loses max 20 percent", hasNoRingBad.getXP(), greaterThan(7999));
		
		hasRingGood.setXP(10000);
		dierWithRingGood.onDead(new Entity() {
		});
		assertThat("ring wearer, good, with broken ring, loses max 10 percent", hasRingGood.getXP(), greaterThan(8999));
		assertThat("ring wearer, good, with broken ring, loses min 0 percent", hasRingGood.getXP(), lessThan(10001));
		
	}
	
	

}
