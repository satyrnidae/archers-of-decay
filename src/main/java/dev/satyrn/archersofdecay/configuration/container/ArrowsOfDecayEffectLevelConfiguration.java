package dev.satyrn.archersofdecay.configuration.container;

import dev.satyrn.papermc.api.configuration.v1.ConfigurationNode;
import dev.satyrn.papermc.api.configuration.v1.DifficultyDependentNode;
import dev.satyrn.papermc.api.configuration.v1.IntegerNode;
import org.bukkit.Difficulty;
import org.jetbrains.annotations.NotNull;

/**
 * The configuration of the effect level applied to arrows fired by wither skeleton archers.
 *
 * @author Isabel Maskrey
 * @since 0.0.0-SNAPSHOT
 */
public final class ArrowsOfDecayEffectLevelConfiguration extends DifficultyDependentNode<Integer> {
    // Easy difficulty effect level.
    private final @NotNull IntegerNode easy = new IntegerNode(this, "easy", -1, 255) {
        @Override
        public @NotNull Integer defaultValue() {
            return -1;
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
                        "Easy difficulty arrow effect level.",
                        "Defaults to -1.",
                        "Valid values are whole numbers from -1 to 255. When set to -1, the effect will not be applied."
                );
            }
        }
    };
    // Normal difficulty effect level.
    private final @NotNull IntegerNode normal = new IntegerNode(this, "normal", -1, 255) {
        @Override
        public @NotNull Integer defaultValue() {
            return 0;
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
                        "Normal difficulty arrow effect level.",
                        "Defaults to 0.",
                        "Valid values are whole numbers from -1 to 255. When set to -1, the effect will not be applied."
                );
            }
        }
    };
    // Hard difficulty effect level.
    private final @NotNull IntegerNode hard = new IntegerNode(this, "hard", -1, 255) {
        @Override
        public @NotNull Integer defaultValue() {
            return 1;
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
                        "Hard difficulty arrow effect level.",
                        "Defaults to 1.",
                        "Valid values are whole numbers from -1 to 255. When set to -1, the effect will not be applied."
                );
            }
        }
    };

    /**
     * Creates a new Arrows of Decay configuration section.
     *
     * @param parent The parent container.
     * @param name   The name of the container.
     * @since 0.0.0-SNAPSHOT
     */
    public ArrowsOfDecayEffectLevelConfiguration(final @NotNull ArrowsOfDecayConfiguration parent, final @NotNull String name) {
        super(parent, name);
    }

    /**
     * Gets the node instance for the easy difficulty value.
     *
     * @return The easy node.
     * @since 1.9.0
     */
    @Override
    protected @NotNull ConfigurationNode<Integer> getEasyNode() {
        return this.easy;
    }

    /**
     * Gets the node instance for the normal difficulty value.
     *
     * @return The normal node.
     * @since 1.9.0
     */
    @Override
    protected @NotNull ConfigurationNode<Integer> getNormalNode() {
        return this.normal;
    }

    /**
     * Gets the node instance for the hard difficulty value.
     *
     * @return The hard node.
     * @since 1.9.0
     */
    @Override
    protected @NotNull ConfigurationNode<Integer> getHardNode() {
        return this.hard;
    }

    /**
     * Gets the value for a specific difficulty.
     *
     * @param difficulty The world difficulty.
     * @return The value for the world difficulty.
     */
    public @NotNull Integer value(final @NotNull Difficulty difficulty) {
        Integer value = super.value(difficulty);
        return value == null ? this.defaultValue() : value;
    }

    /**
     * Gets the value of the node.
     *
     * @return The value.
     * @since 1.0-SNAPSHOT
     */
    @Override
    public @NotNull Integer value() {
        return this.normal.value();
    }

    /**
     * Gets the default value of the node.
     *
     * @return The value.
     * @since 1.3-SNAPSHOT
     */
    @Override
    public @NotNull Integer defaultValue() {
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
                    "The level of the wither effect applied to the arrows."
            );
        }
    }
}
