#ifndef __PROJECTS_PROJECT1_VEHICLE_H__
#define __PROJECTS_PROJECT1_VEHICLE_H__

#include "projects/crossroads/position.h"

#define VEHICLE_STATUS_READY 	0
#define VEHICLE_STATUS_RUNNING	1
#define VEHICLE_STATUS_WAITING	2
#define VEHICLE_STATUS_MOVED	3
#define VEHICLE_STATUS_FINISHED	4

struct vehicle_info {
	char id;
	char state;
	char start;
	char dest;
	struct position position;
	struct position position_next;
	struct lock *lock;
	struct condition *vehicle_move;
	struct lock **map_locks;
};
struct vehicle_info_link {
	struct vehicle_info *vi;
	struct vehicle_info_link *next;
};
struct vehicle_info_link *vehicles_list;

void vehicle_loop(void *vi);
void vehicles_list_drawn_signal();
void vehicles_list_lock_acquire();
void vehicles_list_lock_acquire_except(struct vehicle_info *except);
void vehicles_list_lock_release();
void vehicles_list_lock_release_except(struct vehicle_info *except);
struct vehicle_info *vehicles_not_moved_yet();

struct condition *map_drawn;

#endif /* __PROJECTS_PROJECT1_VEHICLE_H__ */