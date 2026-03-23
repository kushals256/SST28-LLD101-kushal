### 1. The Core Problem with the Original Design

Before the refactor, the `OrderService` was tightly coupled to two different third-party payment SDKs: `FastPayClient` and `SafeCashClient`. 
When a payment needed to be made, `OrderService.charge()` likely used a large `switch` statement or `if/else` block based on the `provider` string to figure out which SDK to call.

This is a classic architectural flaw for several reasons:
- **No Common Interface:** `FastPayClient` uses a method called `payNow(customerId, amount)` while `SafeCashClient` uses a chained builder pattern `createPayment(amount, customerId).confirm()`. They have different method names, different parameter orders, and different execution styles.
- **Tight Coupling:** The core `OrderService` (which should only care about business logic) is polluted with the inner workings of external vendor SDKs.
- **OCP Violation:** If the business decided to add a third provider like `Stripe`, a developer would have to open `OrderService` and modify its core logic to add another `else-if` branch.

### 2. How the Solution Was Architected (The Adapter Refactor)

We solved this problem using the **Adapter Pattern**, which acts just like a physical travel adapter for an electrical plug. It translates one interface into another so that incompatible systems can work together.

#### A. Defining the Target Interface (`PaymentGateway.java`)
First, we defined exactly what our application *wants* a payment processor to look like. We created a clean, simple `PaymentGateway` interface with a single method: `charge(String customerId, int amountCents)`.

#### B. Building the Adapters (`FastPayAdapter` & `SafeCashAdapter`)
We created wrapper classes for both SDKs that implement our new `PaymentGateway` interface.
- Inside `FastPayAdapter`, the `charge()` method intercepts the request and translates it into `client.payNow()`.
- Inside `SafeCashAdapter`, the `charge()` method intercepts the request and translates it into `client.createPayment().confirm()`.

By doing this, we effectively "hid" the mismatched third-party methods behind a uniform, predictable wall.

#### C. Refactoring the Service (`OrderService.java` & `App.java`)
The `OrderService` was refactored to rely **only** on the `PaymentGateway` interface. We removed all `if/else` branching. Instead, `OrderService` is given a `Map<String, PaymentGateway>` (a registry) during construction.

When `charge()` is called, it simply looks up the adapter by name in the map and calls `gateway.charge()`. The `OrderService` is now completely blissfully unaware of whether it is talking to FastPay or SafeCash!

### 3. Teaching Guide: How to Explain This

**Use the "Travel Plug" Analogy:**
> "Imagine you travel from the USA to Europe. Your laptop charger (our `OrderService`) has flat prongs, but the wall outlets in Europe (the third-party SDKs) have round holes. You obviously can't plug your laptop directly into the wall, nor should you rip open the wall to rewire the hotel (`SafeCashClient`), nor should you solder round prongs onto your laptop (`OrderService`).
>
> What you do is buy a **Travel Adapter**. 
> - The back of the travel adapter accepts your flat prongs (`PaymentGateway` interface).
> - The front of the travel adapter plugs into the round holes (`SafeCashAdapter` translating to `SafeCashClient.confirm()`).
> 
> Because we wrapped our external vendors in these software 'travel adapters', our core laptop (`OrderService`) can travel anywhere and use any outlet. If we add a new payment provider in Asia, we just build a new travel adapter. Our laptop's code never has to change!"
