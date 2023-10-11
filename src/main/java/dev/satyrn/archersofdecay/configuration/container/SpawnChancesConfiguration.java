package dev.satyrn.archersofdecay.configuration.container;

import dev.satyrn.papermc.api.configuration.v1.*;
import org.bukkit.Difficulty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Spawn chances configuration options.
 *
 * @author Isabel Maskrey
 * @since 0.0.0-SNAPSHOT
 */
public final class SpawnChancesConfiguration extends DifficultyDependentNode<Double> {
    // Easy difficulty spawn chances.
    private final @NotNull DoubleNode easy = new DoubleNode(this, "easy", 0D, 1D) {
        @Override
        public @NotNull Double defaultValue() {
            return 0.1D;
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
                        "Easy difficulty spawn rates.",
                        "Default value: 0.1, a.k.a. 1 in 10 spawns.",
                        "Valid values are any value between 0 and 1."
                );
            }
        }
    };
    // Normal difficulty spawn chances.
    private final @NotNull DoubleNode normal = new DoubleNode(this, "normal", 0D, 1D) {
        @Override
        public @NotNull Double defaultValue() {
            return 0.5D;
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
                        "Normal difficulty spawn rates.",
                        "Default value: 0.5, a.k.a. 1 in 2 spawns.",
                        "Valid values are any value between 0 and 1."
                );
            }
        }
    };
    // Hard difficulty spawn chances.
    private final @NotNull DoubleNode hard = new DoubleNode(this, "hard", 0D, 1D) {
        @Override
        public @NotNull Double defaultValue() {
            return 1D;
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
                        "Hard difficulty spawn rates.",
                        "Default value: 1, a.k.a. all spawns.",
                        "Valid values are any value between 0 and 1."
                );
            }
        }
    };

    /**
     * Creates a new spawn chances configuration subsection.
     *
     * @param parent The parent configuration container.
     * @param name   The name of the container.
     */
    public SpawnChancesConfiguration(final @NotNull ConfigurationNode<?> parent, final @NotNull String name) {
        super(parent, name);
    }

    /**
     * Gets the node instance for the easy difficulty value.
     *
     * @return The easy node.
     * @since 1.0.2
     */
    @Override
    protected @NotNull ConfigurationNode<Double> getEasyNode() {
        return this.easy;
    }

    /**
     * Gets the node instance for the normal difficulty value.
     *
     * @return The normal node.
     * @since 1.0.2
     */
    @Override
    protected @NotNull ConfigurationNode<Double> getNormalNode() {
        return this.normal;
    }

    /**
     * Gets the node instance for the hard difficulty value.
     *
     * @return The hard node.
     * @since 1.0.2
     */
    @Override
    protected @NotNull ConfigurationNode<Double> getHardNode() {
        return this.hard;
    }

    /**
     * Gets the configured value for a specific difficulty.
     *
     * @param difficulty The world difficulty.
     * @return The value for the specific difficulty.
     */
    public @NotNull Double value(final @NotNull Difficulty difficulty) {
        Double value = super.value(difficulty);
        return value == null ? this.defaultValue() : value;
    }

    /**
     * Gets the value of the node.
     *
     * @return The value.
     * @since 1.0-SNAPSHOT
     */
    @Override
    public @NotNull Double value() {
        return this.normal.value();
    }

    /**
     * Gets the default value of the node.
     *
     * @return The value.
     * @since 1.3-SNAPSHOT
     */
    @Override
    public @NotNull Double defaultValue() {
        return this.normal.defaultValue();
    }

    /**
     * Writes the value of the node to the config file.
     *
     * @since 1.9.0
     */
    @Override
    public void save() {
        super.save();

        if (this.getComments(true).isEmpty()) {
            this.setComments(true,
                    "Chances that a skeleton in the nether will be replaced with a wither skeleton archer.",
                    "Settings are between 0 and 1, separated by difficulty level.");
        }
    }
}
