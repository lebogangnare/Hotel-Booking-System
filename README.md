# 🏨 Hotel Room Booking System — FORGE Java Workshop

## Overview

In this workshop you will design and implement a hotel room booking simulation in Java. The system manages different types of hotel rooms and processes guest bookings through a queue-based system.

The skeleton project is provided. Your task is to implement the classes described in the steps below, following the principles of **Encapsulation** and **Inheritance**.

### Learning Outcomes

- OOP (Encapsulation, Inheritance)
- Greenfield Development
- Unit Testing
- System Design & UML

---

## Time

**3 hours** — guided, instructor-led session. You will follow along as we build this system together.

---

## Project Structure

```
hotel-booking/
├── pom.xml
└── src/
    ├── main/
    │   └── java/
    │       └── com/
    │           └── hotelbooking/
    │               ├── Main.java
    │               ├── model/
    │               │   ├── Room.java
    │               │   ├── RoomType.java
    │               │   └── Booking.java
    │               └── service/
    │                   ├── Hotel.java
    │                   ├── BoutiqueHotel.java
    │                   └── ChainHotel.java
    └── test/
        └── java/
            └── com/
                └── hotelbooking/
                    ├── RoomTest.java
                    ├── RoomTypeTest.java
                    ├── BookingTest.java
                    └── HotelTest.java
```

---

To check your progress at any point, run the full test suite:

```bash
mvn clean compile test
```

Your goal is to reach **100% of tests passing** by the end of the session.

---

## Implementation Steps

Work through the steps **in order** — later classes depend on earlier ones.

---

### Step 1 — Implement `Room`

**File:** `src/main/java/com/hotelbooking/model/Room.java`

The `Room` class represents a single hotel room with a number, a nightly rate, and an availability status.

#### Fields

| Field           | Type      | Details                                                          |
|-----------------|-----------|------------------------------------------------------------------|
| `roomNumber`    | `String`  | The room identifier (e.g. `"101"`). Must be **private**.         |
| `pricePerNight` | `double`  | The cost per night in Rands. Must be **private**.                |
| `available`     | `boolean` | Whether the room is available for booking. Must be **private**.  |

#### Constructor

Accepts `roomNumber` (String) and `pricePerNight` (double). Sets `available` to `true` by default.

#### Methods

| Method                         | Details                                                                      |
|--------------------------------|------------------------------------------------------------------------------|
| `roomNumber()`                 | Returns the room number.                                                     |
| `pricePerNight()`              | Returns the nightly rate.                                                    |
| `isAvailable()`                | Returns whether the room is currently available.                             |
| `updatePrice(double)`          | Updates the nightly rate. Throws `IllegalArgumentException` if negative.     |
| `setAvailability(boolean)`     | Updates the room's availability status.                                      |
| `toString()`                   | Returns a readable summary, e.g. `"Room 101: R850.0/night [Available]"`.    |

---

### Step 2 — Implement `RoomType`

**File:** `src/main/java/com/hotelbooking/model/RoomType.java`

A `RoomType` groups rooms of the same category (e.g. Single, Double, Suite) and must protect its internal room list from outside mutation.

#### Fields

| Field     | Type          | Details                                                              |
|-----------|---------------|----------------------------------------------------------------------|
| `name`    | `String`      | The category name (e.g. `"Deluxe Suite"`). Must be **private**.      |
| `rooms`   | `List<Room>`  | The rooms belonging to this category. Must be **private**.           |

#### Constructor

Accepts a `String` name and a `List<Room>`. Store a **mutable copy** of the provided list — do not store the reference directly.

#### Methods

| Method                   | Details                                                                                   |
|--------------------------|-------------------------------------------------------------------------------------------|
| `name()`                 | Returns the room type name.                                                               |
| `rooms()`                | Returns a **defensive copy** of the room list so callers cannot modify internal state.    |
| `addRoom(Room)`          | Appends the given room to the internal list.                                              |
| `availableRooms()`       | Returns a list of only the rooms where `isAvailable()` is `true`.                        |
| `toString()`             | Returns the type name followed by each room on its own line.                              |

> **Design Tip — Defensive Copies:** If `rooms()` returned the actual internal list, any caller could call `.add()` or `.remove()` on it and silently change your data. Returning `new ArrayList<>(rooms)` prevents this.

---

### Step 3 — Implement `Booking`

**File:** `src/main/java/com/hotelbooking/model/Booking.java`

A `Booking` links a guest to a `Room` and tracks the current state of that booking through a lifecycle.

#### Fields

| Field         | Type            | Details                                              |
|---------------|-----------------|------------------------------------------------------|
| `bookingId`   | `int`           | Unique numeric ID for this booking. Must be **private**. |
| `guestName`   | `String`        | The name of the guest. Must be **private**.           |
| `room`        | `Room`          | The room being booked. Must be **private**.           |
| `nights`      | `int`           | Number of nights for the stay. Must be **private**.   |
| `status`      | `BookingStatus` | Current status. Defaults to `PENDING`. Must be **private**. |

#### Constructor

Accepts `bookingId`, `guestName`, `room`, and `nights`. Sets `status` to `BookingStatus.PENDING`.

> The `BookingStatus` enum and constructor signature are provided in the skeleton — you just need to implement the missing fields and methods.

#### Methods

| Method                        | Details                                                        |
|-------------------------------|----------------------------------------------------------------|
| `bookingId()`                 | Returns `bookingId`.                                           |
| `guestName()`                 | Returns the guest name.                                        |
| `room()`                      | Returns the `Room`.                                            |
| `nights()`                    | Returns the number of nights.                                  |
| `totalCost()`                 | Returns `room.pricePerNight() * nights`.                       |
| `status()`                    | Returns the current `BookingStatus`.                           |
| `updateStatus(BookingStatus)` | Updates the booking status.                                    |
| `toString()`                  | Returns a summary including `bookingId`, `guestName`, room number, nights, total cost, and status. |

