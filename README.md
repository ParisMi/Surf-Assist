### Surf Assist

Surf Assist is a lightweight Java companion tool for TF2 Surfers on servers using the ReSurfed surf timer plugin.
It uses an extensible log parsing system to record and anaylze events occuring ingame and can update records even when hl2.exe
is not running. Perhaps most importantly, it registers these events without needing to directly reading game memory, avoiding the risk of a VAC ban.

It (will) track your personal records, world records, and map comments for every map you visit with it running or can be ran
after playing to update your logs to the latest session.

### Features
**Not fully functional in current release. Here is a list of currently implemented functions:**
Track Surf Times in stages, bonuses, linear maps, and overall for your own play.
Record world records.
Write short comments on any map you've played.
Look up your map times and world records while offline.
Read and update your map times from your gameplay session while you are playing or after it.


### Bugs / To Do
Will ping surf servers and track players and maps played, with notifications.
Timer -- tracks and will notify you when a map change occurs (because some maps just suck!)
**Currently does not allow username changes.**


### Dependencies

Surf Assist requires JavaFX (jfxrt.jar) and gson (built against v2.2.4).
