void stackPrint(char* stack, int* size) {
    for(int i=0;i<((*size)-1);i++) {
        printf("%c-", stack[i]);
    }
    printf("%c", stack[(*size)-1]);
    printf("\n");
}

void stackPush(char* stack, int* size, char character) {
    stack = (char*) realloc(stack, *size + 1);
    stack[*size] = character;
    (*size)++;
}

char stackPop(char* stack, int* size) {
    char result = stack[*size - 1];
    stack = (char*) realloc(stack, (*size) - 1);
    (*size)--;
    return result;
}
