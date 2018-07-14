# Tours

Tour management plugin.<br/>
<i>Allows a 'guide' to create a temporary group that other players can join.<br/>
Guides then have access to teleportation commands that can only be used on members of that group.<br/></i>
<br/>

Current authors: DonoA

Author: Dags

## <b>Permissions</b>
All users with '<b>Tours.user</b>' permission node can:
- '<b>/tour</b>' to find info on tours.
- '<b>/tour help</b>' to find info on Tours commands.
- '<b>/tour join \<tourname\></b>' to join a currently running tour.
- '<b>/tour leave</b>' to leave the currently running tour.
- '<b>/tour request</b>' sends an alert to any Rangers online that use is looking for a tour.
- '<b>/ttp</b>' teleports the user to their tour guide.

Users with the '<b>Tours.ranger</b>' permission node can:
- '<b>/tour start</b>' creates new tour, names it after user, and announces it.
- '<b>/tour stop</b>' stops user's current tour and removes all group members.
- '<b>/tour list</b>' lists all users in the tour group.
- '<b>/tour hat</b>' allows the tour guide to wear their currently held block as a hat.
- '<b>/tourtp \<player\></b>' teleports \<player\> to user. \<player\> must be part of tour group.
- '<b>/ttp \<player\></b>' shortcut for tourtp.
- '<b>/tourtpa</b>' teleports all players in tour group to user.
- '<b>/ttpa</b>' shortcut for tourtpa.<br/>
## <b>Discord</b>

In **config.yml**, set `discord: enabled:` to `true` to enable Discord compatibility.  This requires [DiscordSVR](https://github.com/Scarsz/DiscordSRV/). To select a channel to broadcast messages in, set `discord: 
channel:` to the name of the desired channel. Once enabled, the plugin will send a message to the specified channel whenever a Guide runs the **/tour start** command successfully.

## <b>Contributions</b>

Any contributions should - as best as possible - maintain the existing design paterns, conventions, and styles currently in place in this project.<br/>
Deprecated or NMS code is to be avoided and subject to scrutiny.<br/>

## <b>License</b>

Tours is licensed under the GNU General Public License V3:<br/>
http://www.gnu.org/copyleft/gpl.html<br/>
<br/>
<i>In the spirit of good-faith that this source has been made available, the author requests accreditation for works, in the form of the follow tag, to be appended to any class that contains his/her original code:<br/>
(This request is an optional, additional courtesy)</i><br/>
/**<br/>
 \* @author dags_ <dags@dags.me><br/>
 \*/<br/>
