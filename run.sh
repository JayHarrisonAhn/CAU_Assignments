cd threads
make
cd build

trap "cd ../../; make clean;" SIGINT
../../utils/pintos mfq $1