package dev.satyrn.archersofdecay.configuration;

import dev.satyrn.archersofdecay.configuration.container.ArrowDropConfig;
import dev.satyrn.archersofdecay.configuration.container.ArrowsOfDecayConfiguration;
import dev.satyrn.archersofdecay.configuration.container.SpawnChancesConfiguration;
import dev.satyrn.papermc.api.configuration.v1.*;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The configuration of the plugin.
 *
 * @author Isabel Maskrey
 * @since 0.0.0-SNAPSHOT
 */
public final class Configuration extends RootNode {
    private static final int CONFIG_VERSION = 1;

    /**
     * The locale to use for internationalizing plugin chat messages.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final @NotNull StringNode locale = new StringNode(this, "locale") {
        @Override
        public @NotNull String defaultValue() {
            return "en_US";
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
                        "The locale to use while translating chat messages.",
                        "Default value: en_US."
                );
            }
        }
    };

    /**
     * The chance that a skeleton will be replaced with a wither skeleton archer.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final @NotNull SpawnChancesConfiguration spawnChances = new SpawnChancesConfiguration(this, "spawnChances");

    /**
     * Whether armor should be transferred from a spawned skeleton to the replacement wither skeleton.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final @NotNull BooleanNode transferArmor = new BooleanNode(this, "transferArmor") {
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
                        "Whether armor transfers from the skeleton to the wither skeleton.",
                        "This could lead to incredibly overpowered wither skeletons!",
                        "Defaults to false."
                );
            }
        }
    };

    /**
     * Whether wither skeletons fire flaming arrows regardless of the enchantments present on their bows.
     * This matches vanilla functionality but if decay arrows are enabled it might be a bit much.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final @NotNull BooleanNode flamingArrows = new BooleanNode(this, "flamingArrows"){
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
                        "Whether withers should be allowed to fire flaming arrows regardless of whether their bow has a \"flame\" enchantment.",
                        "This matches vanilla functionality.",
                        "Defaults to false."
                );
            }
        }
    };

    /**
     * Configuration for arrows of decay fired by wither skeleton archers.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final @NotNull ArrowsOfDecayConfiguration arrowsOfDecay = new ArrowsOfDecayConfiguration(this, "arrowsOfDecay");

    /**
     * Whether arrows should drop from wither skeletons who die holding a bow.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final @NotNull ArrowDropConfig dropArrows = new ArrowDropConfig(this, "dropArrows");

    /**
     * Whether debug mode should be enabled for the plugin.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final @NotNull BooleanNode debug = new BooleanNode(this, "debug"){
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
                        "Whether to show debug output in the console.",
                        "Defaults to false."
                );
            }
        }
    };

    /**
     * Whether metrics should be collected for the plugin.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final @NotNull BooleanNode metrics = new BooleanNode(this, "metrics") {
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
                        "Whether to send usage telemetry. Uses bStats (https://bstats.org).",
                        "Defaults to false.",
                        "Only opt in to metrics if you are an adult, and are comfortable sharing non-identifying data with bStats.",
                        "See the GitHub readme at https://github.com/satyrnidae/archers-of-decay for more info on metrics.",
                        "You can also see what metrics are collected on the bStats metrics page here: https://bstats.org/plugin/bukkit/Archers%20of%20Decay/20029",
                        "Please make sure you've read the bStats privacy policy at https://bstats.org/privacy-policy before enabling!"
                );
            }
        }
    };

    /**
     * Creates a new configuration instance for the plugin.
     *
     * @param plugin The parent plugin.
     * @since 0.0.0-SNAPSHOT
     */
    public Configuration(@NotNull Plugin plugin) {
        super(plugin);
    }

    /**
     * Updates the configuration file to the latest version.
     *
     * @since 1.0.2
     */
    @Override
    public void upgrade() {
        final @NotNull Plugin plugin = this.getPlugin();
        final @NotNull Logger logger = plugin.getLogger();
        final int previousVersion = this.getVersion();

        if (previousVersion > CONFIG_VERSION) {
            logger.log(Level.WARNING, "Unsupported config version found! Performing a hard downgrade. This will restore old / renamed config values and reset the file to version " + CONFIG_VERSION + ". Newer settings will be ignored. Configured settings may be lost, and your configuration may be forced into an invalid state!");
        }

        // Sequential config upgrades
        this.upgrade_0_to_1(logger, previousVersion);

        this.setVersion(CONFIG_VERSION);

        if (previousVersion > CONFIG_VERSION) {
            this.getConfig().setInlineComments("_version", List.of("I mean it! Don't touch!"));
        }

        this.save();
    }

    // Upgrades the config from version 0 to version 1.
    // Codename Paper Wasp API version 1.9.0 introduced a lot of new functionality
    // I also wanted to remove the opt-out metrics and replace with opt-in
    // If there's one thing that the metrics I had collected told me, it's that
    //    nobody updates their config, and anyone who does opts out of metrics. Lol.
    private void upgrade_0_to_1(Logger logger, int previousVersion) {
        if (previousVersion >= 1) {
            return;
        }
        logger.log(Level.INFO, "Found version file < 1, updating.");

        // Disable metrics by default in the new version.
        this.metrics.setValue(false);
        this.metrics.setComments(
                "Whether to send usage telemetry. Uses bStats (https://bstats.org).",
                "Defaults to false.",
                "Only opt in to metrics if you are an adult, and are comfortable sharing non-identifying data with bStats.",
                "See the GitHub readme at https://github.com/satyrnidae/archers-of-decay for more info on metrics.",
                "You can also see what metrics are collected on the bStats metrics page here: https://bstats.org/plugin/bukkit/Archers%20of%20Decay/20029",
                "Please make sure you've read the bStats privacy policy at https://bstats.org/privacy-policy before enabling!"
        );
        logger.log(Level.INFO, "Reset metrics configuration to " + this.metrics.value() + ". If you opted in before, you will need to opt in again.");

        // Update dropArrows to new nested config
        final @NotNull String dropArrowsBasePath = this.dropArrows.getBasePath(new StringBuilder());
        logger.log(Level.INFO, "Updating the format of the " + dropArrowsBasePath + " configuration setting...");
        final boolean previousValue = this.getConfig().getBoolean(dropArrowsBasePath);
        this.dropArrows.setValue(previousValue);
        logger.log(Level.INFO, "Config value " + this.dropArrows.getValuePath() + " set to " + previousValue + ".");
        final boolean dropTippedArrowsEnabled = this.arrowsOfDecay.value();
        this.dropArrows.dropTippedArrows.setValue(dropTippedArrowsEnabled);
        logger.log(Level.INFO, "Config value " + this.dropArrows.dropTippedArrows.getValuePath() + " set to " + dropTippedArrowsEnabled + ".");
        this.dropArrows.setComments(true,
                "Whether Wither Skeleton archers should drop arrows on death.",
                "Set \"" + this.dropArrows.getValuePath() + "\" to true to enable this functionality."
        );
        this.dropArrows.setComments(
                "Whether to drop arrows if a wither skeleton has a bow.",
                "Will drop as \"Uncraftable Tipped Arrow\" with a wither effect if arrows of decay are enabled.",
                "Defaults to true."
        );
        this.dropArrows.dropTippedArrows.setComments(
                "Whether the arrows which are dropped should be normal or tipped with the Wither effect.",
                "Wither effect levels depend on difficulty.",
                "Ignored if dropArrows is not enabled, effect level is set to a negative value, or effect duration is set to 0.",
                "Defaults to false, initially set to true."
        );
    }
}
