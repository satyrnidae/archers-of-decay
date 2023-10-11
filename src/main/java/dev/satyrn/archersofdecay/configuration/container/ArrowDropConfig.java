package dev.satyrn.archersofdecay.configuration.container;

import dev.satyrn.papermc.api.configuration.v1.BooleanNode;
import dev.satyrn.papermc.api.configuration.v1.ConfigurationNode;
import org.jetbrains.annotations.NotNull;

/**
 * Decouples dropping tipped arrows from firing tipped arrows.
 *
 * @since 1.0.2
 * @author Isabel Maskrey
 */
public class ArrowDropConfig extends BooleanNode {
    // Whether the arrows dropped should be tipped or not tipped.
    public @NotNull BooleanNode dropTippedArrows = new BooleanNode(this, "dropTippedArrows") {
        /**
         * Writes the value of the node to the config file.
         *
         * @since 1.9.0
         */
        @Override
        public void save() {
            super.save();

            // Restore the node comments if they were lost.
            if (this.getComments().isEmpty()) {
                this.setComments(
                        "Whether the arrows which are dropped should be normal or tipped with the Wither effect.",
                        "Wither effect levels depend on difficulty.",
                        "Ignored if " + this.getBasePath(new StringBuilder()) + " is not enabled, effect level is set to a negative value, or effect duration is set to 0.",
                        "Defaults to false, initially set to true."
                );
            }
        }
    };

    /**
     * Creates a new configuration node with a boolean value.
     *
     * @param parent The parent container.
     * @param name   The name of the configuration node.
     * @since 1.0.2
     */
    public ArrowDropConfig(@NotNull ConfigurationNode<?> parent, @NotNull String name) {
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

        // Restore value node comments if they were lost.
        if (this.getComments().isEmpty()) {
            this.setComments(
                    "Whether to drop arrows if a wither skeleton has a bow.",
                    "Will drop as \"Uncraftable Tipped Arrow\" with a wither effect if arrows of decay are enabled.",
                    "Defaults to true."
            );
        }

        // Restore base node comments if they were lost.
        if (this.getComments(true).isEmpty()) {
            this.setComments(true,
                    "Whether Wither Skeleton archers should drop arrows on death.",
                    "Set \"" + this.getValuePath() + "\" to true to enable this functionality."
            );
        }
    }
}
