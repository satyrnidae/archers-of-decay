package dev.satyrn.archersofdecay.configuration.container;

import dev.satyrn.papermc.api.configuration.v1.ConfigurationContainer;
import dev.satyrn.papermc.api.configuration.v1.DoubleNode;
import org.bukkit.Difficulty;
import org.jetbrains.annotations.NotNull;

/**
 * Spawn chances configuration options.
 *
 * @author Isabel Maskrey
 * @since 0.0.0-SNAPSHOT
 */
public final class SpawnChancesConfiguration extends ConfigurationContainer {
    // Easy difficulty spawn chances.
    private final @NotNull DoubleNode easy = new DoubleNode(this, "easy", 0D, 1D) {
        @Override
        public @NotNull Double defaultValue() {
            return 0.1D;
        }
    };
    // Normal difficulty spawn chances.
    private final @NotNull DoubleNode normal = new DoubleNode(this, "normal", 0D, 1D) {
        @Override
        public @NotNull Double defaultValue() {
            return 0.5D;
        }
    };
    // Hard difficulty spawn chances.
    private final @NotNull DoubleNode hard = new DoubleNode(this, "hard", 0D, 1D) {
        @Override
        public @NotNull Double defaultValue() {
            return 1D;
        }
    };

    /**
     * Creates a new spawn chances configuration subsection.
     *
     * @param parent The parent configuration container.
     * @param name   The name of the container.
     */
    public SpawnChancesConfiguration(final @NotNull ConfigurationContainer parent, final @NotNull String name) {
        super(parent, name);
    }

    /**
     * Gets the configured value for a specific difficulty.
     *
     * @param difficulty The world difficulty.
     * @return The value for the specific difficulty.
     */
    public double value(final @NotNull Difficulty difficulty) {
        return difficulty == Difficulty.HARD ? this.hard.value() : difficulty == Difficulty.NORMAL ? this.normal.value() : difficulty == Difficulty.EASY ? this.easy.value() : 0D;
    }
}
