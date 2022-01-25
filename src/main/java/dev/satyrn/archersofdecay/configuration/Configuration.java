package dev.satyrn.archersofdecay.configuration;

import dev.satyrn.archersofdecay.configuration.container.ArrowsOfDecayConfiguration;
import dev.satyrn.archersofdecay.configuration.container.SpawnChancesConfiguration;
import dev.satyrn.papermc.api.configuration.v1.BooleanNode;
import dev.satyrn.papermc.api.configuration.v1.ConfigurationContainer;
import dev.satyrn.papermc.api.configuration.v1.StringNode;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * The configuration of the plugin.
 *
 * @author Isabel Maskrey
 * @since 0.0.0-SNAPSHOT
 */
public final class Configuration extends ConfigurationContainer {
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
    public final @NotNull BooleanNode transferArmor = new BooleanNode(this, "transferArmor");

    /**
     * Whether wither skeletons fire flaming arrows regardless of the enchantments present on their bows.
     * This matches vanilla functionality but if decay arrows are enabled it might be a bit much.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final @NotNull BooleanNode flamingArrows = new BooleanNode(this, "flamingArrows");

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
    public final @NotNull BooleanNode dropArrows = new BooleanNode(this, "dropArrows") {
        @Override
        public @NotNull Boolean defaultValue() {
            return true;
        }
    };

    /**
     * Whether debug mode should be enabled for the plugin.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final @NotNull BooleanNode debug = new BooleanNode(this, "debug");

    /**
     * Whether metrics should be collected for the plugin.
     *
     * @since 0.0.0-SNAPSHOT
     */
    public final @NotNull BooleanNode metrics = new BooleanNode(this, "metrics");

    /**
     * Creates a new configuration instance for the plugin.
     *
     * @param plugin The parent plugin.
     * @since 0.0.0-SNAPSHOT
     */
    public Configuration(@NotNull Plugin plugin) {
        super(plugin);
    }

}
