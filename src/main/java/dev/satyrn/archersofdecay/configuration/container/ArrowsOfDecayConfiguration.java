package dev.satyrn.archersofdecay.configuration.container;

import dev.satyrn.archersofdecay.configuration.Configuration;
import dev.satyrn.papermc.api.configuration.v1.BooleanNode;
import dev.satyrn.papermc.api.configuration.v1.ConfigurationContainer;
import dev.satyrn.papermc.api.configuration.v1.ConfigurationNode;
import dev.satyrn.papermc.api.configuration.v1.IntegerNode;
import org.jetbrains.annotations.NotNull;

/**
 * Arrow of decay configuration settings.
 * Value node is whether wither skeletons fire and drop tipped arrows with a decay attribute.
 *
 * @author Isabel Maskrey
 * @since 0.0.0-SNAPSHOT
 */
public final class ArrowsOfDecayConfiguration extends BooleanNode {
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
    public ArrowsOfDecayConfiguration(final @NotNull Configuration parent, final @NotNull String name) {
        super(parent, name);
    }

    /**
     * Writes the value of the node to the config file.
     *
     * @since 1.9.0
     */
    @Override
    public void save() {
        super.save();

        if (this.getComments().isEmpty()) {
            this.setComments(
                    "If true, wither skeletons will fire tipped arrows with a decay attribute.",
                    "Defaults to true.");
        }
        if (this.getComments(true).isEmpty()) {
            this.setComments(true,
                    "Whether wither skeletons fire tipped arrows with a decay attribute.");
        }
    }
}
