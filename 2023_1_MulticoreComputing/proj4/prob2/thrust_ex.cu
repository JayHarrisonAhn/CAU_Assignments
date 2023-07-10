#include <thrust/device_vector.h>
#include <thrust/transform.h>
#include <thrust/sequence.h>
#include <thrust/copy.h>
#include <thrust/fill.h>
#include <thrust/replace.h>
#include <thrust/functional.h>
#include <iostream>

using namespace thrust::placeholders;

int N = 1000000000;
double step = 1.0/(double)N;

struct function {
    __host__ __device__
    double operator()(const double& x) const {
        return 4 / (1 + x * x);
    }
};

int main ()
{
    clock_t tStart = clock();
    thrust::device_vector<double> Y(N);
    thrust::sequence(Y.begin(), Y.end(), step*0.5, step);
    thrust::transform(Y.begin(), Y.end(), Y.begin(), function());
    thrust::transform(Y.begin(), Y.end(), Y.begin(), step * _1);
    double integral = thrust::reduce(Y.begin(), Y.end());
    clock_t tEnd = clock();
    printf("Execution Time : %fms\n", (double)(tEnd-tStart)/CLOCKS_PER_SEC*1000);
    printf("pi=%.10lf\n", integral);
    return 0;
}
