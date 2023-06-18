#include <iostream>
#include <vector>
#include <climits>
#include "omp.h"

using namespace std;

const int arr_size = 20000;
vector<vector<int>> arr(arr_size, vector<int>(arr_size));

void init_arr();
pair<int, int> find_row_min_sum(int num_threads);
long long calculate_total_sum(int num_threads);

int main() {
	init_arr();

	omp_set_nested(1);
	double t1 = omp_get_wtime();
#pragma omp parallel sections
	{
#pragma omp section
		{
			auto [row, min_sum] = find_row_min_sum(16);
			cout << "Row with min sum = " << row << ", sum = " << min_sum << endl;
		}
		
#pragma omp section
		{
			cout << "Total sum = " << calculate_total_sum(16) << endl;
		}
	}
	double t2 = omp_get_wtime();
	
	cout << "Total time - " << t2 - t1 << " seconds" << endl;
	return 0;
}

void init_arr() {
	for (int i = 0; i < arr_size; i++) {
		for (int j = 0; j < arr_size; j++) {
			arr[i][j] = i + j;
		}
	}
}

pair<int, int> find_row_min_sum(int num_threads) {
	int min_row = 0;
	int min_sum = INT_MAX;
	#pragma omp parallel for num_threads(num_threads)
	for (int i = 0; i < arr_size; i++) {
		int row_sum = 0;
		for (int j = 0; j < arr_size; j++) {
			row_sum += arr[i][j];
		}
		#pragma omp critical
		if (row_sum < min_sum) {
			min_sum = row_sum;
			min_row = i;
		}
	}
	return {min_row, min_sum};
}

long long calculate_total_sum(int num_threads) {
	long long total_sum = 0;
	#pragma omp parallel for reduction(+:total_sum) num_threads(num_threads)
	for (int i = 0; i < arr_size; i++) {
		for (int j = 0; j < arr_size; j++) {
			total_sum += arr[i][j];
		}
	}
	return total_sum;
}
