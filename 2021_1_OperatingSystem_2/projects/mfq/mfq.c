#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "threads/init.h"
#include "threads/malloc.h"
#include "threads/synch.h"
#include "threads/thread.h"
#include "threads/interrupt.h"

#include "devices/timer.h"

#include "projects/mfq/mfq.h"


void test_loop(void *aux)
{
    int i;
    for(i=0; i<10000; i++) {
        struct thread *t = thread_current();
        for(int j=0; j<10000000; j++) {
        }
        // timer_msleep(300);
        printf("-%s: loop %d-\n", t->name, i);
    }
}

void run_mfqtest(char **argv)
{   
    int cnt;
	char *token, *save_ptr;

    enum intr_level old_level;
    old_level = intr_disable ();

    /// TODO: make your own test
	cnt = 0;
	for (token = strtok_r (argv[1], ":", &save_ptr); token != NULL; 
		token = strtok_r (NULL, ":", &save_ptr)) {

        char *subtoken, *subtoken2, *save_ptr2;
        subtoken = strtok_r (token, ".", &save_ptr2);
        printf("thread name: %s\n", &subtoken[1]);
        subtoken2 = strtok_r (NULL, ".", &save_ptr2);
        printf("priority: %d\n", atoi(subtoken2));

        // you can create threads here 
        thread_create(&subtoken[1], atoi(subtoken2), test_loop, NULL);

		cnt++;
	}
    intr_set_level (old_level);

    while (1) {
        timer_msleep(1000);
    }
}