#include <stdio.h>
#include <stdlib.h>
#include <omp.h>

#define NUM_END 200000

int isPrime(int x) {
	int i;
	if (x <= 1) return 0;
	for (i = 2; i < x; i++) {
		if (x % i == 0) return 0;
	}
	return 1;
}

void countPrime(int i, int* counter) {
	if (isPrime(i)) {
#pragma omp critical
		(*counter)++;
	}
}

int main(int argc, char* argv[]) {
	int NUM_THREADS = atoi(argv[2]);
	int schedulingType = atoi(argv[1]);
	int counter = 0, i;
	double t1, t2;
	t1 = omp_get_wtime();
	switch (schedulingType) {
	case 1:
#pragma omp parallel for num_threads(NUM_THREADS) schedule(static)
		for (i = 0; i < NUM_END; i++) countPrime(i, &counter);
		break;
	case 2:
#pragma omp parallel for num_threads(NUM_THREADS) schedule(dynamic)
		for (i = 0; i < NUM_END; i++) countPrime(i, &counter);
		break;
	case 3:
#pragma omp parallel for num_threads(NUM_THREADS) schedule(static, 10)
		for (i = 0; i < NUM_END; i++) countPrime(i, &counter);
		break;
	case 4:
#pragma omp parallel for num_threads(NUM_THREADS) schedule(dynamic, 10)
		for (i = 0; i < NUM_END; i++) countPrime(i, &counter);
		break;
	}
	t2 = omp_get_wtime();
	printf("Program Execution Time : %fms\n", (t2 - t1) * 1000);
	printf("1...%d prime# counter=%d\n", NUM_END - 1, counter);
	return 0;
}