
#include <stdio.h>

#include "threads/thread.h"
#include "devices/timer.h"
#include "threads/synch.h"
#include "projects/crossroads/vehicle.h"
#include "projects/crossroads/map.h"


#define ANSI_NONE "\033[0m"
#define ANSI_BLACK "\033[30m"
#define ANSI_RED "\033[31m"
#define ANSI_GREEN "\033[32m"
#define ANSI_YELLOW "\033[33m"
#define ANSI_BLUE "\033[34m"
#define ANSI_MAGENTA "\033[35m"
#define ANSI_CYAN "\033[36m"
#define ANSI_WHITE "\033[37m"

#define ON_ANSI_BLACK "\033[40m"
#define ON_ANSI_RED "\033[41m"
#define ON_ANSI_GREEN "\033[42m"
#define ON_ANSI_YELLOW "\033[43m"
#define ON_ANSI_BLUE "\033[44m"
#define ON_ANSI_MAGENTA "\033[45m"
#define ON_ANSI_CYAN "\033[46m"
#define ON_ANSI_WHITE "\033[47m"

#define clear() printf("\033[H\033[J")
#define gotoxy(y,x) printf("\033[%d;%dH", (y), (x))

#define DEBUG_MODE 0


const char map_draw_default[7][7] = {
	{'X', 'X', ' ', 'X', ' ', 'X', 'X'}, 
	{'X', 'X', ' ', 'X', ' ', 'X', 'X'}, 
	{' ', ' ', ' ', '-', ' ', ' ', ' '}, 
	{'-', '-', '-', '-', '-', '-', '-'}, 
	{' ', ' ', ' ', '-', ' ', ' ', ' '}, 
	{'X', 'X', ' ', '-', ' ', 'X', 'X'}, 
	{'X', 'X', ' ', '-', ' ', 'X', 'X'}, 
};

int num_of_vehicles = 0;
int drawn_vehicles = 0;

void map_draw(void)
{
	int i, j;

	/* Count number of threads before sync */
	if(num_of_vehicles == 0) {
		if(num_of_vehicles < drawn_vehicles) {
			// initialize

			map_drawn = malloc(sizeof(struct condition));
			cond_init(map_drawn);

			num_of_vehicles = drawn_vehicles;
			drawn_vehicles = 0;
		}
	} 
	if(num_of_vehicles != 0) {
		wait_until_all_movable_vehicles_move();
	}

	if(!DEBUG_MODE) {
		clear();

		for (i=0; i<7; i++) {
			for (j=0; j<7; j++) {
				printf("%c ", map_draw_default[i][j]);
			}
			printf("\n");
		}
		printf("unit step: %d\n", crossroads_step++);
		gotoxy(0, 0);
	}
}

void map_draw_vehicle(char id, int row, int col)
{
	drawn_vehicles += 1;

	if(!DEBUG_MODE) {
		if (row >= 0 && col >= 0) {
			gotoxy(row + 1, col * 2 + 1);
			printf("%c ", id);
			gotoxy(0, 0);
		}
	}
	if(num_of_vehicles > 0) {
		if(num_of_vehicles == drawn_vehicles) {
			/* If this is this step's last draw */
			vehicles_list_lock_acquire();
			vehicles_list_drawn_signal();
			vehicles_list_lock_release();
			drawn_vehicles = 0;
		}
	}
}

void map_draw_reset(void)
{
	if(!DEBUG_MODE) {
		clear();
	}
}

void wait_until_all_movable_vehicles_move() {
	vehicles_list_lock_acquire();
	while(true) {
		struct vehicle_info *vehicle_not_moved = vehicles_not_moved_yet();
		if(vehicle_not_moved == NULL) {
			break;
		} else {
			printf("안움직:(%c,%d,%d)", vehicle_not_moved->id, vehicle_not_moved->position_next.row, vehicle_not_moved->position_next.col);
			// vehicles_list_lock_release_except(vehicle_not_moved);
			cond_wait(vehicle_not_moved->vehicle_move, vehicle_not_moved->lock);
			// vehicles_list_lock_acquire_except(vehicle_not_moved);
		}
	}
	vehicles_list_lock_release();
}