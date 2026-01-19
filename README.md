# Crossing Paths

Crossing Paths is a **2D online multiplayer puzzle‑platformer** built around tight cooperation, asymmetric level design, and
precision movement.

The game is designed for **exactly two players**. Each player has a distinct role, route, and set of constraints,
meaning progress is impossible without coordination.

---

## Gameplay Overview

- **Two players required** (networked multiplayer)
- Players control either the **Red** or **Blue** character
- Each player:
    - Has **slightly different routes** through the level
    - Can pass through obstacles of **their own color**
    - Is blocked by obstacles of the **other player’s color**
      This asymmetric design forces players to:
- Communicate constantly
- Time movements precisely
- Solve puzzles cooperatively rather than individually

Mistakes by one player can block or trap the other, increasing difficulty and tension.

----------

## Networking Model

Crossing Paths uses a **custom authoritative networking model**:

- **Client‑side prediction** with server reconciliation

- **TCP byte‑based networking** (no engine‑level networking)

- **Protocol Buffers (protobuf)** for all network messages

- Continuous interpolation with authoritative correction

Key characteristics:

- Smooth movement despite latency
- Server‑authoritative state
- Deterministic correction when players stop moving

This system was built specifically for this game rather than relying on third‑party networking frameworks.

----------

## Engine

The game is built on **logangamelib**, a custom Java game engine developed specifically for Crossing Paths.

logangamelib (lgl) provides:

- Scene management
- Entity system
- Rendering abstraction
- Input handling
- Game loop and timing

All gameplay logic, networking, and synchronization are implemented on top of this engine.

The engine's source code is available at the `com.logandhillon.logangamelib` package.

----------

## Technology Stack

- **Language:** Java 21
- **Build System:** Gradle 8
- **Networking:**
    - TCP (byte‑based)
    - Protocol Buffers (protobuf)
- **Engine:** logangamelib (custom)

----------

## Requirements

To build and run the game, you need:

- **JDK 21**
- **Gradle 8**

- A system capable of running JavaFX (for rendering)

----------

## Building

Clone the repository and build with Gradle:

```
./gradlew build
```

Run the game using the appropriate Gradle task or IDE run configuration.

----------

## Multiplayer Notes

- One player acts as the **server/host**

- The other connects as a **client**

- The game does not support single‑player or AI substitutes

Both players must be running compatible builds.

----------

## Project Status

Crossing Paths is an actively developed project focused on:

- Tight movement feel

- Robust multiplayer synchronization

- Challenging cooperative puzzle design

The architecture intentionally avoids off‑the‑shelf engines or networking libraries to maintain full control over
gameplay behavior.

----------

## License

&copy; 2026 Logan Dhillon & Jack Ross. All rights reserved.