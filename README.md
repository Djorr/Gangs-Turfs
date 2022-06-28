**Note: This plugin is Dutch by default. It isn't possible at the moment to change the language, maybe in the future.**

# Game Informatie:
Gangs & Turfs is de ultieme minecraft plugin die games van GTA toevoegt aan de Minetopia minecraft gamemode. Dat maakt de Minetopia gamemode nog veel leuker!

Dit is een plugin addon die alleen werkt als je MinetopiaSDB hebt geinstalleerd in je server. MinetopiaSDB is gemaakt door MrWouter. Deze plugin is gemaakt door mij.

# Uitleg video:
* https://youtu.be/ivBeJ23UaNQ

Hoe plaats je een NPC waar je gangs kunt aanmaken?
Stap 1: Sta op de plek waar je de npc wilt laten staan.
Stap 2: Gebruik nu het commando /gnpc create gang-1 (moet met gang- beginnen)
Stap 3: Je hebt nu succesvol een gang NPC geplaatst!

Hoe maak je een Turf aan?
Stap 1: Maak een worldguard region met /rg create turf-1 (moet beginnen met turf-)
Stap 2: Sta op een plek in de worldguard region waar je de turf npc wilt laten staan.
Stap 3: Maak een turf aan met het commando /turf create turf-1 (de turf moet dezeflde naam hebben als worldguard)
Stap 4: Je hebt nu succesvol een turf aangemaakt!


# Vereisten:
Voordat je gebruik kunt maken van deze plugin addon moet MinetopiaSDB geinstalleerd zijn op je server. Verder heb je ook de volgende plugins nodig: Vault, Essentials, WorldEdit (FastAsyncWorldEdit mag ook) en WorldGuard.

Je kunt de juiste vereisten plus versies downloaden op de spigotmc pagina: https://www.spigotmc.org/resources/gangs-turfs-minetopiasdb-addon-supports-1-12-1-12-2.102879/

# Mogelijkheden:
* Je kunt gangs aanmaken en verwijderen.
* Je kunt spelers inviten voor je gang en ook kicken uit je gang.
* Je kunt turfs aanmaken en verwijderen.
* Je kunt turfs starten en beëindigen.
* Je kunt alle informatie wijzigen in een config.
* Je kunt alle berichten wijzigen in het "language" bestand.
* Je kunt zelf kiezen of je bepaalde features wilt gebruiken of niet.

# Commands & Permissies:
## Gangs
### Speler command(s):
 * /gang help - Een lijst van gang commands
 * /gang invite <speler> - Een speler uitnodigen voor de gang.
 * /gang promote <speler> - Promoveer een speler in je gang.
 * /gang demote <speler> - Demote een speler in jou gang.
 * /gang join - Join een gang
 * /gang show - Zie jou eigen gang informatie
### Admin command(s):
 * /gang forcejoin | gangturfs.gang.forcejoin
 * /gang forcekick | gangturfs.gang.forcekick
 * /gang forcepromote | gangturfs.gang.forcepromote
 * /gang forcedemote | gangturfs.gang.forcedemote
 * /gang forcerename | gangturfs.gang.forcerename
 * /gang list | gangturfs.gang.list
 * /gang save | gangturfs.gang.save
 * /gang tphere | gangturfs.gang.tphere
 * /gang show <name> - gangturfs.gang.show
## Turfs
### Admin command(s):
 * /turf create | gangturfs.turf.create
 * /turf remove | gangturfs.turf.remove
 * /turf list | gangturfs.turf.list
 * /turf stop | gangturfs.turf.stop
 * /turf resetcooldown | gangturfs.turf.resetcooldown
## Combat
### Speler command(s):
 * /ct check - Bekijken of je in combat zit
### Admin command(s):
 * /ct cancel <speler> | gangturfs.combat.cancelcooldown
## NPC
### Admin command(s):
 * /gnpc create gang-<nummer> | gangturfs.npc.create
 * /gnpc remove gang-<nummer> | gangturfs.npc.remove

# Voorwaarden:
```Op elk moment is het mogelijk dat ik de voorwaarden kan wijzigen, laatste wijzigingen van deze voorwaarden zijn op 26-06-2022.
Gangs & Turfs is gemaakt door Djorr en niet door iemand anders, dus doe je niet voor alsof jij Gangs & Turfs hebt gemaakt.
Voor support join de Support Discord.
