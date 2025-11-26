<p align="center">
  <a href="https://github.com/tantaihaha4487/ShiroVerse/actions/workflows/build-plugin.yml" style="text-decoration:none;"><img src="https://github.com/tantaihaha4487/QDropConfirm/actions/workflows/build-plugin.yml/badge.svg" alt="Build Status"></a>
  <a href="https://github.com/tantaihaha4487/ShiroVerse/releases/latest" style="text-decoration:none;"><img src="https://img.shields.io/github/v/release/tantaihaha4487/ShiroVerse?style=flat-square" alt="Latest Release"></a>
  <a href="https://jitpack.io/#tantaihaha4487/ShiroVerse" style="text-decoration:none;"><img src="https://jitpack.io/v/tantaihaha4487/ShiroVerse.svg" alt="jitpack badge"></a>
</p>

# ShiroCore

**ShiroCore** is a powerful library plugin for Minecraft (Spigot/Paper) designed to simplify the creation of complex, interactive player abilities. It provides a robust framework for shift-activated skills, complete with progress bars, event handling, and a clean API, allowing developers to focus on building unique gameplay mechanics.

---

## 🛠️ Understanding the Components: Engine vs. API

The ShiroVerse project is composed of two key modules that work together: `ShiroCore` (the engine) and `shiro-api` (the developer kit). Understanding their distinct roles is crucial for using the framework effectively.

### **`ShiroCore` — The Engine**

- **What It Is:** The actual Spigot/Paper plugin that you install on your Minecraft server. It's the heart of the system.
- **What It Does:** It contains all the core logic for detecting player actions (like shift-spamming), managing ability states, handling cooldowns, and displaying visual feedback like action bar progress bars.
- **Who It's For:**
    - **Server Owners:** You install this single plugin in your `plugins` folder. It's the powerhouse that makes all dependent plugins work.
    - **Developers:** Your plugins require `ShiroCore` to be present on the server to function. It's the runtime environment for your creations.

### **`shiro-api` — The Developer Kit**

- **What It Is:** A lightweight Java library (`.jar`) that developers use to write their own plugins that integrate with `ShiroCore`.
- **What It Does:** It provides a clean and simple API to register custom abilities, listen to events, and control the activation system without needing to access the complex internal code of the engine. Key components include:
    - `AbilityManager`: For registering and managing your custom abilities.
    - `ShiftAbility`: An abstract class you extend to create your own shift-activated skills quickly.
    - `ActionbarMessage`: A utility for creating stylish progress bars and alerts.
- **Who It's For:**
    - **Developers:** You include `shiro-api` as a dependency in your plugin's `pom.xml` (or other build system). It is your toolbox for building new gameplay features on top of the ShiroCore engine.

---

## 🌟 Core Features

### **1. Shift Activation System**
Create abilities that activate through rapid shift-key spamming (configurable threshold).

- 🎯 **Configurable Progress** - Set custom shift count requirements
- 📊 **Progress Events** - Track activation progress in real-time
- 🎨 **Action Bar Integration** - Beautiful progress bars with mini-font support
- ⚡ **Material-Based Registration** - Register specific materials for activation

### **2. Ability Manager API**
Simplified API for creating and managing shift-activated abilities.

- 🔌 **Plugin-Friendly** - Easy integration with any Spigot/Paper plugin
- 🔄 **Automatic State Management** - Handles activation/deactivation automatically
- 🎮 **Event-Driven** - Built-in event handling for abilities
- 🛡️ **Thread-Safe** - Concurrent-safe ability tracking

### **3. Action Bar Utilities**
Create stylish action bar messages with multiple design options.

- 🎨 **Multiple Styles** - Default, gradient, and minimalist loading bars
- 🔤 **Mini-Font Support** - Small, clean text rendering
- 🌈 **Custom Colors** - Full color customization
- ⚡ **Alert Messages** - Pre-formatted alert system

