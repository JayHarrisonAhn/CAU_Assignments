make clean
cd threads
make
cd build
../../utils/pintos mfq $1
cd ../../