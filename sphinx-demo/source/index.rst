.. Plant UML 9000 Demo documentation master file, created by
   sphinx-quickstart on Sun Sep 28 21:59:24 2014.
   You can adapt this file completely to your liking, but it should at least
   contain the root `toctree` directive.

Welcome to Plant UML 9000 Demo's documentation!
===============================================

Salt9000 examples
=================

Complete test with Salt9000:

.. uml::
   :scale: 75
   
   @startsalt9000
   {
   {* File | Edit | Help}
   {
     {/ <b>Ticket | Other tab }
     {Ticket no: | "655321" | Date: | "2014-08-23" }
     {Shop: | ^Las Vegas^ | . | . }
     {() Radio unchecked}
     {(X) Radio checked}
     {[] Check box unchecked}
     {[X] Check box checked}
     {#
       Product | Quantity | Price
       USB pen | 1 | 10 
       DVD | 2 | 1 
        .  | . | . 
     }
     {[New] | [Edit] | [Delete] }
     {Total: | "12.00" }
     {[Save] | [Cancel] }
   }
   }
   @endsalt9000

The same example rendered with original Salt:

.. uml::
   :scale: 75
   
   @startsalt
   {
   {* File | Edit | Help}
   {
     {/ <b>Ticket | Other tab }
     {Ticket no: | "655321" | Date: | "2014-08-23" }
     {Shop: | ^Las Vegas^ | . | . }
     {() Radio unchecked}
     {(X) Radio checked}
     {[] Check box unchecked}
     {[X] Check box checked}
     {#
       Product | Quantity | Price
       USB pen | 1 | 10 
       DVD | 2 | 1 
        .  | . | . 
     }
     {[New] | [Edit] | [Delete] }
     {Total: | "12.00" }
     {[Save] | [Cancel] }
   }
   }
   @endsalt

