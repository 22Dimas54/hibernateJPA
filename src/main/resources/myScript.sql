select p
from Persons p
where lower(p.cityOfLiving.name)= :nameCity