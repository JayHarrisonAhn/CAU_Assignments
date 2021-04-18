
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

			inner_crossroad_sema = malloc(sizeof(struct semaphore));
			sema_init(inner_crossroad_sema, 7);

			num_of_vehicles = drawn_vehicles;
			drawn_vehicles = 0;
		}
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
			// printf("1");
			move_vehicles_in_crossroad_and_wait();
			// printf("2");
			move_vehicles_not_in_crossroad_and_wait();
			// printf("3");
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

/*	move vehicles after crossroad prior than vehicles that are before crossroad
		and wait until they're all moved
		(all vehicles in crossroad should move)	*/
void move_vehicles_in_crossroad_and_wait() {
	vehicles_list_make_not_movable();
	struct vehicle_info_link *last_link = vehicles_list;
	while(last_link != NULL) {
		struct vehicle_info *vi = last_link->vi;
		lock_acquire(vi->lock);
		if(vehicle_after_crossroad(vi)) {
			/*	make vehicles movable that are after crossroad	*/
			vi->state = VEHICLE_STATUS_RUNNING;
			cond_broadcast(map_drawn, vi->lock);
			vi->movable = 1;
			cond_broadcast(vi->became_movable, vi->lock);
		}
		lock_release(vi->lock);
		last_link = last_link->next;
	}
	last_link = vehicles_list;
	while(last_link != NULL) {
		struct vehicle_info *vi = last_link->vi;
		lock_acquire(vi->lock);
		if(vehicle_after_crossroad(vi)) {
			if(vi->state == VEHICLE_STATUS_RUNNING) {
				/* wait until all vehicles after crossroad move */
				cond_wait(vi->vehicle_move, vi->lock);
			}
		}
		lock_release(vi->lock);
		last_link = last_link->next;
	}
}

/*	move vehicles before crossroad
		and wait until all vehicles move that can move 	*/
void move_vehicles_not_in_crossroad_and_wait() {
	struct vehicle_info_link *last_link = vehicles_list;
	while(last_link != NULL) {
		struct vehicle_info *vi = last_link->vi;
		if(!vehicle_after_crossroad(vi)) {
			/*	make vehicles movable that are before crossroad	*/
			lock_acquire(vi->lock);
			if(vi->state == VEHICLE_STATUS_MOVED) {
				vi->state = VEHICLE_STATUS_RUNNING;
			}
			if((vi->state == VEHICLE_STATUS_READY)||(vi->state == VEHICLE_STATUS_RUNNING)) {
				cond_broadcast(map_drawn, vi->lock);
				vi->movable = 1;
				cond_broadcast(vi->became_movable, vi->lock);
			}
			lock_release(vi->lock);
		}
		last_link = last_link->next;
	}
	last_link = vehicles_list;

	vehicles_list_lock_acquire();
	while(true) {
		/* find vehicle that doesn't move even though it can */
		struct vehicle_info *not_moved_vehicle = vehicles_not_moved_yet();
		if(not_moved_vehicle == NULL) {
			/*	if all vehicles move, it's done	*/
			break;
		} else {
			/*	wait until that vehicle move	*/
			cond_wait(not_moved_vehicle->vehicle_move, not_moved_vehicle->lock);
		}
	}
	vehicles_list_lock_release();
}

/*	find a vehicle that doesn't move even though it can	
		and returns its pointer	*/
struct vehicle_info *vehicles_not_moved_yet() {
	int position_check[7][7] = { 0, };

	struct vehicle_info_link *last_link;
	last_link = vehicles_list;
	while(last_link != NULL) {
		struct vehicle_info *vi = last_link->vi;
		if((vi->state == VEHICLE_STATUS_READY)||(vi->state == VEHICLE_STATUS_RUNNING)) {
			if((vi->position.row == vi->position_next.row)&&(vi->position.col == vi->position_next.col)) {
				/*	if vehicle didn't updated position_next, wait	*/
				cond_wait(vi->vehicle_position_next_update, vi->lock);
			}
		}
		/*	mark all vehicles into position_check	*/
		if((vi->state == VEHICLE_STATUS_RUNNING)||(vi->state == VEHICLE_STATUS_MOVED)) {
			position_check[vi->position.row][vi->position.col] = 1;
		}
		last_link = last_link->next;
	}
	last_link = vehicles_list;
	while(last_link != NULL) {
		struct vehicle_info *vi = last_link->vi;
		if(vehicle_before_crossroad(vi)) {
			if(position_check[vi->position_next.row][vi->position_next.col] == 0) {
				if(vehicle_at_crossroad_enterance(vi)) {
					if((inner_crossroad_sema->value) > 1) {
						/*	if vehicle's position_next is empty and
								if it's at the entance and could enter into inner crossroad,
								it could move but didn't */
						return vi;
					}
				} else {
					/* if vehicle's position_next is empty, it could move but didn't */
					return vi;
				}
			}
		}
		last_link = last_link->next;
	}

	return NULL;
}