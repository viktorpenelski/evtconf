# EvtConf

Most pre-made solutions for conference apps are either too expensive, too hard to use or fail to deliver
a good user experience.

EvtConf aims to solve those issues by - being open source, allowing the organizer to set it using only
a google spreadsheet and focusing on a minimalistic design, providing both clarity and a good visual experience for the user.


__NOTE:__ this is still an early WIP prototype. Many things are likely to change during the dev process.
The MVP will focus on simply displaying a list of events/lectures, taken from a google spreadsheet and will target Android only.

Since the project is written in kotlin, porting to iOS (if I get my hands on an apple device)
and/or having a backend should there be a need, will not be part of the initial release, 
but will definitely be planned for in the future.

__NOTE2:__ the app is currently branded for a particular event happening on 24th and 25th of March 2018.





### TODO:

* bugfixes from google play console crashes
* add kotlin coroutines (https://github.com/Kotlin/kotlinx.coroutines/blob/master/ui/coroutines-guide-ui.md)
* refactor downstream communication to be on separate layer
* add google analytics sdk

### Improvements / Bugs:

* add some tests ~_~
* replace restTemplate with OkHttp (optional ~250kb smaller apk)
* implement persistant caches, so that they can be resoted if app is closed long enough
to get garbage collected, but before the 30 minute refresh period that I am aiming for.

### Ideas / open questions:

* Highlight current event (time based)
  * optional - show info about bracket on home screen?
* Figure out branding - dynamic? custom per release?
* scavenge hunt (QR codes)
