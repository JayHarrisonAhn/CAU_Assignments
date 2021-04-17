
#include <stdio.h>

#include "threads/thread.h"
#include "devices/timer.h"
#include "threads/synch.h"
#include "projects/crossroads/vehicle.h"
#include "projects/crossroads/map.h"


/* path. A:0 B:1 C:2 D:3 */
const struct position vehicle_path[4][4][10] = {
	/* from A */ {
		/* to A */
		{{-1,-1},},
		/* to B */
		{{4,0},{4,1},{4,2},{5,2},{6,2},{-1,-1},},
		/* to C */
		{{4,0},{4,1},{4,2},{4,3},{4,4},{4,5},{4,6},{-1,-1},},
		/* to D */
		{{4,0},{4,1},{4,2},{4,3},{4,4},{3,4},{2,4},{1,4},{0,4},{-1,-1}}
	},
	/* from B */ {
		/* to A */
		{{6,4},{5,4},{4,4},{3,4},{2,4},{2,3},{2,2},{2,1},{2,0},{-1,-1}},
		/* to B */
		{{-1,-1},},
		/* to C */
		{{6,4},{5,4},{4,4},{4,5},{4,6},{-1,-1},},
		/* to D */
		{{6,4},{5,4},{4,4},{3,4},{2,4},{1,4},{0,4},{-1,-1},}
	},
	/* from C */ {
		/* to A */
		{{2,6},{2,5},{2,4},{2,3},{2,2},{2,1},{2,0},{-1,-1},},
		/* to B */
		{{2,6},{2,5},{2,4},{2,3},{2,2},{3,2},{4,2},{5,2},{6,2},{-1,-1}},
		/* to C */
		{{-1,-1},},
		/* to D */
		{{2,6},{2,5},{2,4},{1,4},{0,4},{-1,-1},}
	},
	/* from D */ {
		/* to A */
		{{0,2},{1,2},{2,2},{2,1},{2,0},{-1,-1},},
		/* to B */
		{{0,2},{1,2},{2,2},{3,2},{4,2},{5,2},{6,2},{-1,-1},},
		/* to C */
		{{0,2},{1,2},{2,2},{3,2},{4,2},{4,3},{4,4},{4,5},{4,6},{-1,-1}},
		/* to D */
		{{-1,-1},}
	}
};

static int is_position_outside(struct position pos)
{
	return (pos.row == -1 || pos.col == -1);
}

/* return 0:termination, 1:success, -1:fail */
static int try_move(int start, int dest, int step, struct vehicle_info *vi)
{
	struct position pos_cur, pos_next;

	pos_next = vehicle_path[start][dest][step];
	pos_cur = vi->position;

	lock_acquire(vi->lock);
	vi->position_next = pos_next;
	// printf("%d, %d\n", vi->position_next.row, vi->position_next.col);
	if (vi->state == VEHICLE_STATUS_RUNNING) {
		/* check termination */
		if (is_position_outside(pos_next)) {
			/* actual move */
			vi->position.row = vi->position.col = -1;
			/* release previous */
			lock_release(&vi->map_locks[pos_cur.row][pos_cur.col]);
			lock_release(vi->lock);
			return 0;
		}
	}
	lock_release(vi->lock);

	/* lock next position */
	lock_acquire(&vi->map_locks[pos_next.row][pos_next.col]);
	lock_acquire(vi->lock);
	if (vi->state == VEHICLE_STATUS_READY) {
		/* start this vehicle */
		vi->state = VEHICLE_STATUS_RUNNING;
	} else {
		/* release current position */
		lock_release(&vi->map_locks[pos_cur.row][pos_cur.col]);
	}
	/* update position */
	vi->position = pos_next;
	vi->state = VEHICLE_STATUS_MOVED;
	lock_release(vi->lock);
	
	return 1;
}

