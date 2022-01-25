package dev.satyrn.archersofdecay;

import dev.satyrn.archersofdecay.command.ArchersOfDecayCommand;
import dev.satyrn.archersofdecay.configuration.Configuration;
import dev.satyrn.archersofdecay.event.ArchersOfDecayEvents;
import dev.satyrn.papermc.api.lang.v1.I18n;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

/**
 * Plugin lifecycle management for the Archers of Decay plugin.
 *
 * @author Isabel Maskrey
 * @since 0.0.0-SNAPSHOT
 */
@SuppressWarnings("unused")
public final class ArchersOfDecay extends JavaPlugin {
    // The internationalization instance for the plugin.
    private I18n i18n;

    /**
     * Called when the plugin is loaded.
     *
     * @since 0.0.0-SNAPSHOT
     */
    @Override
    public void onLoad() {
        this.getLogger().log(Level.INFO, "Loading Archers of Decay...");
        this.saveDefaultConfig();
    }

    /**
     * Called when the plugin is enabled.
     *
     * @since 0.0.0-SNAPSHOT
     */
    @Override
    public void onEnable() {
        this.getLogger().log(Level.INFO, "Enabling Archers of Decay...");
        this.i18n = new I18n(this, "lang");
        this.i18n.enable();

        final Configuration configuration = new Configuration(this);
        if (configuration.debug.value()) {
            this.getLogger().setLevel(Level.ALL);
        }
        if (configuration.metrics.value()) {
            final Metrics metrics = new Metrics(this, 14055);
            metrics.addCustomChart(new SimplePie("locale", configuration.locale::value));
            metrics.addCustomChart(new SimplePie("wither_skeletons_fire_flaming_arrows", configuration.flamingArrows.value()::toString));
            metrics.addCustomChart(new SimplePie("transfer_armor_to_wither_skeleton", configuration.transferArmor.value()::toString));
            metrics.addCustomChart(new SimplePie("arrows_of_decay_enabled", configuration.arrowsOfDecay.enabled.value()::toString));
            metrics.addCustomChart(new SimplePie("wither_archers_drop_arrows", configuration.dropArrows.value()::toString));
        }
        this.i18n.setLocale(configuration.locale.value());
        this.registerEvents(configuration);
        this.registerCommand(configuration);

        this.getLogger().log(Level.INFO, "Archers of Decay successfully enabled!");
    }

    /**
     * Called when the plugin is disabled.
     *
     * @since 0.0.0-SNAPSHOT
     */
    @Override
    public void onDisable() {
        this.getLogger().log(Level.INFO, "Disabling Archers of Decay!");
        this.i18n.disable();
    }

    // Registers the event listeners for the plugin.
    private void registerEvents(final @NotNull Configuration configuration) {
        this.getServer().getPluginManager().registerEvents(new ArchersOfDecayEvents(this, configuration), this);
    }

    // Registers the command handlers for the plugin.
    private void registerCommand(final @NotNull Configuration configuration) {
        new ArchersOfDecayCommand(this, configuration).setupCommand(this, "archersofdecay");
    }
}
