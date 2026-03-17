---
layout: page
title: User Guide
---

AddressBook Level 3 (AB3) is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI). If you can type fast, AB3 can get your contact management tasks done faster than traditional GUI apps.

* Table of Contents
  * [Quick start](#quick-start)
  * [Features](#features)
  * [FAQ](#faq)
  * [Known issues](#known-issues)
  * [Command summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.<br>
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/se-edu/addressbook-level3/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar addressbook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John-Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John-Doe t/friend` or as `n/John-Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
A person can have any number of tags (including 0)
</div>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Adding an equipment : `add-e`

Adds a new piece of physical equipment into the inventory so it can be tracked and loaned.

Format: `add-e n/NAME c/CATEGORY s/STATUS`

Acceptable values:
* Name: Alphanumeric characters and spaces. Cannot be empty. Multiple spaces between words are collapsed into a single space (e.g., John   Doe becomes John Doe). Case-sensitive for display.
* Category: Single word alphabetic. Cannot be empty.
* Status: Available, Booked, Maintenance, Damaged. Cannot be empty. Case-insensitive (e.g. available is accepted).
* Parameters can be in any order.
  e.g. if the command specifies n/NAME c/CATEGORY s/STATUS, c/CATEGORY n/NAME s/STATUS is also acceptable.

Duplicate handling:
* Case-insensitive for duplicate checking. If you have multiple equipment of the same name and category, it should be named with a number as “Basketball-1”, “Basketball-2”, etc.

Examples:
* `add-e n/Wilson-Evolution-Basketball c/Basketball s/Available`.
* `add-e n/Yonex-Astrox c/Badminton s/Booked`.

Outputs:
* Success
![addEquipmentSuccess.png](images/addEquipmentSuccess.png)
* Failure
![addEquipmentFail.png](images/addEquipmentFail.png)

Possible errors:
* Invalid command such as missing n/, c/, and s/ prefix

### View equipment inventory list : `list-e`

Displays a complete list of all equipment currently stored in the inventory, including their status and category.

Format: `list-e`

Acceptable values:
* Only accepts `list-e`.

Duplicate handling:
* Not applicable for a view command.

Examples:
* `list-e`.

Outputs:
* Success
  ![viewEquipmentListSuccess.png](images/viewEquipmentListSuccess.png)
* Failure <br>
  Inventory list has not been created. Please proceed to add equipment first.

Possible errors:
* Inventory list has not been created.
* Any extra input after `list-e`, (e.g. `list-e Basketball`, `list-e 123` etc.) will be invalid command.

### Delete equipment from inventory list : `delete-e`

Deletes equipment from the inventory.

Format: `delete-e INDEX`

Acceptable values:
* Index: Positive integer corresponding to the current displayed list from `list-e`.

Duplicate handling:
* Not applicable for a delete command.

Examples:
* `delete-e 7`.

Outputs:
* Success
  ![deleteEquipmentSuccess.png](images/deleteEquipmentSuccess.png)
* Failure
  ![deleteEquipmentFail.png](images/deleteEquipmentFail.png)

Possible errors:
* Attempt to delete an equipment that is out of the inventory index list.
* Attempt to delete an equipment that is having a ‘Booked’ status.

### Adding a room : `add-r`

Adds a new facility or venue into the system.

Format: `add-r n/NAME l/LOCATION s/STATUS`

Acceptable values:
* Name and Category: Alphanumeric characters and spaces. Cannot be empty. Multiple spaces between words are collapsed into a single space (e.g., John   Doe becomes John Doe). Case-sensitive for display.
* Status: Available, Booked, Maintenance. Cannot be empty. Case-insensitive (e.g. available is accepted).
* Parameters can be in any order.
  e.g. if the command specifies n/NAME l/LOCATION s/STATUS, l/LOCATION n/NAME s/STATUS is also acceptable.

Duplicate handling:
* Case-insensitive for duplicate checking. Only one physical "MPSH-1" exists, therefore duplicate names would cause booking conflicts and error.

Examples:
* `add-r n/MPSH-2 l/Sports-Centre s/Available`.
* `add-r n/Sports-Hall-2 l/University-Town s/Booked`.

Outputs:
* Success
  ![addRoomSuccess.png](images/addRoomSuccess.png)
* Failure
  ![addRoomFail.png](images/addRoomFail.png)

Possible errors:
* Invalid command such as missing n/, l/, and s/ prefix

### View room list : `list-r`

Displays a complete list of all facilities and rooms managed by the system, showing their location and status.

Format: `list-r`

Acceptable values:
* Only accepts `list-r`.

Duplicate handling:
* Not applicable for a view command.

Examples:
* `list-r`.

Outputs:
* Success
  ![viewRoomListSuccess.png](images/viewRoomListSuccess.png)
* Failure <br>
  Room list has not been created. Please proceed to add room first.

Possible errors:
* Room list has not been created.
* Any extra input after `list-r`, (e.g, `list-r Sports-Hall`, `list-r YIH` etc.) will be invalid command.

### Delete a room : `delete-r`

Deletes a room equipment from the room list.

Format: `delete-r INDEX`

Acceptable values:
* Index: Positive integer corresponding to the current displayed list.

Duplicate handling:
* Not applicable for a delete command.

Examples:
* `delete-r 3`.

Outputs:
* Success
  ![deleteRoomSucces.png](images/deleteRoomSucces.png)
* Failure
  ![deleteRoomFail.png](images/deleteRoomFail.png)

Possible errors:
* Attempt to delete a room that is out of the room index list.
* Attempt to delete a room that is having a ‘Booked’ status.

### Add a new student profile : `add-s`

Adds a new borrower in the database so they can begin borrowing items

Format: `add-s n/NAME m/MATRIC_NUMBER p/PHONE_NUMBER e/EMAIL​`

Examples:
*  `add-s n/John Doe m/A0123456B p/91234567 e/e0123456@u.nus.edu` Adds a new student with the name `John Doe`, matric number `A0123456B`, phone number `91234567` and email address `e0123456@u.nus.edu`.

Acceptable values:
* Name: Alphabets only, no special characters or numbers
* Matric number: Start with an alphabet followed by 7 digits and end with an alphabet.
* Phone number: 8 digits continuous number
* Email: Valid email format such as `name@domain.com`

Duplicate handling:
* If matric number, phone number or email already exist in the system, the command will be rejected to prevent duplicate identity.

### Check a student's loans : `check-s`

To check the list of equipment or venues loaned to a student.

Format: `check-s MATRIC_NUMBER​`

Examples:
* `check-s A0123456B`

Acceptable values:
* Matric numbers must start with an alphabet followed by 7 digits and end with an alphabet

Duplicate handling:
* The system searches by the unique matric number, so there is no risk of returning the wrong student's data.

Possible errors: 
* No matric number in the system.

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the AddressBook to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

### Reserving a facility/equipment: `reserve`

Reserves a room or equipment for a student at a specified date and time.

Format: `reserve ITEM_OR_ROOM_ID STUDENT_ID f/START_DATE_TIME t/END_DATE_TIME`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
You can reserve facilities such as halls, courts, and multi-purpose rooms in advance to avoid double bookings.
</div>

* Creates a reservation for the specified item or room under the specified student.
* The start and end date/time must be valid and the end date/time must be later than the start date/time.
* The reservation will be rejected if it conflicts with an existing booking for the same item or room.

Duplicate handling:
* Duplicate or overlapping reservations are not allowed.
* If the specified item or room is already reserved for the requested time period, the command will be rejected.

Examples:
* `reserve Hall-2 a1234567a f/2026-03-01 1400 t/2026-03-01 1600`
* `reserve MPSH-1 a1234567a f/2026-03-10 0900 t/2026-03-10 1200`

![reserve command screenshot](images/reserveCommand.png)

Possible errors:
* Invalid `ITEM_OR_ROOM_ID`
* Invalid `STUDENT_ID`
* Invalid date/time format
* End time is earlier than start time
* Reservation conflicts with an existing booking

### Issuing an equipment item: `issue`

Issues an equipment item to a student with a due date and time for return.

Format: `issue ITEM_ID STUDENT_ID d/DUE_DATE_TIME`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Use this command to keep track of borrowed equipment and who is responsible for returning it.
</div>

* Issues the specified equipment item to the specified student.
* The due date/time must be in the future and follow the format `yyyy-MM-dd HHmm`.
* The command will be rejected if the item is already issued to another student.

Duplicate handling:
* If the item is already issued, the system will reject the command.
* The system will show the current holder of the item and its due date/time.

Examples:
* `issue Wilson-Evolution-Basketball-1 A1203763K d/2026-03-05 1700`
* `issue Molten-Volleyball A1206789J d/2026-03-02 1200`

![issue command screenshot](images/issueCommand.png)


Possible errors:
* Invalid `ITEM_ID`
* Invalid `STUDENT_ID`
* Item is already issued
* Invalid due date/time format
* Due date/time is in the past

### Creating an alias: `alias`

Creates a short alias for an equipment item or room.

Format: `alias ITEM_OR_ROOM_ID ALIAS_NAME`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Aliases are useful for long item or room IDs, especially during busy periods when faster command entry is helpful.
</div>

* Assigns a short alias to the specified item or room.
* `ALIAS_NAME` should be a short string containing letters, numbers, or underscores.
* Each alias must be unique across the system.

Duplicate handling:
* Duplicate aliases are not allowed.
* If the alias is already in use, the command will be rejected.

Examples:
* `alias Wilson-Evolution-Basketball-1 b1`
* `alias MPSH-1 hall1`

![alias command screenshot](images/aliasCommand.png)


Possible errors:
* Invalid `ITEM_OR_ROOM_ID`
* Alias already exists

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Clear** | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Edit** | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [t/TAG]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`
**Find** | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`
**List** | `list`
**Help** | `help`
