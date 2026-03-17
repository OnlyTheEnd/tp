---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

* {list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams are in this document `docs/diagrams` folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</div>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<img src="images/BetterModelClassDiagram.png" width="450" />

</div>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Logic.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

Similarly, how an undo operation goes through the `Model` component is shown below:

![UndoSequenceDiagram](images/UndoSequenceDiagram-Model.png)

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* Oversees high-traffic locations including sports halls, equipment stores, and multi-purpose rooms.
* Prefer desktop apps over other types
* Acts as the primary point of contact for all facility and equipment resource requests.
* Can type fast
* Prefers typing to mouse interactions
* Is reasonably comfortable using CLI apps

**Value proposition**: The app will help facility managers keep track of bookings made by NUS students.



### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

                                                 |

| Priority | As a …​        | I want to …​                                                                    | So that I can…​                                                                       |
|---------|----------------|---------------------------------------------------------------------------------|---------------------------------------------------------------------------------------|
| `* * *` | new user       | type help to see all the commands                                               | I don't have to ask my supervisor for help.                                           |
| `* * *` | new user       | reserve the equipment/room on a specified time/date                             | only I have access to it at that time/date                                            |
| `* * *` | user           | issue an item to a student                                                      | the system records that the item is no longer in the store.                           |
| `* * *` | user           | remove an equipment from inventory                                              | I can remove it                                                                       |
| `* * *` | user           | remove an equipment name from a student                                         | I can mark the item as "Returned" when they bring it back.                            |
| `* * *` | user           | check if a equipment is in use                                                  | I can quickly verify if a equipment is in use                                         |
| `* * *` | user           | find a student by name                                                          | I can quickly check the loan status of a specific person standing at the counter.     |
| `* * *` | user           | add a new student with their Name                                               | I can create a record for them in the system.                                         |
| `* * *` | user           | add a new student with their Matric Number                                      | I can create a record for them in the system.                                         |
| `* * *` | user           | add a new student with their Phone Number                                       | I can create a record for them in the system.                                         |
| `* * *` | user           | add a new student with their school email                                       | I can create a record for them in the system.                                         |
| `* * *` | user           | delete a student                                                                | I can remove records of students who have graduated or left the university.           |
| `* *`   | user           | have a checklist of ALL equipment in inventory                                  | I can verify if the equipment is available                                            |
| `* *`   | user           | find which student has borrowed a specific item                                 | I can update the status of an equipment manually                                      |
| `* *`   | user           | update an equipment details                                                     | minimize chance of someone else seeing them by accident                               |
| `* *`   | user           | block a facility for "Maintenance"                                              | no one can book the room.                                                             |
| `* *`   | user           | be warned when adding duplicate name                                            | no redundant information is stored                                                    |
| `* *`   | user           | keep track of history of the loans                                              | have the transaction on record                                                        |
| `* *`   | user           | keep track of the date of the loan                                              | have the date on record                                                               |
| `* *`   | user           | keep track of the time of the loan                                              | have the time on record                                                               |
| `* *`   | user           | blacklist a student                                                             | the system will warn me if I try to loan to a student with a history of overdue loans |
| `* *`   | user           | undo my last command                                                            | I can recover from accidental deletions or typos                                      |
| `* *`   | busy user      | simple view of equipment on loan or due                                         | I can spend time chasing it                                                           |
| `* *`   | busy user      | sort the item/room to a specified date                                          | I can know what is being used/occupied on that date                                   |
| `* *`   | advanced user  | create tags to equipment/room as a category                                     | I can see at a glance what is borrowed/book for that category                         |
| `* *`   | advanced user  | group equipment by function                                                     | I can find alternatives for equipment loans                                           |
| `* *`   | advanced user  | group equipment by date                                                         | be ready to collect them for return                                                   |
| `* *`   | advanced user  | create a list of authorized users                                               | have equipment only lent to authorized users                                          |
| `* *`   | advanced user  | create a list of authorized equipment                                           | have restrictions on who can borrow what equipment                                    |
| `* `    | user           | edit a student's contact details                                                | I can update if they change it.                                                       |
| `* `    | user           | issue multiple items at once                                                    | I can loan out and keep track of multiple items easily in the system                  |
| `* `    | user           | clear all records                                                               | I can reset if needed                                                                 |
| `* `    | user           | check a student loan history                                                    | I can record it                                                                       |
| `* `    | busy user      | automate sending reminder to borrower                                           | send reminders so equipment return puntually                                          |
| `*  `   | busy user      | automate sending late reminders                                                 | to remind the borrower to return eqipment                                             |
| `* `    | forgetful user | view a list of items due today upon launching the app                           | I am immediately informed of what needs to be returned                                |
| `* `    | advanced user  | create alias to the equipment/rooms                                             | I can fast lookup and manage students                                                 |
| `* `    | advanced user  | import new equipment from a file                                                | Add items more quickly                                                                |
| `* `    | advanced user  | import new people from a file                                                   | Add people more quickly                                                               |
| `* `    | advanced user  | export data as csv file                                                         | I can create reports for my supervisor to see                                         |
| `* `    | advanced user  | attach events to loans                                                          | quicken the loan process during a large school event                                  |
| `* `    | advanced user  | forecast future school events                                                   | anticipate future loans                                                               |
| `* `    | advanced user  | automate the process of aquiring a loan by extracting from a specified request  | simpler requests can be granted more easily                                           |


*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add a new equipment**

**MSS**

1.  Facility Manager requests to add a new equipment by providing its name, category, and status.
2.  TrackMasterPro validates the input and checks for duplicates.
3.  TrackMasterPro adds the equipment to the inventory.

    Use case ends.

**Extensions**

* 1a. The command format is invalid (missing n/, c/, or s/ prefixes).

    * 1a1. TrackMasterPro shows an error message and the correct command format.

  Use case ends.

* 2a. An equipment with the same name and category already exists.

    * 2a1. TrackMasterPro notifies the Facility Manager that a duplicate was found and suggests a numbered name (e.g., Basketball-1).

      Use case ends.

**Use case: View equipment inventory list**

**MSS**

1.  Facility Manager requests to view the list of equipment.
2.  TrackMasterPro retrieves and shows a list of all equipment with their categories and statuses.

    Use case ends.

**Extensions**

* 2a. The inventory is empty.

    * 2a1. TrackMasterPro shows a message that the inventory has not been created yet and prompts to add equipment.

  Use case ends.

**Use case: Remove an equipment**

**MSS**

1.  Facility Manager requests to View equipment inventory list
2.  TrackMasterPro shows the list of equipment.
3.  Facility Manager requests to delete a specific equipment in the list by its index.
4.  TrackMasterPro deletes the equipment from the inventory.

    Use case ends.

**Extensions**

* 3a. The given index is invalid (out of bounds or not a positive integer).

    * 3a1. TrackMasterPro shows an error message.

  Use case ends.

* 3b. The equipment at the specified index has a "Booked" status.

    * 3b1. TrackMasterPro shows an error message stating that booked equipment cannot be removed.

      Use case ends.

**Use case: Edit an equipment**

**MSS**

1.  Facility Manager requests to View equipment inventory list
2.  TrackMasterPro shows the list of equipment.
3.  Facility Manager requests to edit details (name, category, or status) of a specific equipment in the list by its index.
4.  TrackMasterPro validates the new details and updates the equipment.

    Use case ends.

**Extensions**

* 3a. The given index is invalid (out of bounds or not a positive integer).

    * 3a1. TrackMasterPro shows an error message.

  Use case resumes at step 2.

* 3b. The Facility Manager provides an invalid command format or missing fields.

    * 3b1. TrackMasterPro shows an error message and the correct command format.

      Use case ends.

**Use case: Delete a person**

**MSS**

1.  User requests to list persons
2.  AddressBook shows a list of persons
3.  User requests to delete a specific person in the list
4.  AddressBook deletes the person

    Use case ends.

<<<<<<< branch-usecases-zien
**Use case: Tag Equipment/Room**

**MSS**:
1. User chooses to tag an equipment or room.
2. User enters the equipment/room ID and tag.
3. System requests for the equipment/room ID and tag.
4. System applies the tag and displays a success message.
   Use case ends.

Extensions:
* 3a. System detects that the equipment/room ID is invalid.
  * 3a1. System displays a failure message.
  * 3a2. User re-enters a valid equipment/room ID and tag.
  * Steps 3a1-3a2 are repeated until a valid ID is entered.
  * Use case resumes from step 4.

* 3b. System detects that the equipment/room has already been tagged with the same tag.
  * 3b1. System displays a duplicate tag failure message.
  * Use case ends.

Use case: Untag Equipment/Room
MSS:
1. User chooses to untag an equipment or room.
2. User enters the equipment/room ID and tag.
3. System requests for the equipment/room ID and tag to remove.
4. System removes the tag and displays a success message.
   Use case ends.

Extensions:

* 3a. System detects that the equipment/room ID is invalid.
  * 3a1. System displays a failure message.
  * Use case ends.

* 3b. System detects that the tag does not exist on the equipment/room.
  * 3b1. System displays an already-untagged failure message.
  * Use case ends.


Use case: View Help Command
MSS:
1. User chooses to view help.
2. System displays a list of all available commands with short descriptions.
   Use case ends.

Extensions:
* 1a. User requests help for a specific command.
  * 1a1. System checks if the command exists.
  * 1a2. System displays the command details and an example usage.
  * Use case ends.

  * 1a1a. System detects that the specified command does not exist.
    * 1a1a1. System displays a failure message indicating the command was not found.
  * Use case ends.

Use case: Filter by Tag
MSS:
1. User chooses to filter by tag.
2. System requests for the type and tag to filter by.
3. User enters the type (equipment, room, or student) and tag.
4. System retrieves and displays all matching results under the specified tag.
   Use case ends.

Extensions:
* 3a. System detects that the specified type is invalid.
  * 3a1. System displays a failure message.
  * 3a2. User re-enters a valid type and tag.
  * Steps 3a1-3a2 are repeated until a valid type is entered.
  * Use case resumes from step 4.

* 3b. System detects that the specified tag does not exist.
  * 3b1. System displays a failure message indicating nothing was found under the tag.
  * Use case ends.
=======
**Use case: Add a Student Profile**

**MSS**
1.  Facility Manager enters the command to add a student (add-s) with the student's name, matriculation number, phone number, and email.
2.  TrackMasterPro validates the format of the input.
3.  TrackMasterPro checks that the student’s identity (matriculation number, phone number, and email) is unique.
4.  TrackMasterPro registers the new student profile.
5.  TrackMasterPro displays the success message and the details of the added student.

    Use case ends.

**Extensions**
- 2a. The input format is invalid (e.g., missing a prefix or wrong data format).
   -   2a1. TrackMasterPro shows an error message and provides the correct command format.
   -   Use case ends.

- 3a. A student with the same matriculation number, phone number or email adddress already exists.
   -   3a1. TrackMasterPro shows an error message indicating a duplicate identity was found.
   -   Use case ends.

**Use case: View Student Loans**
**MSS**
1.   Facility Manager enters the command to check loans (check-s) followed by the student's matriculation number.
2.   TrackMasterPro validates the format of the matriculation number.
3.   TrackMasterPro searches the database for a student matching that matriculation number.
4.   TrackMasterPro retrieves all active loan records associated with that student.
5.   TrackMasterPro displays the student's name, matriculation number, and a list of their currently borrowed items (including status and due dates).

     Use case ends.

**Extensions**
- 2a. The matriculation number format is invalid.
   - 2a1. TrackMasterPro shows an error message indicating the correct format.
   - Use case ends.
- 3a. No student is found with the provided matriculation number.
   - 3a1. TrackMasterPro informs the Manager that the user cannot be found.
   - Use case ends.
- 4a. The student has no active loans.
   - 4a1. TrackMasterPro displays a message stating "No existing loans" for that student.
   - Use case ends.

**Use case: Reserve equipment on a specified date/time**

**MSS**

1. User requests to list equipment
2. System shows a list of available equipment
3. User selects a specific equipment item
4. User specifies the desired date and time slot
5. System checks the availability of the equipment
6. System confirms the equipment is available
7. User confirms the reservation
8. System records the reservation

   Use case ends.

**Extensions**

* 5a. Equipment is not available for the selected time
  * 5a1. System informs the user that the slot is unavailable
  * 5a2. User selects a different date/time
  * Resume from step 4

* 3a. Equipment not found in the list
  * 3a1. System shows an error message
  * Use case ends

**Use case: Reserve room on a specified date/time**

**MSS**

1. User requests to list rooms
2. System shows a list of rooms
3. User selects a specific room
4. User specifies the desired date and time slot
5. System checks for scheduling conflicts
6. System confirms the room is available
7. User confirms the reservation
8. System records the booking

   Use case ends.

**Extensions**

* 5a. Room is already booked for the selected time
  * 5a1. System informs the user of the conflict
  * 5a2. User selects another time slot
  * Resume from step 4

* 4a. Invalid date or time format entered
  * 4a1. System shows validation error
  * 4a2. User re-enters correct information
  * Resume from step 4

**Use case: Issue an item to a student**

**MSS**

1. User requests to list students
2. System shows a list of students
3. User selects a specific student
4. User requests to issue a specific item
5. System checks that the item exists and is available
6. System records the item as issued to the student
7. System updates the item status

   Use case ends.

**Extensions**

* 5a. Item is not available
  * 5a1. System shows an error message
  * Use case ends

* 3a. Student not found
  * 3a1. System shows an error message
  * Use case ends

**Use case: Remove an item from a student**

**MSS**

1. User requests to list issued items
2. System shows items currently issued to students
3. User selects a specific issued item
4. User requests to remove the item from the student
5. System updates the item status to available
6. System records the return transaction

   Use case ends.

**Extensions**

* 3a. Item not found in issued list
  * 3a1. System shows an error message
  * Use case ends

* 4a. Item was not issued to the selected student
  * 4a1. System shows an error message
  * Use case ends

**Use case: Create alias for equipment**

**MSS**

1. User requests to list equipment
2. System shows list of equipment
3. User selects a specific equipment item
4. User enters an alias for the equipment
5. System checks that the alias is not already used
6. System saves the alias for the equipment

   Use case ends.

**Extensions**

* 5a. Alias already exists
  * 5a1. System shows an error message
  * Use case ends

* 3a. Equipment not found
  * 3a1. System shows an error message
  * Use case ends

**Use case: View all commands**

**MSS**

1. User requests to view all available commands
2. System displays the full list of supported commands

   Use case ends.

**Extensions**

* 2a. No commands available (system error case)
  * 2a1. System shows an empty list message
  * Use case ends

*{More to be added}*

### Non-Functional Requirements

**Technical and Environmental Requirements**

1.  The system should run on Windows, macOS, and Linux, provided that Java 17 is installed on the OS.
2.  The application should be distributed in a single JAR file.

**Performance and Scalability**
1.  The system must be able to handle at least 1,000 equipment items, 50 rooms, and 2,000 student profiles without any perceptible lag in command execution.
2.  The system should be handle to multiple rapid commands during peak period (etc 1 command every 2 seconds)

**Reliability & Data Integrity**
1.   All data (equipment, rooms, students, and loans) must be saved to the local storage immediately after any state-changing command is executed for persistency.
2.   If the application is closed unexpectedly, the data file must remain uncorrupted and readable upon the next launch.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others

* **Equipment** : Any item that is being loaned out for the school, saved as a string, with spaces replaced as hyphens.<br>
   example: Wilson-Evolution-Basketball

* **Room** : Any location that is being reserved, saved as a string, with spaces replaced as hyphens.<br>
   example: MPSH-1

*{More to be added}*
--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_


