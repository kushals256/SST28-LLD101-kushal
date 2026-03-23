### 1. The Core Problem with the Original Design

Before the refactor, the `GeoDash` map application was suffering from severe memory bloat. Every single time a new `MapMarker` was created to be placed on the map, it carried its own private `MarkerStyle` object (recording the shape, color, size, and filled status).

If the map tried to render 10,000 red pins of size 12, the application would instantiate **10,000 identical `MarkerStyle` objects** in memory. This is completely wasteful and violates the **Flyweight Pattern**. Since the style characteristics are exactly the same across those 10,000 markers, they shouldn't each have their own copy of the data. 

### 2. How the Solution Was Architected (The Refactor)

We solved this massive memory issue by applying the **Flyweight Pattern**, which separates an object's state into two distinct categories:
- **Intrinsic State:** The data that is completely independent, reusable, and identical across many items (e.g., "Red, Pin, Size 12").
- **Extrinsic State:** The data that is unique to each individual item and changes based on context (e.g., the specific Latitude and Longitude).

#### A. Extracting the Intrinsic State (`MarkerStyle.java`)
We ensured that the `MarkerStyle` class was completely immutable (no setters, all fields `final`). This is critical: if thousands of markers are sharing a single style object, we cannot allow anyone to accidentally change the color to "Blue" and inadvertently turn the entire map blue!

#### B. The Flyweight Factory (`MarkerStyleFactory.java`)
We created a Factory that acts as a librarian or cache for these styles. Before generating a new style, the Factory converts the requested properties into a simple String key (e.g., `"PIN|RED|12|F"`).
- It checks a `HashMap` to see if a `MarkerStyle` with that exact key already exists.
- If it does, it **reuses** and returns that existing style.
- If it doesn't, it creates it, stores it in the cache for next time, and returns it.

#### C. The Refactored Class (`MapMarker.java`)
The `MapMarker` class was updated. Now, it only stores the unique **extrinsic state** (lat, lng, label) and holds a *reference* to the shared `MarkerStyle`.

#### D. The Clean Flow (`MapDataSource.java`)
When `MapDataSource` loops to create 10,000 markers, it now asks the `MarkerStyleFactory` for the style. If there are only 96 possible combinations of shapes, colors, and sizes, the factory only ever creates exactly 96 `MarkerStyle` objects. All 10,000 markers simply point back to one of those 96 shared objects, saving enormous amounts of memory.

### 3. Teaching Guide: How to Explain This

**Use the "Board Game Tokens" Analogy:**
> "Imagine a massive war board game with 10,000 miniature soldier tokens. 
> 
> In the bad code, the game manufacturer cast every single tiny plastic soldier with its own heavy, golden nameplate glued directly to the bottom. Each nameplate redundantly etched 'Infantry Unit, Green, 10 HP'. It was incredibly heavy, expensive, and a massive waste of material.
>
> What we did with the **Flyweight Pattern** is separate the cheap plastic soldier (the Extra/Extrinsic state: its grid position on the board) from the heavy, golden rulebook (the Intrinsic state: unit stats).
>
> Now, we only ever print exactly ONE heavy golden rulebook that says 'Infantry Unit, Green, 10 HP'. We put it on the edge of the board. When you pick up any of the 10,000 plastic soldiers, there's just a tiny string pointing back to that one single shared rulebook. 
> 
> By moving the reusable data (the `MarkerStyle`) out of the individual items (`MapMarker`) and sharing it via a Factory (`MarkerStyleFactory`), we saved massive amounts of memory without changing how the game is played."
