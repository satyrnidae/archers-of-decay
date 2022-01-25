package dev.satyrn.archersofdecay.configuration.container;

import dev.satyrn.papermc.api.configuration.v1.ConfigurationContainer;
import dev.satyrn.papermc.api.configuration.v1.IntegerNode;
import org.bukkit.Difficulty;
import org.jetbrains.annotations.NotNull;

/**
 * The configuration of the effect level applied to arrows fired by wither skeleton archers.
 *
 * @author Isabel Maskrey
 * @since 0.0.0-SNAPSHOT
 */
public final class ArrowsOfDecayEffectLevelConfiguration extends ConfigurationContainer {
    // Easy difficulty effect level.
    private final @NotNull IntegerNode easy = new IntegerNode(this, "easy", -1, 255) {
        @Override
        public @NotNull Integer defaultValue() {
            return -1;
        }
    };
    // Normal difficulty effect level.
    private final @NotNull IntegerNode normal = new IntegerNode(this, "normal", -1, 255) {
        @Override
        public @NotNull Integer defaultValue() {
            return 0;
        }
    };
    // Hard difficulty effect level.
    private final @NotNull IntegerNode hard = new IntegerNode(this, "hard", -1, 255) {
        @Override
        public @NotNull Integer defaultValue() {
            return 1;
        }
    };

    /**
     * Creates a new Arrows of Decay configuration section.
     *
     * @param parent The parent container.
     * @param name   The name of the container.
     * @since 0.0.0-SNAPSHOT
     */
    public ArrowsOfDecayEffectLevelConfiguration(final @NotNull ConfigurationContainer parent, final @NotNull String name) {
        super(parent, name);
    }

    /**
     * Gets the value for a specific difficulty.
     *
     * @param difficulty The world difficulty.
     * @return The value for the world difficulty.
     */
    public int value(final @NotNull Difficulty difficulty) {
        return difficulty == Difficulty.HARD ? this.hard.value() : difficulty == Difficulty.NORMAL ? this.normal.value() : difficulty == Difficulty.EASY ? this.easy.value() : 0;
    }
}
