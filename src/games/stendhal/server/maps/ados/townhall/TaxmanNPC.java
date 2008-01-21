package games.stendhal.server.maps.ados.townhall;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.core.pathfinder.FixedPath;
import games.stendhal.server.core.pathfinder.Node;
import games.stendhal.server.entity.npc.SpeakerNPC;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Builds an NPC to keep track of all the traders in Faiumoni
 * This means players can come find prices of all items. 
 * The shop signs now have to be coded in XML not java because the implementation got moved over :(
 * So if you want to read them see data/conf/zones/ados.xml
 * @author kymara
 */
public class TaxmanNPC implements ZoneConfigurator {

	/**
	 * Configure a zone.
	 *
	 * @param	zone		The zone to be configured.
	 * @param	attributes	Configuration attributes.
	 */
	public void configureZone(StendhalRPZone zone, Map<String, String> attributes) {
		buildNPC(zone);
	}

	private void buildNPC(StendhalRPZone zone) {
		// Please change the NPCOwned Chest name if you change this NPC name.
		SpeakerNPC npc = new SpeakerNPC("Tax Man") {

			@Override
			protected void createPath() {
				List<Node> nodes = new LinkedList<Node>();
				nodes.add(new Node(2, 14));
				nodes.add(new Node(9, 14));
				nodes.add(new Node(9, 16));
				nodes.add(new Node(16,16));
				nodes.add(new Node(9, 16));
				nodes.add(new Node(9, 14));
				setPath(new FixedPath(nodes, true));
			}

			@Override
			protected void createDialog() {
				addGreeting("Hello. What do you want?");
				addJob("I calculate the duty and taxes owed by each trader in the land. It's the ones who buy weapons that I have to be most careful of.");
				addHelp("I expect you are wondering what this chaos here is. Well, each book you see is for a different shop or trade. I can work out how much to tax the shop owner. But don't poke your nose into them, it's private business!");
				addOffer("Me? Trade? You have it all wrong! I'm the tax man. It's my job to keep an eye on all traders across the land. That's why I have so many books open, I have to know exactly what these shopkeepers are doing.");
				addQuest("Ask Mayor Chalmers upstairs what Ados needs.");
 				addGoodbye("Bye - and don't you even think of looking into these records!");
			}
		};
		npc.setDescription("You see a terrifying half human, the Tax Man.");
		npc.setEntityClass("taxmannpc");
		npc.setPosition(2, 14);
		npc.initHP(100);
		zone.add(npc);


	}
}
