**1. How did you achieve functional cohesion? Which routines did you extract?**
Functional cohesion was achieved by taking the large, procedural `processCustomer` method and breaking it down so that each new routine performs exactly one specific task. 
I extracted the following routines:
* `calculateTotalOrders()`: Focuses solely on summing the array and validating positive inputs.
* `calculateDiscount()`: Maps the customer tier to a discount rate and catches invalid types.
* `generateInvoiceMessage()`: Handles string formatting and VIP logic.
* `notifyCustomer()`: Isolates the email dependency and null-checking logic.

**2. What parameter passing issues did you encounter (e.g., `d` modified but not returned)?**
In the original code, the parameter `d` (representing the current balance) was a primitive `double`. 
Because Java passes primitives by value, the assignment `d = total;` only modified the local copy of the variable inside the method. 
The actual variable passed by the caller remained unchanged. 
I solved this by returning the new total so the caller can update their own state.

**3. How would the `d` update behave differently if the language used pass-by-value-result?**
If the language used pass-by-value-result, a local copy of `d` would still be made at the start of the routine. 
However, upon exiting the routine (copy-out phase), the final value of the local `d` (which was updated to equal `total`) would be automatically copied back into the actual parameter in the caller's scope. 
The caller's variable would successfully reflect the new balance.