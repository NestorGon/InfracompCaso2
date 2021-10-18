#!/bin/bash
cd ~/InfracompCaso2/Caso2/src/
for f in ~/InfracompCaso2/Caso2/data/pruebas/*
do
	echo "Processing $f"
	output="$(java main.Monitor <<<  $f)" 
	name=(${f//_/ })
	echo "${name[1]},${name[2]%".txt"},$output" >> ~/InfracompCaso2/Caso2/resultados.txt
done
