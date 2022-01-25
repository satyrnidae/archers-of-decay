package dev.satyrn.archersofdecay.configuration.container;

import dev.satyrn.papermc.api.configuration.v1.BooleanNode;
import dev.satyrn.papermc.api.configuration.v1.ConfigurationContainer;
import dev.satyrn.papermc.api.configuration.v1.IntegerNode;
import org.jetbrains.annotations.NotNull;

/**
 * Arrow of decay configuration settings.
 *
 * @author Isabel Maskrey
 * @since 0.0.0-SNAPSHOT
 */
public final class ArrowsOfDecayConfiguration extends ConfigurationContainer {
    /**
     * Whether wither skeleton archers will fire wither-tipped arrows.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final BooleanNode enabled = new BooleanNode(this, "enabled") {
        @Override
        public @NotNull Boolean defaultValue() {
            return true;
        }
    };

    /**
     * The duration of the wither effect applied to hit entities.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final ArrowsOfDecayEffectDurationConfiguration duration = new ArrowsOfDecayEffectDurationConfiguration(this, "duration");

    /**
     * The effect level of the wither effect applied to hit entities.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final @NotNull ArrowsOfDecayEffectLevelConfiguration effectLevel = new ArrowsOfDecayEffectLevelConfiguration(this, "effectLevel");

    /**
     * Creates a new Arrows of Decay configuration section.
     *
     * @param parent The parent container.
     * @param name   The name of the container.
     * @since 0.0.0-SNAPSHOT
     */
    public ArrowsOfDecayConfiguration(final @NotNull ConfigurationContainer parent, final @NotNull String name) {
        super(parent, name);
    }
}