---

## 📦 Installation

### **As a Server Plugin**
1. Download the latest JAR from [Releases](https://github.com/tantaihaha4487/ShiroVerse/releases)
2. Place in your server's `plugins` folder
3. Restart the server

### **As a Dependency**

Add to your `pom.xml`:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.tantaihaha4487</groupId>
        <artifactId>ShiroCore</artifactId>
        <version>1.21-SNAPSHOT</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

Add to your `plugin.yml`:
```yaml
depend: [ShiroCore]
```

---

## 🚀 Quick Start

### **Example 1: Create a Shift-Activated Ability**

```java
import net.thanachot.ShiroCore.api.ability.AbilityManager;
import net.thanachot.ShiroCore.api.ability.ShiftAbility;

public class MyPlugin extends JavaPlugin {
    
    @Override
    public void onEnable() {
        // Get the AbilityManager
        AbilityManager manager = AbilityManager.getOrThrow();
        
        // Register your custom ability
        manager.registerAbility(new SuperJumpAbility());
    }
}

// Custom ability class
public class SuperJumpAbility extends ShiftAbility {
    private final Set<UUID> activePlayers = new HashSet<>();
    
    public SuperJumpAbility() {
        super("superjump", item -> 
            item != null && item.getType() == Material.FEATHER
        );
    }
    
    @Override
    public void onActivate(Player player, ItemStack item) {
        activePlayers.add(player.getUniqueId());
        player.sendActionBar(Component.text("Super Jump Activated!")
            .color(NamedTextColor.GREEN));
        player.addPotionEffect(new PotionEffect(
            PotionEffectType.JUMP, 
            Integer.MAX_VALUE, 
            2
        ));
    }
    
    @Override
    public void onDeactivate(Player player) {
        activePlayers.remove(player.getUniqueId());
        player.removePotionEffect(PotionEffectType.JUMP);
    }
    
    @Override
    public boolean isActive(Player player) {
        return activePlayers.contains(player.getUniqueId());
    }
}
```

**That's it!** The ability will:
- ✅ Automatically listen for shift-spam events
- ✅ Show progress bar on action bar
- ✅ Activate when threshold is reached
- ✅ Deactivate when item is swapped

---

### **Example 2: Action Bar Messages**

```java
import net.thanachot.ShiroCore.api.text.ActionbarMessage;

// Simple loading bar
Component loadingBar = ActionbarMessage.getLoadingBar(currentProgress, maxProgress);
player.sendActionBar(loadingBar);

// Stylized gradient bar
Component gradientBar = ActionbarMessage.getStylizedLoadingBar(
    current, 
    max, 
    NamedTextColor.AQUA,  // filled color
    NamedTextColor.GRAY    // empty color
);

// Minimalist dot bar
Component dotBar = ActionbarMessage.getDotLoadingBar(current, max);

// Alert message
Component alert = ActionbarMessage.getAlert("Warning!", NamedTextColor.RED);
```

**Output Examples:**
```
╞═══▰════╡ 40%          (Default)
【█▓▒░░░░░░░】 40%        (Gradient)
●●●●○○○○○○ 40%          (Dots)
(i) Warning!            (Alert)
```

---

## 📚 API Documentation

### **AbilityManager**

```java
// Get the manager
AbilityManager manager = AbilityManager.getOrThrow();

// Register an ability
manager.registerAbility(ShiftAbility ability);

// Unregister an ability
manager.unregisterAbility(String abilityId);

// Get a player's active ability
Optional<ShiftAbility> active = manager.getActiveAbility(Player player);

// Check if player has any ability active
boolean hasAbility = manager.hasActiveAbility(Player player);

// Deactivate all abilities for a player
manager.deactivateAll(Player player);
```

### **ShiftActivation (Low-Level API)**

```java
// Get the service
ShiftActivation shift = ShiftActivation.getOrThrow();

// Set shift count required
shift.setMaxProgress(10);

// Register materials for shift activation
shift.register(handler, Material.NETHERITE_PICKAXE, Material.DIAMOND_SWORD);

// Check if material is registered
boolean registered = shift.isRegistered(Material.NETHERITE_PICKAXE);
```

### **Events**

```java
// Listen to shift progress
@EventHandler
public void onShiftProgress(ShiftProgressEvent event) {
    Player player = event.getPlayer();
    int progress = event.getPercentage();
    Component message = event.getActionBarMessage();
    
    // Cancel to prevent default behavior
    event.setCancelled(true);
}

// Listen to shift activation
@EventHandler
public void onShiftActivation(ShiftActivationEvent event) {
    Player player = event.getPlayer();
    ItemStack item = event.getItem();
    
    // Your custom logic
}
```

---

## 🎨 Action Bar Styles

ShiroCore provides **three beautiful loading bar styles**:

### **1. Default Style**
```java
ActionbarMessage.getLoadingBar(current, max)
```
`╞═══▰════╡ 40%`

**Features:**
- Smooth transition character (▰)
- Green filled, black empty
- Clean, modern look

### **2. Gradient Style**
```java
ActionbarMessage.getStylizedLoadingBar(current, max, filledColor, emptyColor)
```
`【█▓▒░░░░░░░】 40%`

**Features:**
- Multi-level gradient effect (█ ▓ ▒ ░)
- Custom color support
- Asian-style brackets

### **3. Minimalist Dots**
```java
ActionbarMessage.getDotLoadingBar(current, max)
```
`●●●●○○○○○○ 40%`

**Features:**
- Clean, simple design
- Filled/empty dot indicators
- Minimal visual noise

---

## 🏗️ Architecture

```
ShiroCore
├── api/
│   ├── ability/
│   │   ├── ShiftAbility.java          (Abstract base class)
│   │   └── AbilityManager.java        (Service API)
│   ├── event/
│   │   ├── ShiftProgressEvent.java
│   │   ├── ShiftActivationEvent.java
│   │   └── ShiftEvent.java
│   ├── text/
│   │   └── ActionbarMessage.java
│   └── ShiftActivation.java
└── internal/
    ├── ability/
    │   ├── AbilityManagerImpl.java    (Implementation)
    │   └── AbilityListener.java       (Event handling)
    ├── handler/
    │   └── ShiftActivationHandler.java
    └── system/
        └── ShiftActivationService.java
```

---

## 💡 Use Cases

### **What You Can Build**

- 🔨 **Super Tools** - Pickaxes with 3x3 mining, shovels with area digging
- ⚔️ **Combat Abilities** - Swords with special attacks, bows with enhanced arrows
- 🏃 **Movement Skills** - Speed boots, jump abilities, teleportation
- 🛡️ **Defensive Powers** - Shields with absorption, armor with resistance
- 🎯 **Custom Mechanics** - Anything that activates via shift-spam!

### **Real World Example: SuperPickaxe**

Check out the [SuperPickaxe-Prototype](https://github.com/yourusername/SuperPickaxe-Prototype) plugin that uses ShiroCore:

- 🔨 3x3 block breaking
- 🎨 Beautiful activation effects
- ⚡ Only ~80 lines of code (thanks to ShiroCore!)

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

---

## 🔗 Links

- **GitHub**: [tantaihaha4487/ShiroVerse](https://github.com/tantaihaha4487/ShiroVerse)
- **JitPack**: [ShiroCore on JitPack](https://jitpack.io/#tantaihaha4487/ShiroVerse)
- **Issues**: [Report a Bug](https://github.com/tantaihaha4487/ShiroVerse/issues)

---

## ✨ Credits

Created by **tantaihaha4487** for the ShiroVerse project.

Special thanks to all contributors and the Minecraft development community!

---

<p align="center">
  <sub>Built with ❤️ for the Minecraft community</sub>
</p>