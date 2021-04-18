#ifndef __PROJECTS_PROJECT1_MAPDATA_H__
#define __PROJECTS_PROJECT1_MAPDATA_H__

#include <stdio.h>
#include "projects/crossroads/position.h"


extern int crossroads_step;
extern int initialized;
extern int num_of_vehicles;

void map_draw(void);
void map_draw_vehicle(char id, int row, int col);
void map_draw_reset(void);

void move_vehicles_not_in_crossroad_and_wait();
struct vehicle_info *vehicles_not_moved_yet();

#endif /* __PROJECTS_PROJECT1_MAPDATA_H__ */