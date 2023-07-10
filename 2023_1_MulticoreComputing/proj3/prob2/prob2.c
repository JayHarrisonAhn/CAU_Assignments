#include <omp.h>
#include <stdio.h>

long num_steps = 10000000;
double step;

double calculatePi(long i) {
	double x = (i + 0.5) * step;
	return 4.0 / (1.0 + x * x);
}

int main(int argc, char* argv[]) {
	int schedulingType = atoi(argv[1]);
	int SIZE_CHUNK = atoi(argv[2]);
	int NUM_THREADS = atoi(argv[3]);

	long i; double x, pi, sum = 0.0;
	double start_time, end_time;

	start_time = omp_get_wtime();
	step = 1.0 / (double)num_steps;
	switch (schedulingType) {
	case 1:
#pragma omp parallel for reduction(+:sum) schedule(static, SIZE_CHUNK) num_threads(NUM_THREADS)
		for (i = 0; i < num_steps; i++) sum += calculatePi(i);
		break;
	case 2:
#pragma omp parallel for reduction(+:sum) schedule(dynamic, SIZE_CHUNK) num_threads(NUM_THREADS)
		for (i = 0; i < num_steps; i++) sum += calculatePi(i);
		break;
	case 3:
#pragma omp parallel for reduction(+:sum) schedule(guided, SIZE_CHUNK) num_threads(NUM_THREADS)
		for (i = 0; i < num_steps; i++) sum += calculatePi(i);
		break;
	}
	pi = step * sum;
	end_time = omp_get_wtime();
	double timeDiff = end_time - start_time;
	printf("Execution Time : %lfs\n", timeDiff);

	printf("pi=%.24lf\n", pi);
	return 0;
}
