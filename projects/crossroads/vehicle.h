#ifndef __PROJECTS_PROJECT1_VEHICLE_H__
#define __PROJECTS_PROJECT1_VEHICLE_H__

#include "projects/crossroads/position.h"

#define VEHICLE_STATUS_READY 	0
#define VEHICLE_STATUS_RUNNING	1
#define VEHICLE_STATUS_MOVED	3
#define VEHICLE_STATUS_FINISHED	2

struct vehicle_info {
	char id;
	char state;
	char start;
	char dest;
	struct position position;
	struct position position_next;
	struct lock *lock;
	struct lock **map_locks;
};
struct vehicle_info_link {
	struct vehicle_info *vi;
	struct vehicle_info_link *next;
};
struct vehicle_info_link *vehicles_list;

void vehicle_loop(void *vi);
void vehicles_list_drawn_signal();

struct condition *map_drawn;

#endif /* __PROJECTS_PROJECT1_VEHICLE_H__ */