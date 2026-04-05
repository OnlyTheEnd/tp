---
layout: page
title: User Guide
---

TrackMasterPro (TMP) is a **desktop management solution** tailored for **Facility Managers** who handle high-volume bookings and equipment tracking for CCAs, Clubs, and Halls. While it features a clean Graphical User Interface (GUI), it is optimized for those who prefer the speed of a Command Line Interface (CLI).

During high-pressure periods such as the Inter-Hall Games (IHG), Inter-College Games (ICG), or intensive CCA training seasons, clicking through menus is too slow. If you are a fast typer, TrackMasterPro allows you to manage logistical chaos faster than any traditional mouse-based application.


* Table of Contents
{:toc}
  * [Quick start](#1-quick-start)
  * [Features](#2-features)
  * [Data management](#3-data-management)
  * [FAQ](#4-faq)
  * [Known issues](#5-known-issues)
  * [Command summary](#6-command-summary)

--------------------------------------------------------------------------------------------------------------------

## 1. Quick start

1. TrackMasterPro runs on Java `17`. Check if you already have it installed in your Computer:<br>

   **Windows user:** Open the Start menu, search for `cmd` and open the **Command Prompt** app. Type `java -version` and press Enter. If yous see Java `17`, you're good to go!

   **Mac users:** Open the **Terminal** app. Type `java -version` and press Enter. If yous see Java `17`, you're good to go!

   If Java `17` is not installed:
   * Windows: Guide to download and install Java `17` [here](https://se-education.org/guides/tutorials/javaInstallationWindows.html).

   * Mac: Guide to download and install Java `17` [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

2. Download the latest `trackmasterpro.jar` file [here](https://github.com/AY2526S2-CS2103T-T14-4/tp/releases/).

3. Copy the file to the folder you want to use as the _home folder_ for TrackMasterPro
   (e.g create a new folder called `TrackMasterPro`  on your Desktop).

4. TrackMasterPro is launched from the **terminal**. Here's how to run it: </br>

   **Windows:**
   1. Locate your file: Open File Explorer and go to the folder where `trackmasterpro.jar` is saved.

   2. Open the Terminal: Click on the address bar at the top of the window (where the folder path is shown), type `cmd`, and hit Enter. This opens the Command Prompt directly in that folder.

   3. Launch the App: Type the following command and press **Enter**: `java -jar TrackMasterPro.jar`.

   **Mac:**
   1. Open Terminal: Press `Command + Space`, type **Terminal**, and hit Enter.

   2. Navigate to the folder: Type `cd` followed by a space, then drag the folder containing the `.jar` file from Finder directly into the Terminal window. Hit **Enter**.

   3. Launch the App: Type the following command and press Enter: `java -jar trackmasterpro.jar`

   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `add-s n/John Doe m/A0123456B p/91234567 e/e0123456@u.nus.edu` : Adds a new student with the name `John Doe`, matric number `A0123456B`, phone number `91234567` and email address `e0123456@u.nus.edu`.

   * `add-r` : Adds a new facility or venue into the system.

   * `exit` : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## 2. Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add-s n/NAME`, `NAME` is a parameter which can be used as `add n/John-Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John-Doe t/friend` or as `n/John-Doe`.

* Items with `…` after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</div>

### 2.1 Equipment Management

#### Adding an equipment : `add-e`

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

#### View equipment inventory list : `list-e`

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

#### Delete equipment from inventory list : `delete-e`

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

#### Edit equipment from inventory list : `edit-e`

Edit details for existing equipment from the inventory.

Format: `edit-e INDEX [n/NAME] [c/CATEGORY] [s/STATUS]`

Acceptable values:
* Index: Positive integer corresponding to the current displayed list from `list-e`.
* (With at least one of the fields)
    * Name: Alphanumeric characters and spaces.
    * Category: Single word alphanumeric.
    * Status: Available, Booked, Maintenance, Damaged.

Duplicate handling:
* Case-insensitive for duplicate checking. If you have multiple equipment of the same name and category,
  it should be named with a number as “Basketball-1”, “Basketball-2”, etc.

Examples:
* `edit-e 6 s/Booked`.

Outputs:
* Success
  ![editEquipmentSuccess.png](images/editEquipmentSuccess.png)
* Failure
  ![editEquipmentFail.png](images/editEquipmentFail.png)

Possible errors:
* Attempt to edit an equipment that is not in the inventory.
* Wrong command given that is not of the n/, c/, and s/ prefix.

### 2.2 Facility & Venue Management

#### Adding a room : `add-r`

Adds a new facility or venue into the system.

Format: `add-r n/NAME l/LOCATION s/STATUS`

Acceptable values:
* Name and Location: Alphanumeric characters and spaces. Cannot be empty. Multiple spaces between words are collapsed into a single space (e.g., John   Doe becomes John Doe). Case-sensitive for display.
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

#### View room list : `list-r`

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

#### Delete a room : `delete-r`

Deletes a room equipment from the room list.

Format: `delete-r INDEX`

Acceptable values:
* Index: Positive integer corresponding to the current displayed list.

Duplicate handling:
* Not applicable for a delete command.

Examples:
* `delete-r 8`.

Outputs:
* Success
  ![deleteRoomSucces.png](images/deleteRoomSucces.png)
* Failure
  ![deleteRoomFail.png](images/deleteRoomFail.png)

Possible errors:
* Attempt to delete a room that is out of the room index list.
* Attempt to delete a room that is having a ‘Booked’ status.

#### Edit room from room list : `edit-r`

Edit details for existing room from the room list.

Format: `edit-r INDEX [n/NAME] [c/LOCATION] [s/STATUS]`

Acceptable values:
* Index: Positive integer corresponding to the current displayed list from `list-r`.
* (With at least one of the fields)
  * Name: Alphanumeric characters and spaces.
  * Location: Alphanumeric characters and spaces.
  * Status: Available, Booked, Maintenance.

Duplicate handling:
* Name case-insensitive for duplicate checking.

Examples:
* `edit-r 3 n/Tennis-Court s/Booked`.

Outputs:
* Success
  ![editRoomSuccess.png](images/editRoomSuccess.png)
* Failure
  ![editRoomFail.png](images/editRoomFail.png)

Possible errors:
* Attempt to edit a room that is not in the room list.
* Wrong command given that is not of the n/, l/, and s/ prefix.

### 2.3 Borrower Management

#### Add a new student profile : `add-s`

Adds a new student in the database so they can begin borrowing equipment or booking room/facility.

Format: `add-s n/NAME m/MATRIC_NUMBER p/PHONE_NUMBER e/EMAIL`

Examples:
*  `add-s n/John Doe m/A0123456B p/91234567 e/e0123456@u.nus.edu`
*  Adds a new student with the name `John Doe`, matric number `A0123456B`, phone number `91234567` and email address `e0123456@u.nus.edu`.

**Acceptable values:**
* **Name:** Alphabets and internal spaces only (e.g., `John Lim`). No special characters or numbers (e.g., `-`, `.`, `*`). The system trims any spaces at the very beginning or end of a name.
* **Matric Number:** Must be exactly 9 characters long. It starts with a letter (usually 'A'), followed by 7 digits, and ends with a check letter. (e.g., `A0123456B`). Case insensitive.
* **Phone Number:** 8-digit mobile number (e.g `81234567`).
* **Email:** Valid email format (e.g., `e0123456@u.nus.edu`). Case insensitive.

Outputs:
* Success
  ![AddStudentSuccess.png](images/AddStudentSuccess.png)
* Failure
  ![AddStudentFailure.png](images/AddStudentFailure.png)

Duplicate handling:
* To ensure data integrity, each Student must have a unique Matric Number, Phone Number, and Email. If any of these are already registered to another student, the command will fail.
![AddStudentDuplicate.png](images/AddStudentDuplicate.png)

Possible errors:
* Hyphens `(-)`, periods `(.)`, and apostrophes `(')`, numbers `(1)` in name will cause an error

#### Check a student's loans : `check-s`

To check the list of equipment or venues loaned to a student.

Format: `check-s MATRIC_NUMBER`

Examples:
* `check-s A0123456B`

Acceptable values:
* Matric numbers must start with an alphabet followed by 7 digits and end with an alphabet

Outputs:
* Success
  ![CheckStudentLoanSuccess.png](images/CheckStudentLoanSuccess.png)
* Failure
  ![CheckStudentLoanFailure.png](images/CheckStudentLoanFailure.png)

Duplicate handling:
* The system searches by the unique matric number, so there is no risk of returning the wrong student's data.

Possible errors:
* No matric number in the system.

#### Display all students : `list-s`

To display a list of all registered students in the system.

Format: `list-s`

Acceptable values:
* Only accepts `list-s`.

Duplicate handling:
* Not applicable for a view command.

Examples:
* `list-e`.

Outputs:
* Success
![ListStudentsSuccess.png](images/ListStudentsSuccess.png)

Possible errors:
* Any extra input after `list-s`, (e.g. `list-s 1`, `list-e a` etc.) will be invalid command.

#### Delete a student's profile : `delete-s`

To permanently delete a student’s record from the system database.

Format: `delete-s MATRIC_NUMBER`

Examples:
* `delete-s A0123456B`

Success:
![DeleteStudentSuccess.png](images/DeleteStudentSuccess.png)

Failure:
![DeleteCommandFailure.png](images/DeleteCommandFailure.png)


Acceptable values:
* Matric number: A 9-character identifier. Must start with an alphabet (usually 'A'), followed by 7 digits, and end with an alphabet (e.g., A1234567X).

* Note: The command is case-insensitive (a1234567x is treated the same as A1234567X).

Possible errors:
* Student Not Found: The matric number entered does not exist in the current database.

* Active Loans: Deletion is blocked if the student currently has equipment that has not been returned.

* Active Reservations: Deletion is blocked if the student has upcoming room or facility bookings.

<div markdown="span" class="alert alert-primary">:bulb: **Important:**
A student profile **cannot be deleted** if there are outstanding records.
Please ensure all borrowed items are returned and all upcoming reservations are cancelled before attempting to remove the student.
</div>

#### Edit student's details : `edit-s`

Edits an existing student's details in the address book.

Format: `edit-s INDEX [n/NAME] [p/PHONE] [e/EMAIL]`

Acceptable values:
* INDEX: Must be a positive integer `(1, 2, 3...)` as shown in the current displayed list.
* Fields: At least one field must be provided.
* (With at least one of the fields)
    * Name: Alphabets and spaces only. No special characters or numbers.
    * Matric Number: 9 characters, starting and ending with a letter (e.g., `A1234567X`). Case-insensitive.
    * Phone: 8-digit continuous Singaporean mobile number.
    * Email: Valid email format (e.g., `e0123456@u.nus.edu`).
* The order does not matter (e.g., `p/` can come before `n/`).
* You **cannot** edit any details of a student if they currently have an active equipment loan or a facility reservation.

Examples:
* `edit-s 2 n/Tom p/91234561 e/e1234567@u.nus.edu`.

Possible errors:
* Current student still has active loans or reservations linked to their current ID.
* Index provided is invalid.
* A student with the edited information already exists.

### 2.4 Loans & Reservations

#### Reserving a facility/equipment: `reserve`

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

#### Cancel a reservation: `cancel`

Cancels an existing reservation.

**Format**
`cancel ITEM_OR_ROOM_ID STUDENT_ID f/START_DATE_TIME`

**Date/time format**
`yyyy-MM-dd HHmm`

**Example**
`cancel Hall-2 a1234567a f/2099-03-15 0900`

**Success**

Reservation cancelled:
Reserved HALL-2 by Student a1234567a from 2099-03-15 0900 to 2099-03-15 1100

**Failure**
`Error:
Wilson-Evolution-Basketball-1 is not currently issued.`

#### Issuing an equipment item: `issue`

Issues an equipment item to a student with a due date and time for return.

Format: `issue ITEM_ID STUDENT_ID DUE_DATE_TIME`

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
* `issue Wilson-Evolution-Basketball-1 A1203763K 2026-03-05 1700`
* `issue Molten-Volleyball A1206789J 2026-03-02 1200`

![issue command screenshot](images/issueCommand.png)


Possible errors:
* Invalid `ITEM_ID`
* Invalid `STUDENT_ID`
* Item is already issued
* Invalid due date/time format
* Due date/time is in the past

#### Return an equipment: `return`

Returns an issued equipment item back to the inventory.

**Format**
`return ITEM_ID`

**Example**
`return Wilson-Evolution-Basketball-1`

**Success**
`WILSON-EVOLUTION-BASKETBALL-1 returned successfully from a1234567a`

**Failure**
- item is not currently issued
- invalid command format
`Error:
No matching reservation found for Hall-2 by a1234567a from 2099-03-15 0900 to 2099-03-15 1100`

**Notes**
- aliases are supported, so if `b1` is an alias for `Wilson-Evolution-Basketball-1`, then `return b1` also works


#### Creating an alias: `alias`

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

* Possible errors:
* Invalid `ITEM_OR_ROOM_ID`
* Alias already exists


### Return an equipment: `return`

Returns an issued equipment item back to the inventory.

**Format**
`return ITEM_ID`

**Example**
`return Wilson-Evolution-Basketball-1`

**Success**
`WILSON-EVOLUTION-BASKETBALL-1 returned successfully from a1234567a`

**Failure**
- item is not currently issued
- invalid command format

**Notes**
- aliases are supported, so if `b1` is an alias for `Wilson-Evolution-Basketball-1`, then `return b1` also works

### Cancel a reservation: `cancel`

Cancels an existing reservation.

**Format**
`cancel ITEM_OR_ROOM_ID STUDENT_ID f/START_DATE_TIME`

**Date/time format**
`yyyy-MM-dd HHmm`

**Example**
`cancel Hall-2 a1234567a f/2099-03-15 0900`

**Success**

Reservation cancelled:
Reserved HALL-2 by Student a1234567a from 2099-03-15 0900 to 2099-03-15 1100


### 2.5 Tag & Filter:

#### Tagging an item or room: `tag`

Tags an equipment item or room with a label for categorisation.

Format: `tag c/EQUIPMENT_NAME t/TAG` or `tag l/ROOM_NAME t/TAG`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Tags are useful for categorising equipment or rooms, such as marking items as spoilt or rooms under renovation.
</div>

<div markdown="span" class="alert alert-primary">:bulb: **Tip for IHG:**
Tag equipment as `t/IHG_RESERVED` during competition weeks to quickly filter items that should not be loaned out for casual use.
</div>

* Assigns a tag to the specified equipment item or room.
* `TAG` should be a short string containing letters, numbers, or underscores. It is not case-sensitive.
* The system will detect and warn against duplicate tags.

Duplicate handling:
* Duplicate tags on the same equipment or room are not allowed.
* If the tag already exists on the item or room, the command will be rejected.

Examples:
* `tag c/Wilson-Evolution-Basketball-1 t/spoilt`
* `tag l/MPSH-1 t/renovation`

![tag command screenshot](images/TagSuccess.png)

Possible errors:
* Invalid equipment or room ID
* Tag already exists on the specified equipment or room
* Missing command indicators, e.g. missing `t/`

---

#### Removing a tag from an equipment or room: `untag`

Removes an existing tag from an equipment item or room.

Format: `untag c/EQUIPMENT_NAME t/TAG` or `untag l/ROOM_NAME t/TAG`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Use this command to remove outdated or incorrect tags from equipment or rooms.
</div>

* Removes the specified tag from the specified equipment item or room.
* `TAG` should be a short string containing letters, numbers, or underscores. It is not case-sensitive.
* The command will be rejected if the specified tag does not exist on the item or room.

Duplicate handling:
* Not applicable.

Examples:
* `untag c/Wilson-Evolution-Basketball-1 t/spoilt`
* `untag l/MPSH-1 t/renovation`

![untag command screenshot](images/UntagSuccess.png)

Possible errors:
* Invalid equipment or room ID
* Tag does not exist on the specified equipment or room
* Missing command indicators, e.g. missing `t/`

---

#### Filtering by tag: `filter`

Filters equipment items or rooms by a specified tag.

Format: `filter c/ t/TAG` or `filter l/ t/TAG`

<div markdown="span" class="alert alert-primary">:bulb: **Tip:**
Use this command to quickly find all equipment or rooms associated with a particular tag, such as all items marked as spoilt.
</div>

* Displays all equipment items or rooms that have the specified tag.
* `TAG` should be a string containing letters, numbers, or underscores. It is not case-sensitive.

Duplicate handling:
* Not applicable.

Examples:
* `filter c/ t/spoilt`
* `filter l/ t/renovation`

![filter command screenshot](images/FilterSuccess.png)

Possible errors:
* No equipment or rooms found with the specified tag
* Missing command indicators, e.g. missing `t/`

### 2.6 System Utilities

#### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

#### Viewing help : `help`

Shows a message explaining how to access the help page.

![help message](images/helpMessage.png)

Format: `help`

#### Exiting the program : `exit`

Exits the program.

Format: `exit`

## 3. Data Management

### Automatic Saving

TrackMasterPro data is saved in the local storage automatically after any command that changes the data. There is no need to save manually.

### Manual Data Editing

TrackMasterPro data is saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, TrackMasterPro will discard all data and start with an empty data file at the next run. Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the TrackMasterPro to behave in unexpected ways (e.g., if a value entered is outside of the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</div>

--------------------------------------------------------------------------------------------------------------------

## 4. FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous TrackMasterPro home folder.

--------------------------------------------------------------------------------------------------------------------

## 5. Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------

## 6. Command summary

Action | Format, Examples
--------|------------------
**Add Equipment** | `add-e n/NAME c/CATEGORY s/STATUS` <br> e.g., `add-e n/Wilson-Evolution-Basketball c/Basketball s/Available`
**List Equipment** | `list-e`
**Delete Equipment**| `delete-e INDEX` <br> e.g., `delete-e 3`
**Edit Equipment** | `edit-e INDEX [n/NAME] [c/CATEGORY] [s/STATUS]` <br> e.g. `edit-e 6 s/Booked`
**Add Room** | `add-r n/NAME l/LOCATION s/STATUS` <br> e.g., `add-r n/MPSH-2 l/Sports-Centre s/Available`
**List Rooms** | `list-r`
**Delete Room** | `delete-r INDEX` <br> e.g., `delete-r 1`
**Edit Room** | `edit-r INDEX [n/NAME] [c/LOCATION] [s/STATUS]` <br> e.g. `edit-r 3 n/Tennis-Court s/Booked`
**Add Student** | `add-s n/NAME m/MATRIC_NUMBER p/PHONE_NUMBER e/EMAIL` <br> e.g., `add-s n/John Doe m/A0123456B p/91234567 e/e0123456@u.nus.edu`
**Check Loans** | `check-s MATRIC_NUMBER` <br> e.g., `check-s A0123456B`
**List Students** | `list-s`
**Delete Student** | `delete-s MATRIC_NUMBER` <br> e.g., `delete-s A0123456B`
**Edit Student** | `edit-s INDEX [n/NAME] [p/PHONE] [e/EMAIL]` <br> e.g. `edit-s 2 n/Tom p/91234561 e/e1234567@u.nus.edu`
**Reserve** | `reserve ITEM_OR_ROOM_ID STUDENT_ID [f/START_DATE_TIME] [t/END_DATE_TIME]` <br> e.g., `reserve Hall-2 a1234567a f/2026-03-01 1400 t/2026-03-01 1600`
**Cancel** | `cancel ITEM_OR_ROOM_ID STUDENT_ID [f/START_DATE_TIME] [t/END_DATE_TIME]` <br> e.g., `cancel Hall-2 a1234567a f/2099-03-15 0900 t/2099-03-15 1100`
**Issue** | `issue ITEM_ID STUDENT_ID [d/DUE_DATE_TIME]` <br> e.g., `issue Wilson-Basketball-1 A1203763K d/2026-03-05 1700`
**Return** | `return ITEM_ID` <br> e.g. `return Wilson-Evolution-Basketball-1`
**Tag** | `tag [c/EQUIPMENT_NAME or l/ROOM_NAME] t/TAG` <br> e.g., `tag c/Basketball-1 t/spoilt`
**Filter** | `filter [c/ or l/] t/TAG` <br> e.g., `filter l/ t/renovation`
**Alias** | `alias ITEM_OR_ROOM_ID ALIAS_NAME` <br> e.g., `alias MPSH-1 hall1`
**Clear** | `clear`
**Exit** | `exit`
