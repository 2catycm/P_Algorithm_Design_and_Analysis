#!/usr/bin/env bash
# don't use windows to run, use wsl.
export EVOSUITE="java -jar ./evosuite-1.0.6.jar"

$EVOSUITE -class lab3.ProblemB_AlternatingShortestPath -projectCP ../../../../../target/classes