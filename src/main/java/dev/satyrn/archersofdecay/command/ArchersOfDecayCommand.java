package dev.satyrn.archersofdecay.command;

import dev.satyrn.archersofdecay.configuration.Configuration;
import dev.satyrn.papermc.api.commands.v1.CommandHandler;
import dev.satyrn.papermc.api.lang.v1.I18n;
import org.bukkit.Difficulty;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Handles the archersofdecay command, displaying information about the plugin or reloading the configuration.
 *
 * @author Isabel Maskrey
 * @since 0.0.0-SNAPSHOT
 */
public final class ArchersOfDecayCommand extends CommandHandler {
    // The configuration instance.
    public final Configuration configuration;

    /**
     * Initializes a new command handler.
     * @param plugin The parent plugin.
     * @since 0.0.0-SNAPSHOT
     */
    public ArchersOfDecayCommand(final @NotNull Plugin plugin, final @NotNull Configuration configuration) {
        super(plugin);
        this.configuration = configuration;
    }

    /**
     * Executes the given command, returning its success.
     * <br>
     * If false is returned, then the "usage" plugin.yml entry for this command
     * (if defined) will be sent to the player.
     *
     * @param sender  Source of the command
     * @param command Command which was executed
     * @param label   Alias of the command which was used
     * @param args    Passed command arguments
     * @return true if a valid command, otherwise false
     * @since 0.0.0-SNAPSHOT
     */
    @Override
    public boolean onCommand(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String label, final @NotNull String[] args) {
        if (args.length >= 1 && "reload".equalsIgnoreCase(args[0])) {
            if (sender.hasPermission("archersofdecay.admin")) {
                this.getPlugin().reloadConfig();
                final @Nullable I18n instance = I18n.getInstance();
                if (instance != null) {
                    instance.setLocale(this.configuration.locale.value());
                }
                sender.sendMessage(I18n.tr("command.reload", this.getPlugin().getDescription().getFullName()));
            } else {
                sender.sendMessage(I18n.tr("command.reload.deny"));
            }
        } else {
            final @NotNull StringBuilder aboutMessage = new StringBuilder();
            String pluginName = this.getPlugin().getDescription().getName();
            String pluginVersion = this.getPlugin().getDescription().getVersion();
            String pluginAuthors = String.join(", ", this.getPlugin().getDescription().getAuthors());
            if (sender instanceof final @NotNull Player player) {
                final Difficulty difficulty = player.getWorld().getDifficulty();
                final String difficultyPrefix = difficulty.toString().substring(0, 1).toLowerCase(Locale.ROOT);
                aboutMessage.append(I18n.tr("command.about.player",
                        pluginName,
                        pluginVersion,
                        pluginAuthors,
                        difficultyPrefix,
                        this.configuration.spawnChances.value(difficulty) * 100,
                        this.configuration.flamingArrows.value(),
                        this.configuration.dropArrows.value() ? (this.configuration.dropArrows.dropTippedArrows.value() ? I18n.tr("command.about.dropArrows.witherTippedEnabled") : "true") : "false",
                        this.configuration.transferArmor.value(),
                        this.configuration.arrowsOfDecay.value()));
                if (this.configuration.arrowsOfDecay.value()) {
                    aboutMessage.append('\n').append(I18n.tr("command.about.player.arrows",
                            difficultyPrefix,
                            this.configuration.arrowsOfDecay.duration.value(difficulty) / 20F,
                            difficultyPrefix,
                            this.configuration.arrowsOfDecay.effectLevel.value(difficulty)));
                }
            } else {
                aboutMessage.append(I18n.tr("command.about",
                        pluginName,
                        pluginVersion,
                        pluginAuthors,
                        this.configuration.spawnChances.value(Difficulty.EASY) * 100,
                        this.configuration.spawnChances.value(Difficulty.NORMAL) * 100,
                        this.configuration.spawnChances.value(Difficulty.HARD) * 100,
                        this.configuration.flamingArrows.value(),
                        this.configuration.dropArrows.value() ? (this.configuration.dropArrows.dropTippedArrows.value() ? I18n.tr("command.about.dropArrows.witherTippedEnabled") : "true") : "false",
                        this.configuration.transferArmor.value(),
                        this.configuration.arrowsOfDecay.value()));
                if (this.configuration.arrowsOfDecay.value()) {
                    aboutMessage.append('\n').append(I18n.tr("command.about.arrows",
                            this.configuration.arrowsOfDecay.duration.value(Difficulty.EASY) / 20F,
                            this.configuration.arrowsOfDecay.duration.value(Difficulty.NORMAL) / 20F,
                            this.configuration.arrowsOfDecay.duration.value(Difficulty.HARD) / 20F,
                            this.configuration.arrowsOfDecay.effectLevel.value(Difficulty.EASY),
                            this.configuration.arrowsOfDecay.effectLevel.value(Difficulty.NORMAL),
                            this.configuration.arrowsOfDecay.effectLevel.value(Difficulty.HARD)));
                }
            }

            sender.sendMessage(aboutMessage.toString());
        }
        return true;
    }

    /**
     * Requests a list of possible completions for a command argument.
     *
     * @param sender  Source of the command.  For players tab-completing a
     *                command inside a command block, this will be the player, not
     *                the command block.
     * @param command Command which was executed
     * @param alias   The alias used
     * @param args    The arguments passed to the command, including final
     *                partial argument to be completed and command label
     * @return A List of possible completions for the final argument, or null
     * to default to the command executor
     */
    @Override
    @Contract(value = "_, _, _, _ -> !null", pure = true)
    public @NotNull List<String> onTabComplete(final @NotNull CommandSender sender, final @NotNull Command command, final @NotNull String alias, final @NotNull String[] args) {
        final List<String> completionOptions = new ArrayList<>();
        if (args.length == 1) {
            if (sender.hasPermission("archersofdecay.admin")) {
                completionOptions.add("reload");
            }
        }
        return completionOptions;
    }
}
