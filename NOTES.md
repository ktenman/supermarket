# Notes

* I added UUID and discount fields to Product class. 
* I assume that each Product has only one Discount.
* I classified pricing discount schemes into three:

Type | Example
------------ | -------------
BY_COUNT | Buy one, get one free
BY_COUNT | Buy three items for the price of two
STATIC_PRICE | Buy two items for £1
PERCENTAGE | Buy one kilo of vegetables for half price

###For the simplicity of this task 
* I assume that there is __no minimum weighted amount__ when the discount by percentage 
applies. 
* There no minimum/maximum limits how much items the customer can buy.

###Ideas for the improvement
* Each product could have multiple discounts
* Each discount should have start at and finished at dates.

###Nice to have ideas for the future
* Each product could have a name
* Weighted product could have some logic around unit