---

### Step 4 — Implement `Hotel` (Abstract Class)

**File:** `src/main/java/com/hotelbooking/service/Hotel.java`

`Hotel` is an **abstract base class** that manages room types and processes bookings. It delegates the hotel-specific check-in behaviour to its subclasses through an abstract method.

#### Fields

| Field           | Type                    | Details                                                           |
|-----------------|-------------------------|-------------------------------------------------------------------|
| `hotelName`     | `String`                | The hotel's display name. Must be **private**.                    |
| `roomTypes`     | `Map<String, RoomType>` | Maps room type names to `RoomType` objects. Must be **private**.  |
| `bookingQueue`  | `List<Booking>`         | Ordered list of all placed bookings. Must be **private**.         |
| `bookingCounter`| `int`                   | Starts at `0`; incremented each time a booking is placed. Must be **private**. |

#### Constructor

Accepts `hotelName`. Initialises `roomTypes` as a new `HashMap` and `bookingQueue` as a new `ArrayList`.

#### Concrete Methods (fully implement these)

| Method                                   | Details                                                                                                                     |
|------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------|
| `addRoomType(RoomType)`                  | Adds the room type to the map using its name as the key.                                                                    |
| `getRoomType(String)`                    | Returns the `RoomType` for the given name, or `null` if not found.                                                          |
| `getAllRoomTypes()`                       | Returns an unmodifiable Map of room types.                                                                                  |
| `placeBooking(String, String, int)`      | Creates a new `Booking` (`bookingId = ++bookingCounter`), marks the room as unavailable, adds it to the queue, and returns it. Throws `IllegalArgumentException` if the room type is not found or has no available rooms. |
| `processNextBooking()`                   | Finds the first `PENDING` booking, sets status to `IN_PROGRESS`, calls `checkIn(booking)`, sets status to `CONFIRMED`, and returns it. Returns `null` if no pending bookings exist. |
| `bookingQueue()`                         | Returns an unmodifiable list of the booking queue.                                                                          |
| `hotelName()`                            | Returns `hotelName`.                                                                                                        |

#### Abstract Method

```java
protected abstract void checkIn(Booking booking);
```

Subclasses must override `checkIn()` to provide hotel-specific check-in behaviour. It is called automatically by `processNextBooking()`.

---

### Step 5 — Implement `BoutiqueHotel` & `ChainHotel`

**Files:** `service/BoutiqueHotel.java` and `service/ChainHotel.java`

These two concrete classes extend `Hotel` and provide hotel-specific check-in output.

#### BoutiqueHotel

|                  | Details                                                                                                             |
|------------------|---------------------------------------------------------------------------------------------------------------------|
| Extends          | `Hotel`                                                                                                             |
| Constructor      | Accepts `hotelName`, passes it to `super()`.                                                                        |
| `checkIn(Booking)` | Prints: `"[hotelName] — Welcome, [guestName]! Your [roomNumber] has been personally prepared for your [nights]-night stay."` |

#### ChainHotel

|                  | Details                                                                                                             |
|------------------|---------------------------------------------------------------------------------------------------------------------|
| Extends          | `Hotel`                                                                                                             |
| Constructor      | Accepts `hotelName`, passes it to `super()`.                                                                        |
| `checkIn(Booking)` | Prints: `"[hotelName] — Booking #[bookingId] confirmed for [guestName]. Room [roomNumber] assigned for [nights] night(s). Total: R[totalCost]."` |

---

## UML Class Diagram

Produce a UML class diagram for this project using [draw.io](https://draw.io).

Your diagram must include all six classes and show the following for each:

- Class name
- All fields with their types and access modifiers (`+` public, `-` private, `#` protected)
- All methods with their return types and parameters
- Relationships — inheritance arrows where one class extends another, association arrows where one class holds a reference to another

Export your diagram as a **PNG or PDF** and place it in the root of your project named `uml.pdf`.

---

## How the Classes Relate

```
                    Hotel  (abstract)
                   /     \
        BoutiqueHotel   ChainHotel

        Hotel ──────────────► RoomType ──► Room
        Hotel ──────────────► Booking  ──► Room
```

- `Hotel` manages a map of `RoomType` objects and a queue of `Booking` objects
- `RoomType` holds a list of `Room` objects
- `Booking` holds a reference to a single `Room`
- `BoutiqueHotel` and `ChainHotel` extend `Hotel` and implement `checkIn()`

---

## Concepts Covered in This Workshop

| Concept | Where You'll See It |
|---|---|
| Encapsulation | Private fields in `Room`, defensive copies in `RoomType` |
| Inheritance | `BoutiqueHotel` and `ChainHotel` extending abstract `Hotel` |
| Abstraction | `checkIn()` defined in `Hotel`, implemented in subclasses |
| Greenfield Setup | Starting from a blank Maven project with defined structure |
| Unit Testing | JUnit tests validating each class as we build |
| UML | Designing the class diagram before writing a single line of code |

---

## Getting Started

### Prerequisites

Make sure you have the following installed:

- Java JDK 17 or higher
- Maven 3.8+
- An IDE (IntelliJ IDEA recommended)

### Fork & Clone

1. Fork this repository to your own GitHub account
2. Clone your fork locally:

```bash
git clone https://github.com/YOUR-USERNAME/hotel-booking-workshop.git
cd hotel-booking-workshop
```

3. Verify the project compiles:

```bash
mvn clean compile
```

You should see `BUILD SUCCESS` — with all tests failing since nothing is implemented yet. That's expected.

---

*Built with ☕ by FORGE Community*
