# 🧾 SE 3006: Software Architecture - Lab 02 Report

**Course:** SE 3006 - Software Architecture  
**Lab:** 02 — Modular Monolith Built with Pure Java  
**Date:** April 2026  
**Student:** Muhammed Mustafa Özdemir

---

## Executive Summary

This lab refactors the Lab 01 layered system into a Modular Monolith with two vertical modules: `catalog` and `orders`. Key changes include package-private internals for information hiding, module-to-module communication only via public interfaces (`CatalogService`), and use of factories to wire and export modules.

---

## Architecture Comparison

- **Layered Architecture (Lab 1)**
  - Structure: horizontal layers — Presentation → Business → Persistence.
  - Change impact: adding a feature typically touches controller, service, and repository layers.
  - Pros: clear technical separation and predictable wiring.
  - Cons: feature work often ripples across layers; higher coordination and more regression surface.

- **Modular Monolith (Lab 2)**
  - Structure: vertical, domain-oriented modules (`catalog`, `orders`) exposing small public APIs.
  - Change impact: features are localized to a module; cross-module changes only occur when interfaces change.
  - Pros: stronger encapsulation, smaller blast radius, easier incremental evolution and team autonomy.
  - Cons: must manage interface stability; risk of duplicated logic across modules if not factored.

## Adding a New Feature — Behavior Comparison

Example: Add a "reserve stock" capability to the order flow.

- **Layered Architecture (Lab 1)**
  - Steps: update presentation (`OrderController`), implement logic in `OrderService`, and extend `ProductRepository` to support reservation.
  - Impact: changes span multiple technical layers and packages; requires cross-cutting tests and careful coordination.
  - Development speed: slower for feature work due to multiple touch points and higher regression risk.

- **Modular Monolith (Lab 2)**
  - Steps: define or extend a small method on `CatalogService` (e.g., `reserveStock`), implement reservation inside the `catalog` module (package-private repository changes), and call it from `orders` module.
  - Impact: most work stays inside the `catalog` module; `orders` only depends on the public interface and needs minimal changes if any.
  - Development speed: faster and safer for domain features; easier to write focused tests and refactor internals.

## Practical Recommendations

- Keep inter-module APIs small and stable; prefer additive API changes.
- Use factories to encapsulate wiring and avoid leaking internals.
- Write module-level tests to validate behavior without touching unrelated modules.

---

## Status & Next Steps

- README lists TODOs: implement `ProductRepository` methods, `CatalogServiceImpl` logic, module factories, `OrderService.placeOrder`, and `OrderController` wiring.
- Complete the implementations listed in the README, run the app tests, then commit & push to your branch.

---
