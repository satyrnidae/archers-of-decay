# ![Archers of Decay](https://imgur.com/u6jNt1U.png)

Adds Minecraft Dungeons-inspired wither skeleton archers to the nether! For PaperMC 1.18.1

[![Maven](https://github.com/satyrnidae/archers-of-decay/actions/workflows/maven.yml/badge.svg)](https://github.com/satyrnidae/archers-of-decay/blob/main/res/Archers%20of%20Decay%20Title.png?raw=true)

## Introduction

Tired of seeing boring old skeleton archers wandering the soul sand plains? Want some more spice in your life? Archers of Decay may be for you!

## Key Features

- Replaces some nether-spawned skeletal archers with wither skeleton archers! (Configurable)
- Wither skeleton archers no longer shoot fire arrows without a flame bow! (Configurable)
- Wither skeleton archers now fire wither-tipped arrows! (Configurable)
- Configurable wither arrow level and duration by world difficulty!
- Adds an uncraftable Wither variant tipped arrow, which can drop from the new archers! (Configurable)
- Custom locale files (Place in plugins/ArchersOfDecay/lang/ folder)

## Notes For Use

Be warned: Wither archers are not to be trifled with! Be careful when configuring the plugin, as it is very easy to make them incredibly difficult to beat!

The default configuration settings have been tuned to game difficulty, but feel free to experiment!

### Metrics

**Only enable metrics if you are an adult, and willing to disclose this information.**

This mod optionally uses bStats (https://bstats.org) for usage metrics. This functionality is disabled by default, but may be opted into by changing the "metrics" setting in the configuration file to "true".

Data collected by these metrics include:
- Minecraft version
- Whether the server is set to Online mode
- Player count
- Server architecture (software, core count, operating system, processor architecture, java version)
- Plugin configuration (version, locale, plugin-specific config settings)
- Server country of origin (where the server is hosted)

These metrics may be used to determine which features should be developed in the future. **Only enable metrics if you are an adult, and are willing to disclose this information.** Please read the [bStats privacy policy](https://bstats.org/privacy-policy) before opting in to metrics.

## Configuration

The default configuration file is contained within the details below.

<details>

```yaml
# Don't touch this.
_version: 1
# The locale to use while translating chat messages.
# Default value: en_US.
locale: "en_US"
# Chances that a skeleton in the nether will be replaced with a wither skeleton archer.
# Settings are between 0 and 1, separated by difficulty level.
spawnChances:
  # Easy difficulty spawn rates.
  # Default value: 0.1, a.k.a. 1 in 10 spawns.
  # Valid values are any value between 0 and 1.
  easy: 0.1
  # Normal difficulty spawn rates.
  # Default value: 0.5, a.k.a. 1 in 2 spawns.
  # Valid values are any value between 0 and 1.
  normal: 0.5
  # Hard difficulty spawn rates.
  # Default value: 1, a.k.a. all spawns.
  # Valid values are any value between 0 and 1.
  hard: 1
# Whether withers should be allowed to fire flaming arrows regardless of whether their bow has a "flame" enchantment.
# This matches vanilla functionality.
# Defaults to false.
flamingArrows: false
# Whether armor transfers from the skeleton to the wither skeleton.
# This could lead to incredibly overpowered wither skeletons!
# Defaults to false.
transferArmor: false
# Whether wither skeletons fire tipped arrows with a decay attribute.
arrowsOfDecay:
  # If true, wither skeletons will fire tipped arrows with a decay attribute.
  # Defaults to true.
  enabled: true
  # The duration in ticks that the decay will last.
  duration:
    # Easy difficulty wither effect duration in ticks..
    # Defaults to 0.
    # Valid values are whole numbers 0 or greater. Setting to 0 will cause the effect not to be applied.
    easy: 0
    # Normal difficulty wither effect duration in ticks.
    # Defaults to 200, or ten seconds.
    # Valid values are whole numbers 0 or greater. Setting to 0 will cause the effect not to be applied.
    normal: 200
    # Hard difficulty wither effect duration in ticks.
    # Defaults to 100, or five seconds.
    # Valid values are whole numbers 0 or greater. Setting to 0 will cause the effect not to be applied.
    hard: 100
  # The level of the wither effect applied to the arrows.
  effectLevel:
    # Easy difficulty arrow effect level.
    # Defaults to -1.
    # Valid values are whole numbers from -1 to 255. When set to -1, the effect will not be applied.
    easy: -1
    # Normal difficulty arrow effect level.
    # Defaults to 0.
    # Valid values are whole numbers from -1 to 255. When set to -1, the effect will not be applied.
    normal: 0
    # Hard difficulty arrow effect level.
    # Defaults to 1.
    # Valid values are whole numbers from -1 to 255. When set to -1, the effect will not be applied.
    hard: 1
# Whether Wither Skeleton archers should drop arrows on death.
# Set "dropArrows.enabled" to true to enable this functionality.
dropArrows:
  # Whether to drop arrows if a wither skeleton has a bow.
  # Will drop as "Uncraftable Tipped Arrow" with a wither effect if arrows of decay are enabled.
  # Defaults to true.
  enabled: true
  # Whether the arrows which are dropped should be normal or tipped with the Wither effect.
  # Wither effect levels depend on difficulty.
  # Ignored if dropArrows is not enabled, effect level is set to a negative value, or effect duration is set to 0.
  # Defaults to false, initially set to true.
  dropTippedArrows: true
# Whether to show debug output in the console.
# Defaults to false.
debug: false
# Whether to send usage telemetry. Uses bStats (https://bstats.org).
# Defaults to false.
# Only opt in to metrics if you are an adult, and are comfortable sharing non-identifying data with bStats.
# See the GitHub readme at https://github.com/satyrnidae/archers-of-decay for more info on metrics.
# You can also see what metrics are collected on the bStats metrics page here: https://bstats.org/plugin/bukkit/Archers%20of%20Decay/20029
# Please make sure you've read the bStats privacy policy at https://bstats.org/privacy-policy before enabling!
metrics: false
```

</details>

## Permissions

There is only one permission provided by this plugin:

- archersofdecay.admin: Allows a user or group to reload the plugin configuration file. Defaults to operator only.

## Commands

There are a few commands provided by this plugin:

- archersofdecay: Allows a user to view information about the plugin, including the configuration for the current world difficulty!
  - Aliases:
    - aod
    - archers
- archersofdecay reload: Allows a user or group with the archersofdecay.admin permission to reload the plugin configuration.
  - Aliases:
    - aod reload
    - archers reload


