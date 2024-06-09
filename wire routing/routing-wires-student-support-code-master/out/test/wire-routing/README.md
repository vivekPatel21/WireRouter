1.) The algorithm solves a board of any n x n size.
There are multiple checks in place through the code to make sure that is possible,
these include removing wire and changing up the goal order in order to make sure that we get the 
best wire length possible, as well as making sure that it is actually possible. As well as that there 
are checks for special cases such as 1 x 1 boards, in where we check if the coordinate is equal start to end. 

2.)This algorithm case solve some pretty amazing boards such as:

|  1   0  -1   0   0   0   0   0   0   0 |

|  0   0  -1   0   0   0   2   0   0   0 |

|  0   0  -1   0   0   0   0   0   0   0 |

|  0   0  -1   1   0   0   0   0   0   0 |

|  0   0   0   0   0   0   0   0   0   0 |

|  0   0   0   0   0   0   2   0   0   0 |

|  0   0   0   0  -1   0   0   0   0   0 |

|  0   0   0   0  -1   0   0   0   0   0 |

|  0   0   0   0  -1   0   0   0   0   0 |

|  0   0   0   0   0   0   0   0   0   0 |
This being a 10 x 10 board with 2 obstacles and 2 goals. 

As well as smaller boards such as:

|  1   0   2   0   0 |

|  0   0   0   1   0 |

|  0   0   2   0   0 |

|  0   0   0   0   0 |

|  0   0   0   0   0 |

This being a board that has to really make sure that it does not cross something 
such as an endpoint or another wire to reach its goal, making a unique shape in the process. 

3.) This algorithm was made with the idea of using as few wires as possible, this was done through the bestwires idea
that is shown in the algorithm, in where we move wires around in order to see if it is possible to find a shorter route
, leading to newer wires that the program may use instead. Resulting on those newer wires
being placed on the board rather than the inferior ones that were either longer, or did not work.

4.) With the regards time complexity, due to the nature of Breadth First Search we can say that the
time complexity by the BFS function in the code is approximately O(N*V), where N is going to be the 
number of endpoints and V the vertices on the grid. When we look at the check for "bestwires"
we can see that it uses a nested for loop with an average of O(n^2) as the i and j in that nested loop are 
both going to n, which is equal to bestWires.size(). So for the overall find paths we can say for consistency that the 
big-O notation is O(N*V) as we must traverse through the graph of N*V which in most cases will be larger
than something such as O(n^2) as we only consider wires for that while we consider the whole graph for
O(N*V).

As for the wall clock time, that is largely going to be dependent on the board, and how far
the points are on the board. 