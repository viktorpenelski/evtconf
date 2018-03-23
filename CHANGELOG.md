#### Version 1.3

* added fade in / fade out transitions between activities
* improved quality of map action icon

## Version 1.2

* Added map button to the action bar, showing the venue map.
* external links now start zoomed out in case they do not fit the screen.

## Version 1.1

* Pinch-to-zoom is now allowed for in-app browser.

* Fixed a bug where devices with 12h format would not show day/month in header.
* Fixed a bug where if "show twitch chat" switch is pressed twice fast,
a load bar would keep scrolling indefinitely until switch is clicked again.

---
* Moved spreadsheet URL/s to app.properties file.


## Version 1.0 - branded for GPlay Burgas 2018

* The app has three main tabs:
  * MAIN STAGE
    * Contains a list of all events happening on the main stage of the event.
    * All entries are optionally clickable, linking to outside URLs via in-app browser.
  * TOURNAMENTS
    * Contains a list of all tournaments happening at the event.
    * All entries are optionally clickable, linking to outside URLs via in-app browser.
  * TWITCH CHAT
    * supports in-app view-only chat.
    * can open twitch in either dedicated app (if installed) or mobile version in web browser.

* Implemented swipe up to refresh (for MAIN STAGE and TOURNAMENTS tabs)

---

* if URL is invalid, do not launch WebView from any element
* stop traffic from twitch chat once not in focus
* profile app to make sure it is
    * only downloading pictures ONCE
    * not using /too much/ traffic for the twitch chat
    
    
