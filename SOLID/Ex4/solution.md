### 1. The Core Problem with the Original Design

Before the refactor, the `FeeCalculator.calculate()` method (which previously lived inside `HostelFeeCalculator`) was a giant `switch` statement that checked the `roomType`. If it was a `SINGLE`, it added one price; if it was `DOUBLE`, it added another; etc. Additionally, it had multiple `if` branches checking for various add-ons like `LAUNDRY` or `MESS`.

This design severely violated the **Open-Closed Principle (OCP)**. The Open-Closed Principle states that a class should be *open for extension but closed for modification*. In the old design, if a new hostel building was built with a brand new "PENTHOUSE" room type, or if a new "GYM" add-on was offered, the developer would be forced to open the main calculator file and modify its core logic by adding new `case` statements and `if` blocks.

Furthermore, the calculator was also violating the Single Responsibility Principle by directly printing receipts and saving bookings!

### 2. How the Solution Was Architected (The Refactor)

We solved this problem by defining a universal financial contract for any item that costs money, and extracting the prices out of the central calculator.

#### A. The Shared Abstraction (`PricingComponent.java`)
We created a simple interface called `PricingComponent` that guarantees every billable item can answer three questions:
1. What is your `monthlyAmount()`?
2. What is your `depositAmount()`?
3. What is your `nameOf()`?

#### B. Converting Enums/Switch Cases to Polymorphic Classes
Instead of a simple Enum that gets fed into a `switch` statement, both Room Types and Add-ons are now represented by individual classes that implement `PricingComponent`:
- **Rooms:** `SingleRoomPricing`, `DoubleRoomPricing`, `DeluxeRoomPricing`.
- **Add-ons:** `LaundryAddOn`, `MessAddOn`, `GymAddOn`, and even dynamic fees like `LateFee`.

#### C. The Refactored Calculator (`FeeCalculator.java`)
Because every single room and add-on now guarantees it can calculate its own price via the `PricingComponent` interface, the `FeeCalculator` is miraculously simple. 
It takes the `BookingRequest`, asks the main `roomType` for its base monthly amount, and then simply *loops* over the list of `addOns`, asking each one for its price to get the total.

- **Benefit (Closed for Modification):** The `FeeCalculator` never uses an `if` or a `switch` statement. It simply loops through `PricingComponent`s and adds their values together.
- **Benefit (Open for Extension):** If the hostel creates a new "Penthouse" room type, we just write a new `PenthouseRoomPricing` class. Our calculator doesn't need to be touched at all. It will automatically ask the Penthouse for its price.

#### D. Separating Printing and Persistence
We moved the `System.out.println` statements into a dedicated `ReceiptPrinter.java` and let `HostelFeeCalculator.process()` handle the high-level orchestration across the calculator, printer, and `FakeBookingRepo`.

### 3. Teaching Guide: How to Explain This

**Use the "Restaurant Bill" Analogy:**
> "Imagine a server at a restaurant trying to add up your bill. If they are acting like the old code, they have to physically memorize the price of every single item on the menu. If the manager adds a new seasonal soup, the server has to be pulled aside, trained to memorize the new soup's price, and hopefully doesn't forget the price of a hamburger in the process. (This is the `switch` statement).
>
> What we did with the Open-Closed Principle is put **price tags on the food itself**.
> - We created an interface called `PricingComponent`—this is the physical price tag.
> - Every room (`SingleRoom`, `DoubleRoom`) and every add-on (`Laundry`, `Gym`) now wears its own price tag.
>
> Now, the `FeeCalculator`'s only job is to look at the tray of items, read the price tags, and add them up. If the manager introduces a brand new item tomorrow, they just slap a price tag on it. The calculator never needs to learn anything new or be modified; it just reads the total! The system is closed to modification, but infinitely open to extension."
