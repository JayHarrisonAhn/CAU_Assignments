
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
	cond_broadcast(vi->vehicle_position_next_update, vi->lock);
	// printf("%d, %d\n", vi->position_next.row, vi->position_next.col);
	if (vi->state == VEHICLE_STATUS_RUNNING) {
		/* check termination */
		if (is_position_outside(pos_next)) {
			/* actual move */
			vi->position.row = vi->position.col = -1;
			/* release previous */
			lock_release(&vi->map_locks[pos_cur.row][pos_cur.col]);
			// printf("(아웃%c)", vi->id);
			cond_broadcast(vi->vehicle_move, vi->lock);
			vi->state = VEHICLE_STATUS_FINISHED;
			lock_release(vi->lock);
			return 0;
		}
	}

	while(true) {
		lock_release(vi->lock);
		if(vehicle_at_crossroad_enterance(vi)) {
			// printf("(%c%d)", vi->id, inner_crossroad_sema->value);
			sema_down(inner_crossroad_sema);
			// printf("(%c%d)", vi->id, inner_crossroad_sema->value);
		}
		lock_acquire(&vi->map_locks[pos_next.row][pos_next.col]);
		lock_acquire(vi->lock);
		if(vi->movable) {
			break;
		} else {
			lock_release(&vi->map_locks[pos_next.row][pos_next.col]);
			if(vehicle_at_crossroad_enterance(vi)) {
				sema_up(inner_crossroad_sema);
			}
			cond_wait(vi->became_movable, vi->lock);
		}
	}

	if (vi->state == VEHICLE_STATUS_READY) {
		/* start this vehicle */
		vi->state = VEHICLE_STATUS_RUNNING;
	} else {
		/* release current position */
		if(vehicle_at_crossroad_exit(vi)) {
			sema_up(inner_crossroad_sema);
		}
		lock_release(&vi->map_locks[pos_cur.row][pos_cur.col]);
	}
	/* update position */
	vi->position = pos_next;
	vi->state = VEHICLE_STATUS_MOVED;
	// printf("(릴리즈%c%d%d)", vi->id, vi->position.row, vi->position.col);
	cond_broadcast(vi->vehicle_move, vi->lock);
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

	vi->vehicle_move = malloc(sizeof(struct condition));
	cond_init(vi->vehicle_move);

	vi->vehicle_position_next_update = malloc(sizeof(struct condition));
	cond_init(vi->vehicle_position_next_update);

	vi->became_movable = malloc(sizeof(struct condition));
	cond_init(vi->became_movable);

	lock_acquire(vi->lock);
	start = vi->start - 'A';
	dest = vi->dest - 'A';
	step = 0;
	vi->position.row = vi->position.col = -1;
	vi->position_next = vehicle_path[start][dest][step];
	vi->state = VEHICLE_STATUS_READY;
	vi->movable = 1;
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
		// printf("(시작%c)", vi->id);
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
	}	

	/* status transition must happen before sema_up */
	// vi->state = VEHICLE_STATUS_FINISHED;
}



void vehicles_list_lock_acquire() {
	vehicles_list_lock_acquire_except(NULL);
}
void vehicles_list_lock_acquire_except(struct vehicle_info *except) {
	struct vehicle_info_link *last_link = vehicles_list;
	while(last_link != NULL) {
		struct vehicle_info *vi = last_link->vi;
		if(vi != except) {
			// printf("(락요청%c)", vi->id);
			lock_acquire(vi->lock);
			// printf("(락요청성공%c)", vi->id);
		}
		last_link = last_link->next;
	}
}
void vehicles_list_lock_release() {
	vehicles_list_lock_release_except(NULL);
}
void vehicles_list_lock_release_except(struct vehicle_info *except) {
	struct vehicle_info_link *last_link = vehicles_list;
	while(last_link != NULL) {
		struct vehicle_info *vi = last_link->vi;
		if(vi != except) {
			lock_release(vi->lock);
		}
		last_link = last_link->next;
	}
}

void vehicles_list_make_not_movable() {
	// printf("1");
	vehicles_list_lock_acquire();
	// printf("2");
	struct vehicle_info_link *last_link = vehicles_list;
	while(last_link != NULL) {
		struct vehicle_info *vi = last_link->vi;
		vi->movable = 0;
		last_link = last_link->next;
	}
	vehicles_list_lock_release();
}


int vehicle_in_crossroad(struct vehicle_info *vi) {
	if((vi->position.row == 2)&&(vi->position.col == 2)) { return 1; }
	else if((vi->position.row == 2)&&(vi->position.col == 3)) { return 1; }
	else if((vi->position.row == 2)&&(vi->position.col == 4)) { return 1; }
	else if((vi->position.row == 3)&&(vi->position.col == 2)) { return 1; }
	else if((vi->position.row == 3)&&(vi->position.col == 4)) { return 1; }
	else if((vi->position.row == 4)&&(vi->position.col == 2)) { return 1; }
	else if((vi->position.row == 4)&&(vi->position.col == 3)) { return 1; }
	else if((vi->position.row == 4)&&(vi->position.col == 4)) { return 1; }

	else if((vi->position.row == 2)&&(vi->position.col == 0)) { return 1; }
	else if((vi->position.row == 2)&&(vi->position.col == 1)) { return 1; }

	else if((vi->position.row == 5)&&(vi->position.col == 2)) { return 1; }
	else if((vi->position.row == 6)&&(vi->position.col == 2)) { return 1; }

	else if((vi->position.row == 4)&&(vi->position.col == 5)) { return 1; }
	else if((vi->position.row == 4)&&(vi->position.col == 6)) { return 1; }

	else if((vi->position.row == 0)&&(vi->position.col == 4)) { return 1; }
	else if((vi->position.row == 1)&&(vi->position.col == 4)) { return 1; }

	else { return 0; }
}
int vehicle_before_crossroad(struct vehicle_info *vi) {
	if((vi->position.row == 4)&&(vi->position.col == 0)) { return 1; }
	else if((vi->position.row == 4)&&(vi->position.col == 1)) { return 1; }

	else if((vi->position.row == 5)&&(vi->position.col == 4)) { return 1; }
	else if((vi->position.row == 6)&&(vi->position.col == 4)) { return 1; }

	else if((vi->position.row == 2)&&(vi->position.col == 5)) { return 1; }
	else if((vi->position.row == 2)&&(vi->position.col == 6)) { return 1; }

	else if((vi->position.row == 0)&&(vi->position.col == 2)) { return 1; }
	else if((vi->position.row == 1)&&(vi->position.col == 2)) { return 1; }

	else { return 0; }
}
int vehicle_at_crossroad_enterance(struct vehicle_info *vi) {
	if((vi->position.row == 4)&&(vi->position.col == 1)) { return 1; }
	else if((vi->position.row == 5)&&(vi->position.col == 4)) { return 1; }
	else if((vi->position.row == 2)&&(vi->position.col == 5)) { return 1; }
	else if((vi->position.row == 1)&&(vi->position.col == 2)) { return 1; }

	else { return 0; }
}
int vehicle_at_crossroad_exit(struct vehicle_info *vi) {
	if((vi->position.row == 2)&&(vi->position.col == 1)) { return 1; }
	else if((vi->position.row == 5)&&(vi->position.col == 2)) { return 1; }
	else if((vi->position.row == 4)&&(vi->position.col == 5)) { return 1; }
	else if((vi->position.row == 1)&&(vi->position.col == 4)) { return 1; }

	else { return 0; }
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
