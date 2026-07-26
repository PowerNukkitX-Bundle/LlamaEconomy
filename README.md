# LlamaEconomy

LlamaEconomy is a lightweight economy plugin for
[PowerNukkitX](https://github.com/PowerNukkitX/PowerNukkitX). It provides the
essential commands for managing player balances, supports multiple storage
backends, and exposes an API and events for other plugins.

## Features

- Player accounts are created automatically on first join
- Configurable starting balance and currency symbol
- YAML, MySQL, and MongoDB storage
- Balance, payment, administration, and leaderboard commands
- Configurable command names, aliases, descriptions, usage messages, and
  permissions
- Customizable messages
- Optional PlaceholderAPI integration
- Public Java API and economy events for plugin developers

## Requirements

- A PowerNukkitX server compatible with API `3.0.0`
- JDK 21 for building and running the plugin
- [PlaceholderAPI](https://powernukkitx.org/plugins/PowerNukkitX-Bundle.PlaceholderAPI) (optional)
- DBDrivers installed on the server when using MySQL

## Installation

1. Download a LlamaEconomy JAR or [build it from source](#building-from-source).
2. Place the JAR in your server's `plugins` directory.
3. Start the server once to generate the configuration files.
4. Edit `plugins/LlamaEconomy/config.yml` if required.
5. Restart the server after changing the main configuration.

The default YAML provider works without an external database.

## Configuration

The generated `config.yml` contains the storage, economy, and command settings:

```yaml
Provider: "yaml"

DefaultMoney: 1000.0
MonetaryUnit: "$"

MongoDB:
  Uri: "mongodb://localhost:27017"
  Database: "llamaeconomy"

MySql:
  Host: "127.0.0.1"
  Port: "3306"
  User: "root"
  Password: "change-me"
  Database: "llamaeconomy"
```

Set `Provider` to `yaml`, `mysql`, or `mongodb`. Database credentials are only
used by the selected provider. Do not commit real database credentials to
source control.

Command properties can be changed under `Commands`. Keep each `Parameters`
entry unchanged because it defines the parameters expected by the plugin.
Player-facing text and formatting can be customized in `messages.yml`.

`/lecoreload` reloads the language messages. Restart the server to apply
provider, economy, or command configuration changes.

## Commands and permissions

| Command | Description | Permission |
| --- | --- | --- |
| `/money [player]` | Show your own or another player's balance | None |
| `/pay <player> <amount>` | Transfer money to another player | None |
| `/topmoney [page]` | Show the richest players | None |
| `/addmoney <player> <amount>` | Add money to a player's balance | `llamaeconomy.addmoney` |
| `/reducemoney <player> <amount>` | Remove money from a player's balance | `llamaeconomy.reducemoney` |
| `/setmoney <player> <amount>` | Set a player's balance | `llamaeconomy.setmoney` |
| `/lecoreload` | Reload `messages.yml` | `llamaeconomy.reload` |

Names, aliases, and permissions shown above are the defaults and can be
changed in `config.yml`.



## PlaceholderAPI

When PlaceholderAPI is installed, LlamaEconomy registers the `money`
placeholder. Use it through PlaceholderAPI's normal placeholder syntax:

```text
%money%
```

The placeholder returns the player's raw balance.

## Developer API

Add the PowerNukkitX repository and LlamaEconomy as a provided/compile-only
dependency. The plugin must still be installed separately on the server.

### Maven

```xml
<repositories>
    <repository>
        <id>powernukkitx-releases</id>
        <url>https://repo.powernukkitx.org/releases</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>net.lldv.llamaeconomy</groupId>
        <artifactId>LlamaEconomy</artifactId>
        <version>1.0.2</version>
        <scope>provided</scope>
    </dependency>
</dependencies>
```

### Gradle (Groovy DSL)

```groovy
repositories {
    maven {
        url = uri("https://repo.powernukkitx.org/releases")
    }
}

dependencies {
    compileOnly "net.lldv.llamaeconomy:LlamaEconomy:1.0.2"
}
```

### Gradle (Kotlin DSL)

```kotlin
repositories {
    maven("https://repo.powernukkitx.org/releases")
}

dependencies {
    compileOnly("net.lldv.llamaeconomy:LlamaEconomy:1.0.2")
}
```

Add LlamaEconomy as a dependency or soft dependency in your plugin's
`plugin.yml`:

```yaml
depend:
  - LlamaEconomy
```

### Usage

Get the API instance from the plugin's main class:

```java
import net.lldv.llamaeconomy.LlamaEconomy;
import net.lldv.llamaeconomy.components.api.API;

API economy = LlamaEconomy.getAPI();

double balance = economy.getMoney(player);
economy.addMoney(player, 250.0);
economy.reduceMoney(player, 50.0);
economy.setMoney(player, 1000.0);
```

Most account and balance methods accept a `Player`, `UUID`, or player name.
Additional methods provide access to the default balance, monetary unit,
number formatter, and all stored balances.

Balance changes made through the API fire:

- `AddMoneyEvent`
- `ReduceMoneyEvent`
- `SetMoneyEvent`

The corresponding commands also fire `PlayerAddMoneyEvent`,
`PlayerReduceMoneyEvent`, `PlayerSetMoneyEvent`, and `PlayerPayMoneyEvent` when
executed by a player.

## Building from source

Clone the repository and build it with Maven:

```bash
git clone https://github.com/PowerNukkitX-Bundle/LlamaEconomy.git
cd LlamaEconomy
mvn clean package
```

The shaded plugin JAR is generated at:

```text
target/LlamaEconomy.jar
```

## License

LlamaEconomy is licensed under the
[GNU General Public License v3.0](LICENSE).
