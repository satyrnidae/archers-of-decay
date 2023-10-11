package dev.satyrn.archersofdecay.event;

import dev.satyrn.archersofdecay.configuration.Configuration;
import dev.satyrn.papermc.api.util.v1.Cast;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.projectiles.ProjectileSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.logging.Level;

/**
 * Handles entity spawn events for the plugin.
 *
 * @author Isabel Maskrey
 * @since 0.0.0-SNAPSHOT
 */
@SuppressWarnings("ClassCanBeRecord")
public class ArchersOfDecayEvents implements Listener {
    // The plugin.
    private final @NotNull Plugin plugin;
    // The configuration.
    private final @NotNull Configuration configuration;

    /**
     * Initializes a new spawn entity listener.
     *
     * @param plugin        The plugin.
     * @param configuration The configuration.
     * @since 0.0.0-SNAPSHOT
     */
    public ArchersOfDecayEvents(final @NotNull Plugin plugin, final @NotNull Configuration configuration) {
        this.plugin = plugin;
        this.configuration = configuration;
    }

    /**
     * Handles skeleton spawning in the nether.
     *
     * @param event The event.
     * @since 0.0.0-SNAPSHOT
     */
    @EventHandler
    public void onSpawnSkeleton(final @NotNull CreatureSpawnEvent event) {
        // Only replace normal skeletons.
        if (event.getEntity().getType() != EntityType.SKELETON) {
            return;
        }
        // Only replace naturally spawned entities.
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.NATURAL && event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.DEFAULT) {
            return;
        }
        // Only spawn in the nether.
        final World world = event.getEntity().getWorld();
        if (world.getEnvironment() != World.Environment.NETHER) {
            return;
        }
        final double spawnChance = this.configuration.spawnChances.value(world.getDifficulty());
        if (spawnChance <= 0) {
            return;
        }
        final Location location = event.getLocation();
        if (Math.random() < spawnChance) {
            event.setCancelled(true);
            this.plugin.getLogger()
                    .log(Level.FINER, "[Events] Replaced {0} with wither skeleton at x:{1}, y:{2}, z:{3} in world {4} with a chance of {5}%", new Object[]{event.getEntity().getType(), location.getX(), location.getY(), location.getZ(), world.getName(), spawnChance * 100});

            final @NotNull Optional<Skeleton> skeletonResult = Cast.as(Skeleton.class, event.getEntity());
            if (skeletonResult.isEmpty()) {
                return;
            }
            final @NotNull EntityEquipment skeletonEquipment = skeletonResult.get().getEquipment();

            final @NotNull Optional<WitherSkeleton> result = Cast.as(WitherSkeleton.class, world.spawnEntity(location, EntityType.WITHER_SKELETON, CreatureSpawnEvent.SpawnReason.NATURAL));
            if (result.isPresent()) {
                final @NotNull WitherSkeleton witherSkeleton = result.get();
                final @NotNull EntityEquipment witherSkeletonEquipment = witherSkeleton.getEquipment();
                witherSkeletonEquipment.setItemInMainHand(skeletonEquipment.getItemInMainHand());
                if (this.configuration.transferArmor.value()) {
                    witherSkeletonEquipment.setArmorContents(skeletonEquipment.getArmorContents());
                }
            }
        }
    }

    /**
     * Handles wither skeletons shooting arrows.
     *
     * @param event The event.
     */
    @EventHandler
    public void onWitherSkeletonShootArrow(final @NotNull ProjectileLaunchEvent event) {
        final @NotNull Optional<Arrow> result = Cast.as(Arrow.class, event.getEntity());
        if (result.isEmpty()) {
            return;
        }
        final @NotNull Arrow arrow = result.get();
        final @Nullable ProjectileSource shooter = arrow.getShooter();
        if (!(shooter instanceof WitherSkeleton)) {
            return;
        }
        final @NotNull Difficulty difficulty = event.getEntity().getWorld().getDifficulty();
        int duration = configuration.arrowsOfDecay.duration.value(difficulty);
        int effectLevel = configuration.arrowsOfDecay.effectLevel.value(difficulty);
        if (duration > 0 && effectLevel >= 0) {
            final Location location = arrow.getLocation();
            this.plugin.getLogger()
                    .log(Level.FINER, "[Events] Wither skeleton fired arrow at x:{0}, y:{1}, z:{2} in world {3}; applying Wither effect at level {4} for {5} ticks.", new Object[]{location.getX(), location.getY(), location.getZ(), arrow.getWorld().getName(), effectLevel, duration});
            arrow.setBasePotionData(new PotionData(PotionType.UNCRAFTABLE));
            arrow.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, duration, effectLevel), true);
            arrow.setColor(Color.BLACK);
        }
        final @NotNull EntityEquipment equipment = ((WitherSkeleton) shooter).getEquipment();
        final @Nullable ItemStack mainHandItem = equipment.getItemInMainHand();

        // Disable vanilla flaming arrows by configuration.
        if (!this.configuration.flamingArrows.value()) {
            if (mainHandItem.getEnchantmentLevel(Enchantment.ARROW_FIRE) == 0) {
                arrow.setVisualFire(false);
                arrow.setFireTicks(0);
            }
        }
    }

    @EventHandler
    public void onWitherSkeletonDeath(final @NotNull EntityDeathEvent event) {
        // We won't handle the event if drop arrows is disabled or the entity killed was not a wither skeleton.
        if (!this.configuration.dropArrows.value() || event.getEntity().getType() != EntityType.WITHER_SKELETON) {
            return;
        }

        final @NotNull WitherSkeleton witherSkeleton = (WitherSkeleton) event.getEntity();
        final @NotNull EntityEquipment witherSkeletonEquipment = witherSkeleton.getEquipment();
        if (witherSkeletonEquipment.getItemInMainHand().getType() == Material.BOW) {
            int arrowsToDrop = (int) Math.floor(Math.random() * 3);
            if (arrowsToDrop > 0) {
                this.plugin.getLogger()
                        .log(Level.FINER, "[Events] Dropped {0} arrows from wither skeleton at x:{1}, y:{2}, z:{3} in world {4}", new Object[]{arrowsToDrop, witherSkeleton.getLocation().getX(), witherSkeleton.getLocation().getY(), witherSkeleton.getLocation().getZ(), witherSkeleton.getWorld().getName()});
                this.dropArrows(witherSkeleton, arrowsToDrop);
            }
        }
    }

    /**
     * Handles the entity damaged by entity event.
     * @param event The event name.
     * @since 1.0.1
     */
    @EventHandler
    public void onWitherSkeletonDamaged(final @NotNull EntityDamageByEntityEvent event) {
        // We won't handle the event if drop arrows is disabled or the entity killed was not a wither skeleton.
        if (!this.configuration.dropArrows.value() || event.getEntity().getType() != EntityType.WITHER_SKELETON) {
            return;
        }

        final @NotNull WitherSkeleton witherSkeleton = (WitherSkeleton)event.getEntity();
        if (witherSkeleton.getHealth() > event.getFinalDamage()) {
            // Skeleton isn't dead yet!
            return;
        }
        final @NotNull EntityEquipment witherSkeletonEquipment = witherSkeleton.getEquipment();
        // Do not process for wither skeletons not wielding a bow.
        if (witherSkeletonEquipment.getItemInMainHand().getType() != Material.BOW) {
           return;
        }

        int lootMultiplier = 1;
        @Nullable Entity damager = event.getDamager();
        if (damager instanceof final Projectile projectile) {
            ProjectileSource source = projectile.getShooter();
            if (source instanceof final Entity shootingEntity) {
                damager = shootingEntity;
            }
        } else if (damager instanceof final TNTPrimed primedTNT) {
            damager = primedTNT.getSource();
        }

        if (damager instanceof final LivingEntity livingEntity) {
            final @Nullable EntityEquipment entityEquipment = livingEntity.getEquipment();
            if (entityEquipment != null) {
                lootMultiplier += Math.max(entityEquipment.getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS), entityEquipment.getItemInOffHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS));
                int arrowsToDrop = (int)Math.floor(Math.random() * lootMultiplier);
                if (arrowsToDrop > 0) {
                    this.plugin.getLogger()
                            .log(Level.FINER, "[Events] Dropped {0} arrows from wither skeleton at x:{1}, y:{2}, z:{3} in world {4} due to Looting", new Object[]{arrowsToDrop, witherSkeleton.getLocation().getX(), witherSkeleton.getLocation().getY(), witherSkeleton.getLocation().getZ(), witherSkeleton.getWorld().getName()});
                    this.dropArrows(witherSkeleton, arrowsToDrop);
                }
            }
        }
    }

    private void dropArrows(final @NotNull WitherSkeleton witherSkeleton, int count) {
        final ItemStack arrows;
        int duration = configuration.arrowsOfDecay.duration.value(witherSkeleton.getWorld().getDifficulty());
        int effectLevel = configuration.arrowsOfDecay.effectLevel.value(witherSkeleton.getWorld().getDifficulty());

        if (this.configuration.dropArrows.dropTippedArrows.value() && duration > 0 && effectLevel >= 0) {
            arrows = new ItemStack(Material.TIPPED_ARROW, count);
            if (arrows.getItemMeta() instanceof final PotionMeta potionMeta) {
                potionMeta.setBasePotionData(new PotionData(PotionType.UNCRAFTABLE));
                potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.WITHER, duration, effectLevel), true);
                potionMeta.setColor(Color.BLACK);
                arrows.setItemMeta(potionMeta);
            }
        } else {
            arrows = new ItemStack(Material.TIPPED_ARROW, count);
        }
        witherSkeleton.getWorld().dropItemNaturally(witherSkeleton.getLocation(), arrows);
    }
}
