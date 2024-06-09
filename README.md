# WireRouter
Takes an input file and returns the best wire path for the input file.

## About :books:

This program takes in an input file and than proceeds to find the best path to get from point A -> point B.
This is without running into something such as another wire, a wall, or an obstacle. :bricks:


The program uses a Breaths-First Search in order to get the initial results, and than if it cannot get the result, it will reorder the wires and try to solve again to find a better path.

## Try Yourself :hammer_and_wrench:

If you want to try this out for yourself?


All you need to do is put in a .input file and run it through the test to see it do it's job and solve it.
And you need to configure the input file, like so:

`3
3
0
2
0 0 0 2
1 1 2 2`

**PLEASE NOTE THAT WHATEVER YOU MAKE IS ACTUALLY FEASABLE FOR A BETTER RESULT, OTHERWISE YOU WILL GET THE ERROR RESULT** :exclamation:

The first line in that code block is going to be the amount of rows. Which in this case is 3.

The second line in that code block is going to be the amount of columns. Which in this case is also 3.

The third line is going to denote the amount of obstacles that you have, every line after this one is going to be the obstacle path, which is going to have a start coordinate (x,y) and end coordinate (x,y) in the form x_1 y_1 x_2 y_2.

The line after those coordinate lines are going to be the points that you want to connect, using the same format as the obstacles.
