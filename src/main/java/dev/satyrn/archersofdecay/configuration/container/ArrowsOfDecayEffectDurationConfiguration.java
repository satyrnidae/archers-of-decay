package dev.satyrn.archersofdecay.configuration.container;

import dev.satyrn.papermc.api.configuration.v1.*;
import org.bukkit.Difficulty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * The configuration of the effect duration applied to arrows fired by wither skeleton archers.
 *
 * @author Isabel Maskrey
 * @since 0.0.0-SNAPSHOT
 */
public final class ArrowsOfDecayEffectDurationConfiguration extends DifficultyDependentNode<Integer> {
    // Easy difficulty effect duration.
    private final @NotNull IntegerNode easy = new IntegerNode(this, "easy", 0, Integer.MAX_VALUE) {
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
                        "Easy difficulty wither effect duration in ticks.",
                        "Defaults to 0.",
                        "Valid values are whole numbers 0 or greater. Setting to 0 will cause the effect not to be applied."
                );
            }
        }
    };
    // Normal difficulty effect duration.
    private final @NotNull IntegerNode normal = new IntegerNode(this, "normal", 0, Integer.MAX_VALUE) {
        @Override
        public @NotNull Integer defaultValue() {
            return 200;
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
                        "Normal difficulty wither effect duration in ticks.",
                        "Defaults to 200, or ten seconds.",
                        "Valid values are whole numbers 0 or greater. Setting to 0 will cause the effect not to be applied."
                );
            }
        }
    };
    // Hard difficulty effect duration.
    private final @NotNull IntegerNode hard = new IntegerNode(this, "hard", 0, Integer.MAX_VALUE) {
        @Override
        public @NotNull Integer defaultValue() {
            return 100;
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
                        "Hard difficulty wither effect duration in ticks.",
                        "Defaults to 100, or five seconds.",
                        "Valid values are whole numbers 0 or greater. Setting to 0 will cause the effect not to be applied."
                );
            }
        }
    };

    /**
     * Creates a new Arrows of Decay effect duration configuration section.
     *
     * @param parent The parent container.
     * @param name   The name of the container.
     * @since 0.0.0-SNAPSHOT
     */
    public ArrowsOfDecayEffectDurationConfiguration(final @NotNull ArrowsOfDecayConfiguration parent, final @NotNull String name) {
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

        if (this.getComments(true).isEmpty()) {
            this.setComments(true,
                    "The duration in ticks that the decay will last."
            );
        }
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
     * Gets the value of the node.
     *
     * @return The value.
     * @since 1.0-SNAPSHOT
     */
    @Override
    public @NotNull Integer value() { return this.normal.value(); }

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
     * Gets a value specific to the given difficulty value.
     *
     * @param difficulty The difficulty of the current world.
     * @return The value of the node for that difficulty.
     * @since 1.9.0
     */
    @Override
    public @NotNull Integer value(@NotNull Difficulty difficulty) {
        Integer value = super.value(difficulty);
        return value == null ? this.defaultValue() : value;
    }
}
