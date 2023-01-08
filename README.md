## Online order processing simulator
#### Copyright Adina Amzarescu 2022

_______________________________________________________________________________

The main idea:

Implement a Black Friday order processor in Java, which uses parallel 
programming. 

The idea of the project is to process orders in parallel, 
as well as each product separately (even within the same order).

_______________________________________________________________________________

#### Sources:

* Tema2.java

    * This is the main class. Here the input will be accessed, the threads will
      start and end. In the end the output will be written in specific the 
      files.

* ReadInputFiles.java

    * There are 2 types of files. One for orders and one for products.

* Order.java

    * This class defines an order. Each order has an id, a number of products
      and a status. At the beginning each status will be "in progress" by
      default. After an order is processed the status will become "shipped".

* OrderProcessor.java

    * This class will check if all products within the same order have been
    shipped. Before going through the list of products the lock for products
    is enabled. The same goes for orders when the status is changed to
    "shipped". After those actions the locks are unlocked. This way the threads
    are synchronized.

* OrderProcessorThread.java

    * This class calls processOrder() for each order within the orders' list.

* OrderProduct.java

    * This class defines a product. Each product has an id of the order it is
      a part of, an id for the product, a status and an integer (abandoned). At
      the beginning each status will be "in progress" by default. After a 
      product is processed the status will become "shipped". The integer works
      similar to a boolean value. If a product has an order id that is found
      in the list of orders with the number of products equal to 0, then that 
      order was never placed. In this case the product will not be shipped.

* ProductProcessor.java

    * This class checks if a product exists (if the order id is found in the 
      list of orders) and if a product is abandoned. A lock will be enabled 
      for the products before setting the abandoned variable to 1.

* ProductProcessorThread.java

    * This class calls processOrderProducts. If a product is not abandoned then
      the status will become shipped.

* WriteOutputFiles.java

    * There are 2 files. One is for orders and the other one is for products.
      The output for orders will exclude orders with 0 items and the output
      for products will exclude the abandoned products.

_______________________________________________________________________________

### Flow and logic:

When the program starts there are 2 lists. One is for orders and the other is
for the products. each order and product is read by ReadInputFiles from the
input folder (given as the first argument).

Then 2 Reentrant lock are created. One is for the orders and one is for the 
products. After this the threads are created and started.

In the orderProcessorThread each order will be processed. Before an order
can be processed, the lock for Products will be enabled. For every product
of an order (defined as the variable "op") the id will be checked to match 
the current order and then if its status is "in progress" then that product
hasn't been processed yet so the order can't be shipped. The order's status
will stay "in progress" until all the products have been shipped. When
all products have been shipped the "allShipped" variable will become *true*.
If an order is ready to be shipped the ordersLock will lock and the status 
will become "shipped". After this the ordersLock will unlock. In the end the
orderProductsLock will unlock.

For the products there are 2 cases. A product may be abandoned or is possible
that it doesn't exist. So when each product is processed the id of the product
will be searched in the list of orders (with the variable orderIndex). At first
all products are considered non-existent. If the product is found then the
variable "exists" will become true. If a product exists but the number of
products within that order is 0 then the product is set as "abandoned".
If the product is abandoned or does not exist then it won't be shipped.


After this the program will wait for all threads to finish and will join them.

Then the orders will be printed in "orders_out.txt" and the products will be 
printed in "order_products_out.txt".

WriteOutputFiles will write only the orders that have at least 1 product and
only the products that exist and are not abandoned.

_______________________________________________________________________________

Resources

   1. [OCW](https://ocw.cs.pub.ro/courses/apd/laboratoare/05)

   2. [Reentrant Lock in Java](https://www.geeksforgeeks.org/reentrant-lock-java/)

   3. [Synchronization and Locks](https://winterbe.com/posts/2015/04/30/java8-concurrency-tutorial-synchronized-locks-examples/)
   
   _______________________________________________________________________________
