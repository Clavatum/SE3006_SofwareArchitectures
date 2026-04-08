# 🏗️ SE 3006: Software Architecture - Lab 01 Report

## Layered Architecture with Pure Java

**Course:** SE 3006 - Software Architecture  
**Lab:** 01 - Layered Architecture Built with Pure Java  
**Date:** April 2026  
**Student:** Muhammed Mustafa Özdemir

---

## 📋 Executive Summary

This laboratory successfully demonstrates the implementation of a three-tier layered architecture in pure Java without any frameworks. The system exemplifies strict layering principles where data flow only proceeds from top to bottom, and lower layers remain oblivious to upper layers. The implementation uses manual dependency injection to wire the components, providing hands-on understanding of how architectural boundaries are enforced at the code level.

---

## 🎯 Objectives Achieved

1. ✅ **Understand Manual Dependency Injection:** Implemented constructor-based dependency injection across all three layers.
2. ✅ **Implement Strict Layering:** Enforced architectural boundaries where each layer only depends on the layer directly below it.
3. ✅ **Observe Data Flow:** Demonstrated unidirectional data flow from Presentation → Business → Persistence layers.
4. ✅ **Practical System Wiring:** Bootstrapped the system in the Main class by creating and wiring components bottom-up.

---

## 🏛️ Architectural Design

### Layer Structure

```
┌─────────────────────────────────────────┐
│  Presentation Layer                     │
│  (OrderController)                      │
│  - Handles user requests                │
│  - Manages error display                │
└──────────┬──────────────────────────────┘
           │ depends on
┌──────────▼──────────────────────────────┐
│  Business Layer                         │
│  (OrderService)                         │
│  - Implements core business logic       │
│  - Validates orders                     │
│  - Manages inventory                    │
└──────────┬──────────────────────────────┘
           │ depends on
┌──────────▼──────────────────────────────┐
│  Persistence Layer                      │
│  (ProductRepository)                    │
│  - Manages data storage                 │
│  - In-memory HashMap database           │
│  - Unaware of business logic            │
└─────────────────────────────────────────┘
```

### Layering Rules Enforced

- ✅ **Presentation Layer** only knows about Business layer
- ✅ **Business Layer** only knows about Persistence layer
- ✅ **Persistence Layer** has no knowledge of upper layers
- ✅ **Data flow** strictly unidirectional (top → bottom)

---

## 🛠️ Implementation Details

### TASK 1: Persistence Layer - ProductRepository

**File:** `src/tr/edu/mu/se3006/persistence/ProductRepository.java`

**Implementation:**

- **HashMap Database:** Used `Map<Long, Product>` to store products with their IDs as keys
- **findById(Long id):** Retrieves a product by ID; returns null if not found
- **save(Product product):** Updates or inserts a product into the database
- **Pre-loaded Data:** Constructor initializes the database with sample products:
  - MacBook Pro (ID: 1, Stock: 5)
  - Logitech Mouse (ID: 2, Stock: 20)

**Key Features:**

- No knowledge of business rules or ordering logic
- Pure data access operations
- Provides abstraction over storage mechanism

---

### TASK 2: Business Layer - OrderService

**File:** `src/tr/edu/mu/se3006/business/OrderService.java`

**Implementation:**

- **Constructor Injection:** Accepts `ProductRepository` dependency via constructor
- **placeOrder(Long productId, int quantity):**
  1. Finds the product via repository
  2. Validates stock availability (throws `IllegalArgumentException` if insufficient)
  3. Reduces product stock by the order quantity
  4. Saves the updated product back to repository

**Business Rules Enforced:**

- Stock availability check
- Stock reduction logic
- Exception handling for invalid orders

**Dependency:** ProductRepository (injected via constructor)

---

### TASK 3: Presentation Layer - OrderController

**File:** `src/tr/edu/mu/se3006/presentation/OrderController.java`

**Implementation:**

- **Constructor Injection:** Accepts `OrderService` dependency via constructor
- **handleUserRequest(Long productId, int quantity):**
  - Displays request details
  - Wraps service call in try-catch block
  - Shows ✅ Order Confirmed on success
  - Shows ❌ ERROR: [message] on failure
  - Handles and displays exceptions gracefully

**User Interaction:**

- Simple interface for the user to submit orders
- Clear feedback on success/failure
- Decoupled from business logic

