# WireRouter
Takes an input file and returns the best wire path for the input file.

## About

This program takes in an input file and than proceeds to find the best path to get from point A -> point B.
This is without running into something such as another wire, a wall, or an obstacle. :bricks:


The program uses a Breaths-First Search in order to get the initial results, and than if it cannot get the result, it will reorder the wires and try to solve again to find a better path.

## Try Yourself

If you want to try this out for yourself?


All you need to do is put in a .input file and run it through the test to see it do it's job and solve it.
And you need to configure the input file, like so:

3

3

0

2

0 0 0 2

1 1 2 2

