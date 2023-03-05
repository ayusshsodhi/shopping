# Shopping Basket Price Calculator App

For a Given Shopping Basket List, the app calculates the total price of the basked considering the discounts/offers
currently running.

## Run the App

The business logic is exposed in the form of an API which expects the input of a List of Item Names(String) present in 
the basket -

To Test the functionality,

1. Simply run the ShoppingApplication Class on the Default Port in an environment which supports Java 17
2. Hit the exposed api /checkout from postman with a List of String(Item Names) as body parameter like -
   [
   "Apple", "Banana", "Melon", "Lime", "Melon", "Melon","Lime","Lime","Lime","Lime"
   ]
3. For ease of testing, the prices and offers currently are kept to the ones mentioned in the assignment.
   - Apples are 35p each
   - Bananas are 20p each
   - Melons are 50p each, but are available as ‘buy one get one free’
   - Limes are 15p each, but are available in a ‘three for the price of two’ offer
4. To test different scenarios, you can modify offers by creating/modifying PricingAssignment object in either 
controller or Test classes
5. The total price of the basket in p is returned and is also printed in console

### Assumptions

1. Currently, the app supports one offer on one Item, if we intend to have multiple offers on a single Item, we can 
accommodate that by looping over the offers to apply the correct price, provided, the offers don't conflict with each 
other. for example - BuyXGetYFree and XForThePriceOfY cannot be applied on same item simultaneously.
2. A Tracker is used to ensure discounts/offers are applied correctly as it was mentioned that 
"_**Items are presented one at a time, in a list**_". Otherwise we can simply get the count of each item in the list
and do the pricing/apply offers, accordingly.
3. The pricingAssignments/Offers are initialized to accommodate the scenario in question. It can be kept as an input as
well
4. It is assumed that you cannot have more free items than the items purchased and that there are no free items

Note : I was facing some IDE error with Google-java-format plugin because of the community edition of Intellij,
therefore formatting is done using default formatter.