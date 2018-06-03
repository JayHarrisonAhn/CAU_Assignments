//
//  main.c
//  DSAssignment
//
//  Created by Harrison on 2018. 6. 1..
//  Copyright © 2018년 Harrison. All rights reserved.
//

#include <stdio.h>
#include <stdlib.h>
#include "stack.h"
#include "node.h"

int main(int argc, const char * argv[]) {
    char input[6];
    
    char* stack = (char*) malloc(sizeof(char) * 0);
    int stackSize = 0;
    
    node* head = NULL;

    while(1) {
    FIRST:
        for(int i=0;i<5;i++) {
            input[i] = ' ';
        }
        printf(">>");
        scanf("%s", input);
        while(getchar() != '\n');

        if((input[0] == ' ')||(input[1] != '-')||(input[2] == ' ')||(input[3] != '-')||(input[4] == ' ')) {
            goto FIRST;
        }
        //정상적인 입력이 들어옴

        if(head==NULL) {//head가 없을 상황
            switch(input[2]) {
                case 'F':
                    head = initNode(input[0], NULL, NULL);
                    head->father = initNode(input[4], NULL, NULL);
                    break;
                case 'M':
                    head = initNode(input[0], NULL, NULL);
                    head->mother = initNode(input[4], NULL, NULL);
                    break;
            }
            printTree(head);
            goto FIRST;
        }

        if(head->name == input[4]) {    //head가 확장되는 상황
            switch(input[2]) {
                case 'F':
                    head = initNode(input[0], head, NULL);
                    break;
                case 'M':
                    head = initNode(input[0], NULL, head);
                    break;
            }
            printTree(head);
            goto FIRST;
        }

        switch(input[2]) {
            case 'F':
                findNode(input[0], head)->father = initNode(input[4], NULL, NULL);
                printTree(head);
                break;
            case 'M':
                findNode(input[0], head)->mother = initNode(input[4], NULL, NULL);
                printTree(head);
                break;
            case '?':
                printRelation(input[4], findNode(input[0], head), stack, &stackSize);
                free(stack);
                stack = (char*) malloc(sizeof(char) * 0);
                stackSize = 0;
                break;
            default:
                goto FIRST;
        }
    }
}




