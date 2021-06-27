# Helpwanted (FABRIC ONLY)
A mod that allows you to attract villagers to come work for you.
This is a client/server mod, both the client and the server must have the mod installed.

Credit:
Original creator: captaintenneal81 https://www.curseforge.com/members/captaintenneal81/followers
Decompilation work and remapping: MxOrbulent (www.mxorbulent.space) https://github.com/MxOrbulent

### Dependencies
Fabric API. https://www.curseforge.com/minecraft/mc-mods/fabric-api
Cloth Config. https://www.curseforge.com/minecraft/mc-mods/cloth-config

Both need to be installed on both server and client, but client needs permissions to use the config.
If you don't have a permission mod installed, this mod will create it's own form of permissions that you can
add users to by typing /helpwanted addconfigperm NameOfPlayer
You can do /helpwanted removeconfigperm NameOfPlayer
This will add/remove the players UUID.

If you have a permission mod of some kind, the permission to grant access to the config is 
**helpwanted.configperm**

### Why not simply ask the original creator for source?
I wanted to remap this myself for the experience. And it succeeded, it works perfectly.
I'd also suggest to others that if you release something as "CopyLeft" or Public Domain, that you also
release the source. If you use git, it takes basically no time at all to do it.
If you want to be a greedy handrubber and keep the code to yourself, don't use things like a MIT license.

### Don't you need to ask for permission?
The original creator set his release as MIT, this means that doing basically anything with the compiled binaries are
100% fair game. So no, I don't need his permission at all. The only thing I need to provide is a MIT license.

I also want to note that he missed filling in the proper copyrights, or maybe
curseforge did. Either way it's not good because it casts copyright into doubt.
Copyrights are granted by default, that's true. 
But kinda hard to truly say he has the copyright now does it? 
This is why you check your licenses that are generated.


I'm fixing copyright notice as a token of good faith and as thanks for such
a great mod idea by publishing a proper mit license which correctly fills out the copyright part 
of the mit license and points back to the original creator. 
Removing any possible doubt for the license.

### Why not also decompile and remap for Forge?
I have no interest in Forge. You have the source now, do it yourself. I spent some 8 hours studying
and remapping. If you can't be arsed to atleast try, you should not be running a minecraft server.

### Will you maintain this?
No guarantees. If my own server project succeeds then yes I will maintain it.
If the mod author releases his source code proper, then I will not maintain this repo for public use.
I would simply fork his then and apply the changes I want.

### What version is this for currently and will you backport?
1.17 and no I will not backport this. You have the source, do it yourself.
If I decide to maintain this I will only support 1.17.x and onwards.
Any changes the original mod author makes will be investigated and if neccesary, merged and remapped.
However considering the mod feels pretty much complete, I doubt any major changes will be made.

### Finally, any other reason why you wanted the source.
I want to add config values.
- LIMIT_NUMBER_OF_REQRUITABLE_VILLAGERS_AROUND_SIGN (true/false)
- LIMIT_NUMBER_OF_VISITING_PIGLINS_AROUND_SIGN (true/false)
- DISABLE_EMERALD_BLOCK (true/false)
- SCALE_EXPENSIVITY (true/false)
- DISABLE_CHANCE (true/false) **Effectively makes blocks a requirement in order to reqruit**
- SET_CHANCE(0.000-1.000) **This is a double value where 0.005 for example means 0,5% chance, 
and where 1.000 means 100% chance.**
- SEARCH_RADIUS_AROUND_SIGN (0-256)
- VILLAGER_LIMIT (1-100)
- PIGLIN_LIMIT (1-100)

Basically, it allows server owners to limit the amount of villagers and piglins that a sign
can attract in a area. Whenever the code rolls for a spawn, it searches the area around the sign and if
there's equal to or more than the limit, the spawn fails. This forces players to
actually improve their villages to make villagers breed. 

You can also disable emerald blocks. It gets ridiculously easy to get emeralds after trading for a while.
So you can outright disable the emerald block.

Finally, you can make it more expensive to recruit villagers by setting SCALE_EXPENSIVITY to true.
If you do this, every villager reqruited will require another block to be reqruited immediatly.
For example if 4 villagers are near the sign, then you'd need 4 emerald blocks to recruit another directly

### I have changes I want to make
Fork the repo. If your idea is good you can attempt to make a pull request.
