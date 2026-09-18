# Quick Drag Transfer

A Fabric mod for Minecraft 26.2. Hold **Shift + Left Click** and drag across
inventory slots to instantly transfer every item you hover over, without
clicking each stack individually.

## Features

- Hold Shift + Left Click and drag over slots in your inventory or an open
  container to move every item you pass over.
- Works on standard container screens: chests, barrels, shulker boxes,
  furnaces, brewing stands, crafting tables, anvils, the player inventory,
  and most modded containers built the normal way.
- 100% client-side — no server-side installation needed, safe on vanilla
  multiplayer servers.

## Requirements

| Requirement    | Version   |
|----------------|-----------|
| Minecraft      | 26.2      |
| Fabric Loader  | 0.19.5+   |
| Fabric API     | 0.160.0+26.2 |
| Java           | 25+       |

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/) for Minecraft 26.2.
2. Download [Fabric API](https://modrinth.com/mod/fabric-api) matching 26.2.
3. Download the latest release of this mod.
4. Place both `.jar` files in your `mods` folder:
   - Windows: `%appdata%\.minecraft\mods`
   - macOS: `~/Library/Application Support/minecraft/mods`
   - Linux: `~/.minecraft/mods`

## Building from source

```
git clone https://github.com/AyanStudio/Quick-Drag-Transfer.git
cd Quick-Drag-Transfer
./gradlew build
```

On Windows, use `gradlew.bat build` instead. The first build will download
Gradle, the Minecraft 26.2 client, Fabric Loader, and Fabric API, so it
needs an internet connection and will take a few minutes.

The built jar is written to `build/libs/quickdragtransfer-<version>.jar`.
Use that file, not the accompanying `-sources.jar`.

## Contributing

Issues and pull requests are welcome. If you're changing behavior in
`QuickDragTransferClient.java`, please test it in-game before opening a PR.

## License

[MIT](LICENSE)
