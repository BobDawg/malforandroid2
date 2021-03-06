# MALForAndroid 2

Rewrite of the original MALForAndroid 
 
## Summary

Simple app for managing your anime list via a android 4.0+ device. Earlier device will not be supported by this app.

## Licence

MALForAndroid 2 is licensed under:   
Apache License Version 2.0, January 2004

## Special Thanks
Made possible by the fine work of Cheah Chu Yeow
and his [unofficial MAL API][api].

If you like throw him a bone @ [github][cho_github] or [his blog][cho_blog]

## Contributing

If you want to contribute to MALForAndroid 2, just fork and submit a pull request!

Current contributors can be found under the [Github contributors page][contrib]

## Changelog

v1.4.1

*	fixed spacing on refresh button (thanks BobDawg)
*	fixed manga sync issue
*	moved delete item option into overflow menu (it's to easy to accidentally delete an item)

v1.4.0

*	Attaching to new server

v1.3.0

*	changing ViewPagerAdapter to allow for dynamic page configuration
*	fix for IllegalStateException on backgrounding app before login fragment is opened
*	guard for npe in BaseRecordAdapter 
*	changed the way data refresh is handled so that more changes can be caught 

v1.2.1

*	fix overly zealous completion dialog from popping up when total items is zero.
*	fix update to manga volume count moving manga to reading state.

v1.2.0

*	reordered items on action bar
*	Added action bar item that open MAL site for current anime or manga
*	Animated transitions between item detail fragments
*	When watched or read count indicates completion, app asks if it's ok to move item into completed list
*	adding an indeterminate progress bar to action bar when app is syncing with server

v1.1.0

*	Adds Manga support
*	Resolves Issue #1 - Changes number picker to take keyboard input.

[api]: http://mal-api.com/  
[contrib]: https://github.com/riotopsys/malforandroid2/contributors
[cho_github]: http://github.com/chuyeow/myanimelist-api
[cho_blog]: http://blog.codefront.net/
