### Snowball Stacker
#### Allows you to throw snowballs and create piles of snow or turn water into ice.

### Changelog:

#### Coming soon:
* Towny support
* Added /ss alias for /snowballstacker
* Changed permission node snowballstacker.adjust to snowballstacker.admin
* Added "/snowballstacker perms" command. Op's and Admin's can use these to see the plugin's permissions.

#### Version 1.03 (3/17/2013)
* Use plugin logger instead of Minecraft logger
* Added permissions:
    * snowballstacker.stack: Allows user/group to stack snow
    * snowballstacker.freeze: Allows user/group to freeze water
    * snowballstacker.adjust: Allows user/group to use setfreeze and setsnow commands
    * Use snowballstacker.* for all permissions
* Updated Metrics
* Added commands "/snowballstacker setfreeze [true/false]" and "/snowballstacker setsnow [true/false]"
    * Must be op or have "snowballstacker.adjust" permission.
* Updated holdsSnow list.
* Since github dropped support for their version of downloads, I have set up a new folder structure under downloads:
    * release: always has the latest official version
    * dev: always has the most up to date version (could be the official or a development/test version)
    * old: storage of all old versions in case somebody wants to download an older version.

#### Version 1.0.2 (8/26/2012)
* Added optional support for WorldGuard. Prevents people who don't have "build" permissions from stacking snow in a region.
* Adding Metrics support. To opt out, set "opt-out: true" in PluginMetrics/config.yml
* Changed config option "Freeze Water" to "Freeze_Water"
* Added config option "Only_Works_In_Snow_Biomes"

#### Version 1.0.1 (3/3/2012)
* Added an optional configuration to freeze water. 
* Added the default command /snowballstacker

#### Version 1.0 (3/1/2012)
* Initial Release