**Dependency:** OrderService (injected via constructor)

---

### TASK 4: System Bootstrapping - Main Class

**File:** `src/tr/edu/mu/se3006/Main.java`

**Implementation:**

- **Bottom-Up Wiring:** Objects created and wired from lowest to highest layer
  1. Create `ProductRepository` (foundation)
  2. Create `OrderService`, inject `ProductRepository`
  3. Create `OrderController`, inject `OrderService`
- **Test Scenarios:** Three test cases demonstrate system functionality:
  1. Valid order (Product ID: 1, Quantity: 2) → Success
  2. Insufficient stock (Product ID: 1, Quantity: 10) → Failure
  3. Product not found (Product ID: 3, Quantity: 1) → Failure

---

## 🧪 Test Results

### Test Case 1: Valid Order

```
Request: Product ID=1, Quantity=2
Expected: Order Confirmed
Result: ✅ Success
Details: Reduced MacBook Pro stock from 5 to 3
```

### Test Case 2: Insufficient Stock

```
Request: Product ID=1, Quantity=10
Expected: Error message for insufficient stock
Result: ✅ Exception caught and displayed
Details: Attempted to order more units than available
```

### Test Case 3: Product Not Found

```
Request: Product ID=3, Quantity=1
Expected: Error message for product not found
Result: ✅ Exception caught and displayed
Details: Product ID 3 does not exist in repository
```

---

## 📊 Code Quality Observations

### Strengths

1. **Clear Separation of Concerns:** Each layer has distinct responsibilities
2. **No Framework Dependencies:** Pure Java demonstrates fundamental architecture principles
3. **Manual Dependency Injection:** Explicit wiring makes dependencies visible
4. **Exception Handling:** Proper error propagation and user-friendly error messages
5. **Immutability Considerations:** Objects maintain consistent state through proper encapsulation

### Design Patterns Applied

- **Layered Architecture Pattern:** Horizontal separation by technical responsibility
- **Repository Pattern:** Product persistence abstraction
- **Service Layer Pattern:** Centralized business logic
- **Dependency Injection:** Manual constructor-based injection

---

## 💡 Key Learnings

1. **Strict Layering Benefits:**
   - Lower layers can be tested independently
   - Changes in upper layers don't affect persistence logic
   - Clear architectural boundaries improve maintainability

2. **Dependency Injection:**
   - Manual wiring makes dependencies explicit
   - Easier to understand object relationships
   - Facilitates testing with mock objects

3. **Data Flow Direction:**
   - Strict unidirectional flow (top → bottom) simplifies reasoning
   - Prevents circular dependencies
   - Enforces proper abstraction levels

4. **Framework Abstraction:**
   - Frameworks like Spring Boot automate this wiring
   - Understanding manual implementation highlights what happens "under the hood"
   - Pure Java approach provides foundation for framework understanding

---

## 🔄 Architectural Verification

### Dependency Check

- ❌ Presentation → Persistence: Not allowed (correctly maintained)
- ❌ Persistence → Business: Not allowed (correctly maintained)
- ✅ Presentation → Business: Allowed (implemented)
- ✅ Business → Persistence: Allowed (implemented)
- ✅ Persistence → Domain: Allowed (implemented)

### Exception Propagation

```
User Request
    ↓
OrderController.handleUserRequest()
    ↓
OrderService.placeOrder()
    ↓
ProductRepository.findById()
    ↓
    (Exception if business rule violated)
    ↓
OrderController catches and displays error
```

---

## 📝 Conclusion

This laboratory successfully demonstrated how a three-tier layered architecture enforces separation of concerns through manual dependency injection and strict layering rules. The implementation shows that without frameworks, explicit wiring makes architectural boundaries clear and dependencies transparent. The system effectively handles both successful operations and error cases while maintaining strict layer independence.

The hands-on experience of manually wiring components provides valuable insight into what frameworks like Spring Boot handle automatically, making this a foundational exercise for understanding enterprise application architecture.

---

## 📚 References

- Domain Models: `Product`, `Order`
- Repository Pattern: `ProductRepository`
- Service Layer: `OrderService`
- Presentation: `OrderController`
- Entry Point: `Main`

**Total Implementation Time:** [Estimated 1-2 hours for completion]

---

_Lab Report Completed: April 2026_
