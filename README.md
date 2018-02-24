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

### TODO:
* Call google spreadsheet
  * parse result into usable information

* Dynamically create list from all information taken from above
  * (MVP: listview of text only, nice to have: cached picture + text, linkable to detailed info)
  
* Release to PlayStore
* Figure out QR code installation of the app