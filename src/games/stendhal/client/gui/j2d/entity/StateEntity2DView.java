/*
 * @(#) games/stendhal/client/gui/j2d/entity/StateEntity2DView.java
 *
 * $Id$
 */

package games.stendhal.client.gui.j2d.entity;

//
//

import games.stendhal.client.IGameScreen;
import games.stendhal.client.entity.IEntity;
import games.stendhal.client.sprite.Sprite;
import games.stendhal.client.sprite.SpriteStore;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * The 2D view of an animated entity.
 */
abstract class StateEntity2DView extends Entity2DView {
	/**
	 * Log4J.
	 */
	private static final Logger logger = Logger
			.getLogger(StateEntity2DView.class);

	/**
	 * Map of named sprites.
	 */
	protected Map<Object, Sprite> sprites;

	/**
	 * Create a 2D view of an entity.
	 * 
	 * @param entity
	 *            The entity to render.
	 */
	public StateEntity2DView(final IEntity entity) {
		super(entity);

		sprites = new HashMap<Object, Sprite>();

	}

	//
	// StateEntity2DView
	//

	/**
	 * Build animations.
	 * @param gameScreen 
	 */
	protected void buildAnimations(final IGameScreen gameScreen) {
		buildSprites(sprites, gameScreen);
	}

	/**
	 * Populate named state sprites.
	 * 
	 * @param map
	 *            The map to populate.
	 * @param gameScreen 
	 */
	protected abstract void buildSprites(final Map<Object, Sprite> map,
			IGameScreen gameScreen);

	/**
	 * Get a keyed state sprite.
	 * 
	 * @param state
	 *            The state.
	 * 
	 * @return The appropriate sprite for the given state.
	 */
	protected Sprite getSprite(final Object state) {
		return sprites.get(state);
	}

	/**
	 * Get the current model state.
	 * 
	 * @return The model state.
	 */
	protected abstract Object getState();

	/**
	 * Get the current animated sprite.
	 * 
	 * @return The appropriate sprite for the current state.
	 */
	protected Sprite getStateSprite() {
		final Object state = getState();
		final Sprite sprite = getSprite(state);

		if (sprite == null) {
			logger.debug("No sprite found for: " + state);
			return SpriteStore.get().getFailsafe();
		}

		return sprite;
	}

	//
	// Entity2DView
	//

	/**
	 * Build the visual representation of this entity. This builds all the
	 * animation sprites and sets the default frame.
	 */
	@Override
	protected void buildRepresentation(final IGameScreen gameScreen) {
		buildAnimations(gameScreen);

		setSprite(getStateSprite());

	}

	/**
	 * Handle updates.
	 */
	@Override
	protected void update(final IGameScreen gameScreen) {
		super.update(gameScreen);

	}

	protected void proceedChangedState() {

		setSprite(getStateSprite());

	}

}
