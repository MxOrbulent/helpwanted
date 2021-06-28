# Helpwanted (FABRIC ONLY)
A mod that allows you to attract villagers to come work for you.
This mod can be used on client for singleplayer or client and server for multiplayer.
If you absolutely must have a non-client version (aka server only), something you will often see
mentioned in this readme is: Do it yourself.
Hint: A mod becomes client neccesary when it messes with graphics and adds or removes graphical stuff such as blocks, monsters rendering, custom animations and more.
I'd suggest deleting the custom blocks etc from a fork of your own, and then using a vanilla sign and allowing players to put something like

Villager
Help
Wanted!
As text on the sign, which would make the server register the sign as a "help wanted" sign when the player finishes writing the text on the sign.

Credit:

- Original creator: captaintenneal81 https://www.curseforge.com/members/captaintenneal81/followers 
- Decompilation work and remapping: MxOrbulent (www.mxorbulent.space) https://github.com/MxOrbulent

### Dependencies
Fabric API. https://www.curseforge.com/minecraft/mc-mods/fabric-api
Fabric API needs to be installed on both client and server.

# FAQ:

### Why not simply ask the original creator for source?
I wanted to remap this myself for the experience. And it succeeded, it works perfectly.
I'd also suggest to others that if you release something as Public Domain, that you also
release the source. If you use git, it takes basically no time at all to do it.
You are of course under no obligation to do so. But it would be helpfull.


### Don't you need to ask for permission?
The original creator set his release as MIT, this means that doing basically anything with the compiled binaries are
100% fair game. So no, I don't need his permission at all. The only thing I need to provide is a MIT license.

### Why not also decompile and remap for Forge?
I have no interest in Forge. You have the source now, do it yourself. I spent some 8 hours studying
and remapping. If you can't be arsed to atleast try, you should not be running a minecraft server.

### Will you maintain this?
No guarantees. If my own server project succeeds then yes I will maintain it.
If the mod author releases his source code proper, then I will not maintain this repo.
I would simply fork his then and apply the changes I want. 

### What version is this for currently and will you backport?
1.17 and no I will not backport this. You have the source, "do it yourself™".

If I decide to maintain this I will only support 1.17.x and onwards.
Any changes the original mod author makes will be investigated and if neccesary, merged and remapped.
However considering the mod feels pretty much complete, I doubt any major changes will be made.

### Finally, any other reason why you wanted the source and why you did this?
I want to add config values so one can nerf/buff the mod. I think the current settings are to overpowered 
and no reqruitment limit makes trying to build a proper village for breeding pointless.

Here are the default values.
- LIMIT_NUMBER_OF_VISITING_PIGLINS_AROUND_SIGN:false
- SEARCH_RADIUS_AROUND_SIGN:32
- DEBUGMOD:true
- DEBUGCONFIG:true
- LIMIT_NUMBER_OF_REQRUITABLE_VILLAGERS_AROUND_SIGN:true
- DIAMOND_EXTRA_VILLAGER_CHANCE:20
- CHANCE_OF_ARRIVAL:50
- PIGLIN_LIMIT:4
- VILLAGER_LIMIT:4
- EMERALD_BLOCK:false

(The order the config appears in this list on the README is all fucked up. Thanks Jackson!!! Ur the best <3).
CHANCE_OF_ARRIVAL has a bounding of 1600.
So basically, setting it to 800 means a 50% chance, 400 means 25% chance, 200 means 10% chance, 100 means 5% chance, 50
means 2,5% chance, 10 means 1% chance and so on.
DIAMOND_EXTRA_VILLAGER_CHANCE does not suffer from this problem, it has a bounding of 200, allowing 0,5% as the lowest
chance.
___
I'm also in no mood to wait for updates etc, I like to update mods and make forks for my own server project whenever possible.

Basically, my changes allows server owners to limit the amount of villagers and piglins that a sign
can attract in a area. Whenever the code rolls for a spawn, it searches the area around the sign and if
there's equal to or more than the limit, the spawn fails. This forces players to
actually improve their villages to make villagers breed. 

However it appears like the piglin functionality got lost when I decompiled and remapped the project. 
It will be implemented. (TODO)

You can also disable emerald blocks. It gets ridiculously easy to get emeralds after trading for a while
without nerfing villager trades with some other mod.
So you can outright disable the emerald block from counting as a attractive block for villagers. A alternative is provided where you can
set it to need 2x blocks (TODO, for now you can only disable or enable emerald blocks), which will make it require twice as many emerald blocks.

Finally, you can make it more expensive to recruit villagers by setting SCALE_EXPENSIVITY to true. (TODO)
If you do this, every villager reqruited will require another block to reqruit another villagers.
The scaling is simple. After you have 2 villagers, it will add 1 block as a requirement. 

CHANCE_OF_EXTRA_VILLAGER_DIAMOND_BLOCK allows you to have a small chance to get 2 villagers instead of just 1.
### I have changes I want to make
Fork the repo. If your idea is good you can attempt to make a pull request. I will accept them if they make sense.
But I ask that you please wait until I'm done implementing the config so that we have a proper baseline to move on from onwards.
and also that I have started the 1.18 branch. Basically the 1.18 branch will just fetch from master branch until
minecraft is updated to 1.18 and fabric is ready, then the 1.18 branch will have any mappings migrated.
Thankfully this mod does not really mess around with anything significant at all, even the original mod does not.
So updating it can be done almost to the moment when fabric updates.
