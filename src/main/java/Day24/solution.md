## Solved by hand:

* Reducing the (very regular) input commands to pseudo code yields the following steps:
```
set z = 0

// Block 1
read in new w
if (z % 26 + 14 != w)
    set z = 26 * z + (w + 1)

// Block 2
read in new w
if (z % 26 + 15 != w)
    set z = 26 * z + (w + 7)

// Block 3
read in new w
if (z % 26 + 15 != w)
    set z = 26 * z + (w + 13)

// Block 4
read in new w
set z = z / 26
if (z % 26 - 6 != w)
    set z = 26 * z + (w + 10)

// Block 5
read in new w
if (z % 26 + 14 != w)
    set z = 26 * z + w

// Block 6
read in new w
z = z / 26
if (z % 26 - 4 != w)
    set z = 26 * z + (w + 12)

// Block 7
read in new w
if (z % 26 + 15 != w)
    set z = 26 * z + (w + 11)

// Block 8
read in new w
if (z % 26 + 15 != w)
    set z = 26 * z + (w + 6)

// Block 9
read in new w
if (z % 26 + 11 != w)
    set z = 26 * z + (w + 1)

// Block 10
read in new w
set z = z / 26
if (z % 26 != w)
    set z = 26 * z + (w + 7)

// Block 11
read in new w
set z = z / 26
if (z % 26 != w)
    set z = 26 * z + (w + 11)

// Block 12
read in new w
set z = z / 26
if (z % 26 - 3 != w)
    set z = 26 * z + (w + 14)

// Block 13
read in new w
set z = z / 26
if (z % 26 - 9 != w)
    set z = 26 * z + (w + 4)

// Block 14
read in new w
set z = z / 26
if (z % 26 - 9 != w)
    set z = 26 * z + (w + 10)
```

* So z is a number which is built up as an offset expansion of the digits w to base 26.
* Seven of these blocks are increasing z, seven of them are decreasing them.
* So, in order to get z = 0 eventually, we have to "use" all decreasing opportunities. 
* Furthermore, we must not increase z in those increasing blocks again. We can only prevent this by making the modulo equation true.
* This means that the following blocks have to "cancel each other out" 
and there creates the following rules for out digits w_1 to w_14
  * 4 cancels out 3 
    * => w_3 + 13 - 6 = w_4 <=> w_3 + 7 = w_4
  * 6 cancels out 5 
    * => w_5 + 0 - 4 = w_6 => w_5 - 4 = w_6
  * 10 cancels out 9 
    * => w_9 + 1 - 0 = w_10 => w_9 + 1 = w_10
  * 11 cancels out 8 
    * => w_8 + 6 - 0 = w_11 => w_8 + 6 = w_11
  * 12 cancels out 7 
    * => w_7 + 11 - 3 = w_12 => w_7 + 8 = w_12
  * 13 cancels out 2 
    * => w_2 + 7 - 9 = w_13 => w_2 - 2 = w_13
  * 14 cancels out 1 
    * => w_1 + 1 - 9 = w_14 => w_1 - 8 = w_14
* Using these equations it becomes very easy to deduce the highest and lowest numbers which are accepted.
* Just go through the digits from left to right and choose them as high/low as possible while considering the rules.

```
Highest Number:
01|02|03|04|05|06|07|08|09|10|11|12|13|14
9 |9 |2 |9 |9 |5 |1 |3 |8 |9 |9 |9 |7 |1

Lowest Number:
01|02|03|04|05|06|07|08|09|10|11|12|13|14
9 |3 |1 |8 |5 |1 |1 |1 |1 |2 |7 |9 |1 |1
```


