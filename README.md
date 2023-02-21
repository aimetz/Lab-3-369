# 369-Lab3

I am going to use 2 days of my 4 day grace period for this assignment and take the late penalty

I am not very proficient in Java and I should have come in for help sooner as I couldnt figure out the error messages I was getting


What I tried to do:
1) Map side Join. Use hashmap of country names for each IP in order to use country name as key and 1 as the value. Then use a second map reduce to switch the key and value in map so that they would be sorted by count and then switch them back in reduce

2)Map side join again. Use IP and URL as the key but use a hashmap to replace IP with country name. Use 1 as the value and then reduce should just sum all of the ones for each key. The keys should end up already sorted correctly

3) reduce side Join. Map Should make URL the key and IP the Value. Then reduce should change the IPs to the corresponding countries, but only keep unique countries, and then sort the list and output the same key but a sorted list of countries instead.