void vehicle_loop(void *_vi)
{
	int res;
	int start, dest, step;

	struct vehicle_info *vi = _vi;

	vi->lock = malloc(sizeof(struct lock));
	lock_init(vi->lock);

	lock_acquire(vi->lock);
	start = vi->start - 'A';
	dest = vi->dest - 'A';
	step = 0;
	vi->position.row = vi->position.col = -1;
	vi->position_next = vehicle_path[start][dest][step];
	vi->state = VEHICLE_STATUS_READY;
	vehicles_list_append(vi);
	lock_release(vi->lock);

	/* busy wait until initizlize */
	while((!num_of_vehicles)) {
		/* 아무것도 없이 busy wait하면 thread가 죽는 현상 때문에 timer 설정 */
		timer_msleep(1);
	}

	while (1) {
		lock_acquire(vi->lock);
		while (vi->state == VEHICLE_STATUS_MOVED) {
			cond_wait(map_drawn, vi->lock);
		}
		lock_release(vi->lock);

		/* vehicle main code */
		res = try_move(start, dest, step, vi);
		if (res == 1) {
			step++;
		}

		/* termination condition. */ 
		if (res == 0) {
			break;
		}
		// timer_msleep(1000);

	}	

	/* status transition must happen before sema_up */
	vi->state = VEHICLE_STATUS_FINISHED;
}

/* signal all vehicles that map is drawn */
void vehicles_list_drawn_signal() {
	struct vehicle_info_link *last_link = vehicles_list;
	while(last_link != NULL) {
		struct vehicle_info *vi = last_link->vi;
		lock_acquire(vi->lock);
		if(vi->state == VEHICLE_STATUS_MOVED) {
			vi->state = VEHICLE_STATUS_RUNNING;
		}
		cond_broadcast(map_drawn, vi->lock);
		lock_release(vi->lock);
		last_link = last_link->next;
	}
}

/* returns the number of elements in vehicles_list */
int vehicles_list_all_moved() {
	if(!vehicles_list) {
		return 1;
	}
	struct vehicle_info_link *last_link;
	int position_check[7][7] = { 0, };
	int result = 1;

	last_link = vehicles_list;
	while(last_link != NULL) {
		struct vehicle_info *vi = last_link->vi;
		lock_acquire(vi->lock);
		if(vi->state == VEHICLE_STATUS_MOVED) {
			position_check[vi->position.row][vi->position.col] = 1;
		}
		last_link = last_link->next;
	}

	last_link = vehicles_list;
	while(last_link != NULL) {
		struct vehicle_info *vi = last_link->vi;
		if((vi->state == VEHICLE_STATUS_RUNNING) || (vi->state == VEHICLE_STATUS_READY)) {
			if(position_check[vi->position_next.row][vi->position_next.col] == 0) {
				result = 0;
				break;
			}
		}
		last_link = last_link->next;
	}

	last_link = vehicles_list;
	while(last_link != NULL) {
		struct vehicle_info *vi = last_link->vi;
		lock_release(vi->lock);
		last_link = last_link->next;
	}

	return result;
}

/* returns the number of elements in vehicles_list */
int vehicles_list_count() {
	struct vehicle_info_link *last_link = vehicles_list;
	int result = 0;
	while(last_link != NULL) {
		result += 1;
		last_link = last_link->next;
	}
	return result;
}

/* returns last value's pointer of vehicles_list */
struct vehicle_info_link *vehicles_list_last() {
	if(!vehicles_list) {
		return NULL;
	}
	struct vehicle_info_link *last_link = vehicles_list;
	while(last_link->next != NULL) {
		last_link = last_link->next;
	}
	return last_link;
}

/* append vi into vehicles_list */
void vehicles_list_append(struct vehicle_info *vi) {
	struct vehicle_info_link *vi_list;
	vi_list = malloc(sizeof(struct vehicle_info_link));
	
	vi_list->vi = vi;
	vi_list->next = NULL;

	if(vehicles_list_last() == NULL) {
		vehicles_list = vi_list;
	} else {
		vehicles_list_last()->next = vi_list;
	}
}
