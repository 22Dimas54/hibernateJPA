select o.productName from Order o, Customer c
where c.id = o.customerId.id and lower(c.name)= :nameCustomer