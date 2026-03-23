### 1. The Core Problem with the Original Design

Before the refactor, the `CampusVault` application allowed the `ReportViewer` to directly instantiate and interact with raw report files (e.g., `ReportFile`). This direct access presented three massive architectural issues:
- **No Access Control:** Because anyone could just instantiate a `ReportFile` directly from disk, a student could easily request and read highly classified admin or faculty reports (e.g., Audit or Exam answers). There was no "gatekeeper".
- **Eager & Expensive Loading:** Every time a report object was created, it violently hit the disk, loaded the entire file into memory, and executed heavy processing (simulated via `Thread.sleep`), regardless of whether the user actually ended up viewing it.
- **No Caching:** If an Admin opened the identical "Budget Audit" report five times in a row, the old code would go back to the hard drive to eagerly reload the identical file five separate times!

### 2. How the Solution Was Architected (The Proxy Refactor)

We solved all three issues by introducing the **Proxy Pattern**. A Proxy provides a surrogate or placeholder for another object to control access to it.

#### A. Creating a Common Interface (`Report.java`)
We created a simple `Report` interface with a `display(User)` method. Both our heavy, real report and our lightweight, security proxy will implement this exact same interface. This ensures that the client (`ReportViewer`) doesn't know (or care) whether it is talking to a real report or a proxy.

#### B. Isolating the Heavy Logic (`RealReport.java`)
We moved all the expensive disk-loading and memory-heavy logic into `RealReport`. Notice that it still implements the `Report` interface.

#### C. Building the Gatekeeper (`ReportProxy.java`)
We created the `ReportProxy`. The Proxy holds lightweight metadata (like the ID and classification) but it does **not** load the file content right away. Instead, when `display(User)` is called on the Proxy, it performs two critical interceptions:
1. **Access Control (Protection Proxy):** The Proxy checks the `User` role against the report's classification. If a student tries to read an Admin report, the Proxy blocks them instantly and logs an "ACCESS DENIED" warning, entirely preventing the heavy file from ever being loaded.
2. **Lazy Loading & Caching (Virtual Proxy):** If the user is authorized, the Proxy checks if it has already loaded the `RealReport` into memory. If it has not (it is `null`), it finally instantiates `RealReport` (taking the heavy 120ms hit). However, if the user asks to see the report again later, the Proxy just delegates directly to the already-cached `RealReport`, eliminating redundant disk reads!

### 3. Teaching Guide: How to Explain This

**Use the "Celebrity Bodyguard" Analogy:**
> "Imagine a wildly famous celebrity (`RealReport`). Every time someone talks to the celebrity, it drains their energy and wastes a lot of their valuable time (heavy disk loading/memory usage).
> 
> In the bad code, we let anyone off the street walk straight up and talk to the celebrity. A random student would walk up, and the celebrity would spend hours dealing with them. If a VIP came back 5 times, the celebrity had to start the whole introduction process 5 times.
>
> To fix this, we hired a **Proxy Bodyguard** (`ReportProxy`). 
> - The bodyguard wears the exact same suit and title as the celebrity (`Report` interface), so people line up to talk to the bodyguard thinking it's the official channel.
> - When a random student asks for a meeting, the bodyguard demands their ID (Protection Proxy / Access Control). If they aren't authorized, the bodyguard turns them away instantly. The celebrity in the back room is entirely undisturbed.
> - When an Admin VIP asks for a meeting, the bodyguard checks if the celebrity is even awake yet. If not, the bodyguard wakes them up (Lazy Loading). 
> - If the VIP asks for five meetings in a row, the bodyguard just leaves the back door open to the already-awake celebrity (Caching).
> 
> By putting a proxy in between the client and the heavy object, we secured the data and optimized performance without changing how people ask for meetings!"
