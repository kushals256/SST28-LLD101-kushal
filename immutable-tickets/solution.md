### 1. The Core Problem with the Original Design

Before the refactor, the `IncidentTicket` class was entirely mutable. This design suffered from several severe architectural flaws:
- **Public Setters:** Any piece of code could randomly change the state of a ticket exactly when it pleased (e.g., calling `ticket.setPriority("CRITICAL")`). This makes it structurally impossible to track chronological changes, maintain consistent audit logs, or predict the behavior of automated algorithms handling the ticket.
- **Scattered Validation:** Important checks (like validating email formats or ensuring the SLA timeframe made sense) were scattered all over the application in places like `TicketService.java`. If a developer manually instantiated a ticket via a constructor, they could easily bypass those validations, resulting in corrupt data.
- **Internal State Leakage:** The `tags` list was returned completely raw. Even if there was no setter method, another developer could simply call `ticket.getTags().add("HACKED")`. Because the underlying memory reference was shared, this would silently alter the ticket's true state without any warning.

### 2. How the Solution Was Architected (The Refactor)

The overarching goal was to shift seamlessly to an **Immutable Design** coupled with early, centralized validation using the **Builder Pattern**.

#### A. Locking Down the State (Immutability)
In `IncidentTicket.java`, the first step was making sure the object's properties could never change once set:
- **Private Final Fields**: Every property was marked as `private final` so they can only be assigned exactly once upon construction.
- **Removing Setters**: All public `setX()` methods were deleted.
- **Defensive Copying (Constructor)**: Rather than sharing the `List<String>` memory reference passed into the constructor, the code creates a brand-new list: `this.tags = new ArrayList<>(b.tags)`.
- **Defensive Copying (Getters)**: The `getTags()` method was updated to return `new ArrayList<>(tags)`. This ensures that if external code tries to modify the returned list, they are only modifying a useless copy, keeping the ticket's internal `tags` perfectly safe.

#### B. The Builder Pattern
Because the fields are final, they must all be passed to the constructor. However, a constructor that takes 10 arguments (id, title, email, priority, etc.) is difficult to read. The elegant solution is the `Builder` pattern.
- A nested static `Builder` class was created containing mutable versions of all the ticket properties.
- Fluent builder methods were added (e.g., `public Builder title(String title) { this.title = title; return this; }`) so you can cleanly chain assignments.
- The only way to instantiate the physical `IncidentTicket` now is by calling `new IncidentTicket(Builder b)` (which is private), transferring the values into the locked-down fields.

#### C. Centralizing Validation
Instead of validating properties chaotically in `TicketService`, all validation rules were concentrated entirely into `Builder.build()`.
- Right before `build()` returns the new ticket, it executes static checks using `Validation.java`.
- This acts as an inescapable **Tollbooth**. It is now fundamentally impossible to create an invalid `IncidentTicket`. If any data is bad, it throws an exception right at the moment of creation regardless of where in the application the builder was invoked.

#### D. Non-Destructive "Updates" (The `.from()` method)
Because the objects are permanently immutable, you cannot mutate them when a ticket is escalated. The solution introduced here is the `from(IncidentTicket t)` method inside the `Builder`.
- This method acts as a prototype copier. It takes an existing ticket, pre-fills the `Builder` with all of that ticket’s values, and lets you override just the specific pieces you want.
- Example: To escalate a ticket to critical, the code no longer does `t.setPriority("CRITICAL")`. Instead, it does:
  ```java
  return new IncidentTicket.Builder()
         .from(t) // copy old state
         .priority("CRITICAL") // override
         .build();
  ```

### 3. Teaching Guide: How to Explain This

**Use the "Official Government Passport" Analogy:**
> "A mutable ticket is like a post-it note. Anyone can pick it up, cross out the priority 'Low', scribble 'URGENT', and hand it back. It creates chaos, you don't know who changed it, and there's no paper trail. 
> 
> The **Immutable Builder Pattern** is like an **Official Government Passport**. 
> - The **Builder** is the blank application form. You can fill it out, erase things, cross things out, and change your mind as much as you want.
> - The **Validation Tollbooth (`build()`)** is the clerk at the government desk. Before they stamp anything, they check your application. If it's invalid, they throw it out immediately. 
> - If it's valid, they print your physical Passport (`IncidentTicket`). 
> 
> Once that passport is printed, the ink is permanently embedded in the paper (**`final` fields**). If you ever need to change your name on the passport, you can't just cross it out with a marker (no setters!). You have to fill out a new application form pre-filled with your old data (`Builder.from()`), override your name, and print a brand-new passport. This ensures undeniable consistency and safety in our software!"
