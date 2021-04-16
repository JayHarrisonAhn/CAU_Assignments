#ifndef __PROJECTS_PROJECT1_VEHICLE_H__
#define __PROJECTS_PROJECT1_VEHICLE_H__

#include "projects/crossroads/position.h"

#define VEHICLE_STATUS_READY 	0
#define VEHICLE_STATUS_RUNNING	1
#define VEHICLE_STATUS_FINISHED	2

struct vehicle_info {
	char id;
	char state;
	char start;
	char dest;
	struct position position;
	struct lock **map_locks;
};

void vehicle_loop(void *vi);

struct lock *is_map_drawing_lock;
struct condition *map_draw_start;
struct condition *map_drawn;
void wait_until_map_draw();
void set_map_draw();
void release_map_draw();

#endif /* __PROJECTS_PROJECT1_VEHICLE_H__ */