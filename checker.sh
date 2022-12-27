#!/bin/bash

correct=0

# Final score
function show_score {
	echo ""
	echo "Total: $correct/100"
}

# Compare outputs
function compare_outputs {
    diff -q $1 $2
    if [ $? == 0 ]
    then
        correct=$((correct+5))
    else
        echo "W: There are differences between $1 and $2"
    fi
}

echo "VMCHECKER_TRACE_CLEANUP"
date

# Compile project
cd ../src
make clean &> /dev/null
make build &> build.txt

if [ ! -f Project.class ]
then
	echo "E: Couldn't compile"
	cat build.txt
	show_score
	rm -rf build.txt
	exit
fi

rm -rf build.txt

mv *.class ../checker
cd ../checker

no_threads=("2" "4" "4" "8" "8" "12" "16" "18" "20" "24")
for i in `seq 0 9`
do
	echo ""
	echo "======== Test ${i} ========"
	echo ""
	# rm -rf orders_out.txt
	# rm -rf order_products_out.txt

    java a.out input/input_${i} ${no_threads[$i]} &> /dev/null

	# Check results
    sort -o orders_out.txt orders_out.txt
    compare_outputs output/output_${i}/orders_out.txt orders_out.txt
    sort -o order_products_out.txt order_products_out.txt
    compare_outputs output/output_${i}/order_products_out.txt order_products_out.txt

	echo "End test"
	echo ""

	echo "=========================="
	echo ""
done

make clean &> /dev/null

show_score
