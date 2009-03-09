package games.stendhal.server.core.engine.transformer;

import games.stendhal.server.entity.mapstuff.portal.HousePortal;
import marauroa.common.game.RPObject;

public class HousePortalTransformer implements Transformer {
	public RPObject transform(final RPObject object) {
		return new HousePortal(object);
	}
}
